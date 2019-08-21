package com.ekdleaveapplication.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.ekdleaveapplication.R

class EnterOtpActivity : AppCompatActivity() {
    var  linearLayout:LinearLayout?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_otp)
        linearLayout=findViewById(R.id.linearLayout)
        linearLayout!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, Home_Activity::class.java)
            startActivity(intent)

        })
    }
}
