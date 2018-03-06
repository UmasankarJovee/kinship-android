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

data class UpdateDetailsResult(var count_of_hospitals : String,
                               var count_of_users : String,
                               var count_of_donors : String,
                               var message: String,
                               var status : Boolean)

data class CountryResult(var country : ArrayList<String>)

data class StateResult(var state : ArrayList<String>)

data class SendAddressResult(var status :Boolean)

data class DistrictResult(var district : ArrayList<String>)

data class UserAdditionalDetailsResult(var status: Boolean)

data class Album(val image:Bitmap, val text: String)

data class SearchHospitalResult(var hospitals: ArrayList<String>)

data class SearchBloodInUserRequest(var status: Boolean)

/*data class UserModel(var isSelected:Boolean,var languageNames:String)*/

data class Languages(val languages: String)

data class ImageUpload(var status: Boolean)

data class SendTokenResult(var status: Boolean)

/*data class AndroidResponse(val android_lists:List<GetTop20Result>)*/
data class GetTop20Result(var profile_url : String,
                          var name : String,
                          var district: String,
                          var total_like : Int,
                          var total_donated : Int,
                          var last_donated_date : String)

data class SendingUserProfileEditResult(var status: Boolean,var message: String,var result: String)

data class ReplyBloodRequestResult(var status: Boolean, var phoneNumber : Long)

data class NotComeForRequestResult(var status: Boolean)

data class UserProfileDisplayResult(var status : Boolean,
                                    var first_name : String,
                                    var last_name : String,
                                    var image_url : String,
                                    var phone_number : String,
                                    var email : String,
                                    var blood_group : String,
                                    var date_of_birth : String,
                                    var total_donated : String,
                                    var total_request : String,
                                    var last_donated_date : String,
                                    var street_name : String,
                                    var locality : String,
                                    var city : String,
                                    var district : String,
                                    var state : String
                                    /*var address : List<AddressOfUser>*/)

