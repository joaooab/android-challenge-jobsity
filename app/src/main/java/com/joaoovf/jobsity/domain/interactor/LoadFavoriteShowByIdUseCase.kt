package com.joaoovf.jobsity.domain.interactor

import com.joaoovf.jobsity.domain.model.Show
import com.joaoovf.jobsity.repository.ShowRepository

interface LoadFavoriteShowByIdUseCase {

	suspend operator fun invoke(showId: Int): Show?
}

class LoadFavoriteShowByIdUseCaseImpl(private val repository: ShowRepository) :
	LoadFavoriteShowByIdUseCase {

	override suspend fun invoke(showId: Int): Show? {
		return repository.loadById(showId)
	}

}