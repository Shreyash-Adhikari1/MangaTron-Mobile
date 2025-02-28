package com.example.mangatronmobile.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.mangatronmobile.R
import com.example.mangatronmobile.databinding.ActivityHomeBinding
import com.example.mangatronmobile.ui.fragment.AddProductFragment
import com.example.mangatronmobile.ui.fragment.CategoryFragment
import com.example.mangatronmobile.ui.fragment.FavouritesFragment
import com.example.mangatronmobile.ui.fragment.HomeFragment
import com.example.mangatronmobile.ui.fragment.SearchFragment
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize drawer
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        // Toggle for Hamburger Menu
        binding.homeHamburger.setOnClickListener {
            drawerLayout.openDrawer(navigationView)
        }

        // Handle Navigation Menu Clicks
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> replaceFragment(HomeFragment())
                R.id.nav_category -> replaceFragment(CategoryFragment())
                R.id.nav_about_us -> {
                    // Just In-Cse we decide to make an about us section
                }
            }
            drawerLayout.closeDrawers()
            true
        }

        binding.bottomNavigationView.setOnItemSelectedListener { menu ->
            when(menu.itemId){
                R.id.navHome -> replaceFragment(HomeFragment())
                R.id.navCategory -> replaceFragment(CategoryFragment())
                R.id.navAddManga -> replaceFragment(AddProductFragment())
                R.id.navFavourites -> replaceFragment(FavouritesFragment())

                else -> {}
            }
            true
        }

        binding.searchIcon.setOnClickListener{
            replaceFragment(SearchFragment())
        }

        // Default Fragment
        replaceFragment(HomeFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFrame, fragment)
        fragmentTransaction.commit()
    }
}
