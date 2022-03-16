package com.joaoovf.jobsity.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoovf.jobsity.domain.base.State
import com.joaoovf.jobsity.domain.interactor.FavoriteShowUseCase
import com.joaoovf.jobsity.domain.interactor.LoadFavoriteShowByIdUseCase
import com.joaoovf.jobsity.domain.interactor.LoadSeasonsWithEpisodesUseCase
import com.joaoovf.jobsity.domain.interactor.LoadShowUseCase
import com.joaoovf.jobsity.domain.model.SeasonWithEpisodes
import com.joaoovf.jobsity.domain.model.Show
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailViewModel(
	private val loadShowUseCase: LoadShowUseCase,
	private val loadSeasonsWithEpisodesUseCase: LoadSeasonsWithEpisodesUseCase,
	private val favoriteShowUseCase: FavoriteShowUseCase,
	private val loadFavoriteShowByIdUseCase: LoadFavoriteShowByIdUseCase,
	private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

	private val _showState = MutableStateFlow<State<Show>>(State.Loading())
	val showState: StateFlow<State<Show>> = _showState

	private val seasonWithEpisodes = mutableListOf<SeasonWithEpisodes>()
	private val _seasonWithEpisodesState = MutableStateFlow<State<SeasonWithEpisodes>>(State.Loading())
	val seasonWithEpisodesState: StateFlow<State<SeasonWithEpisodes>> = _seasonWithEpisodesState

	private val _favoriteState = MutableStateFlow(false)
	val favoriteState: StateFlow<Boolean> = _favoriteState

	val inputAction: (DetailAction) -> Unit

	init {
		val detailAction = MutableSharedFlow<DetailAction>(replay = 1)

		inputAction = { action ->
			viewModelScope.launch {
				detailAction.emit(action)
			}
		}

		viewModelScope.launch(dispatcher) {
			detailAction.collectLatest {
				handleWithAction(it)
			}
		}
	}

	private suspend fun handleWithAction(action: DetailAction) {
		when (action) {
			is DetailAction.Fetch -> handleWithFetch(action.showId)
			is DetailAction.ChangeSeason -> handleWithChangeSeason(action.season)
			is DetailAction.Favorite -> handleWithFavorite()
		}
	}

	private suspend fun handleWithFetch(showId: Int) {
		fetchShow(showId)
		fetchEpisodes(showId)
		fetchFavorite(showId)
	}

	private suspend fun fetchShow(showId: Int) = runCatching {
		loadShowUseCase(showId) ?: throw NoSuchElementException()
	}.onSuccess {
		_showState.value = State.Success(it)
	}.onFailure {
		_showState.value = State.Error(it)
	}

	private suspend fun fetchEpisodes(showId: Int) = runCatching {
		loadSeasonsWithEpisodesUseCase(showId)
	}.onSuccess {
		if (it.isEmpty()) throw NoSuchElementException()
		seasonWithEpisodes.addAll(it)
		_seasonWithEpisodesState.emit(State.Success(it.first()))
	}.onFailure {
		_seasonWithEpisodesState.value = State.Error(it)
	}

	private suspend fun fetchFavorite(showId: Int) = runCatching {
		loadFavoriteShowByIdUseCase(showId)
	}.onSuccess {
		_favoriteState.value = it != null
	}.onFailure {
		_favoriteState.value = false
	}

	private fun handleWithChangeSeason(season: Int) {
		viewModelScope.launch(dispatcher) {
			seasonWithEpisodes.find { it.season == season }?.let {
				_seasonWithEpisodesState.emit(State.Success(it))
			}
		}
	}

	private fun handleWithFavorite() {
		viewModelScope.launch(dispatcher) {
			val state = showState.value
			if (state is State.Success) {
				_favoriteState.value = favoriteShowUseCase(state.data)
			}
		}
	}

	fun getAllSeasons() = seasonWithEpisodes.map { it.season }

}

sealed class DetailAction {
	class Fetch(val showId: Int) : DetailAction()
	class ChangeSeason(val season: Int) : DetailAction()
	object Favorite : DetailAction()
}

