package com.joaoovf.jobsity.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joaoovf.jobsity.databinding.ItemEpisodeBinding
import com.joaoovf.jobsity.domain.comparator.EpisodeComparator
import com.joaoovf.jobsity.domain.extension.loadImage
import com.joaoovf.jobsity.domain.model.Episode

class DetailEpisodeAdapter(private val onClick: (episode: Episode) -> Unit) :
	ListAdapter<Episode, DetailEpisodeAdapter.ViewHolder>(EpisodeComparator()) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailEpisodeAdapter.ViewHolder {
		val layoutInflater = LayoutInflater.from(parent.context)
		val binding = ItemEpisodeBinding.inflate(layoutInflater, parent, false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		getItem(position)?.let {
			holder.bind(it)
		}
	}

	inner class ViewHolder(private val binding: ItemEpisodeBinding) :
		RecyclerView.ViewHolder(binding.root) {

		fun bind(episode: Episode) {
			binding.apply {
				imageEpisode.loadImage(episode.image?.medium)
				textEpisodeName.text = episode.name
				root.setOnClickListener {
					onClick(episode)
				}
			}
		}
	}

}