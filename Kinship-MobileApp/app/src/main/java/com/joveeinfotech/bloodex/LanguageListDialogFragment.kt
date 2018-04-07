package com.joveeinfotech.bloodex

import android.app.DialogFragment
import android.support.v4.app.FragmentTransaction
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.joveeinfotech.bloodex.contract.KinshipContract.LanguageListener
import com.joveeinfotech.bloodex.helper.LocaleHelper
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.setStringPreference
import com.joveeinfotech.bloodex.presenter.SettingsFragmentPresenterImpl
import com.joveeinfotech.bloodex.utils.Others.DLog
import kotlinx.android.synthetic.main.alert_language_settings.view.*

/**
 * Created by shanmugarajjoveeinfo on 20/2/18.
 */
class LanguageListDialogFragment(var trans : FragmentTransaction,var mContext: Context) :DialogFragment(),LanguageListener {

    var cancelButton: Button? = null
    var okButton: Button? = null
    var view1: View? = null
    var settingsFragmentPresenterImpl: SettingsFragmentPresenterImpl? = null
    var settingsFragment: SettingsFragment? = null
    var getLanguage: Int = 0
    val language: String? = null
    /*var languageToLoad:String?=null
    lateinit var locale:Locale
    lateinit var config:Configuration

    val languages = arrayOf("English", "தமிழ்", "हिंदी")
    val languages_code = arrayOf("en", "ta", "hi")*/
    /* var lLDFContext:Context?=null
    override fun onAttach(lLDFContext: Context) {
        this.lLDFContext=lLDFContext
        super.onAttach(lLDFContext)
    }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        view1 = inflater.inflate(R.layout.alert_language_settings, null)
        dialog.window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        settingsFragmentPresenterImpl = SettingsFragmentPresenterImpl(mContext, this)
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
        view1?.alert_language_settings_cardView_constraintLayout_listView?.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        view1?.alert_language_settings_cardView_constraintLayout_listView?.adapter = settingsFragmentPresenterImpl?.getLanguagesData()

        /*cancelButton=view1?.findViewById(R.id.alert_language_settings_cardView_constraintLayout_cancelButton)
        okButton=view1?.findViewById(R.id.alert_language_settings_cardView_constraintLayout_okButton)
        cancelButton?.setOnClickListener {

        //cancelButton=view1?.findViewById(R.id.alert_language_settings_cardView_constraintLayout_cancelButton)
        //okButton=view1?.findViewById(R.id.alert_language_settings_cardView_constraintLayout_okButton)
        *//*cancelButton?.setOnClickListener {

           dismiss()
        }*//*
        *//*okButton?.setOnClickListener {
            Toast.makeText(activity, "${getLanguage} Clicked !", Toast.LENGTH_SHORT).show()
            dismiss()
            //var mContext = settingsFragment!!.getContext1()
            when(getLanguage){
                0 ->{
                    Toast.makeText(activity, "${getLanguage} Clicke", Toast.LENGTH_SHORT).show()
                    setStringPreference(mContext,"selectLanguageString","Tamil")
                    LocaleHelper.setLocale(mContext,"ta")
                    //settingsFragment!!.setCreate(mContext,inflater,container,savedInstanceState)
                    DLog("settingsFragment : ","setcreate1")
                    call1().call2(trans)
                }
                1 ->{
                    //Toast.makeText(activity, "${getLanguage} Clicke", Toast.LENGTH_SHORT).show()
                    setStringPreference(mContext,"selectLanguageString","English")
                    //LocaleHelper.setLocale(mContext,"ta")
                    //settingsFragment!!.setCreate(mContext,inflater,container,savedInstanceState)
                    //DLog("settingsFragment : ","setcreate1")
                    //call1().call2(trans)
                    //dismiss()
                }
                2 ->{
                    //Toast.makeText(activity, "${getLanguage} Clicke", Toast.LENGTH_SHORT).show()
                    setStringPreference(mContext,"selectLanguageString","Hindi")
                    //LocaleHelper.setLocale(mContext,"ta")
                    //settingsFragment!!.setCreate(mContext,inflater,container,savedInstanceState)
                    //DLog("settingsFragment : ","setcreate1")
                    //call1().call2(trans)
                }
            }
        }*/
        return view1!!
}
    /*fun setLocale(lang:String){
        val myLocale=Locale(lang)
        val dm=resources.displayMetrics
        val conf=resources.configuration
        conf.locale=myLocale
        resources.updateConfiguration(conf,dm)
        onConfigurationChanged(conf)
    }*/
    override fun onLanguageClick(data: Int) {
        //*settingsFragment?.onLanguageClicks(data)*//
        Log.e("Message", "onItemClicks function")
        if (data == 0) {
            Toast.makeText(activity, "Tamil Clicked !", Toast.LENGTH_SHORT).show()
            LocaleHelper.setLocale(mContext, "ta")
            setStringPreference(mContext, "selectLanguageString", "Tamil")
            getLanguage = 0
            /* val ft = fragmentManager?.beginTransaction()
            ft?.detach(this)?.attach(this)?.commit()*/

            dismiss()
            call1().call2(trans)
            /*val mydialog = LanguageListDialogFragment(trans!!,mContext)
            mydialog.setCancelable(true)
            mydialog.show(activity?.fragmentManager, "tag")*/
        } else if (data == 1) {
            Toast.makeText(activity, "English Clicked !", Toast.LENGTH_SHORT).show()
            LocaleHelper.setLocale(mContext, "en")
            //dismiss()
            setStringPreference(mContext, "selectLanguageString", "English")
            getLanguage = 1
            /*val ft = fragmentManager?.beginTransaction()
            ft?.detach(this)?.attach(this)?.commit()*/

            dismiss()
            call1().call2(trans)
            /*val mydialog = LanguageListDialogFragment(trans!!,mContext)
            mydialog.setCancelable(true)
            mydialog.show(activity?.fragmentManager, "tag")*/
        } else {
            Toast.makeText(activity, "Hindi Clicked !", Toast.LENGTH_SHORT).show()
            LocaleHelper.setLocale(mContext, "hi")
            dismiss()
            setStringPreference(mContext, "selectLanguageString", "Hindi")
            getLanguage = 2
            /*val ft = fragmentManager?.beginTransaction()
            ft?.detach(this)?.attach(this)?.commit()*/

            dismiss()
            call1().call2(trans)
            /*val mydialog = LanguageListDialogFragment(trans!!,mContext)
            mydialog.setCancelable(true)
            mydialog.show(activity?.fragmentManager, "tag")*/
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        DLog("settingsFragment : ", "setcreate1")

    }
}
class call1 {

    fun call2(trans: FragmentTransaction) {
        var fragment = SettingsFragment()
        //val trans = getragmentManager?.beginTransaction()
        DLog("settingsFragment : ","setcreate2")
        //trans?.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        trans?.replace(R.id.activity_home_frame_layout,SettingsFragment.newInstance())
        DLog("settingsFragment : ","setcreate3")
        trans?.commit()
        DLog("settingsFragment : ","setcreate4")
    }
}
