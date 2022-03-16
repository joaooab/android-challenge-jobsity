package com.joaoovf.jobsity.domain.interactor

import com.joaoovf.jobsity.domain.repository.ShowRepository
import com.joaoovf.jobsity.domain.model.Episode

interface LoadEpisodeUseCase {

	suspend operator fun invoke(episodeId: Int): Episode?

}

class LoadEpisodeUseCaseImpl(
	private val repository: ShowRepository,
) : LoadEpisodeUseCase {

	override suspend fun invoke(episodeId: Int) = repository.fetchEpisodeByI(episodeId)

}