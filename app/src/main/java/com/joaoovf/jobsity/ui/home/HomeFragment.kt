package com.joaoovf.jobsity.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import com.joaoovf.jobsity.R
import com.joaoovf.jobsity.domain.base.BaseFragment
import com.joaoovf.jobsity.databinding.FragmentHomeBinding
import com.joaoovf.jobsity.domain.extension.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

	private val viewModel: HomeViewModel by viewModel()
	private val adapter = HomeAdapter {
		findNavController().navigate(
			HomeFragmentDirections.actionNavigationHomeToNavigationDetail(it.id, it.name)
		)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		inflater.inflate(R.menu.search_menu, menu)
		super.onCreateOptionsMenu(menu, inflater)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if (item.itemId == R.id.menuSearch) {
			binding.includeSearch.root.apply {
				setVisible(!this.isVisible)
			}
		}
		return super.onOptionsItemSelected(item)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupView()
	}

	private fun setupView() {
		binding.showList.adapter = adapter
		binding.includeSearch.searchView.apply {
			setQuery(viewModel.getCurrentQuery(), false)
		}
		collectPagingData()
		collectStateAdapter()
		collectSearchQuery()
	}

	private fun collectStateAdapter() = safeFlowCollect {
		adapter.loadStateFlow.collectLatest { state ->
			when (state.source.refresh) {
				is LoadState.Loading -> {
					binding.apply {
						showLoading.show()
						showList.gone()
						includeEmpty.root.gone()
					}
				}
				is LoadState.Error -> {
					binding.apply {
						showLoading.gone()
						showList(state)
					}
					showLoadingStateError()
				}
				else -> {
					binding.apply {
						showLoading.gone()
						showList(state)
					}
				}
			}
		}
	}

	private fun FragmentHomeBinding.showList(state: CombinedLoadStates) {
		if (isEmptyResult(state)) {
			showList.gone()
			includeEmpty.root.show()
		} else {
			showList.show()
			includeEmpty.root.gone()
		}
	}

	private fun isEmptyResult(state: CombinedLoadStates) =
		state.refresh is LoadState.NotLoading && adapter.itemCount == 0

	private fun collectPagingData() = safeFlowCollect {
		viewModel.pagingData.collectLatest {
			adapter.submitData(it)
		}
	}

	private fun collectSearchQuery() = safeFlowCollect {
		binding.includeSearch.searchView.collectInput()
			.debounce(300)
			.distinctUntilChanged()
			.filterNotNull()
			.collectLatest {
				viewModel.inputAction.invoke(SearchAction.Query(it))
			}
	}

	private fun showLoadingStateError() {
		Snackbar
			.make(
				requireView(),
				getString(R.string.message_shows_error),
				Snackbar.LENGTH_INDEFINITE
			)
			.setAction(R.string.retry) { adapter.retry() }
			.show()
	}

}
