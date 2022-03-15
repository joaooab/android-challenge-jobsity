package com.joaoovf.jobsity.domain.interactor

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.joaoovf.jobsity.domain.model.Show
import com.joaoovf.jobsity.repository.ShowRepository
import com.joaoovf.jobsity.source.ShowListSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

interface LoadShowsPagedUseCase {

	operator fun invoke(pageSize: Int): Flow<PagingData<Show>>

}

class LoadShowsPagedUseCaseImpl(
	private val repository: ShowRepository,
	private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : LoadShowsPagedUseCase {

	override fun invoke(pageSize: Int): Flow<PagingData<Show>> {
		return Pager(
			PagingConfig(pageSize = pageSize),
		) {
			ShowListSource(repository, pageSize)
		}.flow.flowOn(dispatcher)
	}

}