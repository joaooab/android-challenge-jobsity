package com.joaoovf.jobsity.domain.comparator

import androidx.recyclerview.widget.DiffUtil
import com.joaoovf.jobsity.domain.model.Show

class ShowComparator : DiffUtil.ItemCallback<Show>() {
	override fun areItemsTheSame(oldItem: Show, newItem: Show): Boolean {
		return oldItem.id == newItem.id
	}

	override fun areContentsTheSame(oldItem: Show, newItem: Show): Boolean {
		return oldItem == newItem
	}
}