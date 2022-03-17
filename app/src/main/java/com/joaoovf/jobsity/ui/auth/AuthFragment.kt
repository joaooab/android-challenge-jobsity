package com.joaoovf.jobsity.ui.auth

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.joaoovf.jobsity.R
import com.joaoovf.jobsity.databinding.FragmentAuthBinding
import com.joaoovf.jobsity.domain.base.BaseFragment
import com.joaoovf.jobsity.domain.base.State
import com.joaoovf.jobsity.domain.extension.*
import com.joaoovf.jobsity.domain.model.Auth
import com.joaoovf.jobsity.domain.util.ValidatorEditText
import com.joaoovf.jobsity.domain.util.ValidatorEditTextBuilder
import com.joaoovf.jobsity.domain.util.ValidatorEditTextType
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthFragment : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {

	private val viewModel: AuthViewModel by viewModel()
	private lateinit var validator: ValidatorEditText

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		validator = ValidatorEditTextBuilder()
			.addField(binding.email, ValidatorEditTextType.Requiered)
			.addField(binding.password, ValidatorEditTextType.Password)
			.build()
		setupView()
	}

	private fun setupView() {
		binding.apply {
			setupLogin()
			setupRegister()
		}

		collectLoginState()
	}

	private fun collectLoginState() = safeFlowCollect {
		viewModel.loginState.collectLatest { state ->
			when (state) {
				is State.Loading -> {
					binding.loading.show()
				}
				is State.Success -> {
					hideKeyboard()
					binding.loading.gone()
					findNavController().navigate(
						AuthFragmentDirections.actionNavigationAuthToNavigationHome()
					)
				}
				is State.Error -> {
					hideKeyboard()
					Snackbar
						.make(
							requireView(),
							getString(R.string.message_error_login),
							Snackbar.LENGTH_SHORT
						)
						.show()
				}
			}
		}
	}

	private fun FragmentAuthBinding.setupRegister() {
		register.setOnClickListener {
			hideKeyboard()
			findNavController().navigate(
				AuthFragmentDirections.actionNavigationAuthToAuthRegisterFragment()
			)
		}
	}

	private fun FragmentAuthBinding.setupLogin() {
		login.setOnClickListener {
			if (validator.validate()) {
				val email = binding.email.getString()
				val password = binding.password.getString()
				viewModel.inputAction.invoke(
					AuthAction.Login(
						Auth(
							email,
							password
						)
					)
				)
			}
		}
	}

}