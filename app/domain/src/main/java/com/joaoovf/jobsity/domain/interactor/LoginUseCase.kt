package com.joaoovf.jobsity.domain.interactor

import com.joaoovf.jobsity.domain.base.State
import com.joaoovf.jobsity.domain.exception.UnauthenticatedUserException
import com.joaoovf.jobsity.domain.model.Auth
import com.joaoovf.jobsity.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface LoginUseCase {

	operator fun invoke(auth: Auth): Flow<State<Auth>>

}

class LoginUseCaseImpl(
	private val repository: AuthRepository,
) : LoginUseCase {

	override fun invoke(auth: Auth): Flow<State<Auth>> = flow {
		repository.loadByUsername(auth.username)?.let {
			if (auth.username == it.username && auth.password == it.password) {
				emit(State.Success(it))
			}
		} ?: emit(State.Error(UnauthenticatedUserException()))
	}

}