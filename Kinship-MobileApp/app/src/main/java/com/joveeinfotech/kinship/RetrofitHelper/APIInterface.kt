package com.joveeinfotech.kinship

import com.joveeinfotech.kinship.model.ImageUpload
import okhttp3.ResponseBody
import retrofit2.Call
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by prandex-and-05 on 6/2/18.
 */
interface APIInterface {

    @POST
    @FormUrlEncoded
    fun postRequest(@Url url: String, @FieldMap params: Map<String, String>): Observable<Response<ResponseBody>>

    @Multipart
    @POST("api/v1/persons")
    fun uploadImage(@Part image: MultipartBody.Part): Observable<ImageUpload>
}