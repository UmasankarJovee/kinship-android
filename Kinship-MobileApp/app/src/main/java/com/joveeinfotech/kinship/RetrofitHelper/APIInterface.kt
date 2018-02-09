package com.example.prandex_and_05.userregistration

import okhttp3.ResponseBody
import retrofit2.Call
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by prandex-and-05 on 6/2/18.
 */
interface APIInterface {

    @POST
    @FormUrlEncoded
    fun postRequest(@Url url: String, @FieldMap params: Map<String, String>): Observable<Response<ResponseBody>>
}