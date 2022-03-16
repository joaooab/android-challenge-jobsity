package com.joaoovf.jobsity.domain.interactor

import com.joaoovf.jobsity.domain.repository.ShowRepository
import com.joaoovf.jobsity.domain.model.Show

interface LoadShowUseCase {

	suspend operator fun invoke(showId: Int): Show?

}

class LoadShowUseCaseImpl(
	private val repository: ShowRepository,
) : LoadShowUseCase {

	override suspend fun invoke(showId: Int) = repository.fetchById(showId)

}