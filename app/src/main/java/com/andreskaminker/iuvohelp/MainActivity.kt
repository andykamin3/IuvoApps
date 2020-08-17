package com.andreskaminker.iuvohelp


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.andreskaminker.iuvohelp.modules.fabButtonHelpers
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(),
    fabButtonHelpers {
    private val TAG = "MainActivity"
    lateinit var bottomAppBar: BottomAppBar
    lateinit var navController: NavController
    lateinit var navView: NavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var fabButton: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.fragment)
        navView = findViewById(R.id.navView)
        fabButton = findViewById(R.id.floatingActionButton)
        bottomAppBar = findViewById(R.id.bottomAppBar)
        drawerLayout = findViewById(R.id.drawer_layout)
        setSupportActionBar(this.bottomAppBar)
        NavigationUI.setupWithNavController(navView, navController)
        bottomAppBar.setNavigationOnClickListener {
            if (drawerLayout.isOpen) {
                drawerLayout.close()
            } else {
                drawerLayout.open()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (drawerLayout.isOpen) {
            drawerLayout.close()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_app_bar, menu)
        return false
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return true
    }

    override fun setFabDrawable(@DrawableRes drawable: Int) {
        fabButton.setImageDrawable(resources.getDrawable(drawable, this.theme))
    }

    override fun setFabColor(@ColorRes color: Int) {
        fabButton.background.setTint(resources.getColor(color, this.theme))
    }

    override fun setFabClickListener(clickListener: () -> Unit) {
        fabButton.setOnClickListener {
            clickListener()
        }
    }
}

//TODO CHANGE getDrawable methods