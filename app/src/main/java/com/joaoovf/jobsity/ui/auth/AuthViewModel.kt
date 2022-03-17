package com.joaoovf.jobsity.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoovf.jobsity.domain.base.State
import com.joaoovf.jobsity.domain.interactor.LoginUseCase
import com.joaoovf.jobsity.domain.interactor.RegisterUseCase
import com.joaoovf.jobsity.domain.model.Auth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AuthViewModel(
	private val loginUseCase: LoginUseCase,
	private val registerUseCase: RegisterUseCase,
	dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

	val inputAction: (AuthAction) -> Unit
	val loginState: Flow<State<Auth>>
	val registerState: Flow<State<Auth>>

	init {
		val actionFlow = MutableSharedFlow<AuthAction>()

		loginState = actionFlow
			.filterIsInstance<AuthAction.Login>()
			.flatMapLatest {
				loginUseCase(it.auth)
			}.flowOn(dispatcher)

		registerState = actionFlow
			.filterIsInstance<AuthAction.Register>()
			.flatMapLatest {
				registerUseCase(it.auth)
			}.flowOn(dispatcher)

		inputAction = { action ->
			viewModelScope.launch {
				actionFlow.emit(action)
			}
		}
	}

}

sealed class AuthAction {
	class Login(val auth: Auth) : AuthAction()
	class Register(val auth: Auth) : AuthAction()
}

