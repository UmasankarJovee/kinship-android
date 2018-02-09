package com.joveeinfotech.kinship.model

data class LoginResult(var status: Boolean,
                       var status_value : Int,
                       var message: String,
                       var user_id : Int,
                       var isRegisterUserProfile : Boolean,
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

data class CountryResult(var country : String)

data class StateResult(var stateNames : String)

data class DistrictResult(var districtNames : String)