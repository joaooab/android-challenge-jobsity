package com.joaoovf.jobsity.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joaoovf.jobsity.databinding.ItemShowBinding
import com.joaoovf.jobsity.domain.comparator.ShowComparator
import com.joaoovf.jobsity.domain.extension.loadImage
import com.joaoovf.jobsity.domain.model.SearchShow
import com.joaoovf.jobsity.domain.model.Show

class SearchAdapter : ListAdapter<Show, SearchAdapter.ViewHolder>(ShowComparator()) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val layoutInflater = LayoutInflater.from(parent.context)
		val binding = ItemShowBinding.inflate(layoutInflater, parent, false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		getItem(position)?.let {
			holder.bind(it)
		}
	}

	inner class ViewHolder(private val binding: ItemShowBinding) : RecyclerView.ViewHolder(binding.root) {
		fun bind(show: Show) {
			binding.textViewName.text = show.name
			binding.imageView.loadImage(show.image?.medium)
		}
	}
}