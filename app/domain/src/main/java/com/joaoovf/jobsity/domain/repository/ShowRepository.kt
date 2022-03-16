package com.joaoovf.jobsity.domain.repository

import com.joaoovf.jobsity.domain.model.Episode
import com.joaoovf.jobsity.domain.model.Show
import kotlinx.coroutines.flow.Flow


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
