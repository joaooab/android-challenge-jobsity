package com.joaoovf.jobsity.domain.comparator

import androidx.recyclerview.widget.DiffUtil
import com.joaoovf.jobsity.domain.model.Episode

class EpisodeComparator : DiffUtil.ItemCallback<Episode>() {
	override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
		return oldItem.id == newItem.id
	}

	override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
		return oldItem == newItem
	}
}