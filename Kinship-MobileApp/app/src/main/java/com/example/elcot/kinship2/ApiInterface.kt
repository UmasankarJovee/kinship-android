package com.example.elcot.kinship2

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
    @POST("login")
    fun userLogin(@Field("phone_number") phone_number : String,
                  @Field("password") password : String) : Observable<LoginResult>

    @FormUrlEncoded
    @POST("register")
    fun UserRegister(@Field("phone_number") phone_number: String,
                     @Field("blood_group") blood_group : String) : Observable<RegisterResult>

    @FormUrlEncoded
    @POST("verify_otp")
    fun verify_otp(@Field("otp") otp : Long) : Observable<OtpResponse>


}