package com.joaoovf.jobsity.domain.comparator

import androidx.recyclerview.widget.DiffUtil
import com.joaoovf.jobsity.domain.model.Show
import com.joaoovf.jobsity.ui.detail.EpisodeItem

class EpisodeItemComparator : DiffUtil.ItemCallback<EpisodeItem>() {
	override fun areItemsTheSame(oldItem: EpisodeItem, newItem: EpisodeItem): Boolean {
		return oldItem == newItem
	}

	override fun areContentsTheSame(oldItem: EpisodeItem, newItem: EpisodeItem): Boolean {
		return oldItem == newItem
	}
}