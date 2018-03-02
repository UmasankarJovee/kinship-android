package com.joveeinfotech.kinship.view

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.presenter.ProfileDisplayPresenterImpl
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile_display.*

class ProfileDisplayFragment : Fragment(), ProfileDisplayView {

    var view1 : View? = null

    lateinit var mContext: Context

    var profileDisplayPresenter: ProfileDisplayPresenterImpl? = null

    override fun onAttach(context: Context) {
        this.mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        view1 = inflater.inflate(R.layout.fragment_profile_display, container, false)

        profileDisplayPresenter = ProfileDisplayPresenterImpl(this, mContext)

        return view1
    }

    override fun setProfileDetails(image_url: String, name: String, total_donated: String,
                                   total_request: String, last_donated_date: String,
                                   email: String, phone_number: String, blood_group: String,
                                   date_of_birth: String, address: String) {
        Picasso.with(mContext).load(image_url).into(fragment_profile_display_user_profile_image)
        fragment_profile_display_TextView_user_name.text = name
        fragment_profile_display_TextView_Total_donated.text = total_donated
        fragment_profile_display_TextView_Total_requested.text = total_request
        fragment_profile_display_TextView_Last_donated.text = total_request
        fragment_profile_display_TextView_user_email.text = email
        fragment_profile_display_TextView_user_phone_number.text = phone_number
        fragment_profile_display_TextView_user_blood_group.text = blood_group
        fragment_profile_display_TextView_user_date_of_birth.text = date_of_birth
        fragment_profile_display_TextView_user_address.text = address
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        fun newInstance() : ProfileDisplayFragment {
            return ProfileDisplayFragment()
        }
    }
}