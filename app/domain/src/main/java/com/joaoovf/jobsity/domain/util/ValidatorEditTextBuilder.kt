package com.joaoovf.jobsity.domain.util

import com.google.android.material.textfield.TextInputLayout
import com.joaoovf.jobsity.domain.extension.*

data class ValidatorEditTextField(
	val field: TextInputLayout,
	val errorMessage: String,
	val onValidation: () -> Boolean
)

sealed class ValidatorEditTextType {
	object Requiered : ValidatorEditTextType()
	object Password : ValidatorEditTextType()
}

class ValidatorEditTextBuilder {

	private val validators = mutableListOf<ValidatorEditTextField>()

	fun addField(
		field: TextInputLayout,
		type: ValidatorEditTextType
	): ValidatorEditTextBuilder {
		when (type) {
			is ValidatorEditTextType.Requiered -> setRequired(field)
			is ValidatorEditTextType.Password -> setPassword(field)
		}

		return this
	}

	private fun setRequired(field: TextInputLayout) {
		addField(field, FIELD_ERROR_REQUIRED) { field.getString().isNotEmpty() }
		field.setTypeRequired()
	}

	private fun setPassword(field: TextInputLayout) {
		addField(field, FIELD_ERROR_PASSWORD) {
			field.getString().isValidPassword()
		}

		field.setTypePassword()
	}

	private fun addField(
		field: TextInputLayout,
		errorMessage: String = "",
		onValidation: () -> Boolean
	) {
		val validator = ValidatorEditTextField(field, errorMessage, onValidation)
		validators.add(validator)
	}

	fun build(): ValidatorEditText {
		return ValidatorEditText(validators)
	}


}

class ValidatorEditText(private val validators: List<ValidatorEditTextField>) {
	fun validate(): Boolean {
		var success = true
		for (validator in validators) {
			if (!validator.onValidation.invoke()) {
				validator.field.error = validator.errorMessage
			}
			if (hasError(validator)) {
				success = false
			}
		}

		return success
	}

	private fun hasError(validatorEditText: ValidatorEditTextField) =
		!validatorEditText.field.error.isNullOrEmpty()
}