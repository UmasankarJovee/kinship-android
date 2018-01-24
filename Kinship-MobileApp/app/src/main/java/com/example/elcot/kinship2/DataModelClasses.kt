package com.example.elcot.kinship2

data class LoginResult(var status_Login : Boolean,var status_value : Int)

data class RegisterResult(var otp : Long)

data class OtpResponse(var status_otp : Boolean, var status_value: Int)