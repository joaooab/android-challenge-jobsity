package com.joaoovf.jobsity.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoovf.jobsity.domain.State
import com.joaoovf.jobsity.domain.interactor.SearchShowsUseCase
import com.joaoovf.jobsity.domain.model.Show
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel(searchShowsUseCase: SearchShowsUseCase) : ViewModel() {

	val dataFlow: Flow<State<List<Show>>>

	val stateAction: StateFlow<SearchAction>
	val inputAction: (SearchAction) -> Unit

	init {
		val searchAction = MutableSharedFlow<SearchAction>()

		stateAction = searchAction.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
			initialValue = SearchAction.Holding
		)

		dataFlow = searchAction
			.filterIsInstance<SearchAction.Query>()
			.flatMapLatest {
				searchShowsUseCase(it.term)
			}

		inputAction = { action ->
			viewModelScope.launch {
				searchAction.emit(action)
			}
		}
	}

	fun getCurrentQuery(): String? {
		val action = stateAction.value
		return if (action is SearchAction.Query)
			action.term
		else null
	}

}

sealed class SearchAction {
	object Holding : SearchAction()
	class Query(val term: String?) : SearchAction()
}

