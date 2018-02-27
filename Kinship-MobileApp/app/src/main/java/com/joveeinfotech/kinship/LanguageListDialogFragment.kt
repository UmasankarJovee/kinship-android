package com.joveeinfotech.kinship

import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.joveeinfotech.kinship.adapter.LanguageListAdapter
import com.joveeinfotech.kinship.contract.KinshipContract.LanguageListener
import com.joveeinfotech.kinship.helper.LocaleHelper
import com.joveeinfotech.kinship.helper.SharedPreferenceHelper.getStringPreference
import com.joveeinfotech.kinship.helper.SharedPreferenceHelper.setStringPreference
import com.joveeinfotech.kinship.model.Languages
import com.joveeinfotech.kinship.presenter.SettingsFragmentPresenterImpl
import com.joveeinfotech.kinship.presenter.SettingsFragmentPresenterImpl.*
import kotlinx.android.synthetic.main.alert_language_settings.*
import kotlinx.android.synthetic.main.alert_language_settings.view.*
import java.util.*

/**
 * Created by shanmugarajjoveeinfo on 20/2/18.
 */
class LanguageListDialogFragment:DialogFragment(),LanguageListener{

    var cancelButton : Button? = null
    var okButton : Button? = null
    var view1 : View? = null
    var sd : SettingsFragmentPresenterImpl? = null
    var settingsFragment : SettingsFragment? = null
    var getLanguage:String?=null
    val language:String?=null
    var languageToLoad:String?=null
    lateinit var locale:Locale
    lateinit var config:Configuration

    val languages = arrayOf("English", "தமிழ்", "हिंदी")
    val languages_code = arrayOf("en", "ta", "hi")

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        view1 =inflater!!.inflate(R.layout.alert_language_settings,null)
        dialog.window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        sd = SettingsFragmentPresenterImpl(activity,this)
        settingsFragment = SettingsFragment()
        /*alert_language_settings_cardView_constraintLayout_spinner?.adapter=ArrayAdapter<String>(activity,R.layout.alert_language_settings,languages)as SpinnerAdapter?

        alert_language_settings_cardView_constraintLayout_spinner?.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                setLocale(languages_code.get(p2))
            }
        }*/
        //view1?.alert_language_settings_cardView_constraintLayout_scrollView_recyclerView?.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        //view1?.alert_language_settings_cardView_constraintLayout_scrollView_recyclerView?.adapter=sd?.getLanguagesData()
        /*cancelButton=view1?.findViewById(R.id.alert_language_settings_cardView_constraintLayout_cancelButton)
        okButton=view1?.findViewById(R.id.alert_language_settings_cardView_constraintLayout_okButton)
        cancelButton?.setOnClickListener {
           dismiss()
        }
        okButton?.setOnClickListener {
            Toast.makeText(activity, "${getLanguage} Clicked !", Toast.LENGTH_SHORT).show()
            when(getLanguage){
                "Tamil" ->{
                    Toast.makeText(activity, "${getLanguage} Clicke", Toast.LENGTH_SHORT).show()
                    LocaleHelper.setLocale(activity,getLanguage)
                }

            }
        }*/
        return view1!!
    }
    fun setLocale(lang:String){
        val myLocale=Locale(lang)
        val dm=resources.displayMetrics
        val conf=resources.configuration
        conf.locale=myLocale
        resources.updateConfiguration(conf,dm)
        onConfigurationChanged(conf)
    }
    override fun onLanguageClick(data: String) {
        /*settingsFragment?.onLanguageClicks(data)*/
        Log.e("Message", "onItemClicks function")
        if(data == "Tamil") {
            Toast.makeText(activity, "${data} Clicked !", Toast.LENGTH_SHORT).show()
            getLanguage=data
        }
        else if(data == "English")
        {
            Toast.makeText(activity, "${data} Clicked !", Toast.LENGTH_SHORT).show()
            getLanguage=data
        }
        else
        {
            Toast.makeText(activity, "${data} Clicked !", Toast.LENGTH_SHORT).show()
            getLanguage=data
        }
    }
}