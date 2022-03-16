package com.joaoovf.jobsity.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.navArgs
import com.joaoovf.jobsity.R
import com.joaoovf.jobsity.databinding.FragmentEpisodeDetailBinding
import com.joaoovf.jobsity.domain.State
import com.joaoovf.jobsity.domain.base.BaseFragment
import com.joaoovf.jobsity.domain.extension.gone
import com.joaoovf.jobsity.domain.extension.loadImage
import com.joaoovf.jobsity.domain.extension.safeFlowCollect
import com.joaoovf.jobsity.domain.extension.show
import com.joaoovf.jobsity.ui.ComponentViewModel
import com.joaoovf.jobsity.ui.Components
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailEpisodeFragment :
	BaseFragment<FragmentEpisodeDetailBinding>(FragmentEpisodeDetailBinding::inflate) {

	private val componentViewModel: ComponentViewModel by sharedViewModel()
	private val viewModel: DetailEpisodeViewModel by viewModel()
	private val arguments by navArgs<DetailEpisodeFragmentArgs>()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		componentViewModel.withComponents = Components(toolbar = true)
		setupView()
	}

	private fun setupView() {
		collectEpisode()
		viewModel.inputAction.invoke(DetailEpisodeAction.Fetch(arguments.episodeId))
	}

	private fun collectEpisode() = safeFlowCollect {
		viewModel.episodeStateFlow.collectLatest { state ->
			when (state) {
				is State.Loading -> {
					binding.apply {
						loading.show()
					}
				}
				is State.Success -> {
					binding.loading.gone()
					binding.apply {
						val data = state.data
						imageDetail.loadImage(data.image?.medium)
						textNameDetail.text = data.name
						textSeason.text = getString(R.string.season_number, data.season)
						textEpisode.text = getString(R.string.episode_number, data.number)
						textSummary.text = HtmlCompat.fromHtml(
							data.summary,
							HtmlCompat.FROM_HTML_MODE_COMPACT
						)
						root.show()
					}
				}
				is State.Error -> {
					binding.apply {
						loading.gone()
					}
				}
			}
		}
	}
}