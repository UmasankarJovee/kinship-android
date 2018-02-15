package com.joveeinfotech.kinship.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.contract.KinshipContract.Listener
import com.joveeinfotech.kinship.model.Album

/**
 * Created by shanmugarajjoveeinfo on 12/2/18.
 */
class CustomAdapter(val list:List<Album>, val listener: Listener):RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.cards_layouts, parent, false)
        Log.e("Message","Before call Before ViewHolder(view)" )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        Log.e("Message","onBindViewHolder function" )
        holder.bindItems(list[position], listener)
    }
    override fun getItemCount(): Int = list.count()

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun bindItems(data:Album, listener: Listener){

            Log.e("Message","bindItems" )
            val toolsIcons:ImageView=itemView.findViewById(R.id.cards_layout_CardView_LinearLayout_ImageView)
            val toolsTitles:TextView=itemView.findViewById(R.id.cards_layout_CardView_LinearLayout_TextView)
            val toolsOnOff:Switch=itemView.findViewById(R.id.cards_layout_CardView_LinearLayout_Switch)

            toolsIcons.setImageBitmap(data.image)
            toolsTitles.text=data.text
            if(toolsTitles.text.toString() == "Notifications"){
                toolsOnOff.visibility=View.VISIBLE
            }
            toolsOnOff.setOnClickListener {
                if(toolsOnOff.isChecked)listener.displayResult(true)
                else listener.displayResult(false)
            }
            Log.e("Message","Before setOnClickListener" )
            val card = itemView.findViewById<CardView>(R.id.cards_layout_CardView)
            card.setOnClickListener{
                if(data.text == "Language") listener.languageSettings()
                else listener.onItemClick(data)
            }
        }
    }
}