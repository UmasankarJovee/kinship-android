package com.joveeinfotech.bloodex.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import com.joveeinfotech.bloodex.R
import com.joveeinfotech.bloodex.contract.KinshipContract.*
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.getIntPreference
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.getStringPreference
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.setIntPreference
import com.joveeinfotech.bloodex.utils.Others.DLog

/**
 * Created by shanmugarajjoveeinfo on 15/2/18.
 */
abstract class LanguageListAdapter<T>(private val mContext: Context, var languages: List<T>,var listener: LanguageListener) : RecyclerView.Adapter<LanguageListAdapter<T>.ViewHolder>(){
    var mSelectedItem:Int = -1
    var counts:Int=0

    override fun getItemCount(): Int{
        counts=languages.size
        return languages.size
    }

    override fun onBindViewHolder(viewHolder: LanguageListAdapter<T>.ViewHolder, i: Int) {

        viewHolder.language_list_item_imageCheck_radioButton.setChecked(i == mSelectedItem)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.language_list_item, viewGroup, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(inflate: View) : RecyclerView.ViewHolder(inflate) {

        public var language_list_item_imageCheck_radioButton: RadioButton
        public var language_list_item_languageNames_textView: TextView
        var mSelectedItems:Int
        var clickListener:View.OnClickListener?=null

        init {
            DLog("Message","1")
            language_list_item_languageNames_textView = inflate.findViewById(R.id.language_list_item_languageNames_textView)
            language_list_item_imageCheck_radioButton = inflate.findViewById(R.id.language_list_item_imageCheck_radioButton)


            var df = getStringPreference(mContext, "selectLanguageString", "English")
            //var dataView = v as TextView
            //var data = dataView.text
            /*if(df == "Tamil"){
                DLog("LanguageListAdapter : ","Tamil")
                language_list_item_imageCheck_radioButton.isChecked=true
                //viewHolder.language_list_item_imageCheck_radioButton.setOnClickListener(clickListener)
                //itemView.setOnClickListener(clickListener)
            }else if(df == "English"){
                DLog("LanguageListAdapter : ","English")
                language_list_item_imageCheck_radioButton.isChecked=true
                //viewHolder.language_list_item_imageCheck_radioButton.setOnClickListener(clickListener)
                //itemView.setOnClickListener(clickListener)
            }else{
                DLog("LanguageListAdapter : ","Hindi")
                language_list_item_imageCheck_radioButton.isChecked=true
                //viewHolder.language_list_item_imageCheck_radioButton.setOnClickListener(clickListener)
                //itemView.setOnClickListener(clickListener)
            }*/


            DLog("Message","2")
            DLog("Preference","Before getIntPreference${mSelectedItem}")
            mSelectedItems= getIntPreference(mContext,"checked",1)
            DLog("Preference","After getIntPreference${mSelectedItems}")
            var i:Int=0
            for (i in 0..counts-1) {
                DLog("Preference","i value ${i}")
                if (i == mSelectedItems) {
                    DLog("Preference", "m if value ${i}")
                    language_list_item_imageCheck_radioButton.performClick()
                    /*clickListener = object : View.OnClickListener{
                        override fun onClick(v: View) {
                            Log.e("Preference", "m if value $")
                            mSelectedItem = getAdapterPosition()
                            //listener.onLanguageClick(mSelectedItem)
                            //setIntPreference(mContext,"checked",mSelectedItem)
                            //Log.e("Preference","After setIntPreference ${mSelectedItem}")
                            notifyDataSetChanged()
                        }
                    }
                    itemView.setOnClickListener(clickListener)
                    language_list_item_imageCheck_radioButton.setOnClickListener(clickListener)*/
                    //itemView.language_list_item_imageCheck_radioButton.isChecked=true
                }
                else{
                    DLog("Preference","m else value ${i}")
                   // itemView.language_list_item_imageCheck_radioButton.isChecked=false
                }
            }

            //callClick()

            clickListener = object : View.OnClickListener{
                override fun onClick(v: View) {
                    mSelectedItem = getAdapterPosition()
                    listener.onLanguageClick(mSelectedItem)
                    setIntPreference(mContext,"checked",mSelectedItem)
                    DLog("Preference","After setIntPreference ${mSelectedItem}")
                    //dismiss()
                    notifyDataSetChanged()
                }
            }



            itemView.setOnClickListener(clickListener)
            language_list_item_imageCheck_radioButton.setOnClickListener(clickListener)
        }
    }

    private fun callClick() {

    }
}
