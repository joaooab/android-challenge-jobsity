package com.joaoovf.jobsity.ui.auth

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.joaoovf.jobsity.R
import com.joaoovf.jobsity.databinding.FragmentRegisterAuthBinding
import com.joaoovf.jobsity.domain.base.BaseFragment
import com.joaoovf.jobsity.domain.base.State
import com.joaoovf.jobsity.domain.extension.*
import com.joaoovf.jobsity.domain.model.Auth
import com.joaoovf.jobsity.domain.util.ValidatorEditText
import com.joaoovf.jobsity.domain.util.ValidatorEditTextBuilder
import com.joaoovf.jobsity.domain.util.ValidatorEditTextType
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthRegisterFragment : BaseFragment<FragmentRegisterAuthBinding>(FragmentRegisterAuthBinding::inflate) {

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
			setupRegister()
		}

		collectRegisterState()
	}

	private fun collectRegisterState() = safeFlowCollect {
		viewModel.registerState.collectLatest { state ->
			when (state) {
				is State.Loading -> {
					binding.loading.show()
				}
				is State.Success -> {
					hideKeyboard()
					binding.loading.gone()
					findNavController().popBackStack()
				}
				is State.Error -> {
					hideKeyboard()
					Snackbar
						.make(
							requireView(),
							getString(R.string.message_error_register),
							Snackbar.LENGTH_SHORT
						)
						.show()
				}
			}
		}
	}

	private fun FragmentRegisterAuthBinding.setupRegister() {
		register.setOnClickListener {
			if (validator.validate()) {
				val email = binding.email.getString()
				val password = binding.password.getString()

				viewModel.inputAction.invoke(
					AuthAction.Register(
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
