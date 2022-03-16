package com.joaoovf.jobsity.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.joaoovf.jobsity.domain.interactor.LoadShowsPagedUseCase
import com.joaoovf.jobsity.domain.model.Show
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
	loadShowsPagedUseCase: LoadShowsPagedUseCase,
	dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

	private val stateAction: StateFlow<SearchAction>
	val pagingData: Flow<PagingData<Show>>
	val inputAction: (SearchAction) -> Unit

	init {
		val searchAction = MutableSharedFlow<SearchAction>()

		stateAction = searchAction.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
			initialValue = SearchAction.Query(null)
		)

		pagingData = stateAction
			.filterIsInstance<SearchAction.Query>()
			.flatMapLatest {
				loadShowsPagedUseCase(it.term).flow
			}.flowOn(dispatcher).cachedIn(viewModelScope)

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
	class Query(val term: String?) : SearchAction()
}

