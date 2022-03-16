package com.joaoovf.jobsity.domain.di

import com.joaoovf.jobsity.domain.interactor.*
import org.koin.dsl.module

val domainModule = module {
	factory<LoadShowsPagedUseCase> { LoadShowsPagedUseCaseImpl(get()) }
	factory<LoadSeasonsWithEpisodesUseCase> { LoadSeasonsWithEpisodesUseCaseImpl(get()) }
	factory<LoadShowUseCase> { LoadShowUseCaseImpl(get()) }
	factory<LoadEpisodeUseCase> { LoadEpisodeUseCaseImpl(get()) }
	factory<LoadAllFavoriteShowUseCase> { LoadAllFavoriteShowUseCaseImpl(get()) }
	factory<LoadFavoriteShowByIdUseCase> { LoadFavoriteShowByIdUseCaseImpl(get()) }
	factory<FavoriteShowUseCase> { FavoriteShowUseCaseImpl(get()) }
}