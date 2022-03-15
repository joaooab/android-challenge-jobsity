package com.joaoovf.jobsity.repository

import com.joaoovf.jobsity.api.ShowAPI
import com.joaoovf.jobsity.domain.model.Show

interface ShowRepository {

	suspend fun fetchAll(page: Int): List<Show>

	suspend fun fetchByQuery(query: String?): List<Show>
}

class ShowRepositoryImpl(private val showAPI: ShowAPI) : ShowRepository {

	override suspend fun fetchAll(page: Int): List<Show> {
		return showAPI.fetchAll(page)
	}

	override suspend fun fetchByQuery(query: String?): List<Show> {
		return showAPI.fetchByQuery(query).map { it.show }
	}

}