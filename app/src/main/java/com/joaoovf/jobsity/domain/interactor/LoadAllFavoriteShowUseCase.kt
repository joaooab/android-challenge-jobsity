package com.joaoovf.jobsity.domain.interactor

import com.joaoovf.jobsity.domain.model.Show
import com.joaoovf.jobsity.repository.ShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface LoadAllFavoriteShowUseCase {

	operator fun invoke(): Flow<List<Show>>
}

class LoadFavoriteShowUseCaseImpl(private val repository: ShowRepository) : LoadAllFavoriteShowUseCase {

	override fun invoke(): Flow<List<Show>> {
		return repository.loadAll().map { list ->
			list.sortedBy { it.name }
		}
	}

}