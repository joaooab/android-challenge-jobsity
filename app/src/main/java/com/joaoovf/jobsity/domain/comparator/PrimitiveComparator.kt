package com.joaoovf.jobsity.domain.comparator

import androidx.recyclerview.widget.DiffUtil

class StringComparator : DiffUtil.ItemCallback<String>() {
	override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
		return oldItem == newItem
	}

	override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
		return oldItem == newItem
	}
}

class IntComparator : DiffUtil.ItemCallback<Int>() {
	override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
		return oldItem == newItem
	}

	override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
		return oldItem == newItem
	}
}