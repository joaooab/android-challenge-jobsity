package com.joaoovf.jobsity.domain.extension

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