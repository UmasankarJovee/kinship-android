package com.example.elcot.kinship2

import com.example.elcot.kinship2.model.*
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {

    /*@FormUrlEncoded
    @POST("api/v1/register")
    fun addUser(@Field("blood_group") blood_group: String,
                @Field("phone_number") phone_number: String,
                @Field("password") password : String): Observable<Record>*/

    @FormUrlEncoded
    @POST("api/v0/updated_detail_of_home")
    fun update_details(@Field("nothing") nothing:String) : Observable<UpdateDetailsResult>

    @FormUrlEncoded
    @POST("api/v1/register")
    fun UserRegister(@Field("phone_number") phone_number: String,
                     @Field("blood_group") blood_group : String) : Observable<RegisterResult>

    @FormUrlEncoded
    @POST("api/v2/otp")
    fun sendOTP(@Field("otp") otp : String) : Observable<OTPResult>

    @FormUrlEncoded
    @POST("api/v3/password")
    fun sendPassword(@Field("password") password : String) : Observable<PasswordResult>

    @FormUrlEncoded
    @POST("api/v4/login")
    fun userLogin(@Field("phone_number") phone_number : String,
                  @Field("password") password : String) : Observable<LoginResult>

    @FormUrlEncoded
    @POST("api/v5/persons")
    fun sendUserProfile(@Field("user_id") user_id : Int,
                        @Field("first_name") first_name : String,
                        @Field("last_name") last_name : String,
                        @Field("date_of_birth") date_of_birth : String,
                        @Field("gender") gender : Int): Observable<UserProfileResult>

    @FormUrlEncoded
    @POST("api/v6/address")
    fun sendLocation(@Field("latitude") latitude : String,
                     @Field("longitude") longitude : String) : Observable<LocationResult>
}