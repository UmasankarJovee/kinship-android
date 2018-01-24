package com.example.elcot.kinship2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {

    var transaction: FragmentTransaction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        /* fadein = AnimationUtils.loadAnimation(applicationContext,
              R.anim.fade_in)
      fadeout = AnimationUtils.loadAnimation(applicationContext,
              R.anim.fade_out)

      val bottomNavigationView = findViewById(R.id.navigation) as BottomNavigationView

      bottomNavigationView.setOnNavigationItemSelectedListener { item ->
          var selectedFragment: Fragment? = null
          when (item.itemId) {
              R.id.home -> selectedFragment = HomeFragment.newInstance()
              R.id.request -> selectedFragment = RequestFragment.newInstance()
              R.id.settings -> selectedFragment = SettingsFragment.newInstance()
          }
          val transaction = supportFragmentManager.beginTransaction()
          transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
          transaction.replace(R.id.frame_layout, selectedFragment)
          transaction.commit()
          true
      }

      //Manually displaying the first fragment - one time only
      val transaction = supportFragmentManager.beginTransaction()
      transaction.replace(R.id.frame_layout, HomeFragment.newInstance())
      transaction.commit()  */

        /*navigation.setOnNavigationItemSelectedListener{
            item ->
                when(item.itemId){
                    R.id.navigation_home -> val transaction = supportFragmentManager.beginTransaction()
                    }
            true
                }
        }*/


}
}
