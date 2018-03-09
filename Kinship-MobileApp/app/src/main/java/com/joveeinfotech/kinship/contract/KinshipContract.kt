package com.joveeinfotech.kinship.contract

import com.joveeinfotech.kinship.adapter.*
import com.joveeinfotech.kinship.adapter.CustomeAdapter
import com.joveeinfotech.kinship.adapter.LanguageListsAdapter
import com.joveeinfotech.kinship.adapter.Top20ListAdapter
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
        fun getLanguagesData(): LanguageListsAdapter
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
        fun userProfileDetails(first_name: String, last_name: String, date_of_birth: String, weight: Int, gender: Int)
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

    // Top20 Fragment
    interface Top20FragmentView{
        fun setAdapterOfTop20(top20ListAdapter: Top20ListAdapter?)
    }
    interface Top20FragmentPresenter{
        fun initPresenter()
        fun loadTop20()
    }

    // ProfileDisplayActivity
    interface ProfileDisplayView{
        fun setProfileDetails(image_url: String, name: String, total_donated: String, total_request: String, last_donated_date: String, email: String, phone_number: String, blood_group: String, date_of_birth: String, address: String)
    }
    interface ProfileDisplayPresenter{
        fun initPresenter()
        fun loadProfileDetails()
    }

    interface Listener {
        fun languageSettings()
        fun onItemClick(data: Album)
        fun displayResult(result:Boolean)
        fun callEditProfile()
        fun logoutuserid()
    }

    interface LanguageListener{
        fun onLanguageClick(data:Int)
    }

    // UserProfileEditFragment
    interface UserProfileEditFragmentView{
        /*fun setCountries(dataAdapter: CountryResult)
        fun setStates(stateList: StateResult)
        fun setDistricts(districtList: DistrictResult)*/
        fun updateDateInView()
        fun call(field:String,value:String,field1:String,value1:String)
        fun setProfileDetails(image_url: String, name: String,phone_number: String,date_of_birth: String,email: String,address: String)
    }
    interface UserProfileEditFragmentPresenter{
        fun initPresenter()
        fun loadProfileDetails()
        fun loadCountries()
        fun sendCountryReceiveState(country : String)
        fun userAddressDetails(country : String,state : String, district : String, city : String, locality : String, street : String)
        fun sendStateReceiveDistrict(state: String)
        fun sendImageString(imageString:String)
    }

    //DonorsFragment
    interface DonorsFragmentView{
        fun setAdapterOfDonors(donorsListAdapter: DonorsListAdapter?)
    }
    interface DonorsFragmentPresenter{
        fun initPresenter()
        fun loadDonorsList()
    }

    //RequestHistoryActivity
    interface RequestHistoryView{
        fun setAdaperofRequestHistory(requestHistoryListAdapter:RequestHistoryListAdapter?)
    }

    interface RequestHistoryPresenter{
        fun initPresenter()
        fun loadRequestHisoryList()
    }
}