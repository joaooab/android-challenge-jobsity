package com.joaoovf.jobsity.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.joaoovf.jobsity.databinding.FragmentFavoritesBinding
import com.joaoovf.jobsity.domain.base.BaseFragment
import com.joaoovf.jobsity.domain.extension.gone
import com.joaoovf.jobsity.domain.extension.safeFlowCollect
import com.joaoovf.jobsity.domain.extension.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

	private val viewModel: FavoriteViewModel by viewModel()
	private val adapter = FavoriteAdapter {
		findNavController().navigate(
			FavoriteFragmentDirections.actionNavigationFavoritesToNavigationDetail(it.id, it.name)
		)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupView()
	}

	private fun setupView() {
		binding.showList.adapter = adapter
		collectFavorites()
	}

	private fun collectFavorites() = safeFlowCollect {
		viewModel.favoritesFlow.collect {
			if (it.isEmpty()) {
				binding.includeEmpty.root.show()
				binding.showList.gone()
			} else {
				adapter.submitList(it)
				binding.showList.show()
				binding.includeEmpty.root.gone()
			}
		}
	}

}