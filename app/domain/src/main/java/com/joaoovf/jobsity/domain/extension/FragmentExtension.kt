package com.joaoovf.jobsity.domain.extension

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Fragment.safeFlowCollect(
	state: Lifecycle.State = Lifecycle.State.STARTED,
	collect: suspend (CoroutineScope) -> Unit
) {
	viewLifecycleOwner.lifecycleScope.launch {
		viewLifecycleOwner.repeatOnLifecycle(state) {
			collect.invoke(this)
		}
	}
}

fun Fragment.hideKeyboard() {
	view?.let { requireActivity().hideKeyboard() }
}

fun Activity.hideKeyboard() {
	val view = currentFocus
	hideKeyboard(this, view)
}

private fun hideKeyboard(context: Activity, view: View?) {
	val inputMethodManager =
		context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	if (view != null) {
		inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
		view.clearFocus()
	} else {
		context.window
			.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
	}
}