package com.ayush.cuevents.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

import com.ayush.cuevents.fragment.FavoriteFragment
import com.ayush.cuevents.fragment.HomeFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import android.app.SearchManager

import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SearchView

import androidx.core.view.MenuItemCompat
import com.ayush.cuevents.R
import com.ayush.cuevents.model.Clubs


class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView
    lateinit var coordinatorLayout: CoordinatorLayout
    var previousMenuItem: MenuItem?=null

    lateinit var newEvent:ExtendedFloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.ayush.cuevents.R.layout.activity_main)

        drawerLayout=findViewById(com.ayush.cuevents.R.id.drawerlayout)

        frameLayout=findViewById(com.ayush.cuevents.R.id.framelayout)
        navigationView=findViewById(com.ayush.cuevents.R.id.navigationview)
        coordinatorLayout=findViewById(com.ayush.cuevents.R.id.coordinatorlayout)

        newEvent=findViewById(com.ayush.cuevents.R.id.add_event)

        setuptoolbar()
        openHome()

        val animation = AnimationUtils.loadAnimation(this, com.ayush.cuevents.R.anim.rotate)

        newEvent.setOnClickListener {
            openLoginBottomSheet(this)

        }


        val actionBarDrawerToggle= ActionBarDrawerToggle(this,drawerLayout,
            com.ayush.cuevents.R.string.open_drawer,
            com.ayush.cuevents.R.string.close_drawer)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {
            if (previousMenuItem != null) {
                previousMenuItem?.isChecked = false
            }
            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it

            when (it.itemId) {
                com.ayush.cuevents.R.id.home -> {
                    openHome()
                    drawerLayout.closeDrawers()
                }
                com.ayush.cuevents.R.id.favorite -> {
                    supportFragmentManager.beginTransaction().replace(
                        com.ayush.cuevents.R.id.framelayout,
                        FavoriteFragment()
                    ).commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title = "Favorites"
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    private fun openLoginBottomSheet(context:Context) {
        val bottomSheetDialog = BottomSheetDialog(context, com.ayush.cuevents.R.style.AppBottomSheetDialogTheme)
        bottomSheetDialog.setContentView(com.ayush.cuevents.R.layout.login_modal_bottomsheet)

        bottomSheetDialog.show()
    }

    fun setuptoolbar(){
        setSupportActionBar(findViewById(com.ayush.cuevents.R.id.toolbar))
        supportActionBar?.title="Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun openHome(){
        supportFragmentManager.beginTransaction().replace(com.ayush.cuevents.R.id.framelayout, HomeFragment()).commit()
        supportActionBar?.title="Home"
        navigationView.setCheckedItem(com.ayush.cuevents.R.id.home)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        if(id==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val frag=supportFragmentManager.findFragmentById(com.ayush.cuevents.R.id.framelayout)
        when(frag){
            !is HomeFragment -> openHome()
            else->super.onBackPressed()
        }
    }
}