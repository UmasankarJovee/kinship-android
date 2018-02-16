package com.joveeinfotech.kinship

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import kotlinx.android.synthetic.main.activity_home.*
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.joveeinfotech.kinship.utils.LocationService
import com.joveeinfotech.kinship.utils.SharedData
import kinship.joveeinfotech.kinship.*

//import javax.swing.text.StyleConstants.setIcon

class Home : AppCompatActivity() {
    var session: SharedData? = null
    var select: Fragment? = null
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                select = HomeFragment.newInstance()
                //return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_request -> {
                select = RequestFragment.newInstance()
                //return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_setting -> {
                select = SettingsFragment.newInstance()
                //return@OnNavigationItemSelectedListener true
            }
        }
        val trans = supportFragmentManager.beginTransaction()
        trans.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        trans.replace(R.id.frame_layout, select)
        trans.commit()
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setLogo(R.mipmap.home_logo)
        supportActionBar!!.setDisplayUseLogoEnabled(true)
        supportActionBar!!.setTitle("Kinship")

        session = SharedData(this)

        //startAlert()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val trans = supportFragmentManager.beginTransaction()
        trans.replace(R.id.frame_layout, HomeFragment.newInstance())
        trans.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu)
        CreateMenu(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return MenuChoice(item)
    }

    private fun CreateMenu(menu: Menu) {
        val mnu1 = menu.add(0, 0, 0, "Edit Profile")
        run {
            //mnu1.setIcon(R.drawable.ic_launcher);
            //mnu1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        val mnu2 = menu.add(0, 1, 0, "Logout")
        run {
            //mnu1.setIcon(R.drawable.ic_launcher);
            //mnu1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    private fun MenuChoice(item: MenuItem): Boolean {
        when (item.itemId) {
            0 -> {
                //Toast.makeText(this, "You clicked on Item 1",Toast.LENGTH_LONG).show()
                val i = Intent(applicationContext, UserProfileEdit::class.java)
                startActivity(i)
                return true
            }
            1 -> {
                //Toast.makeText(this, "You clicked on Item 1",Toast.LENGTH_LONG).show()
                session?.logoutUser()
                session?.createFirstInstallSetFalse()
                finish()
                return true
            }
        }
        return false
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    fun startAlert() {
        //val text = findViewById(R.id.time) as EditText
        //val i = Integer.parseInt(text.text.toString())
        val intent = Intent(this, LocationService::class.java)
        val pendingIntent = PendingIntent.getService(this.applicationContext, 234324243, intent, 0)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10 * 1000, 10000, pendingIntent)
        Toast.makeText(this, "Alarm after 5 seconds", Toast.LENGTH_SHORT).show()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
    }
}