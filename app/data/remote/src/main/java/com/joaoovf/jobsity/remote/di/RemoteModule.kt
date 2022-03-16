package com.joaoovf.jobsity.remote.di

import com.joaoovf.jobsity.remote.AppService
import com.joaoovf.jobsity.remote.ShowAPI
import org.koin.dsl.module

val remoteModule = module {
	single { AppService.create<ShowAPI>() }
}