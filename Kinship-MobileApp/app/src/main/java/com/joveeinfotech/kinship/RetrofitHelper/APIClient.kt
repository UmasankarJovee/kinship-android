package com.joveeinfotech.kinship

import android.content.Context
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.HashMap

/**
 * Created by prandex-and-05 on 6/2/18.
 */
class APIClient {

    companion object {
        fun getClient(): APIInterface? = Retrofit.Builder()
                .baseUrl("http://192.168.8.101/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIInterface::class.java)

        private var default_headers: Map<String, String>? = HashMap()

        fun getCommonHeaders(): Map<String, String>? {
            return default_headers
        }
    }
}