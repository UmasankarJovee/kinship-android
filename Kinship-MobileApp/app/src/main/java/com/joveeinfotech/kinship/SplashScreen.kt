package com.joveeinfotech.kinship

import android.content.Intent
import android.graphics.PixelFormat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils.*
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val window= window
        window.setFormat(PixelFormat.RGBA_8888)
    }
    var session: SharedData? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        session = SharedData(this)

        var anim= loadAnimation(this,R.anim.alpha)
        anim.reset()
        lin_lay.clearAnimation()
        lin_lay.startAnimation(anim)

        anim = loadAnimation(this,R.anim.translate)
        anim.reset()
        logo.clearAnimation()
        logo.startAnimation(anim)

        var splashThread = object : Thread(){
            override fun run() {
                try {
                    var waited = 0
                    while (waited < 3000)
                    {
                        Thread.sleep(100)
                        waited += 100
                    }

                    val intent = Intent(applicationContext,Login::class.java)
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
         splashThread.start()
    }
}
