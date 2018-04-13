package com.joveeinfotech.bloodex.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joveeinfotech.bloodex.R
import com.joveeinfotech.bloodex.model.donationInnerDetails
import com.joveeinfotech.bloodex.utils.Others.DLog
import com.joveeinfotech.bloodex.view.ProfileView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.all_donars_inner_list.view.*
import kotlinx.android.synthetic.main.all_donars_list.view.*

/**
 * Created by shanmugarajjoveeinfo on 12/2/18.
 */

class DonorsListAdapter(val getTop20Result: MutableMap<String, MutableList<donationInnerDetails>>, val mcontext: Context): RecyclerView.Adapter<DonorsListAdapter.ViewHolder>() {

    var mSelectedItem:Int = -1
    var ll = getTop20Result as LinkedHashMap

    var gh: Array<String> = ll.keys.toTypedArray()

    private val colors : Array<String> = arrayOf("#EF5350", "#EC407A", "#AB47BC", "#7E57C2", "#5C6BC0", "#42A5F5")

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //holder.bind(ll.get((ll.keySet()getTop20Result[position], colors)
        // return map.get( (map.keySet().toArray())[ index ] );
        //holder.bind(ll.get((ll.keys.toTypedArray()) as [position]}))
        holder.bind(getTop20Result,gh[position])
    }

    override fun getItemCount(): Int = getTop20Result.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.all_donars_list, parent, false)

        return ViewHolder(view,mcontext)
    }

    class ViewHolder(var view : View, var mContext: Context) : RecyclerView.ViewHolder(view) {

        var mMap = mutableMapOf<String,MutableList<donationInnerDetails>>()
        //var mList = listOf<String>()
        //var mList: MutableList<donationInnerDetails>? = null
        var mdonorsInnerArrayList : ArrayList<donationInnerDetails>? = null
        var donorsInnerListAdapter : DonorsInnerListAdapter? = null

        fun bind(getTop20Result: MutableMap<String, MutableList<donationInnerDetails>>,key: String) {

            itemView.all_donars_list_day.text = "Date : $key"

            var donorsResult: List<donationInnerDetails>? = getTop20Result[key]?.toList()
            /*var i1 =1
            if(donorsResult.date in mMap.keys){
                *//*var mList = mMap[donorsResult.date]
                var df = donationInnerDetails(donorsResult.image_url,donorsResult.name,donorsResult.district)
                mList?.add(donorsResult.image_url,donorsResult.name,donorsResult.district)*//*
                Log.e("DonorsList : ", "inside if")
                mMap[donorsResult.date]?.add(donationInnerDetails(donorsResult.image_url, donorsResult.name, donorsResult.district))
            }else{
                Log.e("DonorsList : ", "inside else")
                var mList2 = mutableListOf<donationInnerDetails>()
                mList2.add(donationInnerDetails(donorsResult.image_url, donorsResult.name, donorsResult.district))
                mMap.put(donorsResult.date,mList2)
            }*/


            //if(i1 == 1){
                itemView.all_donars_inner_list_RecyclerView.setHasFixedSize(true)
                val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(mContext)
                itemView.all_donars_inner_list_RecyclerView.layoutManager = layoutManager

            //}

            //var setList: List<donationInnerDetails>? = mMap[donorsResult.date]?.toList()
            //var details: List<donationDetails> = getTop20Result.donorList
            mdonorsInnerArrayList = ArrayList(donorsResult)
            DLog("DonorsListAdapter : ","${mdonorsInnerArrayList!!.size}")
            donorsInnerListAdapter = DonorsInnerListAdapter(mdonorsInnerArrayList!!, mContext!!)
            itemView.all_donars_inner_list_RecyclerView.adapter = donorsInnerListAdapter

            //itemView.setBackgroundColor(android.graphics.Color.parseColor(colors[position % 6]))
            //itemView.setOnClickListener{ listener.onItemClick(android) }
        }

        class DonorsInnerListAdapter(val getTop20Result:List<donationInnerDetails>, val mcontext: Context): RecyclerView.Adapter<DonorsInnerListAdapter.ViewHolder>() {

            private val colors : Array<String> = arrayOf("#EF5350", "#EC407A", "#AB47BC", "#7E57C2", "#5C6BC0", "#42A5F5")

            override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
                holder?.bind(getTop20Result[position], colors, position)
            }

            override fun getItemCount(): Int = getTop20Result.size

            override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
                val view = LayoutInflater.from(parent?.context).inflate(R.layout.all_donars_inner_list, parent, false)
                DLog("size",getTop20Result.size.toString())
                return ViewHolder(view,mcontext)
            }

            class ViewHolder(view : View, var mcontext: Context) : RecyclerView.ViewHolder(view) {

                var clickListener:View.OnClickListener?=null
                var mSelectedItem:Int=-1
                fun bind(donationInner : donationInnerDetails, colors : Array<String>, position: Int) {

                    DLog("DonorsInnerList : ", "inside bind")
                    //Log.e("DonorsInnerListAdapter : ",)
                    //var image_url= SharedPreferenceHelper.getStringPreference(mcontext, "image_url", "http://192.168.0.56/images/")
                    var url = "http://192.168.0.56/images/${donationInner.image_url}"
                    Picasso.with(mcontext).load(url).into(itemView.all_donars_inner_list_user_profile)
                    DLog("InnerList : ",donationInner.image_url)
                    DLog("InnerList : ",donationInner.name)
                    DLog("InnerList : ",donationInner.district)
                    itemView.all_donars_inner_list_user_name.text = donationInner.name
                    itemView.all_donars_inner_list_user_district.text = donationInner.district
                    clickListener = object : View.OnClickListener{
                        override fun onClick(v: View) {
                            mSelectedItem = getAdapterPosition()
                            //Toast.makeText(mcontext,"${donationInner.name}",Toast.LENGTH_SHORT).show()
                            //SharedPreferenceHelper.setIntPreference(mContext, "checked", mSelectedItem)
                            val intent=Intent(mcontext,ProfileView::class.java)
                            intent.putExtra("person_id",donationInner.person_id)
                            mcontext.startActivity(intent)
                            DLog("Preference","After setIntPreference ${mSelectedItem}")
                            //dismiss()
                            //notifyDataSetChanged()
                        }
                    }
                    itemView.setOnClickListener(clickListener)
                }
            }
        }
    }
}