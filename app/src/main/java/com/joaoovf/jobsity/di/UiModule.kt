package com.joaoovf.jobsity.di

import com.joaoovf.jobsity.ui.ComponentViewModel
import com.joaoovf.jobsity.ui.home.HomeViewModel
import com.joaoovf.jobsity.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
	viewModel { ComponentViewModel() }
	viewModel { HomeViewModel(get()) }
	viewModel { SearchViewModel(get()) }
}