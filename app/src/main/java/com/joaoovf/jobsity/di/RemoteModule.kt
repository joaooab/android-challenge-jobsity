package com.joaoovf.jobsity.di

import com.joaoovf.jobsity.api.Retrofit
import com.joaoovf.jobsity.api.ShowAPI
import org.koin.dsl.module

val remoteModule = module {
	single { Retrofit.create<ShowAPI>() }
}