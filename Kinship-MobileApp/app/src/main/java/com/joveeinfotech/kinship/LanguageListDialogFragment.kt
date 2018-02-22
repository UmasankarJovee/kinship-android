package com.joveeinfotech.kinship

import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
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
import com.joveeinfotech.kinship.model.Languages
import com.joveeinfotech.kinship.presenter.SettingsFragmentPresenterImpl
import com.joveeinfotech.kinship.presenter.SettingsFragmentPresenterImpl.*
import kotlinx.android.synthetic.main.alert_language_settings.view.*

/**
 * Created by shanmugarajjoveeinfo on 20/2/18.
 */
class LanguageListDialogFragment:DialogFragment(),LanguageListener{

    lateinit var lLDFContext: Context
    var onfirmDialog: View?=null
    var alertDialog:AlertDialog?=null
    var alert:AlertDialog.Builder?=null
    public var cancelButton : Button? = null
    public var okButton : Button? = null
    var view1 : View? = null
    override fun onAttach(context: Context) {
        this.lLDFContext=context
        super.onAttach(context)
    }
    var sd : SettingsFragmentPresenterImpl? = null
    var settingsFragment : SettingsFragment? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        view1 =inflater!!.inflate(R.layout.alert_language_settings,null)
        dialog.window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        sd = SettingsFragmentPresenterImpl(lLDFContext,this)
        settingsFragment = SettingsFragment()
        view1?.alert_language_settings_cardView_constraintLayout_scrollView_recyclerView?.layoutManager = LinearLayoutManager(lLDFContext, LinearLayout.VERTICAL, false)
        view1?.alert_language_settings_cardView_constraintLayout_scrollView_recyclerView?.adapter=sd?.getLanguagesData()
        cancelButton=view1?.findViewById(R.id.alert_language_settings_cardView_constraintLayout_cancelButton)
        okButton=view1?.findViewById(R.id.alert_language_settings_cardView_constraintLayout_okButton)
        cancelButton?.setOnClickListener {
           dismiss()
        }
        return view1!!
    }
    override fun onLanguageClick(data: String) {
        /*settingsFragment?.onLanguageClicks(data)*/
        Log.e("Message", "onItemClicks function")
        if(data == "Tamil")Toast.makeText(lLDFContext, "${data} Clicked !", Toast.LENGTH_SHORT).show()
        else if(data == "English")Toast.makeText(lLDFContext, "${data} Clicked !", Toast.LENGTH_SHORT).show()
        else Toast.makeText(lLDFContext, "${data} Clicked !", Toast.LENGTH_SHORT).show()
    }
}