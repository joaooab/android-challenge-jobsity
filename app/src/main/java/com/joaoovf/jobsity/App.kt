package com.joaoovf.jobsity

import android.app.Application
import com.joaoovf.jobsity.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {
	override fun onCreate() {
		super.onCreate()

		startKoin {
			androidContext(this@App)
			modules(
				listOf(
					domainModule,
					repositoryModule,
					remoteModule,
					uiModule,
					localModule,
				)
			)
		}
	}
}