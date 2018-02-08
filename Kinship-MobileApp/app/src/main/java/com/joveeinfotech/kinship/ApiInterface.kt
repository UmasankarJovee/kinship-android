package com.joveeinfotech.kinship

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

   /* @FormUrlEncoded
    @POST("api/v0/updated_detail_of_home")  // Not need
    fun update_details(@Field("nothing") nothing:String) : Observable<UpdateDetailsResult>
*/

    @GET("api/v0/updated_detail_of_home")  // Not need
    fun update_details() : Observable<UpdateDetailsResult>

    @FormUrlEncoded
    @POST("api/v1/register")  // Not need
    fun UserRegister(@Field("phone_number") phone_number: String,
                     @Field("blood_group") blood_group : String) : Observable<RegisterResult>
    @FormUrlEncoded
    @POST("api/v2/otp")  // Not need
    fun sendOTP(@Field("otp") otp : String) : Observable<OTPResult>

    @FormUrlEncoded
    @POST("api/v3/password")  // Not need
    fun sendPassword(@Field("password") password : String,
                     @Field("phone_number") phone_number: String) : Observable<PasswordResult>
    @FormUrlEncoded
    @POST("api/v4/login") // Not need
    fun userLogin(@Field("phone_number") phone_number : String,
                  @Field("password") password : String) : Observable<LoginResult>

    @FormUrlEncoded
    @POST("api/v5/persons") // Not need
    fun sendUserProfile(@Field("user_id") user_id : Int,
                        @Field("first_name") first_name : String,
                        @Field("last_name") last_name : String,
                        @Field("date_of_birth") date_of_birth : String,
                        @Field("gender") gender : Int): Observable<UserProfileResult>

    @FormUrlEncoded
    @POST("api/v6/address") // Not need
    fun sendLocation(@Field("latitude") latitude : String,
                     @Field("longitude") longitude : String) : Observable<LocationResult>
}