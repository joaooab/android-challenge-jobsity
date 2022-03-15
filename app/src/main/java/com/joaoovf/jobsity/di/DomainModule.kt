package com.joaoovf.jobsity.di

import com.joaoovf.jobsity.domain.interactor.LoadShowsPagedUseCase
import com.joaoovf.jobsity.domain.interactor.LoadShowsPagedUseCaseImpl
import com.joaoovf.jobsity.domain.interactor.SearchShowsUseCase
import com.joaoovf.jobsity.domain.interactor.SearchShowsUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
	factory<LoadShowsPagedUseCase> { LoadShowsPagedUseCaseImpl(get()) }
	factory<SearchShowsUseCase> { SearchShowsUseCaseImpl(get()) }
}