package com.joveeinfotech.kinship.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.R.id.language_list_item_imageCheck_radioButton
import com.joveeinfotech.kinship.UserModel
import com.joveeinfotech.kinship.contract.KinshipContract.LanguageListener
import com.joveeinfotech.kinship.helper.SharedPreferenceHelper
import com.joveeinfotech.kinship.helper.SharedPreferenceHelper.getIntPreference
import com.joveeinfotech.kinship.helper.SharedPreferenceHelper.getStringPreference
import com.joveeinfotech.kinship.model.Languages
import org.intellij.lang.annotations.Language

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
        Log.e("Message","Before setText")
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
        Log.e("Message","After setText${languages[i].languages}")
    }
}