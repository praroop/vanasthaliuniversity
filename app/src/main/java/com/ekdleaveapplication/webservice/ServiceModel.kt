package com.example.meetingschedule.Webservice

import android.content.Context

/**
 * * Created by Praroop on 20-05-2019.

 */

class ServiceModel : BaseModelData(), ServiceCallback {

    override fun onSuccess(o: Any) {
        notifyObservers(o)
    }

    override fun onFail(t: String) {
        notifyObservers(t)
    }

    fun doPostRequest(stringHashMap: HashMap<String, String>, serviceName: String, context: Context) {
       ServiceRequests(this, stringHashMap, serviceName).execute(context)
    }


    fun doGetRequest(serviceName: String, context: Context) {
       ServiceRequests(this, serviceName).execute(context)
    }
}
