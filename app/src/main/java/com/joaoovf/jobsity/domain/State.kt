package com.joaoovf.jobsity.domain

sealed class State<out T> {
	class Success<T>(val data: T) : State<T>()
	class Loading<T> : State<T>()
	class Error<T>(val throwable: Throwable) : State<T>()
}