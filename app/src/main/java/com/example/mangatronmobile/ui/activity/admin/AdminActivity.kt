package com.example.mangatronmobile.ui.activity.admin

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.mangatronmobile.R
import com.example.mangatronmobile.databinding.ActivityAdminBinding
import com.example.mangatronmobile.ui.activity.home.HomeActivity
import com.example.mangatronmobile.ui.fragment.CartFragment
import com.example.mangatronmobile.ui.fragment.CategoryFragment
import com.example.mangatronmobile.ui.fragment.HomeFragment
import com.example.mangatronmobile.ui.fragment.WishlistFragment
import com.google.android.material.navigation.NavigationView

class AdminActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminBinding
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = findViewById(R.id.adminDrawerLayout)
        navigationView = findViewById(R.id.adminNavigationView)

        // Toggle for Hamburger Menu
        binding.adminHamburger.setOnClickListener {
            drawerLayout.openDrawer(navigationView)
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_addProduct->{
                    val intent=Intent(this@AdminActivity, AddProductActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_home -> {val intent=Intent(this@AdminActivity, HomeActivity::class.java)
                    startActivity(intent)}

                R.id.nav_category -> replaceFragment(CategoryFragment())
                R.id.nav_cart -> replaceFragment(CartFragment())
                R.id.nav_wishlist -> replaceFragment(WishlistFragment())
            }
            drawerLayout.closeDrawers()
            true
        }

        binding.adminBottomNavigationView.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.navHome -> {val intent=Intent(this@AdminActivity, HomeActivity::class.java)
                    startActivity(intent)}
                R.id.navCategory -> replaceFragment(CategoryFragment())
                R.id.nav_cart->replaceFragment(CartFragment())
                R.id.navWishlist -> replaceFragment(WishlistFragment())

                else -> {}
            }
            true
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.adminDrawerLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainAdminFrame, fragment)
        fragmentTransaction.commit()
    }
}