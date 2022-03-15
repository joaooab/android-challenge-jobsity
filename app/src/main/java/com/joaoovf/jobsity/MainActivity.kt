package com.joaoovf.jobsity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.joaoovf.jobsity.databinding.ActivityMainBinding
import com.joaoovf.jobsity.domain.extension.setVisible
import com.joaoovf.jobsity.ui.ComponentViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

	private val componentViewModel: ComponentViewModel by viewModel()
	private lateinit var binding: ActivityMainBinding
	private val navController by lazy {
		findNavController(R.id.nav_host_fragment_activity_main)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		setSupportActionBar(binding.toolbar)
		setupView()
		handleObservers()
	}

	private fun setupView() {
		val navView: BottomNavigationView = binding.navView

		val appBarConfiguration = AppBarConfiguration(
			setOf(
				R.id.navigation_home,
				R.id.navigation_search,
				R.id.navigation_dashboard,
				R.id.navigation_notifications
			)
		)
		setupActionBarWithNavController(navController, appBarConfiguration)
		navView.setupWithNavController(navController)
	}

	private fun handleObservers() {
		lifecycleScope.launch {
			repeatOnLifecycle(Lifecycle.State.STARTED) {
				componentViewModel.components.collectLatest {
					binding.toolbar.setVisible(it.toolbar)
				}
			}
		}
	}

	override fun onSupportNavigateUp(): Boolean {
		return navController.navigateUp()
	}

}