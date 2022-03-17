package com.joaoovf.jobsity.local.di

import com.joaoovf.jobsity.local.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {
	single { AppDatabase.getInstance(androidContext()) }
	single { get<AppDatabase>().showDAO() }
	single { get<AppDatabase>().authDAO() }
}