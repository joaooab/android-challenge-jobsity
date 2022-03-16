package com.joaoovf.jobsity.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.navArgs
import com.joaoovf.jobsity.R
import com.joaoovf.jobsity.databinding.FragmentDetailBinding
import com.joaoovf.jobsity.domain.State
import com.joaoovf.jobsity.domain.base.BaseFragment
import com.joaoovf.jobsity.domain.extension.*
import com.joaoovf.jobsity.ui.ComponentViewModel
import com.joaoovf.jobsity.ui.Components
import com.joaoovf.jobsity.ui.detail.dialog.SelectSeasonDialog
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

	private val componentViewModel: ComponentViewModel by sharedViewModel()
	private val viewModel: DetailViewModel by viewModel()
	private val arguments by navArgs<DetailFragmentArgs>()
	private val genreAdapter = DetailGenreAdapter()
	private val episodeAdapter = DetailEpisodeAdapter {

	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		componentViewModel.withComponents = Components(toolbar = true)
		setupView()
	}

	private fun setupView() {
		binding.apply {
			includeShowDetail.listGenres.adapter = genreAdapter
			includeEpisodesDetail.listEpisodes.adapter = episodeAdapter
			includeEpisodesDetail.textSeason.setOnClickListener {
				openSelectSeasonDialog()
			}
		}
		collectShow()
		collectEpisodes()
		viewModel.inputAction.invoke(DetailAction.Fetch(arguments.showId))
	}

	private fun FragmentDetailBinding.openSelectSeasonDialog() {
		val seasons = viewModel.getAllSeasons()
		SelectSeasonDialog.newInstance(seasons) {
			viewModel.inputAction.invoke(DetailAction.ChangeSeason(it))
		}.show(childFragmentManager, this.javaClass.simpleName)
	}

	private fun collectShow() = safeFlowCollect {
		viewModel.showStateFlow.collectLatest { state ->
			when (state) {
				is State.Loading -> {
					binding.apply {
						loading.show()
						includeShowDetail.root.hide()
					}
				}
				is State.Success -> {
					binding.loading.gone()
					binding.includeShowDetail.apply {
						val data = state.data
						imageDetail.loadImage(data.image?.medium)
						textNameDetail.text = data.name
						textSchedule.text = data.schedule.format()
						genreAdapter.submitList(data.genres)
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
						includeShowDetail.root.show()
					}
				}
			}
		}
	}

	private fun collectEpisodes() = safeFlowCollect {
		viewModel.seasonWithEpisodesStateFlow.collectLatest { state ->
			when (state) {
				is State.Loading -> {
					binding.apply {
						loadingEpisodes.show()
						includeEpisodesDetail.root.gone()
					}
				}
				is State.Success -> {
					binding.loadingEpisodes.gone()
					binding.includeEpisodesDetail.apply {
						val data = state.data
						textSeason.text = getString(R.string.season_number, data.season)
						listEpisodes.adapter = episodeAdapter
						episodeAdapter.submitList(data.episodes)
						root.show()
					}
				}
				is State.Error -> {
					binding.apply {
						loadingEpisodes.show()
						includeEpisodesDetail.root.show()
					}
				}
			}
		}
	}

}