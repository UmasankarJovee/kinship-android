package com.joveeinfotech.bloodex.contract

import com.joveeinfotech.bloodex.adapter.*
import com.joveeinfotech.bloodex.adapter.CustomeAdapter
import com.joveeinfotech.bloodex.adapter.LanguageListsAdapter
import com.joveeinfotech.bloodex.adapter.Top20ListAdapter
import com.joveeinfotech.bloodex.model.*
import org.json.JSONObject

/**
 * Created by shanmugarajjoveeinfo on 8/2/18.
 */
interface BloodExContract {

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
        fun getOTP()
        fun resetPassword()
        fun closePasswordDialog()
    }
    interface LoginPresenter{
        fun initPresenter()
        fun navigateActivity()
        fun sendOTP(otp: String)
        fun sendPassword(password : String)
        fun callRegisterActivity()
        fun getPhoneNumber(phone_number: String)
        fun userPhoneNumberAndPassword(phone_number : String, password : String)
    }

    // Register Activity
    interface RegisterView{
        fun closeActivity()
        fun closePasswordDialog()
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
        fun setNavigationFragmentValues(isCompleteProfile: Boolean, isCompleteAddress: Boolean, isCompleteAdditionalDetails: Boolean, isCompleteHealthDetails: Boolean)
    }
    interface UserDetailsPresenter{
        fun initPresenter()
        fun onLoad()
    }

    // UserProfileFragment
    interface UserProfileFragmentView{}
    interface UserProfileFragmentPresenter{
        fun initPresenter()
        fun userProfileDetails(imageString: String,first_name: String, last_name: String, date_of_birth: String, weight: Int, gender: Int)
    }

    // UserAddressFragment
    interface UserAddrssFragmentView{
        fun setCountries(dataAdapter: ArrayList<String>)
        fun setStates(stateList: ArrayList<String>)
        fun setDistricts(districtList: ArrayList<String>)
    }
    interface UserAddressFragmentPresenter{
        fun initPresenter()
        fun loadCountries()
        fun loadStates()
        fun loadDistricts()
        fun userAddressDetails(country: String, state: String, district: String, city: String, locality: String, street: String)
        //fun userAddressDetails(country : String,state : String, district : String, city : String, locality : String, street : String)
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
        fun closeActivity()
    }
    interface UserRequestFragmentPresenter{
        fun initPresenter()
        fun loadDistricts()
        fun loadHospitals()
        fun sendUserRequestToServer(search_blood_group: String, units : String, district : String,
                                   hospital : String, time_in_number : String, time_in_string: String)
    }

    // Some One Request Fragment
    interface SomeOneRequestFragmentView{
        fun setDistricts(hospitalsList: DistrictResult)
        fun setHospitals(hospitalsList: SearchHospitalResult)
        fun closeActivity()
    }
    interface SomeOneRequestFragmentPresenter{
        fun initPresenter()
        fun loadDistricts()
        fun loadHospitals()
        fun sendUserRequestToServer(name: String, phone_number: String,
                                    search_blood_group: String, units: String,
                                    district: String, hospital: String,
                                    relationship: String, time_in_number: String, time_in_string: String)
        /*fun sendUserRequestToServer(name: String, phone_number: String, search_blood_group: String, search_units: String, search_district: String, search_hospital: String, relationship: String)*/
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
        fun setCountries(countryList: ArrayList<String>)
        fun setStates(stateList: ArrayList<String>)
        fun setDistricts(districtList:ArrayList<String>)
        fun updateDateInView()
        fun call(field:String,value:String,field1:String,value1:String)
        fun setProfileDetails(image_url: String, name: String,date_of_birth: String,weight:String,address: String,phone_number: String,email: String)
    }
    interface UserProfileEditFragmentPresenter{
        fun initPresenter()
        fun loadProfileDetails()
        fun loadCountries()
        fun sendCountryReceiveState()
        fun userAddressDetails(country : String,state : String, district : String, city : String, locality : String, street : String)
        fun sendStateReceiveDistrict()
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

    // UserHealthDetailsFragment
    interface UserHealthDetailsFragmentView{
    }
    interface UserHealthDetailsFragmentPresenter {
        fun initPresenter()
        fun sendHealthDetails(healthDetails: JSONObject)
    }

    //RequestHistoryActivity
    interface RequestHistoryView{
        fun setAdaperofRequestHistory(requestHistoryListAdapter:RequestHistoryListAdapter?)
    }
    interface RequestHistoryPresenter{
        fun initPresenter()
        fun loadRequestHisoryList()
    }

    interface RequestResponseView{
        fun setRequestResponse(details: RequestResponseListAdapter?)
        fun closeConfirmDialog()
        fun closeNotDonatedConfirmDialog()
        fun closeActivity()
    }
    interface RequestResponsePresenter{
        fun initPresenter()
        fun isDonated(person_id: String)
        fun SendNoOneDonated()
        fun clearRequestResponse(user_id: String?)
    }
    interface RequestResponseListener {
        fun onItemClick(responseResult: InnerRequestResponseResult)
    }
    //ProfileViewActivity
    interface ProfileViewActivity{
        //fun setViewData(name:String,image_url:String,blood_group:String,email:String,occupation:String,facebook_id:String,total_donated: String,total_request: String,last_donated_date: String)
        fun setViewData(first_name:String,last_name:String,image:String,blood_group: String,email: String,occupation:String,facebook_id:String,total_donated: String,total_request: String,last_donated_date: String)
    }
    interface ProfileViewPresenter{
        fun initpresenter(person_id:String)
        fun Click(person_id: String)
    }
}