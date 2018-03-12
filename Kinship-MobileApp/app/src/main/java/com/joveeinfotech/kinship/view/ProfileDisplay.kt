package com.joveeinfotech.kinship.view

import android.content.res.Resources
import android.graphics.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import kotlinx.android.synthetic.main.fragment_profile_display.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory

import java.io.IOException
import java.net.HttpURLConnection

import com.squareup.picasso.Picasso
import java.net.URL
import android.graphics.PorterDuffXfermode
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.presenter.ProfileDisplayPresenterImpl

class ProfileDisplay : AppCompatActivity(), ProfileDisplayView {

    private var mResources: Resources? = null
    var srcBitmap : Bitmap? = null
    var profileDisplayPresenter: ProfileDisplayPresenterImpl? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profile_display)

        mResources = resources
        profileDisplayPresenter = ProfileDisplayPresenterImpl(this, this)

        //srcBitmap = BitmapFactory.decodeResource(mResources, R.drawable.profile)
        //setCircle(bm)

        //Picasso.with(this).load("https://www.gstatic.com/webp/gallery/4.sm.jpg").into(fragment_profile_display_user_profile_image)

        /*srcBitmap = BitmapFactory.decodeFile("https://130513-375933-1-raikfcquaxqncofqfm.stackpathdns.com/wp-content/uploads/2016/10/Shreya-Ghoshal-2.jpg")
        setCircle()*/
        /*val bm = BitmapFactory.decodeResource(resources, R.drawable.profile)
        //profile_images = settings_profile_image_cursor.getString(settings_profile_image_cursor.getColumnIndex(DBStatics.IMAGE))
        fragment_profile_display_user_profile_image.setImageBitmap(getCroppedBitmap(bm))
        Picasso.with(this).load("https://www.gstatic.com/webp/gallery/4.sm.jpg").placeholder(R.drawable.profile).error(R.drawable.profile).into(fragment_profile_display_user_profile_image)

       srcBitmap = getBitmapFromURL("https://130513-375933-1-raikfcquaxqncofqfm.stackpathdns.com/wp-content/uploads/2016/10/Shreya-Ghoshal-2.jpg")
        setCircle()
*/
        /*val url = URL("https://www.gstatic.com/webp/gallery/4.sm.jpg")
        fragment_profile_display_user_profile_image.setImageBitmap(BitmapFactory.decodeStream(url.content as InputStream))
*/
        /*val bmOptions = BitmapFactory.Options()
        var bitmap = BitmapFactory.decodeFile("https://130513-375933-1-raikfcquaxqncofqfm.stackpathdns.com/wp-content/uploads/2016/10/Shreya-Ghoshal-2.jpg", bmOptions)
        bitmap = Bitmap.createScaledBitmap(bitmap, parent, parent.getHeight(), true)
        imageView.setImageBitmap(bitmap)
*/
        /*activity_profile_display_profile_image.setImageResource(R.drawable.profile)
        //setCircle()
        //getUserProfile()*/

    }

    override fun setProfileDetails(image_url: String, name: String, total_donated: String,
                                   total_request: String, last_donated_date: String,
                                   email: String, phone_number: String, blood_group: String,
                                   date_of_birth: String, address: String) {
        //Picasso.with(this).load(image_url).into(fragment_profile_display_user_profile_image)
        fragment_profile_display_TextView_user_name.text = name
        fragment_profile_display_TextView_Total_donated.text = total_donated
        fragment_profile_display_TextView_Total_requested.text = total_request
        fragment_profile_display_TextView_Last_donated.text = total_request
        fragment_profile_display_TextView_user_email.text = email
        fragment_profile_display_TextView_user_phone_number.text = phone_number
        fragment_profile_display_TextView_user_blood_group.text = blood_group
        fragment_profile_display_TextView_user_date_of_birth.text = date_of_birth
        fragment_profile_display_TextView_user_address.text = address
    }

    private fun setCircle() {
        val paint = Paint()
        // Get source bit map width and height
        /*val srcBitmapWidth = srcBitmap?.width
        val srcBitmapHeight = srcBitmap?.height
*/
        val srcBitmapWidth = 110
        val srcBitmapHeight = 110

        // Define border and shadow width
        val borderWidth = 25
        val shadowWidth = 10

        // destination bitmap width
        val dstBitmapWidth = Math.min(srcBitmapWidth!!, srcBitmapHeight!!) + borderWidth * 2
        //float radius = Math.min(srcBitmapWidth,srcBitmapHeight)/2;

        // Initializing a new bitmap to draw source bitmap, border and shadow
        val dstBitmap = Bitmap.createBitmap(dstBitmapWidth, dstBitmapWidth, Bitmap.Config.ARGB_8888)

        // Initialize a new canvas
        val canvas = Canvas(dstBitmap)

        // Draw a solid color to canvas
        canvas.drawColor(Color.WHITE)

        // Draw the source bitmap to destination bitmap by keeping border and shadow spaces
        canvas.drawBitmap(srcBitmap, ((dstBitmapWidth - srcBitmapWidth) / 2).toFloat(), ((dstBitmapWidth - srcBitmapHeight) / 2).toFloat(), null)

        // Use Paint to draw border
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = (borderWidth * 2).toFloat()
        paint.color = Color.WHITE

        // Draw the border in destination bitmap
        canvas.drawCircle((canvas.width / 2).toFloat(), (canvas.height / 2).toFloat(), (canvas.width / 2).toFloat(), paint)

        // Use Paint to draw shadow
        paint.color = Color.LTGRAY
        paint.strokeWidth = shadowWidth.toFloat()

        // Draw the shadow on circular bitmap
        canvas.drawCircle((canvas.width / 2).toFloat(), (canvas.height / 2).toFloat(), (canvas.width / 2).toFloat(), paint)

        val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(mResources!!, dstBitmap)

        // Make the ImageView image to a circular image
        roundedBitmapDrawable.isCircular = true

        roundedBitmapDrawable.setAntiAlias(true)

        // Set the ImageView image as drawable object

        //fragment_profile_display_user_profile_image.setImageDrawable(roundedBitmapDrawable)

        //return dstBitmap

    }

    fun getBitmapFromURL(src: String): Bitmap? {
        try {
            val url = URL(src)
            val connection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input = connection.getInputStream()
            return BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }

    fun getCroppedBitmap(bitmap: Bitmap): Bitmap {
        val output = Bitmap.createBitmap(bitmap.width,
                bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)

        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle((canvas.width / 2).toFloat(), (canvas.height / 2).toFloat(), (canvas.width / 2).toFloat(), paint)
        /*canvas.drawCircle(bitmap.width / 2, bitmap.height / 2,
                bitmap.width / 2, paint)*/
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output
    }
}
