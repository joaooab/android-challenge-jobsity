package com.joaoovf.jobsity.di

import com.joaoovf.jobsity.AuthRepositoryImpl
import com.joaoovf.jobsity.ShowRepositoryImpl
import com.joaoovf.jobsity.domain.repository.AuthRepository
import com.joaoovf.jobsity.domain.repository.ShowRepository
import org.koin.dsl.module

val repositoryModule = module {
	single<ShowRepository> { ShowRepositoryImpl(get(), get()) }
	single<AuthRepository> { AuthRepositoryImpl(get()) }
}