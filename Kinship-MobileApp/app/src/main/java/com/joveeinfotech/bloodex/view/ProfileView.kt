package com.joveeinfotech.bloodex.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.joveeinfotech.bloodex.R
import com.joveeinfotech.bloodex.contract.KinshipContract.*
import com.joveeinfotech.bloodex.presenter.ProfileViewPresenterImpl
import com.joveeinfotech.bloodex.utils.Others.DLog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile_view.*

/**
 * Created by shanmugarajjoveeinfo on 27/3/18.
 */
class ProfileView: AppCompatActivity(),ProfileViewActivity {
    private lateinit var profileViewPresenterImpl:ProfileViewPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_view)
        DLog("message","1")
        val intent=intent
        val person_id=intent.getStringExtra("person_id")
        DLog("message adasasas",person_id)
        profileViewPresenterImpl=ProfileViewPresenterImpl(this,this,person_id)
        DLog("message","")
    }

    override fun setViewData(first_name:String,last_name:String,image:String,blood_group:String,email:String,occupation:String,facebook_id:String,total_donated:String,total_request:String,last_donated_date:String) {
        Picasso.with(this).load(image).into(activity_profile_view_constraintLayout_scrollView_constraintLayout_constraintLayout1_userProfile_imageView)
        DLog("message","${image}")
        activity_profile_view_constraintLayout_scrollView_constraintLayout_constraintLayout1_userName_textView.text="${first_name}${last_name}"
        activity_profile_view_constraintLayout_scrollView_constraintLayout_constraintLayout2_bloodGroup_textView.text=blood_group
        activity_profile_view_constraintLayout_scrollView_constraintLayout_constraintLayout2_email_textView.text=email
        activity_profile_view_constraintLayout_scrollView_constraintLayout_constraintLayout2_occupation_textView.text=occupation
        activity_profile_view_constraintLayout_scrollView_constraintLayout_constraintLayout2_facebookid_textView.text=facebook_id
        activity_profile_view_constraintLayout_scrollView_constraintLayout_constraintLayout3_constraintLayout1_totalDonated_textView.text=total_donated
        activity_profile_view_constraintLayout_scrollView_constraintLayout_constraintLayout3_constraintLayout2_totalRequest_textView.text=total_request
        activity_profile_view_constraintLayout_scrollView_constraintLayout_constraintLayout3_constraintLayout3_lastDonatedDate_textView.text=last_donated_date

        DLog("message","${first_name}${last_name}")

    }
    companion object {
        fun newInstance() : ProfileView {
            return ProfileView()
        }
    }
}
