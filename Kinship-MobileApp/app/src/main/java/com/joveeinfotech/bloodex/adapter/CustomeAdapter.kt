package com.joveeinfotech.bloodex.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joveeinfotech.bloodex.R
import com.joveeinfotech.bloodex.contract.BloodExContract.*
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.getIntPreference
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.setIntPreference
import com.joveeinfotech.bloodex.model.Album
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.joveeinfotech.bloodex.utils.Others.DLog


/**
 * Created by shanmugarajjoveeinfo on 12/2/18.
 */
class CustomeAdapter(val list:List<Album>, val listener: Listener,val mcontext: Context):RecyclerView.Adapter<CustomeAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.cards_layouts, parent, false)
        DLog("Message","Before call Before ViewHolder(view)" )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: com.joveeinfotech.bloodex.adapter.CustomeAdapter.ViewHolder, position: Int) {
        DLog("Message","onBindViewHolder function" )

        holder.bindItems(list[position], listener,mcontext)
    }
    override fun getItemCount(): Int = list.count()

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun bindItems(data:Album, listener: Listener,mcontext: Context){
            var i:Int= getIntPreference(mcontext,"startingIValue",0)
            DLog("Message","bindItems" )
            val toolsIcons:ImageView=itemView.findViewById(R.id.cards_layout_CardView_LinearLayout_ImageView)
            val toolsTitles:TextView=itemView.findViewById(R.id.cards_layout_CardView_LinearLayout_TextView)
            val toolsOnOff:Switch=itemView.findViewById(R.id.cards_layout_CardView_LinearLayout_Switch)

            toolsIcons.setImageBitmap(data.image)
            toolsTitles.text=data.text
            if(toolsTitles.text.toString() == mcontext.getString(R.string.settingsFragment_cardView2_textView)){
                toolsOnOff.visibility=View.VISIBLE
            }
            toolsOnOff.setOnClickListener {
                if(toolsOnOff.isChecked){
                    listener.displayResult(true)
                    setIntPreference(mcontext,"startingIValue",0)
                }
                else{
                    listener.displayResult(false)
                    setIntPreference(mcontext,"startingIValue",1)
                }
            }
            if (i==0){
                toolsOnOff.isChecked=true
                listener.displayResult(true)
            }
            else{
                toolsOnOff.isChecked=false
                listener.displayResult(false)
            }
            DLog("Message","Before setOnClickListener" )
            val card = itemView.findViewById<CardView>(R.id.cards_layout_CardView)
            card.setOnClickListener{
                if (data.text == mcontext.getString(R.string.settingsFragment_cardView1_textView)){
                    listener.callEditProfile()
                }
                else if(data.text == mcontext.getString(R.string.settingsFragment_cardView3_textView)){
                    DLog("Message","Before listener.languageSettings()" )
                    listener.languageSettings()
                }
                else if(data.text == mcontext.getString(R.string.settingsFragment_cardView2_textView)){
                    //Log.e("Message","i value after getIntPreference ${i}" )
                    if (i==1){
                        DLog("Message","i value if condition ${i}" )
                        toolsOnOff.isChecked=true
                        listener.displayResult(true)
                        setIntPreference(mcontext,"startingIValue",0)
                    }
                    else{
                        DLog("Message","i value else ${i}" )
                        toolsOnOff.isChecked=false
                        listener.displayResult(false)
                        setIntPreference(mcontext,"startingIValue",1)
                    }
                }
                else if(data.text == mcontext.getString(R.string.settingsFragment_cardView4_textView)){
                    listener.logoutuserid()
                }
                else listener.onItemClick(data)
            }
        }
    }
}