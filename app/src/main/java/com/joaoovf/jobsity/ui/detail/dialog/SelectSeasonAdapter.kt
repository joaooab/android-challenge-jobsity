package com.joaoovf.jobsity.ui.detail.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joaoovf.jobsity.R
import com.joaoovf.jobsity.databinding.ItemSeasonBinding
import com.joaoovf.jobsity.domain.comparator.IntComparator

class SelectSeasonAdapter : ListAdapter<Int, SelectSeasonAdapter.ViewHolder>(IntComparator()) {

	var onClick: ((Int) -> Unit)? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val layoutInflater = LayoutInflater.from(parent.context)
		val binding = ItemSeasonBinding.inflate(layoutInflater, parent, false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		getItem(position)?.let {
			holder.bind(it)
		}
	}

	inner class ViewHolder(private val binding: ItemSeasonBinding) :
		RecyclerView.ViewHolder(binding.root) {

		fun bind(season: Int) {
			binding.root.apply {
				text = context.getString(R.string.season_number, season)
				setOnClickListener {
					onClick?.invoke(season)
				}
			}
		}
	}

}
