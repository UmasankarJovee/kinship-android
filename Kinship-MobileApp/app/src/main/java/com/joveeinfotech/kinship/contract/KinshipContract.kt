package com.joveeinfotech.kinship.contract

import com.joveeinfotech.kinship.adapter.CustomeAdapter
import com.joveeinfotech.kinship.adapter.LanguageListAdapter
import com.joveeinfotech.kinship.model.*

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
    }
    interface SettingsFragmentPresenter{
        fun initPresenter()
        fun getLanguagesData(): LanguageListAdapter
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
        fun confirmOTP()
        fun confirmPassword()
    }
    interface RegisterPresenter {

        fun initPresenter()
        fun userPhoneNumberAndBloodGroup(phone_number: String, blood_group: String)
        fun OtpContent(otp: String)
        fun passwordContent(password: String, phone_number: String)
    }


    //UserDetails
    interface UserDetailsView{
        fun setNavigationFragmentValues(isCompleteProfile : Boolean, isCompleteAddress : Boolean, isCompleteAdditionalDetails : Boolean)
    }
    interface UserDetailsPresenter{
        fun initPresenter()
        fun onLoad()
    }

    // UserProfileFragment
    interface UserProfileFragmentView{}
    interface UserProfileFragmentPresenter{
        fun initPresenter()
        fun userProfileDetails(first_name : String, last_name : String, date_of_birth : String, gender : Int)
    }

    // UserAddressFragment
    interface UserAddrssFragmentView{
        fun setCountries(dataAdapter: CountryResult)
        fun setStates(stateList: StateResult)
        fun setDistricts(districtList: DistrictResult)
    }
    interface UserAddressFragmentPresenter{
        fun initPresenter()
        fun loadCountries()
        fun sendCountryReceiveState(country : String)
        fun userAddressDetails(country : String,state : String, district : String, city : String, locality : String, street : String)
        fun sendStateReceiveDistrict(state: String)
    }

    // UserAdditionalDetailsFragment
    interface UserAdditionalDetailsFragmentView{}
    interface UserAdditionalDetailsFragmentPresenter{
        fun initPresenter()
        fun moveSkiptoHome()
        fun userAdditionalDetails(category_of_person: String, additionalPhoneNumber : String, additionalEmail: String, socialProfile: String)
    }

    // User Request Fragment
    interface UserRequestFragmentView{
        fun setDistricts(hospitalsList: DistrictResult)
        fun setHospitals(hospitalsList: SearchHospitalResult)
    }
    interface UserRequestFragmentPresenter{
        fun initPresenter()
        fun loadDistricts()
        fun sendDistrictsReceiveHospitals(district: String)
        fun sendUserRequestDetails(search_blood_group: String, search_units: String, search_district: String, search_hospital: String)
        fun sendUserRequestToServer(search_blood_group: String, search_units: String, search_district: String, search_hospital: String)
    }

    // Some One Request Fragment
    interface SomeOneRequestFragmentView{
        fun setDistricts(hospitalsList: DistrictResult)
        fun setHospitals(hospitalsList: SearchHospitalResult)
    }
    interface SomeOneRequestFragmentPresenter{
        fun initPresenter()
        fun loadDistricts()
        fun sendDistrictsReceiveHospitals(district: String)
        fun sendUserRequestDetails(name: String, phone_number: String, search_blood_group: String, search_units: String, search_district: String, search_hospital: String, relationship: String)
        fun sendUserRequestToServer(name: String, phone_number: String, search_blood_group: String, search_units: String, search_district: String, search_hospital: String, relationship: String)
    }

    interface Listener {
        fun languageSettings()
        fun onItemClick(data: Album)
        fun displayResult(result:Boolean)
        fun callEditProfile()
    }

    interface LanguageListener{
        fun onLanguageClick(data:String)
    }

    // UserProfileEditFragment
    interface UserProfileEditFragmentView{
        /*fun setCountries(dataAdapter: CountryResult)
        fun setStates(stateList: StateResult)
        fun setDistricts(districtList: DistrictResult)*/
        fun updateDateInView()
        fun call(field:String,value:String)
    }
    interface UserProfileEditFragmentPresenter{
        fun initPresenter()
        fun loadCountries()
        fun sendCountryReceiveState(country : String)
        fun userAddressDetails(country : String,state : String, district : String, city : String, locality : String, street : String)
        fun sendStateReceiveDistrict(state: String)
    }

}