package com.example.kotlinproject


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var customTitle : TextView
    lateinit var toolbar: Toolbar
    lateinit var navigationView: NavigationView
    lateinit var fab : FloatingActionButton
    var toggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.main_drawer)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        customTitle=findViewById(R.id.cus_name)
        navigationView = findViewById(R.id.nav_view)
        fab= findViewById(R.id.floating_button)

        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout, toolbar ,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        drawerLayout.addDrawerListener(toggle!!)
        toggle!!.syncState()
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayShowTitleEnabled(false);
//        }
        customTitle.text = customTitle.text

        fab.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View) {

                val intent=Intent(this@MainActivity, Activity_tasks::class.java)
                startActivity(intent)
            }


        })

        // Set up the item selected listener for the navigation drawer


        // Set a listener to handle navigation item clicks
        navigationView.setNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.dwr_home) {
                Toast.makeText(this, "home", Toast.LENGTH_SHORT).show()
            } else if (item.itemId == R.id.dwr_set) {
                Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show()
            } else if (item.itemId == R.id.dwr_logout) {
                Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            false
        }
    }

    // Handle clicks on the navigation drawer icon in the action bar
    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        if (toggle?.onOptionsItemSelected(item) == true) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
