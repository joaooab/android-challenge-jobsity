package com.joaoovf.jobsity.domain.interactor

import com.joaoovf.jobsity.domain.base.State
import com.joaoovf.jobsity.domain.exception.UsernameAlreadyInUseException
import com.joaoovf.jobsity.domain.model.Auth
import com.joaoovf.jobsity.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface RegisterUseCase {

	operator fun invoke(auth: Auth): Flow<State<Auth>>

}

class RegisterUseCaseImpl(
	private val repository: AuthRepository,
) : RegisterUseCase {

	override fun invoke(auth: Auth): Flow<State<Auth>> = flow {
		val authEntity = repository.loadByUsername(auth.username)
		if (authEntity == null) {
			repository.save(auth)
			emit(State.Success(auth))
		} else {
			emit(State.Error(UsernameAlreadyInUseException()))
		}
	}

}