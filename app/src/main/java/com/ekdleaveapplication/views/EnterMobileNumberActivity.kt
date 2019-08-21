package com.ekdleaveapplication.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.ekdleaveapplication.R
import com.ekdleaveapplication.model.UserDetailModel
import com.example.biz_intelligence.Utils.Utility
import com.example.meetingschedule.Webservice.ServiceModel
import java.util.*

class EnterMobileNumberActivity : BaseActivity(),View.OnClickListener {

    var serviceModel: ServiceModel = ServiceModel()
    var edit_mobilenumber:EditText?=null
    var btn_submit:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_mobile_number)
        initViews()
        onClickViews()
    }

    private fun onClickViews() {
        btn_submit!!.setOnClickListener(this)
    }

    private fun initViews() {
        edit_mobilenumber=findViewById(R.id.edit_mobilenumber)
        btn_submit=findViewById(R.id.btn_submit)
    }
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btn_submit -> {
                    if (Utility.hasInternet(this)) {
                        //java.lang.NumberFormatException: For input string: "admin@biz-i.io"
                        val stringHashMap = HashMap<String, String>()
                        edit_mobilenumber!!.setText("9582646447")
                        stringHashMap.put("MobileNo",  edit_mobilenumber!!.text.toString())
                        stringHashMap.put("DeviceId", "9718696836")
                        stringHashMap.put("DeviceType",  "I")
                        stringHashMap.put("APPVersion", "9")
                        serviceModel!!.doPostRequest(stringHashMap, "OTPGen", this)
                    } else {
                        Utility.showToast(
                            this, "No Internet Connection", Toast.LENGTH_SHORT)?.show()
                    }
                }
            }
        }
    }
    override fun getModel(): Observable {
        return serviceModel
    }

    override fun update(p0: Observable?, arg: Any?) {
        if (arg is UserDetailModel) {
            val momadd = arg
            if(arg.Status.equals("1")){
                Toast.makeText(this,arg.Message, Toast.LENGTH_SHORT).show()

               // MainApp.savePreference("MomID","",this)



            }
            else{
                /* if (arg.data===null){
                     Toast.makeText(this,arg.Message, Toast.LENGTH_LONG).show()

                 }*/
            }

        }
    }
}
