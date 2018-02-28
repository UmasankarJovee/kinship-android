package com.joveeinfotech.kinship

import android.content.res.Resources
import android.graphics.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.util.Log
import android.widget.ImageView
import com.joveeinfotech.kinship.model.PasswordResult
import com.joveeinfotech.kinship.model.RegisterResult
import com.joveeinfotech.kinship.model.UserProfileDisplayResult
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile_display.*
import kotlinx.android.synthetic.main.top20_donars_list.*
import java.util.HashMap

class ProfileDisplay : AppCompatActivity(), APIListener {

    private var mResources: Resources? = null
    var srcBitmap : Bitmap? = null
    var networkCall : APICall? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_display)

        mResources = resources
        srcBitmap = BitmapFactory.decodeResource(mResources, R.drawable.profile)
        networkCall = APICall(this)
        //activity_profile_display_profile_image.setImageResource(R.drawable.profile)
        setCircle()
        getUserProfile()
    }

    private fun getUserProfile() {
        val queryParams = HashMap<String, String>()
        queryParams.put("phone_number", "9600862338")
        networkCall?.APIRequest("api/v3/profile", queryParams, UserProfileDisplayResult::class.java, this, 3, "Setting your Password...")
    }

    override fun onSuccess(from: Int, response: Any) {
        when (from) {
            1 -> { // User Register
                val result = response as UserProfileDisplayResult
                Log.e("API CALL : ", "inside Main activity and onSuccess")
                if (result.status) {

                    var imageUrl = result.image_url
                    Picasso.with(this).load(imageUrl).into( activity_profile_display_profile_image)

                    fragment_profile_display_TextView_user_name.text=result.name
                    fragment_profile_display_TextView_user_email.text=result.email
                    fragment_profile_display_TextView_user_phone_number.text=result.phone_number
                    fragment_profile_display_TextView_user_blood_group.text=result.blood_group
                    fragment_profile_display_TextView_user_date_of_birth.text=result.date_of_birth

                    Log.e("API CALL : ", "inside Main activity and onSucces and if condition")
                    //Toast.makeText(applicationContext, "You are Registered ${registerResult.status}", Toast.LENGTH_SHORT).show()
                } else {
                    //snackbar(.findViewById(android.R.id.content), "Please wait some minutes")
                }
            }
        }
    }

    override fun onFailure(from: Int, t: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun onNetworkFailure(from: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun setCircle() {
        val paint = Paint()
        // Get source bit map width and height
        val srcBitmapWidth = srcBitmap?.width
        val srcBitmapHeight = srcBitmap?.height

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
        paint.color = Color.RED

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
        top20_donars_list_Linear_layout_CardView_ImageView_profile.setImageDrawable(roundedBitmapDrawable)
    }
}
