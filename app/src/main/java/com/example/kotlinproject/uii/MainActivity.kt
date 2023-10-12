package com.example.kotlinproject.uii

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinproject.Adapters.Adapter
import com.example.kotlinproject.Models.ModelUser
import com.example.kotlinproject.R
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout

    lateinit var customTitle: TextView
    lateinit var toolbar: Toolbar
    lateinit var navigationView: NavigationView
    lateinit var fab: ImageView
    var toggle: ActionBarDrawerToggle? = null
    lateinit var drawer_image : ImageView
    lateinit var firestore: FirebaseFirestore
    lateinit var rvUsers: RecyclerView
    val modelUserList: MutableList<ModelUser> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        drawerLayout = findViewById(R.id.main_drawer)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        customTitle = findViewById(R.id.cus_name)
        navigationView = findViewById(R.id.nav_view)
        fab = findViewById(R.id.floating_button)
        val headerView = navigationView.getHeaderView(0)
        drawer_image=headerView.findViewById(R.id.image_view)



        firestore = FirebaseFirestore.getInstance()
            rvUsers = findViewById(R.id.Recycler_view)
            val adapterUsers = Adapter(modelUserList)


            rvUsers.layoutManager = LinearLayoutManager(this)
            getUsesr()


        if(drawer_image!=null) {
            drawer_image.setOnClickListener(object : OnClickListener {
                override fun onClick(v: View?) {
                    ImageFromAlbum()
                }
            })
        }
        else{
            Toast.makeText(this@MainActivity, "Is Null", Toast.LENGTH_SHORT).show()
        }




            toggle = ActionBarDrawerToggle(
                this,
                drawerLayout, toolbar,
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

            fab.setOnClickListener(object : OnClickListener {
                override fun onClick(view: View) {

                    val intent = Intent(this@MainActivity, Activity_tasks::class.java)
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

        private fun getUsesr() {
            firestore.collection("Tasks").get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        modelUserList.clear() // Clear existing data
                        for (document in task.result) {
                            val tempUser = document.toObject(ModelUser::class.java)
                            modelUserList.add(tempUser)
                        }
                        val adapterUsers = Adapter(modelUserList)
                        rvUsers.adapter = adapterUsers
                    }
                }
        }


        // Handle clicks on the navigation drawer icon in the action bar
        override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
            if (toggle?.onOptionsItemSelected(item) == true) {
                return true
            }

            return super.onOptionsItemSelected(item)
        }
    private fun ImageFromAlbum() {
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(
            Intent.createChooser(i, "Select Picture"),
            MainActivity.SELECT_PICTURE
        )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == MainActivity.SELECT_PICTURE) {
                val selectedImageUri = data?.data
                if (selectedImageUri != null) {
                    // Set the selected image URI to the ImageView
                    drawer_image.setImageURI(selectedImageUri)
                }
            }
        }
    }

    companion object {
        const val SELECT_PICTURE = 200
    }
}
