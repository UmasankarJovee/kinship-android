package com.joveeinfotech.kinship.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.model.donationInnerDetails
import kotlinx.android.synthetic.main.all_donars_inner_list.view.*
import kotlinx.android.synthetic.main.all_donars_list.view.*

/**
 * Created by shanmugarajjoveeinfo on 12/2/18.
 */

class DonorsListAdapter(val getTop20Result: MutableMap<String, MutableList<donationInnerDetails>>, val mcontext: Context): RecyclerView.Adapter<DonorsListAdapter.ViewHolder>() {

    var ll = getTop20Result as LinkedHashMap

    var gh: Array<String> = ll.keys.toTypedArray()

    private val colors : Array<String> = arrayOf("#EF5350", "#EC407A", "#AB47BC", "#7E57C2", "#5C6BC0", "#42A5F5")

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //holder.bind(ll.get((ll.keySet()getTop20Result[position], colors)
        // return map.get( (map.keySet().toArray())[ index ] );
        //holder.bind(ll.get((ll.keys.toTypedArray()) as [position]}))
        holder.bind(getTop20Result,gh[position], colors)
    }

    override fun getItemCount(): Int = getTop20Result.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.all_donars_list, parent, false)

        return ViewHolder(view,mcontext)
    }

    class ViewHolder(view : View, var mContext: Context) : RecyclerView.ViewHolder(view) {

        var mMap = mutableMapOf<String,MutableList<donationInnerDetails>>()
        //var mList = listOf<String>()
        //var mList: MutableList<donationInnerDetails>? = null
        var mdonorsInnerArrayList : ArrayList<donationInnerDetails>? = null
        var donorsInnerListAdapter : DonorsInnerListAdapter? = null

        fun bind(getTop20Result: MutableMap<String, MutableList<donationInnerDetails>>,key: String, colors: Array<String>) {

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
            Log.e("DonorsListAdapter : ","${mdonorsInnerArrayList!!.size}")
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
                Log.e("size",getTop20Result.size.toString())
                return ViewHolder(view)
            }

            class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

                fun bind(donationInner : donationInnerDetails, colors : Array<String>, position: Int) {
                    Log.e("DonorsInnerList : ", "inside bind")
                    //Log.e("DonorsInnerListAdapter : ",)
                    itemView.all_donars_inner_list_user_profile
                    Log.e("InnerList : ",donationInner.image_url)
                    Log.e("InnerList : ",donationInner.name)
                    Log.e("InnerList : ",donationInner.district)
                    itemView.all_donars_inner_list_user_name.text = donationInner.name
                    itemView.all_donars_inner_list_user_district.text = donationInner.district
                }
            }
        }
    }
}