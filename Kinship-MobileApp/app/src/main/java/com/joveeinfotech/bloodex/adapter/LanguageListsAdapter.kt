package com.joveeinfotech.bloodex.adapter

import android.content.Context
import com.joveeinfotech.bloodex.contract.BloodExContract.LanguageListener
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.getStringPreference
import com.joveeinfotech.bloodex.model.Languages
import com.joveeinfotech.bloodex.utils.Others.DLog

/**
 * Created by shanmugarajjoveeinfo on 3/3/18.
 */
class LanguageListsAdapter(context: Context, items: List<Languages>,listener: LanguageListener) : LanguageListAdapter<Languages>(context, items,listener){


    var context:Context=context
    override fun onBindViewHolder(viewHolder: LanguageListAdapter<Languages>.ViewHolder, i: Int) {
        super.onBindViewHolder(viewHolder, i)


        /*var clickListener:View.OnClickListener?=null
        clickListener = object : View.OnClickListener{
            override fun onClick(v: View) {
                mSelectedItem = getIntPreference(context,"checked",1)
                listener.onLanguageClick(mSelectedItem)
                //SharedPreferenceHelper.setIntPreference(context, "checked", mSelectedItem)
                Log.e("Preference","After setIntPreference ${mSelectedItem}")
                notifyDataSetChanged()
            }
        }*/

        var s:Int
        DLog("Message", "Before setText")
        viewHolder.language_list_item_languageNames_textView.text=languages[i].languages

        var df = getStringPreference(context,"selectLanguageString","English")
        if(languages[i].languages == df){
            viewHolder.language_list_item_imageCheck_radioButton.isChecked=true
            //viewHolder.language_list_item_imageCheck_radioButton.setOnClickListener(clickListener)
        }

       /* s=getIntPreference(context,"checked",1)
        var i:Int=0
        for (i in 0..counts-1) {
            Log.e("Preference","i value ${i}")
            if (i == s) {
                Log.e("Preference", "m if value ${i}")
                viewHolder.language_list_item_imageCheck_radioButton.isChecked=true
            }
            else{
                Log.e("Preference","m else value ${i}")
                // itemView.language_list_item_imageCheck_radioButton.isChecked=false
            }
        }*/
        DLog("Message","After setText${languages[i].languages}")
    }
}