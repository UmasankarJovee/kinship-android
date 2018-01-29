package com.example.elcot.kinship2

import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {

    /*@FormUrlEncoded
    @POST("api/v1/register")
    fun addUser(@Field("blood_group") blood_group: String,
                @Field("phone_number") phone_number: String,
                @Field("password") password : String): Observable<Record>*/

    @FormUrlEncoded
    @POST("api/login")
    fun userLogin(@Field("phone_number") phone_number : String,
                  @Field("password") password : String) : Observable<LoginResult>

    @FormUrlEncoded
    @POST("api/v1/register")
    fun UserRegister(@Field("phone_number") phone_number: String,
                     @Field("blood_group") blood_group : String) : Observable<RegisterResult>

    @FormUrlEncoded
    @POST("password")
    fun sendPassword(@Field("user_id") user_id : Int,
                     @Field("password") otp : String) : Observable<PasswordResult>

    @FormUrlEncoded
    @POST("api/v2/persons")
    fun sendUserProfile(@Field("user_id") user_id : Int,
                        @Field("first_name") first_name : String,
                        @Field("last_name") last_name : String,
                        @Field("date_of_birth") date_of_birth : String,
                        @Field("gender") gender : String): Observable<PasswordResult>

    @FormUrlEncoded
    @GET("api/v7/updated_details_of_home")
    fun update_details() : Observable<UpdateDetailsResult>


}