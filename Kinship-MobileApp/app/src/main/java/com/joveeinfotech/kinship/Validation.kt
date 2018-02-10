package com.joveeinfotech.kinship

/**
 * Created by prandex-and-05 on 26/1/18.
 */
object Validation {

    val phone_number = Regex("[0-9]{10}")

    val Email_PATTERN = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")

    fun isValidPhoneNumber(number : String) : Boolean {
        return (number.matches(phone_number))
    }

    fun isValidEmail(email_id:String):Boolean{
        return(email_id.matches(Email_PATTERN))
    }


}