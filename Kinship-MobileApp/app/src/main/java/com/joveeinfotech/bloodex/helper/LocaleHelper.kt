package com.joveeinfotech.bloodex.helper

import android.content.Context
import android.util.Log
import java.util.*

/**
 * Created by shanmugarajjoveeinfo on 24/2/18.
 */
object LocaleHelper {
    fun setLocale(context:Context,language: String):Context {
        Log.e("Message","setLocale After")
        val mylocale= Locale(language)
        val dm=context.resources.displayMetrics
        val conf=context.resources.configuration
        conf.locale=mylocale
        context.resources.updateConfiguration(conf,dm)
        return context
    }
}