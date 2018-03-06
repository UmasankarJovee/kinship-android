package com.joveeinfotech.kinship.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.model.detailsResult
import com.joveeinfotech.kinship.model.donationDetails
import com.joveeinfotech.kinship.model.donationInnerDetails
import kotlinx.android.synthetic.main.all_donars_inner_list.view.*
import kotlinx.android.synthetic.main.all_donars_list.view.*
import kotlinx.android.synthetic.main.fragment_donars.view.*
import kotlinx.android.synthetic.main.top20_donars_list.view.*

/**
 * Created by shanmugarajjoveeinfo on 12/2/18.
 */

class DonorsListAdapter(val getTop20Result:List<donationDetails>, val mcontext: Context): RecyclerView.Adapter<DonorsListAdapter.ViewHolder>() {

    private val colors : Array<String> = arrayOf("#EF5350", "#EC407A", "#AB47BC", "#7E57C2", "#5C6BC0", "#42A5F5")

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(getTop20Result[position], colors, position)
    }

    override fun getItemCount(): Int = getTop20Result.count()

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

        fun bind(donorsResult : donationDetails, colors : Array<String>, position: Int) {

            itemView.all_donars_list_day.text = "Date : ${donorsResult.date}"

            var i1 =1
            if(donorsResult.date in mMap.keys){
                /*var mList = mMap[donorsResult.date]
                var df = donationInnerDetails(donorsResult.image_url,donorsResult.name,donorsResult.district)
                mList?.add(donorsResult.image_url,donorsResult.name,donorsResult.district)*/
                Log.e("DonorsList : ","inside if")
                mMap[donorsResult.date]?.add(donationInnerDetails(donorsResult.image_url,donorsResult.name,donorsResult.district))
            }else{
                Log.e("DonorsList : ","inside else")
                var mList2 = mutableListOf<donationInnerDetails>()
                mList2.add(donationInnerDetails(donorsResult.image_url,donorsResult.name,donorsResult.district))
                mMap.put(donorsResult.date,mList2)
            }

            if(i1 == 1){
                itemView.all_donars_inner_list_RecyclerView.setHasFixedSize(true)
                val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(mContext)
                itemView.all_donars_inner_list_RecyclerView.layoutManager = layoutManager
                i1=2
            }

            var setList: List<donationInnerDetails>? = mMap[donorsResult.date]?.toList()
            //var details: List<donationDetails> = getTop20Result.donorList
            mdonorsInnerArrayList = ArrayList(setList)

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

            override fun getItemCount(): Int = getTop20Result.count()

            override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
                val view = LayoutInflater.from(parent?.context).inflate(R.layout.all_donars_inner_list, parent, false)
                return ViewHolder(view)
            }

            class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

                fun bind(donationInner : donationInnerDetails, colors : Array<String>, position: Int) {
                    Log.e("DonorsInnerList : ","inside bind")
                    itemView.all_donars_inner_list_user_profile
                    itemView.all_donars_inner_list_user_name.text = donationInner.name
                    itemView.all_donars_inner_list_user_district.text = donationInner.district
                }
            }
        }
    }
}