package com.joveeinfotech.kinship.model

import android.graphics.Bitmap
import android.widget.SpinnerAdapter

data class LoginResult(var status: Boolean,
                       var message: String)

data class UserDetailResult(var isRegisterUserProfile : Boolean,
                            var isRegisterHomeAddress : Boolean,
                            var isRegisterHealthDetails: Boolean,
                            var isRegisterAdditionalDetails : Boolean)

data class LocationResult(var message: String)

//data class RegisterResult(var status : Boolean,var status_value: Int, var otp : Long,var message : String, var user_id : Int)
data class RegisterResult(var status: Boolean, var otp : Long)

data class OTPResult(var status: Boolean)

data class PasswordResult(var status : Boolean, var status_value: Int, var message: String, var user_id : Int)

data class UserProfileResult(var status : Boolean, var status_value: Int, var message: String)

data class UpdateDetailsResult(var count_of_hospitals : String,var count_of_users : String,var count_of_donors : String,var message: String,var status : Boolean)

data class CountryResult(var country : ArrayList<String>)

data class StateResult(var state : ArrayList<String>)

data class SendAddressResult(var status :Boolean)

data class DistrictResult(var district : ArrayList<String>)

data class UserAdditionalDetailsResult(var status: Boolean)

data class Album(val image:Bitmap, val text: String)

data class SearchHospitalResult(var hospitals: ArrayList<String>)

data class SearchBloodInUserRequest(var status: Boolean)

data class Languages(val tamil_language:String,val english_language:String,val hindi_language:String)

