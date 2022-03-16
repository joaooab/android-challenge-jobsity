package com.joaoovf.jobsity.domain.interactor

import com.joaoovf.jobsity.domain.MainCoroutineRule
import com.joaoovf.jobsity.domain.model.Show
import com.joaoovf.jobsity.domain.repository.FakeShowRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoadAllFavoriteShowUseCaseImplTest {

	@get:Rule
	var mainCoroutineRule = MainCoroutineRule()

	private val fakeShowList = mutableListOf<Show>()
	private val fakeRepository = FakeShowRepository(fakeShowList)

	@Test
	fun `Given favorite show When load all favorites Then return Show`() = runTest {
		val show = Show(id = 1)
		fakeShowList.add(show)
		val loadAllFavoriteShowUseCase = LoadAllFavoriteShowUseCaseImpl(fakeRepository)

		val result = loadAllFavoriteShowUseCase().first()

		assertThat(show.id, `is`(result.firstOrNull()?.id))
	}

	@Test
	fun `Given show When find by with another id Then return null`() = runTest {
		val loadAllFavoriteShowUseCase = LoadAllFavoriteShowUseCaseImpl(fakeRepository)

		val result = loadAllFavoriteShowUseCase().first()

		assertThat(result.firstOrNull()?.id, nullValue())
	}

}