package com.joveeinfotech.kinship

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.joveeinfotech.kinship.adapter.CustomeAdapter
import com.joveeinfotech.kinship.adapter.LanguageListAdapter
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.model.Album
import com.joveeinfotech.kinship.model.Languages

import com.joveeinfotech.kinship.presenter.*


class SettingsFragment : Fragment(),Listener,SettingsFragmentView{

    private lateinit var settingsFragmentPresenterImpl:SettingsFragmentPresenterImpl
    lateinit var mContext: Context
    var recyclerView:RecyclerView?=null
    var languageRecyclerView:RecyclerView?=null
    var alertDialog:AlertDialog?=null
    var cancelButton : Button? = null
    var okButton : Button? = null

    override fun onAttach(context: Context) {
        this.mContext=context
        super.onAttach(context)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        var view:View=inflater.inflate(R.layout.fragment_settings,container,false)

        recyclerView=view.findViewById<RecyclerView>(R.id.activity_settings_fragment_RecyclerView)
        recyclerView?.layoutManager=LinearLayoutManager(mContext,LinearLayout.VERTICAL,false)
        Log.e("Message","Before SettingsFragmentImpl")
        settingsFragmentPresenterImpl=SettingsFragmentPresenterImpl(this,mContext,this)
        return view
    }

    override fun ReceiveCustomeAdapter(adapter: CustomeAdapter) {
        Log.e("Message","ReceiveCustomeAdapter" )
        recyclerView?.adapter=adapter
    }
    override fun onItemClick(data: Album) {
        Log.e("Message","onItemClick function" )
        Toast.makeText(mContext, "${data.text} Clicked !", Toast.LENGTH_SHORT).show()
    }

    override fun displayResult(result: Boolean) {
        if(result == true)Toast.makeText(mContext,"Notifications is On",Toast.LENGTH_SHORT).show()
        else Toast.makeText(mContext,"Notifications is Off",Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun languageSettings(){
        Log.e("Message","Before LanguageInflater" )
        val li=LayoutInflater.from(mContext)
        val confirmDialog = li.inflate(R.layout.alert_language_settings,null)
        cancelButton = confirmDialog.findViewById<Button>(R.id.alert_language_settings_cardView_constraintLayout_cancelButton)
        okButton = confirmDialog.findViewById<Button>(R.id.alert_language_settings_cardView_constraintLayout_okButton)

        val alert = AlertDialog.Builder(mContext)
        alert.setView(confirmDialog)

        alertDialog = alert.create()
        alertDialog?.show()
        languageRecyclerView=confirmDialog.findViewById<RecyclerView>(R.id.alert_language_settings_cardView_constraintLayout_scrollView_recyclerView)
        languageRecyclerView?.layoutManager=LinearLayoutManager(mContext,LinearLayout.VERTICAL,false)
        settingsFragmentPresenterImpl.getLanguagesData()
    }

    override fun ReceiveLanguageListAdapter(adapter: LanguageListAdapter) {
        languageRecyclerView?.adapter=adapter
        alertDialog?.setCancelable(false)

        cancelButton!!.setOnClickListener{
            alertDialog?.dismiss()
        }
        okButton!!.setOnClickListener {
        }
    }
    override fun onItemClicks(data: Languages) {
        Log.e("Message","onItemClick function" )
        Toast.makeText(mContext, "${data.tamil_language}${data.english_language}${data.hindi_language} Clicked !", Toast.LENGTH_SHORT).show()
    }
    companion object {
        fun newInstance() : SettingsFragment {
            return SettingsFragment()
        }
    }
}
