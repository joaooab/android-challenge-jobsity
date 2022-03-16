package com.joaoovf.jobsity.domain.interactor

import com.joaoovf.jobsity.domain.MainCoroutineRule
import com.joaoovf.jobsity.domain.model.Show
import com.joaoovf.jobsity.domain.repository.FakeShowRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoadShowUseCaseImplTest {

	@get:Rule
	var mainCoroutineRule = MainCoroutineRule()

	private val fakeShowList = mutableListOf<Show>()
	private val fakeRepository = FakeShowRepository(fakeShowList)

	@Test
	fun `Given show  When find by show id Then return Show`() = runTest {
		val show = Show(id = 1)
		fakeShowList.add(show)
		val loadShowUseCaseImpl = LoadShowUseCaseImpl(fakeRepository)

		val result = loadShowUseCaseImpl(show.id)

		assertThat(show.id, `is`(result?.id))
	}

	@Test
	fun `Given show When find by with another id Then return null`() = runTest {
		val show = Show(id = 1)
		fakeShowList.add(show)
		val loadShowUseCaseImpl = LoadShowUseCaseImpl(fakeRepository)

		val result = loadShowUseCaseImpl(showId = 2)

		assertThat(result, nullValue())
	}

}