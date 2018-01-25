package com.example.elcot.kinship2

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {

    var select : Fragment? = null
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                select=HomeFragment.newInstance()
                //return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_request -> {
                select=RequestFragment.newInstance()
                //return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_setting -> {
                select=SettingsFragment.newInstance()
                //return@OnNavigationItemSelectedListener true
            }
        }
        val trans = supportFragmentManager.beginTransaction()
        trans.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out)
        trans.replace(R.id.frame_layout,select)
        trans.commit()
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val trans = supportFragmentManager.beginTransaction()
        trans.replace(R.id.frame_layout,HomeFragment.newInstance())
        trans.commit()
    }
}
