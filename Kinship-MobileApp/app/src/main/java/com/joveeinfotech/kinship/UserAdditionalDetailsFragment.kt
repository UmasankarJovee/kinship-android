package com.joveeinfotech.kinship

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_user_additional_details.*
import kotlinx.android.synthetic.main.fragment_user_additional_details.view.*
import java.util.ArrayList
import java.util.HashMap

class UserAdditionalDetailsFragment : Fragment(), APIListener {

    var networkCall: APICall? = null

    lateinit var mContext: Context
    //var view1 : View? = null

    var category_of_person : String? = null
    var additionalPhoneNumber : String? = null
    var additionalEmail : String? = null
    var socialProfile : String? = null

    override fun onAttach(context: Context) {
        this.mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view : View = inflater.inflate(R.layout.fragment_user_additional_details, container, false)

      /*  val toolbar = view.findViewById<Toolbar>(R.id.MyToolbar) as Toolbar
        //activity.setSupportActionBar(toolbar)
        (sf)?.setSupportActionBar(toolbar)
        (sf)?.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        */

        view.radio_student.setOnClickListener{
            view.radio_others.isChecked=false
            view.radio_working.isChecked=false
            view.editText_others.visibility=View.GONE
            category_of_person="Student"
        }
        view.radio_working.setOnClickListener{
            view.radio_student.isChecked=false
            view.radio_others.isChecked=false
            view.editText_others.visibility=View.GONE
            category_of_person="Working"
        }
        view.radio_others.setOnClickListener{
            view.radio_student.isChecked=false
            view.radio_working.isChecked=false
            view.editText_others.visibility=View.VISIBLE
        }

        view.button_send_additional_details.setOnClickListener{

            if(editText_additional_phone_number.text.toString() == null){
                additionalPhoneNumber = "empty"
            } else{
                additionalPhoneNumber = editText_additional_phone_number.text.toString()
            }
            if(editText_additional_email.text.toString() == null){
                additionalEmail = "empty"
            }else{
                additionalEmail = editText_additional_email.text.toString()
            }
            if(editText_social_profile.text.toString() == null){
                socialProfile = "empty"
            }else{
                socialProfile = editText_social_profile.text.toString()
            }
            category_of_person=editText_others.text.toString()
            if(category_of_person != null){
                sendAdditionalDetails()
            }
        }
        return view
    }

    private fun sendAdditionalDetails() {
        val queryParams = HashMap<String, String>()
        queryParams.put("user_id", "27")
        queryParams.put("status_of_person", category_of_person!!)
        queryParams.put("phone_number", additionalPhoneNumber!!)
        queryParams.put("email", additionalEmail!!)
        queryParams.put("social_profile",socialProfile!!)
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v9/optional", queryParams, UserAdditionalDetailsResult::class.java, this, 1, "Sending your other details...")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_home)
        //retainInstance = true;
        networkCall = APICall(mContext)
    }

    companion object {
        fun newInstance(): UserAdditionalDetailsFragment {
            return UserAdditionalDetailsFragment()
        }
    }

    override fun onSuccess(from: Int, response: Any) {
        when (from) {
            1 -> { // Send Additional Details
                Log.e("API CALL : ", "inside Main activity and onSuccess when")
                val userAdditionalDetailsResult = response as UserAdditionalDetailsResult
                if(userAdditionalDetailsResult.status){
                    val i= Intent(mContext,Home::class.java)
                    startActivity(i)
                }
            }
        }
    }

    override fun onFailure(from: Int, t: Throwable) {}
    override fun onNetworkFailure(from: Int) {}

  /*  override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.e("checking image array",ba.toString())

        outState.putByteArray("myarray", ba)
        outState.putString("first_name",editText_first_name.text.toString())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(bitmap != null) {
            bitmap = BitmapFactory.decodeByteArray(ba, 0, ba!!.size)
        }
        Log.e("inside onActivityCre : ",ba.toString())
        //if(savedInstanceState!=null){
            //ba = savedInstanceState?.getByteArray("myarray")

            view1?.imageView?.setImageBitmap(bitmap)
    }*/

    /*override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        ba = savedInstanceState?.getByteArray("myarray")
        if(bitmap != null) {
            bitmap = BitmapFactory.decodeByteArray(ba, 0, ba!!.size)
        }
        imageView?.setImageBitmap(bitmap)
    }*/

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
    }


}