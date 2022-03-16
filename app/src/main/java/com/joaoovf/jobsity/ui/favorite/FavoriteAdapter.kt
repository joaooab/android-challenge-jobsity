package com.joaoovf.jobsity.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joaoovf.jobsity.databinding.ItemShowBinding
import com.joaoovf.jobsity.domain.comparator.ShowComparator
import com.joaoovf.jobsity.domain.extension.loadImage
import com.joaoovf.jobsity.domain.model.Show

class FavoriteAdapter(private val onClick: (show: Show) -> Unit) :
	ListAdapter<Show, FavoriteAdapter.ViewHolder>(ShowComparator()) {

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
			binding.apply {
				textName.text = show.name
				imageShow.loadImage(show.image.medium)
				root.setOnClickListener {
					onClick(show)
				}
			}
		}
	}

}
