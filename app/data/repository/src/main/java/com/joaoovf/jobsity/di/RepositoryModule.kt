package com.joaoovf.jobsity.di

import com.joaoovf.jobsity.domain.repository.ShowRepository
import com.joaoovf.jobsity.ShowRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
	single<ShowRepository> { ShowRepositoryImpl(get(), get()) }
}