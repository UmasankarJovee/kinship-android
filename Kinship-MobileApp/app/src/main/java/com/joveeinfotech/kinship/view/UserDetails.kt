package com.joveeinfotech.kinship.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.joveeinfotech.kinship.APICall
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.presenter.UserDetailsPresenterImpl

class UserDetails : AppCompatActivity(), UserDetailsView {

    var networkCall : APICall? = null

    var isCompleteProfile : Boolean? = null
    var isCompleteAddress : Boolean? = null
    var isCompleteAdditionalDetails : Boolean? = null

    var userDetailsPresenter : UserDetailsPresenterImpl? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        val trans = supportFragmentManager.beginTransaction()
        userDetailsPresenter = UserDetailsPresenterImpl(trans,this,this)

    }

    override fun setNavigationFragmentValues(isCompleteProfile: Boolean, isCompleteAddress: Boolean, isCompleteAdditionalDetails: Boolean) {
        this.isCompleteProfile = isCompleteProfile
        this.isCompleteAddress = isCompleteAddress
        this.isCompleteAdditionalDetails = isCompleteAdditionalDetails
    }
}
