package com.joveeinfotech.kinship.contract

import com.joveeinfotech.kinship.adapter.CustomeAdapter
import com.joveeinfotech.kinship.adapter.LanguageListAdapter
import com.joveeinfotech.kinship.model.Album
import com.joveeinfotech.kinship.model.Languages

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
    interface HomeFragmentPresenter{
        fun initPresenter()
        fun Click()
    }

    //Settings Fragment
    interface SettingsFragmentView{
        fun ReceiveCustomeAdapter(adapter: CustomeAdapter)
        fun ReceiveLanguageListAdapter(adapter: LanguageListAdapter)
    }
    interface SettingsFragmentPresenter{
        fun initPresenter()
        fun getLanguagesData()
    }

    // Login Activity
    interface LoginView{
        fun closeActivity()
    }
    interface LoginPresenter{
        fun initPresenter()
        fun navigateActivity()
        fun callRegisterActivity()
        fun userPhoneNumberAndPassword(phone_number : String, password : String)
    }

    // Register Activity
    interface RegisterView{
        fun closeActivity()
    }
    interface RegisterPresenter{
        fun initPresenter()
        fun userPhoneNumberAndBloodGroup(phone_number: String, blood_group: String)
        fun confirmOTP()
        fun confirmPassword()
    }

    interface Listener {
        fun onItemClick(data: Album)
        fun onItemClicks(data:Languages)
        fun displayResult(result:Boolean)
        fun languageSetting()
    }

}