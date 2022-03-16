package com.joaoovf.jobsity.domain.extension

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.joaoovf.jobsity.domain.R

fun ImageView.loadImage(imagePath: String?) {
	if (imagePath.isNullOrEmpty()) {
		this.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.ic_image))
	} else {
		val uri = imagePath.toUri().buildUpon().scheme("https").build()
		Glide.with(context)
			.load(uri)
			.apply(
				RequestOptions()
					.placeholder(R.drawable.ic_image)
					.error(R.drawable.ic_image_error)
			)
			.into(this)
	}

}