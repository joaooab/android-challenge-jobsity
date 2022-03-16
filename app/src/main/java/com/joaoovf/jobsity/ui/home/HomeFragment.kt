package com.joaoovf.jobsity.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import com.joaoovf.jobsity.R
import com.joaoovf.jobsity.databinding.FragmentHomeBinding
import com.joaoovf.jobsity.domain.base.BaseFragment
import com.joaoovf.jobsity.domain.extension.collectInput
import com.joaoovf.jobsity.domain.extension.gone
import com.joaoovf.jobsity.domain.extension.safeFlowCollect
import com.joaoovf.jobsity.domain.extension.show
import com.joaoovf.jobsity.ui.ComponentViewModel
import com.joaoovf.jobsity.ui.Components
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

	private val componentViewModel: ComponentViewModel by sharedViewModel()
	private val viewModel: HomeViewModel by viewModel()
	private val adapter = HomeAdapter {
		findNavController().navigate(
			HomeFragmentDirections.actionNavigationHomeToNavigationDetail(it.id)
		)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		componentViewModel.withComponents = Components(toolbar = true)
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
		viewModel.pagingDataFlow.collectLatest {
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
				getString(R.string.loading_shows_error),
				Snackbar.LENGTH_INDEFINITE
			)
			.setAction(R.string.retry) { adapter.retry() }
			.show()
	}

}
