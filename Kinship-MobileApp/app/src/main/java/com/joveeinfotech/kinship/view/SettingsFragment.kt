package com.joveeinfotech.kinship

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import com.joveeinfotech.kinship.R.string.settingsfragment_cardview1_textview
import com.joveeinfotech.kinship.R.string.settingsfragment_cardview2_textview
import com.joveeinfotech.kinship.R.string.settingsfragment_cardview3_textview
import com.joveeinfotech.kinship.adapter.CustomAdapter
import com.joveeinfotech.kinship.contract.KinshipContract.Listener
import com.joveeinfotech.kinship.model.Album

class SettingsFragment : Fragment(),Listener{

    lateinit var mContext: Context

    override fun onAttach(context: Context) {
        this.mContext=context
        super.onAttach(context)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        var view:View=inflater.inflate(R.layout.fragment_settings,container,false)

        val recyclerView:RecyclerView=view.findViewById<RecyclerView>(R.id.activity_settings_fragment_RecyclerView)
        recyclerView.layoutManager=LinearLayoutManager(mContext,LinearLayout.VERTICAL,false)
        val items=ArrayList<Album>()

        //val onoff:Switch=view.findViewById(R.id.cards_layout_CardView_LinearLayout_Switch)
        items.add(Album(BitmapFactory.decodeResource(resources,R.mipmap.edit_profile),getString(settingsfragment_cardview1_textview)))
        items.add(Album(BitmapFactory.decodeResource(resources,R.mipmap.notifications),getString(settingsfragment_cardview2_textview)))
        items.add(Album(BitmapFactory.decodeResource(resources,R.mipmap.language),getString(settingsfragment_cardview3_textview)))
        Log.e("Message","Before call CustomeAdapter class" )
        val adapter=CustomAdapter(items,this)
        Log.e("Message","After call CustomeAdapter class" )
        recyclerView.adapter=adapter
        return view
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
    companion object {
        fun newInstance() : SettingsFragment {
            return SettingsFragment()
        }
    }
}
