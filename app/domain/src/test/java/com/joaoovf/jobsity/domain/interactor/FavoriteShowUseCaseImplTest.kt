package com.joaoovf.jobsity.domain.interactor

import com.joaoovf.jobsity.domain.MainCoroutineRule
import com.joaoovf.jobsity.domain.model.Show
import com.joaoovf.jobsity.domain.repository.FakeShowRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteShowUseCaseImplTest {

	@get:Rule
	var mainCoroutineRule = MainCoroutineRule()

	private val fakeShowList = mutableListOf<Show>()
	private val fakeRepository = FakeShowRepository(fakeShowList)

	@Test
	fun `Given show When favorite Then return favorite show`() = runTest {
		val show = Show(id = 1)
		val favoriteShowUseCaseImpl = FavoriteShowUseCaseImpl(fakeRepository)

		favoriteShowUseCaseImpl(show)
		val favoriteShow = fakeRepository.loadById(show.id)

		assertThat(favoriteShow?.id, `is`(show.id))
	}

	@Test
	fun `Given favorite Show When favorite Then return null`() = runTest {
		val show = Show(id = 1)
		fakeShowList.add(show)
		val favoriteShowUseCaseImpl = FavoriteShowUseCaseImpl(fakeRepository)

		favoriteShowUseCaseImpl(show)
		val favoriteShow = fakeRepository.loadById(show.id)

		assertThat(favoriteShow, `is`(favoriteShow?.id))
	}

}