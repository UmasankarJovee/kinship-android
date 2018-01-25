package com.example.elcot.kinship2

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_user_profile.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

class UserProfile : AppCompatActivity() {

    var year : Int=0
    var month: Int =0
    var day : Int =0

    var bitmap : Bitmap?=null
    var ba:ByteArray?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        imageView.setImageResource(R.drawable.profile_image)
        imageView.setOnClickListener{
            val i= Intent()
            i.type="image/*"
            i.action=Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(i,"select image"),1)
        }

        dob.setOnClickListener{
            val c=Calendar.getInstance()
            year=c.get(Calendar.YEAR)
            month=c.get(Calendar.MONTH)
            day=c.get(Calendar.DAY_OF_MONTH)
            //call()
        }

        user_profile_submit.setOnClickListener{
            sendUserProfile()
        }
    }

    private fun sendUserProfile() {

    }

    /*private fun call() {
        var dd=DatePickerDialog(this,R.style.DialogTheme,DatePickerDialog.OnDateSetListener{
            view,year,monthOfYear,dayOfMonth -> dob.text=dayOfMonth.toString()
        })
        dd.show()
    }*/

    /* private fun call() {
        val dd=DatePickerDialog(this,R.style.DialogTheme,DatePickerDialog.OnDateSetListener{
            view,year,monthOfYear,dayOfMonth ->
                 dob.text=(dayOfMonth.toString()+"-"+(monthOfYear+1)+"-"+year)},year,month,day)
         dd.show()
     }*/

    override fun onActivityResult(RC: Int, RQC: Int, I: Intent?) {
        super.onActivityResult(RC,RQC,I)

        if(RC == 1 && RQC == Activity.RESULT_OK && I != null && I.data != null)
        {
            val uri=I.data
            try {
                bitmap =  MediaStore.Images.Media.getBitmap(contentResolver,uri)
                val bStream = ByteArrayOutputStream()
                bitmap?.compress(Bitmap.CompressFormat.PNG,100,bStream)
                ba= bStream.toByteArray()
                imageView.setImageBitmap(bitmap)

            }
            catch (e : IOException){

            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putByteArray("myarray",ba)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        ba=savedInstanceState?.getByteArray("myarray")
        bitmap = BitmapFactory.decodeByteArray(ba,0,ba!!.size)
        imageView.setImageBitmap(bitmap)
    }
}

