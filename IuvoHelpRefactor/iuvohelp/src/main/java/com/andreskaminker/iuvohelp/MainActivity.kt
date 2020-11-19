package com.andreskaminker.iuvohelp


import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.andreskaminker.iuvohelp.modules.fabButtonHelpers
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import java.util.jar.Manifest


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    lateinit var navView: NavigationView
    lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       drawerLayout = findViewById(R.id.drawer_layout)
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf( android.Manifest.permission.CALL_PHONE), 1)
        }else{

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

}

//TODO CHANGE getDrawable methods