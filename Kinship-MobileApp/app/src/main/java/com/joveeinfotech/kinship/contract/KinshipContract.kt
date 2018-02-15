package com.joveeinfotech.kinship.contract

import android.app.Dialog

/**
 * Created by shanmugarajjoveeinfo on 8/2/18.
 */
interface KinshipContract {

    // Home Fragment
    interface HomeFragmentView{
        fun bloodDonatorInstructions()
        fun bloodRequestorInstructions()
        fun setViewData(count_of_hospitals:String,count_of_donors:String,count_of_users:String)
    }
    interface HomeFragmentPresenterPresenter{
        fun initPresenter()
        fun Click()
    }

    // Login Activity
    interface LoginView{
        fun closeActivity()
    }
    interface LoginPresenterPresenter{
        fun initPresenter()
        fun navigateActivity()
        fun callRegisterActivity()
        fun userPhoneNumberAndPassword(phone_number : String, password : String)
    }

    // Register Activity
    interface RegisterView{
        fun closeActivity()
    }
    interface RegisterPresenterPresenter{
        fun initPresenter()
        fun userPhoneNumberAndBloodGroup(phone_number: String, blood_group : String)
        fun confirmOTP()
        fun confirmPassword()
    }
}