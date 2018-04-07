package com.joveeinfotech.bloodex.adapter

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joveeinfotech.bloodex.R
import com.joveeinfotech.bloodex.`object`.CommonKeys.image_url
import com.joveeinfotech.bloodex.contract.BloodExContract.*
import com.joveeinfotech.bloodex.model.InnerRequestResponseResult
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.all_request_response.view.*

/**
 * Created by shanmugarajjoveeinfo on 12/2/18.
 */
class RequestResponseListAdapter(var responseResult:List<InnerRequestResponseResult>, var listener : RequestResponseListener, val mcontext: Context): RecyclerView.Adapter<RequestResponseListAdapter.ViewHolder>() {


    private val colors : Array<String> = arrayOf("#EF5350", "#EC407A", "#AB47BC", "#7E57C2", "#5C6BC0", "#42A5F5")

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(responseResult[position], colors, position)
    }

    override fun getItemCount(): Int = responseResult.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.all_request_response, parent, false)

        return ViewHolder(view, listener,mcontext)
    }

    class ViewHolder(view : View, var listener: RequestResponseListener, var mcontext: Context) : RecyclerView.ViewHolder(view) {

        fun bind(responseResult : InnerRequestResponseResult, colors : Array<String>, position: Int) {


            var url = "${image_url}${responseResult.image_url}"
            Picasso.with(mcontext).load(url).into(itemView.all_request_response_profile_imageView)

/*
            itemView.all_request_response_name_TextView.text = responseResult.name
            itemView.all_request_response_locality_and_district_TextView.text = "${responseResult.locality}, ${responseResult.district}"
            itemView.all_request_response_call_textView.text = responseResult.phone_number
*/

            itemView.all_request_response_name_TextView.text = responseResult.name
            itemView.all_request_response_locality_and_district_TextView.text = responseResult.district
            //itemView.all_request_response_call_textView.text = responseResult.
            //itemView.all_request_response_phone_number_textView.text = responseResult.phoneNumber
            val card = itemView.findViewById<CardView>(R.id.all_request_response_cardView) as CardView
            card.setOnClickListener{
                //Toast.makeText(mcontext,"${responseResult.name}",Toast.LENGTH_LONG).show()
                listener.onItemClick(responseResult)
            }
            itemView.all_request_response_call_imageView.setOnClickListener{
                if (ActivityCompat.checkSelfPermission(mcontext, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    //var phonenumber = responseResult.phoneNumber
                    var phonenumber = "9600862338"
                    val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "$phonenumber"))
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    mcontext.startActivity(intent)
                } else{
                    //var phonenumber = responseResult.phoneNumber
                    var phonenumber = "9600862338"
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "$phonenumber"))
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    mcontext.startActivity(intent)
                }
            }

            //itemView.setBackgroundColor(android.graphics.Color.parseColor(colors[position % 6]))
            //itemView.setOnClickListener{ listener.onItemClick(android) }
        }
    }
}