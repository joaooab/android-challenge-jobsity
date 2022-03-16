package com.joaoovf.jobsity.domain.extension

import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun SearchView.collectInput(): StateFlow<String?> {
	val query = MutableStateFlow<String?>(null)

	this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
		override fun onQueryTextSubmit(s: String?): Boolean {
			return true
		}

		override fun onQueryTextChange(s: String?): Boolean {
			query.value = s.orEmpty()
			return true
		}

	})

	return query
}
