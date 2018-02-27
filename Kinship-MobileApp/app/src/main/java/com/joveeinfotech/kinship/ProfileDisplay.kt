package com.joveeinfotech.kinship

import android.content.res.Resources
import android.graphics.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import kotlinx.android.synthetic.main.activity_profile_display.*
import kotlinx.android.synthetic.main.top20_donars_list.*

class ProfileDisplay : AppCompatActivity() {

    private var mResources: Resources? = null
    var srcBitmap : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_display)

        mResources = resources
        srcBitmap = BitmapFactory.decodeResource(mResources, R.drawable.profile)
        //activity_profile_display_profile_image.setImageResource(R.drawable.profile)
        setCircle()
    }

    private fun setCircle() {
        val paint = Paint()
        // Get source bit map width and height
        val srcBitmapWidth = srcBitmap?.width
        val srcBitmapHeight = srcBitmap?.height

        /*
                    IMPORTANT NOTE : You should experiment with border and shadow width
                    to get better circular ImageView as you expected.
                    I am confused about those size.
                */
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

        /*
                    RoundedBitmapDrawable
                        A Drawable that wraps a bitmap and can be drawn with rounded corners. You
                        can create a RoundedBitmapDrawable from a file path, an input stream, or
                        from a Bitmap object.
                */
        // Initialize a new RoundedBitmapDrawable object to make ImageView circular
        val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(mResources!!, dstBitmap)

        /*
                    setCircular(boolean circular)
                        Sets the image shape to circular.
                */
        // Make the ImageView image to a circular image
        roundedBitmapDrawable.isCircular = true

        /*
                    setAntiAlias(boolean aa)
                        Enables or disables anti-aliasing for this drawable.
                */
        roundedBitmapDrawable.setAntiAlias(true)

        // Set the ImageView image as drawable object
        //activity_profile_display_profile_image.setImageDrawable(roundedBitmapDrawable)
        top20_donars_list_Linear_layout_CardView_ImageView_profile.setImageDrawable(roundedBitmapDrawable)
    }
}
