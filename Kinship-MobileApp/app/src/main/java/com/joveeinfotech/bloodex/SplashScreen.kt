package com.joveeinfotech.bloodex

import android.content.Intent
import android.graphics.PixelFormat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.getBooleanPreference
import com.joveeinfotech.bloodex.utils.Others
import com.joveeinfotech.bloodex.utils.SharedData
import com.joveeinfotech.bloodex.view.Login
import com.joveeinfotech.bloodex.view.RequestResponse

class SplashScreen : AppCompatActivity() {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val window= window
        window.setFormat(PixelFormat.RGBA_8888)
    }

    var handler : Handler? = null

    var session: SharedData? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        session = SharedData(this)
        handler = Handler()
        startSplash()

        /*var anim= loadAnimation(this,R.anim.alpha)
        anim.reset()
        lin_lay.clearAnimation()
        lin_lay.startAnimation(anim)

        anim = loadAnimation(this,R.anim.translate)
        anim.reset()
        activity_splash_screen_logo.clearAnimation()
        activity_splash_screen_logo.startAnimation(anim)

        var splashThread = object : Thread(){
            override fun run() {
                try {
                    var waited = 0
                    while (waited < 3000)
                    {
                        Thread.sleep(100)
                        waited += 100
                    }

                    val intent = Intent(applicationContext, Login::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    startActivity(intent)
                    //overridePendingTransition(android.R.anim.,android.R.anim.slide_in_left)
                    this@SplashScreen.finish()
                }
                catch ( e : InterruptedException){

                }
                finally {
                    this@SplashScreen.finish()
                }
            }
        }
         splashThread.start()*/

    }

    private fun startSplash() {
        handler?.postDelayed({
            afterSplash()
        },2000)
    }

    private fun afterSplash() {
        if (session?.isFirstInstall()!!) {
            val i = Intent(applicationContext, AppRegister::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(i)
            //loginView.closeActivity()
            finish()
        }else if (getBooleanPreference(this, "isClickDonated", false)){
            //var isClickDonated = getBooleanPreference(this, "isClickDonated", false)
            Others.DLog("qqqqqqqqqqqqqqqq", "call inside if condition islogin")
            val i = Intent(applicationContext, RequestResponse::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(i)
            //loginView.closeActivity()
            finish()
        }else if (session?.isLoggedIn()!!) {
            Others.DLog("qqqqqqqqqqqqqqqq", "call inside if condition islogin")
            val i = Intent(applicationContext, Home::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(i)
            //loginView.closeActivity()
            finish()
        }else{
            val i = Intent(applicationContext, Login::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(i)
            //loginView.closeActivity()
            finish()
        }
    }
}
