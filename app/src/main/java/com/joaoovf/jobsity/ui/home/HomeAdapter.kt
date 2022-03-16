package com.joaoovf.jobsity.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joaoovf.jobsity.databinding.ItemShowBinding
import com.joaoovf.jobsity.domain.comparator.ShowComparator
import com.joaoovf.jobsity.domain.extension.loadImage
import com.joaoovf.jobsity.domain.model.Show

class HomeAdapter(private val onClick: (show: Show) -> Unit) :
	PagingDataAdapter<Show, HomeAdapter.ViewHolder>(ShowComparator()) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
		val layoutInflater = LayoutInflater.from(parent.context)
		val binding = ItemShowBinding.inflate(layoutInflater, parent, false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
		getItem(position)?.let {
			holder.bind(it)
		}
	}

	inner class ViewHolder(private val binding: ItemShowBinding) : RecyclerView.ViewHolder(binding.root) {
		fun bind(show: Show) {
			binding.apply {
				textName.text = show.name
				imageShow.loadImage(show.image?.medium)
				root.setOnClickListener {
					onClick(show)
				}
			}
		}
	}

}
