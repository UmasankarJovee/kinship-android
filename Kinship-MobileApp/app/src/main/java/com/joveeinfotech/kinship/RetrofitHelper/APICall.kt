package com.joveeinfotech.kinship
//package com.joveeinfotech.kinship

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.joveeinfotech.kinship.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.jetbrains.anko.toast
import org.jetbrains.anko.design.snackbar
import org.json.JSONObject
import retrofit2.Response

/**
 * Created by prandex-and-05 on 6/2/18.
 */
class APICall(val mcontext: Context) {

    //private var call: Call? = null
    lateinit var disposable: Disposable

    var progressDialog: ProgressDialog? = null

    val createRetrofitApiClient by lazy {
        APIClient.getClient()
    }

    var from : Int? = null

    private var observable: Observable<Response<ResponseBody>>? = null

    private var responseListener: APIListener? = null

    enum class Method {
        GET, POST, PUT, DELETE

    }

    fun APIRequest(url: String, queryParams: HashMap<String, String>, responseModel: Class<*>,
                   apiListener : APIListener, from: Int, message: String,
                   showProgressDialogue: Boolean = true) {
        if (Network_check.isNetworkAvailable(mcontext)) {
            //progressDialogLoader?.createProgressDialog(mcontext, message)
            if(showProgressDialogue) {
                createDialog(message)
            }
            Log.e("API CALL : ","inside Apirequest" )
            this.from=from
            this.responseListener=apiListener

            createRetrofitApiClient?.postRequest(url, queryParams)?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({ response ->
                        Log.e("API CALL : ","inside Apirequest and response" )
                        if (response.isSuccessful) {
                            /*try {
                                Log.e("API CALL : ","inside Apirequest isSuccessful" )
                                //progressDialogLoader?.dismissProgressDialog(mcontext)
                                if(showProgressDialogue) {
                                    dismissDialog()
                                }
                                apiListener.onSuccess(from, Gson().fromJson(response.body()!!.string(), responseModel))
                            } catch (e: Exception) {
                                e.printStackTrace()
                                handleError(Throwable(e.cause))
                            }*/
                            if(showProgressDialogue) {
                                dismissDialog()
                            }
                            apiListener.onSuccess(from, Gson().fromJson(response.body()!!.string(), responseModel))

                        } else {
                            Log.e("API CALL : ","inside Apirequest and response else error ${response.code()}" )
                            //val errorMessage = JSONObject(response.code().toString())
                            //HelperClass.SOT(mContext, errorMessage.getString("error_message").toString())
                            //mcontext.toast(errorMessage.getString("error_message").toString())
                            when (response.code()) {
                                401 -> {
                                    //un authorized
                                    //snackbar((mContext as Activity).findViewById(android.R.id.content), "un authorized")
                                }
                                500 -> {
                                    // server internal error
                                }
                                502 -> {
                                    // Bad Gateway, it means version either deprecated or not listed in our system
                                }
                            }
                        }
                    }, { error ->
                        Log.e("API CALL : ","inside Apirequest handle error" )
                        //progressDialogLoader?.dismissProgressDialog(mcontext as Activity)
                        if(showProgressDialogue){
                            dismissDialog()
                        }
                        responseListener?.onFailure(from!!, error)
                        Log.e("response error", error.message.toString())
                    })
        } else {
            showNetworkError()
        }
    }

    private fun dismissDialog() {
        progressDialog?.dismiss()
    }

    private fun createDialog(message: String) {
        progressDialog = ProgressDialog(mcontext, R.style.MyAlertDialogStyle)
        progressDialog?.setMessage(message)
        progressDialog?.show()
    }

    private fun handleError(error: Throwable) {
        Log.e("API CALL : ","inside Apirequest handle error method" )
        //progressDialogLoader?.dismissProgressDialog(mcontext as Activity)
        responseListener?.onFailure(from!!, error)
        Log.e("response error", error.message.toString())
        //mContext.toast("response error")
    }

    fun showNetworkError() {
        snackbar((mcontext as Activity).findViewById(android.R.id.content), "You are offline")
        mcontext.toast("Check your Network Connection")
    }
}