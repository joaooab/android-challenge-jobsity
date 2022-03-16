package com.joaoovf.jobsity.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoovf.jobsity.domain.State
import com.joaoovf.jobsity.domain.interactor.LoadEpisodeUseCase
import com.joaoovf.jobsity.domain.model.Episode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailEpisodeViewModel(
	private val loadEpisodeUseCase: LoadEpisodeUseCase,
	dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

	private val _episodeStateFlow = MutableStateFlow<State<Episode>>(State.Loading())
	val episodeStateFlow: StateFlow<State<Episode>> = _episodeStateFlow

	val inputAction: (DetailEpisodeAction) -> Unit

	init {
		val detailAction = MutableSharedFlow<DetailEpisodeAction>(replay = 1)

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

	private suspend fun handleWithAction(action: DetailEpisodeAction) {
		when (action) {
			is DetailEpisodeAction.Fetch -> handleWithFetch(action.episodeId)
		}
	}

	private suspend fun handleWithFetch(showId: Int) {
		fetchEpisode(showId)
	}

	private suspend fun fetchEpisode(episodeId: Int) = runCatching {
		loadEpisodeUseCase(episodeId) ?: throw NoSuchElementException()
	}.onSuccess {
		_episodeStateFlow.value = State.Success(it)
	}.onFailure {
		_episodeStateFlow
	}

}

sealed class DetailEpisodeAction {
	class Fetch(val episodeId: Int) : DetailEpisodeAction()
}

