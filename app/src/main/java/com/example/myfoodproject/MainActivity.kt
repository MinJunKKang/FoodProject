package com.example.myfoodproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myfoodproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val navController = binding.frgNav.getFragment<NavHostFragment>().navController
        val bottomNav = binding.bottomNav

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.newnickFragment, R.id.writeFragment, R.id.infoFragment)
        )

        setupActionBarWithNavController(navController)
        bottomNav.setupWithNavController(navController)

        // AppBarConfiguration에서 loginFragment, registerFragment를 제외
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.loginFragment || destination.id == R.id.registerFragment) {
                // 로그인 및 회원가입 프래그먼트에서는 하단바 감추기
                bottomNav.visibility = View.GONE
            } else {
                // 다른 프래그먼트에서는 하단바 보이기
                bottomNav.visibility = View.VISIBLE
            }
        }

        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = binding.frgNav.getFragment<NavHostFragment>().navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}