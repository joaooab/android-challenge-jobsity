package com.joaoovf.jobsity.domain.extension

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputLayout

private const val MIN_PASSWORD_LENGHT = 3

const val FIELD_ERROR_REQUIRED = "Required"
const val FIELD_ERROR_PASSWORD = "Password must contain at least $MIN_PASSWORD_LENGHT PASSWORD LENGTH characters"

fun String.isValidPassword(): Boolean {
	return this.length >= MIN_PASSWORD_LENGHT
}

fun TextInputLayout.setTypeRequired() {
	this.errorListener()
	this.editText?.let {
		it.setOnFocusChangeListener { _, hasFocus ->
			if (!hasFocus) {
				error = if (it.text.toString().isNotEmpty()) {
					null
				} else {
					FIELD_ERROR_REQUIRED
				}
			}
		}
	}
}

fun TextInputLayout.setTypePassword() {
	this.errorListener()
	this.editText?.let {
		it.setOnFocusChangeListener { _, hasFocus ->
			if (!hasFocus) {
				error = when {
					this.getString().isValidPassword() -> {
						null
					}
					else -> {
						FIELD_ERROR_PASSWORD
					}
				}
			}
		}
	}
}

fun TextInputLayout.errorListener() {
	editText?.addTextChangedListener(object :
		TextWatcher {
		override fun afterTextChanged(s: Editable?) {
		}

		override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
		}

		override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
			error = null
		}
	})
}
