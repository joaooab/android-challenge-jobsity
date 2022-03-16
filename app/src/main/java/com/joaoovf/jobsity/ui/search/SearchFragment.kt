package com.joaoovf.jobsity.ui.search

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.joaoovf.jobsity.R
import com.joaoovf.jobsity.databinding.FragmentSearchBinding
import com.joaoovf.jobsity.domain.State
import com.joaoovf.jobsity.domain.base.BaseFragment
import com.joaoovf.jobsity.domain.extension.*
import com.joaoovf.jobsity.ui.ComponentViewModel
import com.joaoovf.jobsity.ui.Components
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

	private val componentViewModel: ComponentViewModel by sharedViewModel()
	private val viewModel: SearchViewModel by viewModel()
	private val adapter = SearchAdapter {
		findNavController().navigate(
			SearchFragmentDirections.actionNavigationSearchToNavigationDetail(it.id)
		)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		componentViewModel.withComponents = Components(toolbar = false)
		setupView()
	}

	private fun setupView() {
		binding.list.adapter = adapter
		binding.includeSearch.searchView.apply {
			setQuery(viewModel.getCurrentQuery(), false)
		}
		collectSearchQuery()
		collectData()
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

	private fun collectData() = safeFlowCollect {
		viewModel.dataFlow.collectLatest { state ->
			when (state) {
				is State.Loading -> {
					binding.loading.show()
					binding.list.gone()
					binding.includeEmpty.emptyView.gone()
				}
				is State.Success -> {
					adapter.submitList(state.data)
					binding.loading.gone()
					binding.list.setVisible(!state.data.isNullOrEmpty())
					binding.includeEmpty.emptyView.setVisible(state.data.isNullOrEmpty())
				}
				is State.Error -> {
					showError()
					binding.loading.gone()
					binding.list.gone()
					binding.includeEmpty.emptyView.show()
				}
			}
		}
	}

	private fun showError() {
		Snackbar
			.make(
				requireView(),
				getString(R.string.loading_shows_error),
				Snackbar.LENGTH_SHORT
			)
			.show()
	}

}
