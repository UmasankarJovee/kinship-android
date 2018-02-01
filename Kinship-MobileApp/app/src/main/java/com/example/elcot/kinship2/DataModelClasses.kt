package com.example.elcot.kinship2

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
data class RegisterResult(var status: Boolean)

data class OTPResult(var status: Boolean)

data class PasswordResult(var status : Boolean, var status_value: Int, var message: String, var user_id : Int)

data class UserProfileResult(var status : Boolean, var status_value: Int, var message: String)

data class UpdateDetailsResult(var number_of_hospitals : Int, var number_of_users : Int, var number_of_donated_persons : Int)