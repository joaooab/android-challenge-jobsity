package com.joaoovf.jobsity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.joaoovf.jobsity.databinding.ActivityMainBinding
import com.joaoovf.jobsity.domain.extension.gone
import com.joaoovf.jobsity.domain.extension.show

class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding
	private val navController by lazy {
		findNavController(R.id.nav_host_fragment_activity_main)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		setupView()
	}

	private fun setupView() {
		val navView: BottomNavigationView = binding.navView

		val appBarConfiguration = AppBarConfiguration(
			setOf(
				R.id.navigation_home,
				R.id.navigation_favorites,
			)
		)

		setupActionBarWithNavController(navController, appBarConfiguration)
		navView.setupWithNavController(navController)
		navController.addOnDestinationChangedListener { _, destination, _ ->
			when (destination.id) {
				R.id.navigation_auth,
				R.id.navigation_register_auth -> navView.gone()
				else -> navView.show()
			}
		}
	}

	override fun onSupportNavigateUp(): Boolean {
		return navController.navigateUp()
	}

}