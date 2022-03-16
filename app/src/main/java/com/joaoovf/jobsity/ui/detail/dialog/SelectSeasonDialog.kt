package com.joaoovf.jobsity.ui.detail.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.joaoovf.jobsity.databinding.BottomSheetSeasonBinding

class SelectSeasonDialog : BottomSheetDialogFragment() {

	private var _binding: BottomSheetSeasonBinding? = null
	private val binding get() = _binding!!

	var onClick: ((Int) -> Unit)? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		isCancelable = true
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		dialog?.setOnShowListener { dialog ->
			val d = dialog as BottomSheetDialog
			val bottomSheet = d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
			val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
			bottomSheetBehavior.peekHeight = bottomSheet.height
			bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
			super.onViewCreated(view, savedInstanceState)
		}

		setupView()
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = BottomSheetSeasonBinding.inflate(inflater, container, false)
		return binding.root
	}

	private fun setupView() {
		val seasons = arguments?.getIntegerArrayList(ARG_SEASONS) ?: return
		binding.listSeason.adapter = SelectSeasonAdapter().apply {
			submitList(seasons.toList())
			this.onClick = {
				this@SelectSeasonDialog.onClick?.invoke(it)
				this@SelectSeasonDialog.dismiss()
			}
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	companion object {
		fun newInstance(seasons: List<Int>, onClick: (Int) -> Unit): SelectSeasonDialog {
			return SelectSeasonDialog().apply {
				this.arguments = bundleOf(
					ARG_SEASONS to seasons
				)
				this.onClick = onClick
			}
		}

		private const val ARG_SEASONS = "seasons"
	}

}