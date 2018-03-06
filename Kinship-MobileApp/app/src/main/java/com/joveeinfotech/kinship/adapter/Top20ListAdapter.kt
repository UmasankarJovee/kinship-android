package com.joveeinfotech.kinship.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.model.GetTop20Result
import com.joveeinfotech.kinship.model.detailsResult
import kotlinx.android.synthetic.main.top20_donars_list.view.*

/**
 * Created by shanmugarajjoveeinfo on 12/2/18.
 */
class Top20ListAdapter(val getTop20Result:List<detailsResult>, val mcontext: Context): RecyclerView.Adapter<Top20ListAdapter.ViewHolder>() {


    private val colors : Array<String> = arrayOf("#EF5350", "#EC407A", "#AB47BC", "#7E57C2", "#5C6BC0", "#42A5F5")

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(getTop20Result[position], colors, position)
    }

    override fun getItemCount(): Int = getTop20Result.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.top20_donars_list, parent, false)

        return ViewHolder(view)
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        fun bind(getTop20Result : detailsResult, colors : Array<String>, position: Int) {

            itemView.top20_donars_list_Linear_layout_CardView_ImageView_profile
            itemView.top20_donars_list_Linear_layout_CardView_TextView_name.text = getTop20Result.name
            itemView.top20_donars_list_Linear_layout_CardView_TextView_district.text = getTop20Result.district
            itemView.top20_donars_list_Linear_layout_CardView_TextView_total_like.text = getTop20Result.total_likes.toString()
            itemView.top20_donars_list_Linear_layout_CardView_TextView_total_donation.text = getTop20Result.total_donated.toString()
            itemView.top20_donars_list_Linear_layout_CardView_TextView_last_donation.text = getTop20Result.last_donated_date
            //itemView.setBackgroundColor(android.graphics.Color.parseColor(colors[position % 6]))
            //itemView.setOnClickListener{ listener.onItemClick(android) }
        }
    }
}