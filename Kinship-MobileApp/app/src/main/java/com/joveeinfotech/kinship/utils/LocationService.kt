package com.joveeinfotech.kinship.utils

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.ActivityCompat
import android.util.Log
import com.joveeinfotech.kinship.APICall
import com.joveeinfotech.kinship.APIListener
import com.joveeinfotech.kinship.model.LocationResult

import io.reactivex.disposables.Disposable
import java.util.HashMap

class LocationService : Service(){

    var lm: LocationManager? = null
    var locationListener: LocationListener? = null
    var context: Context? = this
    internal var mCompositeDisposable: Disposable? = null

    var networkCall : APICall? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        //mApiInterface= RetrofitClient.getClient()
        networkCall = APICall(this)
        lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = MyLocationListener()
        //locationListener = MyLocationListener()

        //locationProcess()
        Log.e("qqqqqqqqqqqqqqqq","service call")
        locationProcess1()

        return super.onStartCommand(intent, flags, startId)
    }

    private fun locationProcess1() {
        Log.e("qqqqqqqqqqqqqqqq","method call")
        //Thread.sleep(20000)

        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.e("qqqqqqqqqqqqqqqq","inside if")
            lm?.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    1000,
                    1000f,
                    locationListener)
        }
    }



    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }


    private inner class MyLocationListener : LocationListener, APIListener {

        override fun onProviderDisabled(arg0: String) {

            Log.e("qqqqqqqqqqqqqqqq","onProviderDisabled")
        }

        override fun onLocationChanged(location: android.location.Location?) {
            if (location != null) {
                Log.e("qqqqqqqqqqqqqqqq","call inside location changed")

                //sendLocation()
                /*mCompositeDisposable=mApiInterface?.sendLocation(location.latitude.toString(),location.longitude.toString())!!.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                { result ->
                                    Log.e("qqqqqqqqqqqqqqqq result","${result.message}")
                                    Log.e("qqqqqqqqqqqqqqqq","result")
                                },
                                { error ->
                                    Log.e("qqqqqqqqqqqqqqqq","${error.localizedMessage}")
                                }
                        )*/
                val queryParams = HashMap<String, String>()
                queryParams.put("latitude", location.latitude.toString())
                queryParams.put("longitude", location.longitude.toString())
                Log.e("MAIN ACTIVITY : ","inside location" )
                networkCall?.APIRequest("api/v6/address",queryParams, LocationResult::class.java,this, 1, "Sending Location...",false)
            }
        }

        override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {
            Log.e("qqqqqqqqqqqqqqqq","onStatusChanged")
        }

        override fun onProviderEnabled(arg0: String) {

            Log.e("qqqqqqqqqqqqqqqq","onProviderEnabled")
        }

        override fun onSuccess(from: Int, response: Any) {

        }

        override fun onFailure(from: Int, t: Throwable) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onNetworkFailure(from: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

}
