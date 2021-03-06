package com.joveeinfotech.bloodex.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.joveeinfotech.bloodex.view.UserDetails
import com.joveeinfotech.bloodex.view.Login
import java.util.HashMap

/**
 * Created by prandex-and-05 on 26/1/18.
 */
class SharedData {

    var pref: SharedPreferences? = null

    // Editor for Shared preferences
    var editor: SharedPreferences.Editor? = null

    // Context
    var _context: Context? = null

    // Shared pref mode
    var PRIVATE_MODE = 0

    // Sharedpref file name
    private val PREF_NAME = "kinshipUser"

    // All Shared Preferences Keys
    private val IS_LOGIN = "IsLoggedIn"
    private val IS_FIRST_INSTALL = "IsFirstInstall"

    private val IS_SEARCH = "IsSearch"
    private val SEARCH_OPTION = "SearchOption"

    val KEY_USER_NAME = "user_name"

    val KEY_USER_ID = "user_id"

    val KEY_UNITS = "units"

    @SuppressLint("CommitPrefEdits")

    constructor(context: Context) {
        this._context = context
        pref = _context!!.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref?.edit()
    }

    fun createLoginSession(user_id: String, user_name: String) {

        editor?.putBoolean(IS_LOGIN, true)

        editor?.putString(KEY_USER_ID, user_id)

        editor?.putString(KEY_USER_NAME, user_name)

        editor?.commit()
    }

    fun createFirstInstall() {
        editor?.putBoolean(IS_FIRST_INSTALL, false)
        editor?.commit()
    }
    fun createFirstInstallSetFalse() {
        editor?.putBoolean(IS_FIRST_INSTALL, false)
        editor?.commit()
    }
    fun createFirstSearch(option : String) {
        editor?.putBoolean(IS_SEARCH, true)
        editor?.putString(SEARCH_OPTION,option)
        editor?.commit()
    }
    fun createFirstSearchSetFalse() {
        editor?.putBoolean(IS_SEARCH, false)
        editor?.commit()
    }

    /*fun storeDetails(units : Int){
        editor?.putInt(KEY_UNITS,units)
        editor?.commit()
    }
    fun retrieveDetails() : Int{
        return pref!!.getInt(KEY_UNITS,0)
    }
    fun clearSearchDetails(i :Int) {
        Log.e("SharedData : ","${retrieveDetails()}")
        editor?.putInt(KEY_UNITS,i)
        editor?.commit()
        Log.e("SharedData : ","${retrieveDetails()}")
    }*/

    fun checkLogin() {
        // Check login status
        if (this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity

            val i = Intent(_context, UserDetails::class.java)
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            // Add new Flag to start new Activity
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            // Staring Login Activity
            _context?.startActivity(i)
        }

    }

    fun getUserDetails(): HashMap<String, String> {
        val user = HashMap<String, String>()
        // user name
        user.put(KEY_USER_NAME, pref!!.getString(KEY_USER_NAME, null))
        // user email id
        user.put(KEY_USER_ID, pref!!.getString(KEY_USER_ID, null))

        return user
    }

    fun getSearchOption() : String{
        return pref!!.getString(SEARCH_OPTION,null)
    }

    fun logoutUser() {
        // Clearing all data from Shared Preferences
        editor?.clear()
        editor?.commit()
        // After logout redirect user to Loing Activity
        val i = Intent(_context, Login::class.java)
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        // Add new Flag to start new Activity
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        // Staring Login Activity
        _context?.startActivity(i)
    }

    fun isLoggedIn(): Boolean {
        return pref!!.getBoolean(IS_LOGIN, false)
    }

    fun isFirstInstall(): Boolean {
        return pref!!.getBoolean(IS_FIRST_INSTALL,true)
    }

    fun isEnterIntoSearch(): Boolean {
        return pref!!.getBoolean(IS_SEARCH,false)
    }
}