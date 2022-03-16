package com.joaoovf.jobsity.domain.interactor

import com.joaoovf.jobsity.domain.model.SeasonWithEpisodes
import com.joaoovf.jobsity.repository.ShowRepository

interface LoadSeasonsWithEpisodesUseCase {

	suspend operator fun invoke(showId: Int): List<SeasonWithEpisodes>

}

class LoadSeasonsWithEpisodesUseCaseImpl(
	private val repository: ShowRepository
) : LoadSeasonsWithEpisodesUseCase {

	override suspend fun invoke(showId: Int): List<SeasonWithEpisodes> {
		val result = repository.fetchAllEpisodes(showId)
		return result.groupBy { it.season }.map {
			SeasonWithEpisodes(it.key, it.value)
		}
	}

}