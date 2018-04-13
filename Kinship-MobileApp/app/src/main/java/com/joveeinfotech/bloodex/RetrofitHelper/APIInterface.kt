package com.joveeinfotech.bloodex

import com.google.gson.JsonObject
import com.joveeinfotech.bloodex.model.ImageUpload
import com.joveeinfotech.bloodex.model.UserHealthDetailsResult
import okhttp3.ResponseBody
import io.reactivex.Observable
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.POST



/**
 * Created by prandex-and-05 on 6/2/18.
 */
interface APIInterface {

    @POST
    @FormUrlEncoded
    fun postRequest(@Url url: String, @FieldMap params: Map<String, String>): Observable<Response<ResponseBody>>

    /*@Multipart
    @POST("api/v1/persons")
    fun uploadImage(@Part image: MultipartBody.Part): Observable<ImageUpload>
*/

    @Headers("Content-Type:application/json")
    @POST("api/v1/health")
    fun postJson(@Body body: JsonObject): Observable<Response<ResponseBody>>

}