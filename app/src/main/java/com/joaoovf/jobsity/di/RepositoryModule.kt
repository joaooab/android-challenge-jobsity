package com.joaoovf.jobsity.di

import com.joaoovf.jobsity.repository.ShowRepository
import com.joaoovf.jobsity.repository.ShowRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
	single<ShowRepository> { ShowRepositoryImpl(get(), get()) }
}