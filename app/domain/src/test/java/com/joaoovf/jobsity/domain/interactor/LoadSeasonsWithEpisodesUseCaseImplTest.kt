package com.joaoovf.jobsity.domain.interactor

import com.joaoovf.jobsity.domain.MainCoroutineRule
import com.joaoovf.jobsity.domain.model.Episode
import com.joaoovf.jobsity.domain.model.Show
import com.joaoovf.jobsity.domain.repository.FakeShowRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoadSeasonsWithEpisodesUseCaseImplTest {

	@get:Rule
	var mainCoroutineRule = MainCoroutineRule()

	private val fakeEpisodeList = mutableListOf<Episode>()
	private val fakeRepository = FakeShowRepository(fakeEpisodesList = fakeEpisodeList)

	@Test
	fun `Given 3 diferente season episodes When load seasons with episodes Then return 3 groups`() = runTest {
		fakeEpisodeList.addAll(
			listOf(
				Episode(id = 1, season = 1),
				Episode(id = 1, season = 2),
				Episode(id = 1, season = 3),
			)
		)

		val loadSeasonsWithEpisodesUseCaseImpl = LoadSeasonsWithEpisodesUseCaseImpl(fakeRepository)
		val seasonsWithEpisodes = loadSeasonsWithEpisodesUseCaseImpl(showId = 1)

		assertThat(seasonsWithEpisodes.size, `is`(3))
	}

	@Test
	fun `Given 3 equals season episodes When load seasons with episodes Then return 1 groups`() = runTest {
		fakeEpisodeList.addAll(
			listOf(
				Episode(id = 1, season = 1),
				Episode(id = 1, season = 1),
				Episode(id = 1, season = 1),
			)
		)

		val loadSeasonsWithEpisodesUseCaseImpl = LoadSeasonsWithEpisodesUseCaseImpl(fakeRepository)
		val seasonsWithEpisodes = loadSeasonsWithEpisodesUseCaseImpl(showId = 1)

		assertThat(seasonsWithEpisodes.size, `is`(1))
	}

}