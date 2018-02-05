package com.example.jovee_infotech.kinship

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    var select : Fragment?=null
    private val mOnNavigationItemSelectedListsner=BottomNavigationView.OnNavigationItemSelectedListener { item ->

            when(item.itemId) {
                R.id.navigation_home -> {

                    select = FragmentHome.newInstance()
                    //return true
                }
                R.id.navigation_blood_request -> {
                    select = FragmentBloodRequest.newInstance()
                    //return true
                }
                R.id.navigation_settings -> {
                    select = FragmentSettings.newInstance()
                    //return true
                }
            }
             val trans=supportFragmentManager.beginTransaction()
             trans.replace(R.id.activity_home_framelayout,select)
             trans.commit()
             true
    }
    /*private fun addFragment(fragment:Fragment){
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.activity_home_framelayout,fragment,fragment.javaClass.getSimpleName())
                .addToBackStack(fragment.javaClass.getSimpleName())
                .commit()
    }*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListsner)

        val trans=supportFragmentManager.beginTransaction()
        trans.replace(R.id.activity_home_framelayout,FragmentHome.newInstance())
        trans.commit()
    }
}
