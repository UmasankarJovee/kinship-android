package com.joveeinfotech.kinship

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import com.joveeinfotech.kinship.adapter.CustomeAdapter
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.helper.LocaleHelper
import com.joveeinfotech.kinship.helper.SharedPreferenceHelper.setBooleanPreference
import com.joveeinfotech.kinship.model.Album
import com.joveeinfotech.kinship.presenter.*
import com.joveeinfotech.kinship.utils.SharedData
import com.joveeinfotech.kinship.view.UserProfileEdit
import kotlinx.android.synthetic.main.fragment_settings.view.*


class SettingsFragment : Fragment(),Listener,SettingsFragmentView{

    private lateinit var settingsFragmentPresenterImpl:SettingsFragmentPresenterImpl
    lateinit var mContext: Context
    var view1:View?=null
    var select : Fragment? = null
    var session: SharedData?=null


    var trans: FragmentTransaction? = null
    var inflater : LayoutInflater? = null
    var container : ViewGroup? = null
    var savedInstanceState : Bundle? = null

    override fun onAttach(context: Context) {
        this.mContext=context
        super.onAttach(context)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)

        this.inflater = inflater
        this.container = container
        this.savedInstanceState = savedInstanceState

        trans = fragmentManager?.beginTransaction()

        view1=inflater.inflate(R.layout.fragment_settings,container,false)
        view1?.activity_settings_fragment_RecyclerView?.layoutManager=LinearLayoutManager(mContext,LinearLayout.VERTICAL,false)
        Log.e("Message","Before SettingsFragmentImpl")
        settingsFragmentPresenterImpl=SettingsFragmentPresenterImpl(this,mContext,this)
        return view1
    }

    override fun ReceiveCustomeAdapter(adapter: CustomeAdapter) {
        Log.e("Message","ReceiveCustomeAdapter" )
        view1?.activity_settings_fragment_RecyclerView?.adapter=adapter
    }

    override fun onItemClick(data: Album) {
        Log.e("Message","onItemClick function" )
        Toast.makeText(mContext, "${data.text} Clicked !", Toast.LENGTH_SHORT).show()
    }

    override fun displayResult(result: Boolean) {
        setBooleanPreference(mContext,"notification",result)
        if(result == true)Toast.makeText(mContext,"Notifications is On",Toast.LENGTH_SHORT).show()
        else Toast.makeText(mContext,"Notifications is Off",Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun languageSettings() {

        Log.e("Message","after language settings")
        /*val bundle = Bundle()
        bundle.p(trans!!)
        bundle.putString("mContext", mContext!!)*/
        val mydialog = LanguageListDialogFragment(trans!!,mContext)
        //mydialog.arguments=bundle
        mydialog.setCancelable(true)
        mydialog.show(activity?.fragmentManager, "tag")
        /*mydialog.cancelButton?.setOnClickListener {
            Toast.makeText(mContext,"hello",Toast.LENGTH_SHORT).show()
        }*/
    }

    override fun callEditProfile() {
        /*select=UserProfileEditFragment.newInstance()
        goToSelectFragment()*/
        val i = Intent(activity,UserProfileEdit::class.java)
        startActivity(i)
    }
    private fun goToSelectFragment() {
        val trans = fragmentManager?.beginTransaction()
        trans?.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        trans?.replace(R.id.activity_home_frame_layout,select)
        trans?.commit()
    }

    override fun logoutuserid() {
        /*session?.logoutUser()
        session?.createFirstInstallSetFalse()
        activity?.finish()*/

        LocaleHelper.setLocale(mContext,"ta")
        //setStringPreference(mContext,"language","en")
       /* val trans = fragmentManager?.beginTransaction()
        //trans?.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        trans?.replace(R.id.activity_home_frame_layout,SettingsFragment.newInstance())
        trans?.commit()*/
        //activity?.recreate()
    }

    companion object {
        fun newInstance() : SettingsFragment {
            return SettingsFragment()
        }
    }
    fun getContext1():Context{
        return mContext
    }
    fun setCreate(context: Context,inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) {
        //setStringPreference(mContext,"language","en")

        //activity?.recreate()
        /*val ft = fragmentManager?.beginTransaction()
        ft?.detach(this)?.attach(this)?.commit()*/
        this.mContext=context
        onCreateView(inflater!!, container, savedInstanceState)

        //getFragmentManager()?.beginTransaction()?.detach(this)?.attach(this)?.commit();
        /*DLog("settingsFragment : ","setcreate1")
        val trans = fragmentManager?.beginTransaction()
        DLog("settingsFragment : ","setcreate2")
        //trans?.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        trans?.replace(R.id.activity_home_frame_layout,SettingsFragment.newInstance())
        DLog("settingsFragment : ","setcreate3")
        trans?.commit()
        DLog("settingsFragment : ","setcreate4")*/
    }
}
