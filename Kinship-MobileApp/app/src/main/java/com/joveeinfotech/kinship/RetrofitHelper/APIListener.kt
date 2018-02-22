package com.joveeinfotech.kinship

import okhttp3.ResponseBody

/**
 * Created by prandex-and-05 on 6/2/18.
 */
interface APIListener {

    fun onSuccess(from: Int, response: Any)
    fun onFailure(from: Int, t: Throwable)
    fun onNetworkFailure(from: Int)
}