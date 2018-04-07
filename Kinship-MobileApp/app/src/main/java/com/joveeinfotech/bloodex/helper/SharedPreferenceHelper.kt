package com.joveeinfotech.bloodex.helper

import android.content.Context
import android.content.SharedPreferences
import com.joveeinfotech.bloodex.`object`.CommonKeys

/**
 * Created by shanmugarajjoveeinfo on 19/2/18.
 */
object SharedPreferenceHelper {
    fun getStringPreference(context: Context, key:String, defaultValue:String):String?{
        return getPreference(context).getString(key,defaultValue)
    }

    fun setStringPreference(context: Context, key:String, value:String){
        getPreference(context).edit().putString(key,value).apply()
    }

    fun getBooleanPreference(context: Context, key:String, default_value: Boolean?):Boolean{
        return getPreference(context).getBoolean(key,default_value!!)
    }

    fun setBooleanPreference(context: Context, key:String, value: Boolean?){
        getPreference(context).edit().putBoolean(key,value!!).apply()
    }

    fun getIntPreference(context: Context, key:String, defaultValue: Int):Int{
        return getPreference(context).getString(key,defaultValue.toString()).toInt()
    }

    fun setIntPreference(context: Context, key:String, value: Int){
        getPreference(context).edit().putString(key,value.toString()).apply()
    }

    fun getLongPreference(context: Context, key:String, defaultValue: Long):Long{
        return getPreference(context).getLong(key,defaultValue)
    }

    fun setLongPreference(context: Context, key:String, value: Long){
        getPreference(context).edit().putLong(key,value).apply()
    }

    fun removeKey(context: Context, key:String){
        getPreference(context).edit().remove(key).apply()
    }

    fun getPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(CommonKeys.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        //context.getSharedPreferences(CommonKeys.SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE)
    }
}