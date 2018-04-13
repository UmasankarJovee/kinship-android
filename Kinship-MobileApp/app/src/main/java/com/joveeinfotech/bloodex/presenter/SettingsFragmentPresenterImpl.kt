package com.joveeinfotech.bloodex.presenter

import android.content.Context
import android.graphics.BitmapFactory
import com.joveeinfotech.bloodex.APICall
import com.joveeinfotech.bloodex.R
import com.joveeinfotech.bloodex.R.string.*
import com.joveeinfotech.bloodex.adapter.CustomeAdapter
import com.joveeinfotech.bloodex.adapter.LanguageListsAdapter
import com.joveeinfotech.bloodex.contract.BloodExContract.*
import com.joveeinfotech.bloodex.model.Album
import com.joveeinfotech.bloodex.model.Languages
import com.joveeinfotech.bloodex.utils.Others.DLog

/**
 * Created by shanmugarajjoveeinfo on 16/2/18.
 */
class SettingsFragmentPresenterImpl:SettingsFragmentPresenter {

    private var settingsFragmentView:SettingsFragmentView? = null
    var listeners:Listener?= null
    var languageListeners:LanguageListener?= null
    var languageContext : Context? = null
    var mContext: Context?= null
    var networkCall : APICall? = null

    constructor(context: Context,listener: LanguageListener){
        languageContext=context
        languageListeners=listener
    }

    constructor(view:SettingsFragmentView, mcontext: Context,listener:Listener){
        this.mContext=mcontext
        settingsFragmentView=view
        listeners=listener
        initPresenter()
    }

    override fun initPresenter() {
        DLog("Message", "Before items")
        val items=ArrayList<Album>()

        items.add(Album(BitmapFactory.decodeResource(mContext?.resources,R.mipmap.edit_user_profile), mContext!!.getString(settingsFragment_cardView1_textView)))
        items.add(Album(BitmapFactory.decodeResource(mContext?.resources,R.mipmap.notifications), mContext!!.getString(settingsFragment_cardView2_textView)))
        items.add(Album(BitmapFactory.decodeResource(mContext?.resources,R.mipmap.language), mContext!!.getString(settingsFragment_cardView3_textView)))
        items.add(Album(BitmapFactory.decodeResource(mContext?.resources,R.mipmap.logout_icon),mContext!!.getString(settingsFragment_cardView4_textView)))
        DLog("Message","Before call CustomeAdapter class" )
        val adapter= CustomeAdapter(items,listeners!!,mContext!!)
        DLog("Message","After call CustomeAdapter class" )
        settingsFragmentView?.ReceiveCustomeAdapter(adapter)
    }

    override fun getLanguagesData(): LanguageListsAdapter{

        val items=ArrayList<Languages>()
        items.add(Languages("Tamil"))
        items.add(Languages("English"))
        items.add(Languages("Hindi"))
        DLog("Message","Before call LanguageListAdapter class" )
        val adapter= LanguageListsAdapter(languageContext!!,items,languageListeners!!)
        DLog("Message","After call LanguageListAdapter class" )
        return adapter
    }


}