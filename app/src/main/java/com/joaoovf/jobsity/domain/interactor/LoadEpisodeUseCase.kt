package com.joaoovf.jobsity.domain.interactor

import com.joaoovf.jobsity.domain.model.Episode
import com.joaoovf.jobsity.repository.ShowRepository

interface LoadEpisodeUseCase {

	suspend operator fun invoke(episodeId: Int): Episode?

}

class LoadEpisodeUseCaseImpl(
	private val repository: ShowRepository,
) : LoadEpisodeUseCase {

	override suspend fun invoke(episodeId: Int) = repository.fetchEpisodeByI(episodeId)

}