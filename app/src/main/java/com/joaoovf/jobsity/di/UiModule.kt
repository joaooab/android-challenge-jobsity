package com.joaoovf.jobsity.di

import com.joaoovf.jobsity.ui.ComponentViewModel
import com.joaoovf.jobsity.ui.detail.DetailEpisodeViewModel
import com.joaoovf.jobsity.ui.detail.DetailViewModel
import com.joaoovf.jobsity.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
	viewModel { ComponentViewModel() }
	viewModel { HomeViewModel(get()) }
	viewModel { DetailViewModel(get(), get()) }
	viewModel { DetailEpisodeViewModel(get()) }
}