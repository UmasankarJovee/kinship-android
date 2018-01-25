package com.example.elcot.kinship2

data class LoginResult(var status_Login : Boolean,var status_value : Int)

data class RegisterResult(var otp : Long,var status : Int,var message : String, var user_id : Int )

data class PasswordResult(var status_password : Boolean, var message: String)