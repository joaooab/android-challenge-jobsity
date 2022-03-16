package com.joaoovf.jobsity

import com.joaoovf.jobsity.domain.repository.ShowRepository
import com.joaoovf.jobsity.local.dao.ShowDAO
import com.joaoovf.jobsity.domain.model.Episode
import com.joaoovf.jobsity.domain.model.Show
import com.joaoovf.jobsity.mapper.mapList
import com.joaoovf.jobsity.mapper.toEntity
import com.joaoovf.jobsity.mapper.toModel
import com.joaoovf.jobsity.remote.ShowAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ShowRepositoryImpl(
	private val api: ShowAPI,
	private val dao: ShowDAO
) : ShowRepository {

	override suspend fun fetchAll(page: Int): List<Show> {
		return api.fetchAll(page).mapList { toModel() }
	}

	override suspend fun fetchById(id: Int): Show? {
		return api.fetchById(id)?.toModel()
	}

	override suspend fun fetchByQuery(query: String?): List<Show> {
		return api.fetchByQuery(query).mapList { show.toModel() }
	}

	override suspend fun fetchAllEpisodes(id: Int): List<Episode> {
		return api.fetchAllEpisodes(id).mapList { toModel() }
	}

	override suspend fun fetchEpisodeByI(id: Int): Episode? {
		return api.fetchEpisodeById(id)?.toModel()
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