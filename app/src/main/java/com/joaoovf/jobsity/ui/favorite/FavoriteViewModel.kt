package com.joaoovf.jobsity.ui.favorite

import androidx.lifecycle.ViewModel
import com.joaoovf.jobsity.domain.interactor.LoadAllFavoriteShowUseCase
import com.joaoovf.jobsity.domain.model.Show
import kotlinx.coroutines.flow.Flow

class FavoriteViewModel(
	loadAllFavoriteShowUseCase: LoadAllFavoriteShowUseCase,
) : ViewModel() {

	val favoritesFlow: Flow<List<Show>> = loadAllFavoriteShowUseCase()

}