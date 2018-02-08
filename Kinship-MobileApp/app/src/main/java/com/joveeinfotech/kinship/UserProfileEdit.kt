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

        ephone_number.setLines(1)
        ephone_number.setHorizontallyScrolling(true);
        ephone_number.setSingleLine();

        tmail.setLines(1)
        tmail.setHorizontallyScrolling(true);
        tmail.setSingleLine();

        email.setLines(1)
        email.setHorizontallyScrolling(true);
        email.setSingleLine();
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
        spinner_blood_group_edit.adapter=blood_dataAdapter

        spinner_blood_group_edit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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
        spinner_gender_edit.adapter=gender_dataAdapter

        spinner_gender_edit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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

        edit.setOnClickListener{
            tphone_number.visibility = View.GONE
            edit.visibility=View.GONE
            ephone_number.visibility = View.VISIBLE
            check1.visibility = View.VISIBLE
        }
        check1.setOnClickListener{
            tphone_number.setText(ephone_number.text.toString())
            ephone_number.visibility = View.GONE
            check1.visibility = View.GONE
            tphone_number.visibility = View.VISIBLE
            edit.visibility=View.VISIBLE
        }
        edit2.setOnClickListener{
            tmail.visibility = View.GONE
            edit2.visibility=View.GONE
            email.visibility = View.VISIBLE
            check2.visibility = View.VISIBLE
        }
        edit3.setOnClickListener{
            tblood_group.visibility = View.GONE
            edit3.visibility=View.GONE
            spinner_blood_group_edit.visibility = View.VISIBLE
            check3.visibility = View.VISIBLE
        }
        edit4.setOnClickListener{
            tgender.visibility = View.GONE
            edit4.visibility=View.GONE
            spinner_gender_edit.visibility = View.VISIBLE
            check4.visibility = View.VISIBLE
        }
        edit5.setOnClickListener{
            edit5.visibility=View.GONE
            check5.visibility = View.VISIBLE

            DatePickerDialog(this@UserProfileEdit,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        check2.setOnClickListener{
            tmail.setText(email.text.toString())
            email.visibility = View.GONE
            check2.visibility = View.GONE
            tmail.visibility = View.VISIBLE
            edit2.visibility=View.VISIBLE
        }
        check3.setOnClickListener{
            tblood_group.setText(blood_group)
            spinner_blood_group_edit.visibility = View.GONE
            check3.visibility = View.GONE
            tblood_group.visibility = View.VISIBLE
            edit3.visibility=View.VISIBLE
        }
        check4.setOnClickListener{
            tgender.setText(gender)
            spinner_gender_edit.visibility = View.GONE
            check4.visibility = View.GONE
            tgender.visibility = View.VISIBLE
            edit4.visibility=View.VISIBLE
        }
        check5.setOnClickListener{
            check5.visibility = View.GONE
            tdate_of_birth.visibility = View.VISIBLE
            edit5.visibility=View.VISIBLE
        }
    }

    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        tdate_of_birth.setText(sdf.format(cal.time))
    }
}
