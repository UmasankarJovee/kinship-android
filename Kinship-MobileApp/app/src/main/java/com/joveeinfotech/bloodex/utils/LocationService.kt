package com.joveeinfotech.bloodex.utils

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.ActivityCompat
import com.joveeinfotech.bloodex.Network_check
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.setStringPreference
import com.joveeinfotech.bloodex.utils.Others.DLog

class LocationService : Service(){

    var lm: LocationManager? = null
    var locationListener: LocationListener? = null
    var context: Context? = this
    var network_enabled : Boolean? = null
    var location : Location? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        //locationListener = MyLocationListener()

        DLog("qqqqqqqqqqqqqqqq", "service call")
        //locationProcess1()

        network_enabled = lm?.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (Network_check.isNetworkAvailable(context!!) && network_enabled!!) {

            if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                DLog("qqqqqqqqqqqqqqqq","inside if")
                location = lm?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                DLog("Location1 : ","${location?.latitude},${location?.longitude}")
                setStringPreference(context!!,"latitude",location?.latitude.toString())
                setStringPreference(context!!,"longitude",location?.longitude.toString())
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

   /* private fun locationProcess1() {
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
*/


    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private inner class MyLocationListener : LocationListener {

        override fun onProviderDisabled(arg0: String) {

            DLog("qqqqqqqqqqqqqqqq","onProviderDisabled")
        }

        override fun onLocationChanged(location: android.location.Location?) {
            if (location != null) {
                DLog("qqqqqqqqqqqqqqqq","call inside location changed")

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
                /*val queryParams = HashMap<String, String>()
                queryParams.put("latitude", location.latitude.toString())
                queryParams.put("longitude", location.longitude.toString())
                Log.e("MAIN ACTIVITY : ","inside location" )
                networkCall?.APIRequest("api/v6/address",queryParams, LocationResult::class.java,this, 1, "Sending Location...",false)
           */


            }
        }

        override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {
            DLog("qqqqqqqqqqqqqqqq","onStatusChanged")
        }

        override fun onProviderEnabled(arg0: String) {

            DLog("qqqqqqqqqqqqqqqq","onProviderEnabled")
        }
    }

}
