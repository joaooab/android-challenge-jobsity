package com.joaoovf.jobsity.domain.interactor

import com.joaoovf.jobsity.domain.State
import com.joaoovf.jobsity.domain.model.Show
import com.joaoovf.jobsity.repository.ShowRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

interface SearchShowsUseCase {

	operator fun invoke(query: String?): Flow<State<List<Show>>>

}

class SearchShowsUseCaseImpl(
	private val repository: ShowRepository,
	private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : SearchShowsUseCase {

	override fun invoke(query: String?): Flow<State<List<Show>>> = flow {
		try {
			val result = repository.fetchByQuery(query)
			emit(State.Success(result))
		} catch (e: Exception) {
			emit(State.Error(e))
		}
	}.onStart {
		emit(State.Loading())
	}.flowOn(dispatcher)

}