package com.joaoovf.jobsity.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class ComponentViewModel : ViewModel() {
	private val _components = MutableSharedFlow<Components>()
	val components: SharedFlow<Components> = _components

	var withComponents = Components()
		set(value) {
			field = value
			viewModelScope.launch {
				_components.emit(value)
			}
		}

	init {
		viewModelScope.launch {
			_components.emit(withComponents)
		}
	}
}

data class Components(
	val toolbar: Boolean = true,
)