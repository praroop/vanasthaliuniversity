package com.example.meetingschedule.Webservice


import android.app.Dialog
import android.content.Context
import com.ekdleaveapplication.webservice.ApiClient
import com.example.biz_intelligence.Utils.Utility
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*

/**
 *  * Created by Praroop on 20-05-2019.

 */

class ServiceRequests {

    private var serviceName = ""
    private var serviceInterface: ServiceInterface? = null
    private var serviceResponseInterface: ServiceCallback? = null
    private var stringHashMap: HashMap<String, String>? = null
    private var progressDialog: Dialog? = null

    var response: Observable<*>? = null

    constructor(observable: java.util.Observable, stringHashMap: HashMap<String, String>, serviceName: String) {
        this.serviceResponseInterface = observable as ServiceCallback
        this.serviceName = serviceName
        this.stringHashMap = stringHashMap
    }



    constructor(observable: java.util.Observable, serviceName: String) {
        this.serviceResponseInterface = observable as ServiceCallback
        this.serviceName = serviceName
    }

    fun execute(context: Context) {
        serviceInterface = ApiClient.client!!.create(ServiceInterface::class.java)
        doRequest(context)
    }

    private fun doRequest(context: Context) {
        if (progressDialog == null) {
            progressDialog = Utility.progressDialog(context)

            if (serviceName == "OTPGen") {
                progressDialog!!.show()
                progressDialog!!.setCancelable(false)
                response = serviceInterface!!.getLogin(
                    stringHashMap!!["MobileNo"].toString(),
                    stringHashMap!!["DeviceId"].toString(),
                    stringHashMap!!["DeviceType"].toString(),
                    stringHashMap!!["APPVersion"].toString()
                )
            }




            response!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { o -> o }
                .subscribe(Consumer<Any> { this.handleResults(it) }, Consumer<Throwable> { this.handleError(it) })
        }
    }

    private fun handleResults(o: Any) {
        progressDialog!!.dismiss()
        progressDialog = null
        serviceResponseInterface!!.onSuccess(o)
    }

    private fun handleError(t: Throwable) {
        progressDialog!!.dismiss()
        progressDialog = null
        serviceResponseInterface!!.onFail(t.message!!)
    }

}
