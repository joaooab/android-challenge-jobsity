package com.joaoovf.jobsity.repository

import com.joaoovf.jobsity.api.ShowAPI
import com.joaoovf.jobsity.data.dao.ShowDAO
import com.joaoovf.jobsity.domain.model.Episode
import com.joaoovf.jobsity.domain.model.Show
import com.joaoovf.jobsity.repository.mapper.mapList
import com.joaoovf.jobsity.repository.mapper.toEntity
import com.joaoovf.jobsity.repository.mapper.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface ShowRepository {

	suspend fun fetchAll(page: Int): List<Show>

	suspend fun fetchById(id: Int): Show?

	suspend fun fetchByQuery(query: String?): List<Show>

	suspend fun fetchAllEpisodes(id: Int): List<Episode>

	suspend fun fetchEpisodeByI(id: Int): Episode?

	fun loadAll(): Flow<List<Show>>

	suspend fun loadById(showId: Int): Show?

	suspend fun save(show: Show)

	suspend fun delete(show: Show)
}

class ShowRepositoryImpl(
	private val api: ShowAPI,
	private val dao: ShowDAO
) : ShowRepository {

	override suspend fun fetchAll(page: Int): List<Show> {
		return api.fetchAll(page)
	}

	override suspend fun fetchById(id: Int): Show? {
		return api.fetchById(id)
	}

	override suspend fun fetchByQuery(query: String?): List<Show> {
		return api.fetchByQuery(query).map { it.show }
	}

	override suspend fun fetchAllEpisodes(id: Int): List<Episode> {
		return api.fetchAllEpisodes(id)
	}

	override suspend fun fetchEpisodeByI(id: Int): Episode? {
		return api.fetchEpisodeById(id)
	}

	override fun loadAll(): Flow<List<Show>> {
		return dao.loadAll().map { it.mapList { toModel() } }
	}

	override suspend fun loadById(showId: Int): Show? {
		return dao.loadById(showId)?.toModel()
	}

	override suspend fun save(show: Show) {
		return dao.save(show.toEntity())
	}

	override suspend fun delete(show: Show) {
		return dao.delete(show.toEntity())
	}

}