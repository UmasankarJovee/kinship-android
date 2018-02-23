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
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.joveeinfotech.kinship.adapter.LanguageListAdapter
import com.joveeinfotech.kinship.contract.KinshipContract.LanguageListener
import com.joveeinfotech.kinship.helper.SharedPreferenceHelper.getStringPreference
import com.joveeinfotech.kinship.helper.SharedPreferenceHelper.setStringPreference
import com.joveeinfotech.kinship.model.Languages
import com.joveeinfotech.kinship.presenter.SettingsFragmentPresenterImpl
import com.joveeinfotech.kinship.presenter.SettingsFragmentPresenterImpl.*
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
    var languageToLoad:String?=null
    lateinit var locale:Locale
    lateinit var config:Configuration

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        view1 =inflater!!.inflate(R.layout.alert_language_settings,null)
        dialog.window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        sd = SettingsFragmentPresenterImpl(activity,this)
        settingsFragment = SettingsFragment()
        view1?.alert_language_settings_cardView_constraintLayout_scrollView_recyclerView?.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        view1?.alert_language_settings_cardView_constraintLayout_scrollView_recyclerView?.adapter=sd?.getLanguagesData()
        cancelButton=view1?.findViewById(R.id.alert_language_settings_cardView_constraintLayout_cancelButton)
        okButton=view1?.findViewById(R.id.alert_language_settings_cardView_constraintLayout_okButton)
        cancelButton?.setOnClickListener {
           dismiss()
        }
        okButton?.setOnClickListener {
            Toast.makeText(activity, "${getLanguage} Clicked !", Toast.LENGTH_SHORT).show()
            when(getLanguage){
                "Tamil" ->{
                    Toast.makeText(activity, "${getLanguage} Clicke", Toast.LENGTH_SHORT).show()
                            languageToLoad = "ta" // your language
	                        locale =Locale(languageToLoad)
	                        Locale.setDefault(locale)
	                        config =Configuration()
	                        config.locale = locale
	                        activity.getResources().updateConfiguration(config,activity.getResources().getDisplayMetrics())
	                        //activity.setContentView(R.layout.fragment_settings)
                    dismiss()
                }

            }
        }
        return view1!!
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