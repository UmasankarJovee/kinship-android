package com.joveeinfotech.bloodex

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import kotlinx.android.synthetic.main.activity_home.*
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioButton
import com.joveeinfotech.bloodex.`object`.BottomNavigationHelper
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.getStringPreference
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.setBooleanPreference

import com.joveeinfotech.bloodex.utils.LocationService
import com.joveeinfotech.bloodex.utils.SharedData
import com.joveeinfotech.bloodex.view.*
import com.joveeinfotech.bloodex.view.Top20Fragment


import kinship.joveeinfotech.kinship.*

//import javax.swing.text.StyleConstants.setIcon

class Home : AppCompatActivity() {

    var button_next : AppCompatButton? = null
    var radio_your : RadioButton? = null
    var radio_some_one : RadioButton? = null
    var search_option : String? = null

    var session: SharedData? = null
    var select : Fragment? = null

    var user_id : String? = null
    var userOptionString : String? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                select= HomeFragment.newInstance()
                goToSelectFragment()
                //return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_blood_request -> {
                getSearchOption()

                //return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                //select= SettingsFragment.newInstance()
                //goToSelectFragment()
                userOption()
                //return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_donors -> {
                select= DonarsFragment.newInstance()
                goToSelectFragment()
            }

            R.id.navigation_top20 -> {
                select= Top20Fragment.newInstance()
                goToSelectFragment()
            }
        }
        true
    }

    private fun userOption() {
        //user_id = getStringPreference(this, "user_id", "")
        userOptionString = getStringPreference(this, "userOptionString${user_id}", "")
        if(userOptionString == "Requestor"){
            val i = Intent(applicationContext,UserDetails::class.java)
            startActivity(i)
        }
    }

    private fun goToSelectFragment() {
        val trans = supportFragmentManager.beginTransaction()
        trans.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        trans.replace(R.id.activity_home_frame_layout, select)
        trans.commit()
    }

    private fun getSearchOption() {
        session = SharedData(this)
        if(session?.isEnterIntoSearch()!!){
            var option = session?.getSearchOption()
            if(option.equals("your")){
                //goToPreviousSelectFragment()
                val trans = supportFragmentManager.beginTransaction()
                trans.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out)
                trans.replace(R.id.activity_home_frame_layout, UserRequestFragment.newInstance())
                trans.commit()
            } else {
                val trans = supportFragmentManager.beginTransaction()
                trans.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out)
                trans.replace(R.id.activity_home_frame_layout, SomeOneRequestFragment.newInstance())
                trans.replace(R.id.activity_home_frame_layout,SomeOneRequestFragment.newInstance())
                trans.commit()
            }
        }
        if(!session?.isEnterIntoSearch()!!){
            val li = LayoutInflater.from(this)
            val confirmDialog = li.inflate(com.joveeinfotech.bloodex.R.layout.alert_search_option_get, null)
            button_next = confirmDialog.findViewById<AppCompatButton>(R.id.button_search_next) as AppCompatButton
            radio_your = confirmDialog.findViewById<RadioButton>(R.id.radio_your_need) as RadioButton
            radio_some_one = confirmDialog.findViewById<RadioButton>(R.id.radio_some_one_need) as RadioButton

            val alert = AlertDialog.Builder(this)
            alert.setView(confirmDialog)

            val alertDialog = alert.create()
            alertDialog.show()
            alertDialog.setCancelable(false)

            radio_your?.setOnClickListener{
                radio_some_one?.isChecked = false
                search_option = "your"
            }
            radio_some_one?.setOnClickListener{
                radio_your?.isChecked = false
                search_option = "some_one"
            }

            button_next?.setOnClickListener {

                if(search_option.equals("your")){
                    select = UserRequestFragment.newInstance()
                } else {
                    select = SomeOneRequestFragment.newInstance()
                }
                alertDialog.dismiss()
                session?.createFirstSearch(this.search_option!!)
                goToSelectFragment()
            }
        }
    }

    /*var mActionBar: ActionBar? = null
    var actionBar: View? = null
    var mTitleTextView: TextView? = null
    var rightBmb: BoomMenuButton? = null
    var mContext: Context? = null
    var isInit: Boolean? = false*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        BottomNavigationHelper.disableShiftMode(activity_login_navigation_bottomNavigationView)
        /*supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setLogo(R.mipmap.home_logo)
        supportActionBar!!.setDisplayUseLogoEnabled(true)
        supportActionBar!!.setTitle("Kinship")*/
        //boomMenu()
        //startAlert()

        //permissions()




        session = SharedData(this)
        activity_login_navigation_bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val trans = supportFragmentManager.beginTransaction()
        trans.replace(R.id.activity_home_frame_layout, HomeFragment.newInstance())
        trans.commit()
    }



    private fun proceedAfterPermission() {

    }

    /*private fun boomMenu() {
        mContext = this@Home

        mActionBar = supportActionBar
        assert(mActionBar != null)
        mActionBar?.setDisplayShowHomeEnabled(false)
        mActionBar?.setDisplayShowTitleEnabled(false)
        val mInflater = LayoutInflater.from(this)

        actionBar = mInflater.inflate(R.layout.custom_actionbar, null)
        mTitleTextView = actionBar?.findViewById<TextView>(R.id.title_text) as TextView
        mTitleTextView?.setText("Kinship")
        mActionBar?.setCustomView(actionBar)
        mActionBar?.setDisplayShowCustomEnabled(true)
        (actionBar?.getParent() as Toolbar).setContentInsetsAbsolute(0, 0)

        rightBmb = actionBar?.findViewById<BoomMenuButton>(R.id.action_bar_right_bmb) as BoomMenuButton
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (isInit == true) {
            initInfoBoom()
        }
        isInit = true
    }

    private fun initInfoBoom() {

        val drawables = arrayOfNulls<Drawable>(3)
        val drawablesResources = intArrayOf(
                R.mipmap.icon_profile1,
                R.mipmap.icon_blood_request,
                R.mipmap.icon_logout
        )
        for (i in 0..2) {
            drawables[i] = ContextCompat.getDrawable(mContext!!, drawablesResources[i])
        }

        val colors = Array(3) { IntArray(2) }
        for (i in 0..2) {
            colors[i][1] = ContextCompat.getColor(mContext!!, R.color.white)
            colors[i][0] = Util.getInstance().getPressedColor(colors[i][1])
        }

        BoomMenuButton.Builder()
                .subButtons(drawables, colors, arrayOf("Profile", "Request History", "Logout"))
                .button(ButtonType.HAM)
                .boom(BoomType.PARABOLA_2)
                .place(PlaceType.HAM_3_1)
                .subButtonsShadow(Util.getInstance().dp2px(2f), Util.getInstance().dp2px(2f))
                .subButtonTextColor(ContextCompat.getColor(mContext!!, R.color.toolBarColor))
                .onSubButtonClick { buttonIndex ->
                    if (buttonIndex == 0) {
                        //Toast.makeText(mContext, "First", Toast.LENGTH_LONG).show()
                        startActivity(Intent(applicationContext, ProfileDisplay::class.java))
                    } else if (buttonIndex == 1) {
                        //Toast.makeText(mContext, "Second", Toast.LENGTH_LONG).show()
                        startActivity(Intent(applicationContext, RequestHistory::class.java))
                    } else {
                        //Toast.makeText(mContext, "Third", Toast.LENGTH_LONG).show()
                        session?.logoutUser()
                        session?.createFirstInstallSetFalse()
                        finish()
                    }
                }
                .init(rightBmb)

    }
*/

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
        val mnu1 = menu.add(0, 0, 0, "Profile")
        run {
            //mnu1.setIcon(R.drawable.ic_launcher);
            //mnu1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        val mnu2 = menu.add(0, 1, 0, "Request History")
        run {
            //mnu1.setIcon(R.drawable.ic_launcher);
            //mnu1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        val mnu3 = menu.add(0, 2, 0, "Logout")
        run {
             //mnu2.setIcon(R.drawable.ic_launcher);
             //mnu2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    private fun MenuChoice(item: MenuItem): Boolean {
        when (item.itemId) {
            0 -> {
                //Toast.makeText(this, "You clicked on Item 1",Toast.LENGTH_LONG).show()
                startActivity(Intent(applicationContext, ProfileDisplay::class.java))
                //sendNotification()
                //Toast.makeText(this, "You clicked Request History",Toast.LENGTH_LONG).show()
                return true
            }
            1 -> {
                //Toast.makeText(this, "You clicked on Item 1",Toast.LENGTH_LONG).show()
                startActivity(Intent(applicationContext, RequestHistory::class.java))
                return true
            }
            2 -> {
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
        session?.createFirstSearchSetFalse()
        //session?.clearSearchDetails(0)
        finishAffinity()
        setBooleanPreference(this, "locationPermission1", true)
    }

    fun startAlert() {
        //val text = findViewById(R.id.time) as EditText
        //val i = Integer.parseInt(text.text.toString())
        val intent = Intent(this, LocationService::class.java)
        val pendingIntent = PendingIntent.getService(this.applicationContext, 234324243, intent, 0)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10 * 1000, 10000, pendingIntent)
        //Toast.makeText(this, "Alarm after 5 seconds", Toast.LENGTH_SHORT).show()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
    }

}

