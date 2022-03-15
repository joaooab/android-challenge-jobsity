package com.joaoovf.jobsity.di

import com.joaoovf.jobsity.api.Retrofit
import com.joaoovf.jobsity.api.ShowAPI
import org.koin.dsl.module

val apiModule = module {
	single { Retrofit.create<ShowAPI>() }
}