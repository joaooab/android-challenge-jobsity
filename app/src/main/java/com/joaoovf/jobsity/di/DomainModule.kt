package com.joaoovf.jobsity.di

import com.joaoovf.jobsity.domain.interactor.*
import org.koin.dsl.module

val domainModule = module {
	factory<LoadShowsPagedUseCase> { LoadShowsPagedUseCaseImpl(get()) }
	factory<SearchShowsUseCase> { SearchShowsUseCaseImpl(get()) }
	factory<LoadSeasonsWithEpisodesUseCase> { LoadSeasonsWithEpisodesUseCaseImpl(get()) }
	factory<LoadShowUseCase> { LoadShowUseCaseImpl(get()) }
	factory<LoadEpisodeUseCase> { LoadEpisodeUseCaseImpl(get()) }
}