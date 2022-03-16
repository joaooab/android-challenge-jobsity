package com.joaoovf.jobsity.domain.repository

import com.joaoovf.jobsity.domain.model.Episode
import com.joaoovf.jobsity.domain.model.Show
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeShowRepository(
	private val fakeShowList: MutableList<Show> = mutableListOf(),
	private val fakeEpisodesList: MutableList<Episode> = mutableListOf()
) : ShowRepository {

	override suspend fun fetchAll(page: Int): List<Show> {
		return fakeShowList
	}

	override suspend fun fetchById(id: Int): Show? {
		return fakeShowList.find { it.id == id }
	}

	override suspend fun fetchByQuery(query: String?): List<Show> {
		if (query == null) return fakeShowList
		return fakeShowList.filter { it.name.contains(query) }
	}

	override suspend fun fetchAllEpisodes(id: Int): List<Episode> {
		return fakeEpisodesList
	}

	override suspend fun fetchEpisodeByI(id: Int): Episode? {
		return fakeEpisodesList.find { it.id == id }
	}

	override fun loadAll(): Flow<List<Show>> = flow {
		emit(fakeShowList)
	}

	override suspend fun loadById(showId: Int): Show? {
		return fakeShowList.find { it.id == showId }
	}

	override suspend fun save(show: Show) {
		fakeShowList.add(show)
	}

	override suspend fun delete(show: Show) {
		fakeShowList.remove(show)
	}

}