package com.joaoovf.jobsity.repository

import com.joaoovf.jobsity.api.ShowAPI
import com.joaoovf.jobsity.domain.model.Episode
import com.joaoovf.jobsity.domain.model.Show

interface ShowRepository {

	suspend fun fetchAll(page: Int): List<Show>

	suspend fun fetchById(id: Int): Show?

	suspend fun fetchByQuery(query: String?): List<Show>

	suspend fun fetchAllEpisodes(id: Int): List<Episode>

	suspend fun fetchEpisodeByI(id: Int): Episode?
}

class ShowRepositoryImpl(private val showAPI: ShowAPI) : ShowRepository {

	override suspend fun fetchAll(page: Int): List<Show> {
		return showAPI.fetchAll(page)
	}

	override suspend fun fetchById(id: Int): Show? {
		return showAPI.fetchById(id)
	}

	override suspend fun fetchByQuery(query: String?): List<Show> {
		return showAPI.fetchByQuery(query).map { it.show }
	}

	override suspend fun fetchAllEpisodes(id: Int): List<Episode> {
		return showAPI.fetchAllEpisodes(id)
	}

	override suspend fun fetchEpisodeByI(id: Int): Episode? {
		return showAPI.fetchEpisodeById(id)
	}

}