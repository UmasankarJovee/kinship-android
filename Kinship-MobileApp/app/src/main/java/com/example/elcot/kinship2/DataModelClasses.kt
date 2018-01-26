package com.example.elcot.kinship2

data class LoginResult(var status : Boolean,var status_value : Int, var message: String)

data class RegisterResult(var status : Boolean,var status_value: Int, var otp : Long,var message : String, var user_id : Int)

data class PasswordResult(var status : Boolean, var status_value: Int, var message: String)

data class UserProfileResult(var status : Boolean, var status_value: Int, var message: String)