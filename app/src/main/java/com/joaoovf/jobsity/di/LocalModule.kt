package com.joaoovf.jobsity.di

import com.joaoovf.jobsity.data.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {
	single { AppDatabase.getInstance(androidContext()) }
	single { get<AppDatabase>().showDAO() }
}