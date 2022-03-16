package com.joaoovf.jobsity.domain.interactor

import com.joaoovf.jobsity.domain.MainCoroutineRule
import com.joaoovf.jobsity.domain.model.Episode
import com.joaoovf.jobsity.domain.repository.FakeShowRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoadEpisodeUseCaseImplTest {

	@get:Rule
	var mainCoroutineRule = MainCoroutineRule()

	private val fakeEpisodeList = mutableListOf<Episode>()
	private val fakeRepository = FakeShowRepository(fakeEpisodesList = fakeEpisodeList)

	@Test
	fun `Given episode When find by show id Then return Show`() = runTest {
		val episode = Episode(id = 1)
		fakeEpisodeList.add(episode)
		val loadEpisodeUseCaseImpl = LoadEpisodeUseCaseImpl(fakeRepository)

		val result = loadEpisodeUseCaseImpl(episode.id)

		assertThat(episode.id, `is`(result?.id))
	}

	@Test
	fun `Given episode When find by with another id Then return null`() = runTest {
		val episode = Episode(id = 1)
		fakeEpisodeList.add(episode)
		val loadEpisodeUseCaseImpl = LoadEpisodeUseCaseImpl(fakeRepository)

		val result = loadEpisodeUseCaseImpl(2)

		assertThat(result, nullValue())
	}

}