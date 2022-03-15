package com.joaoovf.jobsity.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import com.joaoovf.jobsity.R
import com.joaoovf.jobsity.databinding.FragmentHomeBinding
import com.joaoovf.jobsity.domain.extension.gone
import com.joaoovf.jobsity.domain.extension.safeFlowCollect
import com.joaoovf.jobsity.domain.extension.show
import com.joaoovf.jobsity.ui.ComponentViewModel
import com.joaoovf.jobsity.ui.Components
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

	private var _binding: FragmentHomeBinding? = null

	private val binding get() = _binding!!
	private val componentViewModel: ComponentViewModel by sharedViewModel()
	private val viewModel: HomeViewModel by viewModel()
	private val adapter = HomeAdapter()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentHomeBinding.inflate(inflater, container, false)
		val root: View = binding.root
		componentViewModel.withComponents = Components(toolbar = true)
		setupView()
		return root
	}

	private fun setupView() {
		binding.showList.adapter = adapter
		collectStateAdapter()
		collectPagingData()
	}

	private fun collectStateAdapter() = safeFlowCollect {
		adapter.loadStateFlow.collectLatest { state ->
			when (state.source.refresh) {
				is LoadState.Loading -> {
					binding.showLoading.show()
					binding.showList.gone()
				}
				is LoadState.Error -> {
					binding.showLoading.gone()
					binding.showList.show()
					showLoadingStateError()
				}
				else -> {
					binding.showLoading.gone()
					binding.showList.show()
				}
			}
		}
	}

	private fun collectPagingData() = safeFlowCollect {
		viewModel.pagingDataFlow.collectLatest {
			adapter.submitData(it)
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

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

}
