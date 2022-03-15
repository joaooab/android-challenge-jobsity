package com.joaoovf.jobsity.api

import com.joaoovf.jobsity.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Retrofit {

	private fun createClient(): OkHttpClient {
		val httpLoggingInterceptor = HttpLoggingInterceptor()
		httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
		return OkHttpClient.Builder()
			.addInterceptor(httpLoggingInterceptor)
			.connectTimeout(5, TimeUnit.SECONDS)
			.readTimeout(30, TimeUnit.SECONDS)
			.writeTimeout(30, TimeUnit.SECONDS)
			.build()
	}

	val service: Retrofit = Retrofit.Builder()
		.baseUrl(BuildConfig.BASE_URL)
		.client(createClient())
		.addConverterFactory(GsonConverterFactory.create())
		.build()

	inline fun <reified T> create(): T = service.create(T::class.java)

}