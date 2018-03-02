package com.joveeinfotech.kinship

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import com.joveeinfotech.kinship.adapter.CustomeAdapter
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.helper.SharedPreferenceHelper.setBooleanPreference
import com.joveeinfotech.kinship.model.Album
import com.joveeinfotech.kinship.presenter.*
import com.joveeinfotech.kinship.view.UserProfileEditFragment
import kotlinx.android.synthetic.main.fragment_settings.view.*


class SettingsFragment : Fragment(),Listener,SettingsFragmentView{

    private lateinit var settingsFragmentPresenterImpl:SettingsFragmentPresenterImpl
    lateinit var mContext: Context
    var view1:View?=null
    var select : Fragment? = null
    override fun onAttach(context: Context) {
        this.mContext=context
        super.onAttach(context)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
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

        val mydialog = LanguageListDialogFragment()
        mydialog.setCancelable(true)
        mydialog.show(activity?.fragmentManager, "tag")
        mydialog.cancelButton?.setOnClickListener {
            Toast.makeText(mContext,"hello",Toast.LENGTH_SHORT).show()
        }
    }

    override fun callEditProfile() {
        select=UserProfileEditFragment.newInstance()
        goToSelectFragment()
    }
    private fun goToSelectFragment() {
        val trans = fragmentManager?.beginTransaction()
        trans?.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        trans?.replace(R.id.activity_home_frame_layout,select)
        trans?.commit()
    }

    companion object {
        fun newInstance() : SettingsFragment {
            return SettingsFragment()
        }
    }
}
