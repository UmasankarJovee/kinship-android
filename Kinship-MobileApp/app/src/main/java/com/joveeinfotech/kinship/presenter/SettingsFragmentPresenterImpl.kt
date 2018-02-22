package com.joveeinfotech.kinship.presenter


import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import com.joveeinfotech.kinship.APICall
import com.joveeinfotech.kinship.LanguageListDialogFragment
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.R.string.*
import com.joveeinfotech.kinship.adapter.CustomeAdapter
import com.joveeinfotech.kinship.adapter.LanguageListAdapter
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.model.Album
import com.joveeinfotech.kinship.model.Languages

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

    constructor(context: Context, listener: LanguageListener){
        languageContext=context
        languageListeners=listener
    }

    constructor(view:SettingsFragmentView, context: Context,listener:Listener){
        this.mContext=context
        settingsFragmentView=view
        listeners=listener
        initPresenter()
    }

    override fun initPresenter() {
        Log.e("Message","Before items" )
        val items=ArrayList<Album>()

        items.add(Album(BitmapFactory.decodeResource(mContext?.resources,R.mipmap.edit_profile), mContext!!.getString(settingsfragment_cardview1_textview)))
        items.add(Album(BitmapFactory.decodeResource(mContext?.resources,R.mipmap.notifications), mContext!!.getString(settingsfragment_cardview2_textview)))
        items.add(Album(BitmapFactory.decodeResource(mContext?.resources,R.mipmap.language), mContext!!.getString(settingsfragment_cardview3_textview)))
        Log.e("Message","Before call CustomeAdapter class" )
        val adapter= CustomeAdapter(items,listeners!!,mContext!!)
        Log.e("Message","After call CustomeAdapter class" )
        settingsFragmentView?.ReceiveCustomeAdapter(adapter)
    }

    override fun getLanguagesData(): LanguageListAdapter {

        val items=ArrayList<Languages>()
        items.add(Languages("Tamil","English","Hindi"))
        Log.e("Message","Before call LanguageListAdapter class" )
        val adapter= LanguageListAdapter(items,languageListeners!!)
        Log.e("Message","After call LanguageListAdapter class" )
        return adapter
    }


}