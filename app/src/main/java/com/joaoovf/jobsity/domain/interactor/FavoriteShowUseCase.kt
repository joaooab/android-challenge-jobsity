package com.joaoovf.jobsity.domain.interactor

import com.joaoovf.jobsity.domain.model.Show
import com.joaoovf.jobsity.repository.ShowRepository

interface FavoriteShowUseCase {

	suspend operator fun invoke(show: Show) : Boolean
}

class FavoriteShowUseCaseImpl(private val repository: ShowRepository) : FavoriteShowUseCase {

	override suspend fun invoke(show: Show): Boolean {
		return if (isFavorite(show)) {
			repository.delete(show)
			false
		} else {
			repository.save(show)
			true
		}
	}

	private suspend fun isFavorite(show: Show) =
		repository.loadById(show.id) != null

}