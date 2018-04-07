package com.joveeinfotech.kinship

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.about_us.*

class AboutUS : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_us)
        activity_select_language_constraintLayout2_skip_textView.setOnClickListener {
            startActivity(Intent(this@AboutUS,SelectLanguage::class.java))
            finish()
        }
    }
}
