package com.joaoovf.jobsity.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.joaoovf.jobsity.domain.interactor.LoadShowsPagedUseCase
import com.joaoovf.jobsity.domain.model.Show
import kotlinx.coroutines.flow.Flow

class HomeViewModel(loadShowsPagedUseCase: LoadShowsPagedUseCase) : ViewModel() {

	val pagingDataFlow: Flow<PagingData<Show>> =
		loadShowsPagedUseCase(PAGE_SIZE).cachedIn(viewModelScope)

	companion object {
		private const val PAGE_SIZE = 250
	}

}