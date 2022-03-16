package com.joaoovf.jobsity.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
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
	private lateinit var menu: Menu
	private val genreAdapter = DetailGenreAdapter()
	private val episodeAdapter = DetailEpisodeAdapter {
		findNavController().navigate(
			DetailFragmentDirections.actionNavigationDetailToDetailEpisodeFragment(it.id, it.name)
		)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		this.menu = menu
		inflater.inflate(R.menu.favorite_menu, menu)
		super.onCreateOptionsMenu(menu, inflater)
		updateFavoriteMenu(viewModel.favoriteState.value)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if (item.itemId == R.id.menuFavotite) {
			viewModel.inputAction.invoke(DetailAction.Favorite)
			showFavoriteMessage(!viewModel.favoriteState.value)
		}
		return super.onOptionsItemSelected(item)
	}

	private fun showFavoriteMessage(isFavorite: Boolean) {
		if (isFavorite) {
			showSuccessMesage(R.string.message_favorite_selected)
		} else {
			showSuccessMesage(R.string.message_favorite_unselected)
		}
	}

	override fun onStart() {
		super.onStart()
		viewModel.inputAction.invoke(DetailAction.Fetch(arguments.showId))
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
		collectShowState()
		collectEpisodesState()
		collectFavoriteState()
	}

	private fun FragmentDetailBinding.openSelectSeasonDialog() {
		val seasons = viewModel.getAllSeasons()
		SelectSeasonDialog.newInstance(seasons) {
			viewModel.inputAction.invoke(DetailAction.ChangeSeason(it))
		}.show(childFragmentManager, this.javaClass.simpleName)
	}

	private fun collectShowState() = safeFlowCollect {
		viewModel.showState.collectLatest { state ->
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
						imageDetail.loadImage(data.image.medium)
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

	private fun collectEpisodesState() = safeFlowCollect {
		viewModel.seasonWithEpisodesState.collectLatest { state ->
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

	private fun collectFavoriteState() = safeFlowCollect {
		viewModel.favoriteState.collect { isFavorite ->
			updateFavoriteMenu(isFavorite)
		}
	}

	private fun updateFavoriteMenu(isFavorite: Boolean) {
		runCatching {
			menu.getItem(0).let { menuItem ->
				if (isFavorite) {
					menuItem.setIcon(
						ContextCompat.getDrawable(
							requireContext(),
							R.drawable.ic_baseline_star_rate_24
						)
					)
				} else {
					menuItem.setIcon(
						ContextCompat.getDrawable(
							requireContext(),
							R.drawable.ic_baseline_star_outline_24
						)
					)
				}
			}
		}
	}

	private fun showSuccessMesage(@StringRes message: Int) {
		Snackbar
			.make(
				requireView(),
				getString(message),
				Snackbar.LENGTH_SHORT
			)
			.show()
	}

}