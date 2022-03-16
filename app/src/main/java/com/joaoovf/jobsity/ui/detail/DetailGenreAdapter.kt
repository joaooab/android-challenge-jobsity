package com.joaoovf.jobsity.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joaoovf.jobsity.databinding.ItemGenreBinding
import com.joaoovf.jobsity.domain.comparator.StringComparator

class DetailGenreAdapter : ListAdapter<String, DetailGenreAdapter.ViewHolder>(StringComparator()) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val layoutInflater = LayoutInflater.from(parent.context)
		val binding = ItemGenreBinding.inflate(layoutInflater, parent, false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		getItem(position)?.let {
			holder.bind(it)
		}
	}

	inner class ViewHolder(private val binding: ItemGenreBinding) :
		RecyclerView.ViewHolder(binding.root) {

		fun bind(genre: String) {
			binding.root.text = genre
		}
	}

}
