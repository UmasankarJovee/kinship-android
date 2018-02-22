package com.joveeinfotech.kinship.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.model.Languages

/**
 * Created by shanmugarajjoveeinfo on 15/2/18.
 */
class LanguageListAdapter(val list:List<Languages>, val listener: LanguageListener): RecyclerView.Adapter<LanguageListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageListAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.languages_list, parent, false)
        Log.e("Message","Before call Before ViewHolder(view)" )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: LanguageListAdapter.ViewHolder, position: Int) {
        Log.e("Message","onBindViewHolder function" )
        holder.bindItems(list[position], listener)
    }
    override fun getItemCount(): Int = list.count()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindItems(data: Languages, listener: LanguageListener){

            Log.e("Message","bindItems" )
            var tamil_language:RadioButton=itemView.findViewById(R.id.languages_list_tamil_radioButton)
            var english_language:RadioButton=itemView.findViewById(R.id.languages_list_english_radioButton)
            var hindi_language:RadioButton=itemView.findViewById(R.id.languages_list_hindi_radioButton)
            tamil_language.text=data.tamil_language
            english_language.text=data.english_language
            hindi_language.text=data.hindi_language
            Log.e("Message","Before setOnClickListener" )
            tamil_language.setOnClickListener {
                english_language.isChecked=false
                hindi_language.isChecked=false
                listener.onLanguageClick("Tamil")
            }
            english_language.setOnClickListener {
                tamil_language.isChecked=false
                hindi_language.isChecked=false
                listener.onLanguageClick("English")
            }
            hindi_language.setOnClickListener {
                tamil_language.isChecked=false
                english_language.isChecked=false
                listener.onLanguageClick("Hindi")
            }
        }
    }
}