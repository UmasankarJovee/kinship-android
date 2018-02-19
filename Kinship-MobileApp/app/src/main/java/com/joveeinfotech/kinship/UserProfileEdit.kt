package com.joveeinfotech.kinship

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_user_profile_edit.*
import java.text.SimpleDateFormat
import java.util.*

class UserProfileEdit : AppCompatActivity() {

    var blood_group : String? = null
    var gender : String? = null
    var date_of_birth :String? = null
    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile_edit)

        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phone_number_editText.setLines(1)
        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phone_number_editText.setHorizontallyScrolling(true);
        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phone_number_editText.setSingleLine();

        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_email_textView.setLines(1)
        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_email_textView.setHorizontallyScrolling(true);
        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_email_textView.setSingleLine();

        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_email_editText.setLines(1)
        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_email_editText.setHorizontallyScrolling(true);
        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_email_editText.setSingleLine();
        //ephone_number.setImeOptions(EditorInfo.IME_ACTION_DONE);


        var categories_blood=ArrayList<String>()
        /*categories.add("Select Blood Group")
        categories.add("Blood Group (AB+)")
        categories.add("Blood Group (AB-)")
        categories.add("Blood Group (A+)")
        categories.add("Blood Group (A-)")
        categories.add("Blood Group (B+)")
        categories.add("Blood Group (B-")
        categories.add("Blood Group (O+)")
        categories.add("Blood Group (O-)")*/

        categories_blood.add("Select Blood Group")
        categories_blood.add("AB+")
        categories_blood.add("AB-")
        categories_blood.add("A+")
        categories_blood.add("A-")
        categories_blood.add("B+")
        categories_blood.add("B-")
        categories_blood.add("O+")
        categories_blood.add("O-")

        val blood_dataAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,categories_blood)
        blood_dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_blood_group_spinner.adapter=blood_dataAdapter

        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_blood_group_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                blood_group=categories_blood.get(position).toString()
                //Toast.makeText(applicationContext,blood_group,Toast.LENGTH_LONG).show()
            }
        }

        var categories_gender=ArrayList<String>()
        /*categories.add("Select Blood Group")
        categories.add("Blood Group (AB+)")
        categories.add("Blood Group (AB-)")
        categories.add("Blood Group (A+)")
        categories.add("Blood Group (A-)")
        categories.add("Blood Group (B+)")
        categories.add("Blood Group (B-")
        categories.add("Blood Group (O+)")
        categories.add("Blood Group (O-)")*/

        categories_gender.add("Select Gender")
        categories_gender.add("Male")
        categories_gender.add("Female")

        val gender_dataAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,categories_gender)
        gender_dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_gender_spinner.adapter=gender_dataAdapter

        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_gender_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                gender=categories_gender.get(position).toString()
                //Toast.makeText(applicationContext,blood_group,Toast.LENGTH_LONG).show()
            }
        }

        /*var phone_number : TextView = findViewById<TextView>(R.id.tphone_number) as TextView
        var tmail : TextView = findViewById<TextView>(R.id.tmail) as TextView
        var tblood_group : TextView = findViewById<TextView>(R.id.tblood_group) as TextView
        var tgender : TextView = findViewById<TextView>(R.id.tgender) as TextView
        var tdate_of_birth : TextView = findViewById<TextView>(R.id.tdate_of_birth) as TextView

        var ephone_number : EditText = findViewById<EditText>(R.id.ephone_number) as EditText
        var email : EditText = findViewById<EditText>(R.id.email) as EditText

        var edit1 : ImageView = findViewById<ImageView>(R.id.edit) as ImageView
        var edit2 : ImageView = findViewById<ImageView>(R.id.edit2) as ImageView
        var edit3 : ImageView = findViewById<ImageView>(R.id.edit3) as ImageView
        var edit4 : ImageView = findViewById<ImageView>(R.id.edit4) as ImageView
        var edit5 : ImageView = findViewById<ImageView>(R.id.edit5) as ImageView

        var check1 : ImageView = findViewById<ImageView>(R.id.check1) as ImageView
        var check2 : ImageView = findViewById<ImageView>(R.id.check2) as ImageView
        var check3 : ImageView = findViewById<ImageView>(R.id.check3) as ImageView
        var check4 : ImageView = findViewById<ImageView>(R.id.check4) as ImageView
        var check5 : ImageView = findViewById<ImageView>(R.id.check5) as ImageView*/


        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }

        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_editIcon1_imageView.setOnClickListener{
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phoneNumber_textView.visibility = View.GONE
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_editIcon1_imageView.visibility=View.GONE
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phone_number_editText.visibility = View.VISIBLE
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_checkIcon1_imageView.visibility = View.VISIBLE
        }
        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_checkIcon1_imageView.setOnClickListener{
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phoneNumber_textView.setText(activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phone_number_editText.text.toString())
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phone_number_editText.visibility = View.GONE
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_checkIcon1_imageView.visibility = View.GONE
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phoneNumber_textView.visibility = View.VISIBLE
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_editIcon1_imageView.visibility=View.VISIBLE
        }
        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_editIcon2_imageView.setOnClickListener{
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_email_textView.visibility = View.GONE
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_editIcon2_imageView.visibility=View.GONE
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_email_editText.visibility = View.VISIBLE
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_checkIcon2_imageView.visibility = View.VISIBLE
        }
        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon3_imageView.setOnClickListener{
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_blood_group_textView.visibility = View.GONE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon3_imageView.visibility=View.GONE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_blood_group_spinner.visibility = View.VISIBLE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon3_imageView.visibility = View.VISIBLE
        }
        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon4_imageView.setOnClickListener{
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_gender_textView.visibility = View.GONE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon4_imageView.visibility=View.GONE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_gender_spinner.visibility = View.VISIBLE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon4_imageView.visibility = View.VISIBLE
        }
        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon5_imageView.setOnClickListener{
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon5_imageView.visibility=View.GONE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon5_imageView.visibility = View.VISIBLE

            DatePickerDialog(this@UserProfileEdit,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_checkIcon2_imageView.setOnClickListener{
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_email_textView.setText(activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_email_editText.text.toString())
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_email_editText.visibility = View.GONE
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_checkIcon2_imageView.visibility = View.GONE
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_email_textView.visibility = View.VISIBLE
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_email_editText.visibility=View.VISIBLE
        }
        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon3_imageView.setOnClickListener{
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_blood_group_textView.setText(blood_group)
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_blood_group_spinner.visibility = View.GONE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon3_imageView.visibility = View.GONE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_blood_group_textView.visibility = View.VISIBLE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon3_imageView.visibility=View.VISIBLE
        }
        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon4_imageView.setOnClickListener{
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_gender_textView.setText(gender)
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_gender_spinner.visibility = View.GONE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon4_imageView.visibility = View.GONE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_gender_textView.visibility = View.VISIBLE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon4_imageView.visibility=View.VISIBLE
        }
        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon5_imageView.setOnClickListener{
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon5_imageView.visibility = View.GONE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_dateOfBirth_textView.visibility = View.VISIBLE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon5_imageView.visibility=View.VISIBLE
        }
    }

    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_dateOfBirth_textView.setText(sdf.format(cal.time))
    }
}
