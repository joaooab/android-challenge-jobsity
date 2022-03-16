package com.joaoovf.jobsity.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoovf.jobsity.domain.State
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
	private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

	private val _showStateFlow = MutableStateFlow<State<Show>>(State.Loading())
	val showStateFlow: StateFlow<State<Show>> = _showStateFlow

	private val seasonWithEpisodes = mutableListOf<SeasonWithEpisodes>()
	private val _seasonWithEpisodesStateFlow = MutableStateFlow<State<SeasonWithEpisodes>>(State.Loading())
	val seasonWithEpisodesStateFlow: StateFlow<State<SeasonWithEpisodes>> = _seasonWithEpisodesStateFlow

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
		}
	}

	private suspend fun handleWithFetch(showId: Int) {
		fetchShow(showId)
		fetchEpisodes(showId)
	}

	private suspend fun fetchShow(showId: Int) = runCatching {
		loadShowUseCase(showId) ?: throw NoSuchElementException()
	}.onSuccess {
		_showStateFlow.value = State.Success(it)
	}.onFailure {
		_showStateFlow.value = State.Error(it)
	}

	private suspend fun fetchEpisodes(showId: Int) = runCatching {
		loadSeasonsWithEpisodesUseCase(showId)
	}.onSuccess {
		if (it.isEmpty()) throw NoSuchElementException()
		seasonWithEpisodes.addAll(it)
		_seasonWithEpisodesStateFlow.emit(State.Success(it.first()))
	}.onFailure {
		_seasonWithEpisodesStateFlow.value = State.Error(it)
	}

	private fun handleWithChangeSeason(season: Int) {
		viewModelScope.launch(dispatcher) {
			seasonWithEpisodes.find { it.season == season }?.let {
				_seasonWithEpisodesStateFlow.emit(State.Success(it))
			}
		}
	}

	fun getAllSeasons() = seasonWithEpisodes.map { it.season }

}

sealed class DetailAction {
	class Fetch(val showId: Int) : DetailAction()
	class ChangeSeason(val season: Int) : DetailAction()
}

