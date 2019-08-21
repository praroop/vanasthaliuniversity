package com.example.meetingschedule.Webservice

/**
 *  * Created by Praroop on 20-05-2019.

 */

interface ServiceCallback {

    fun onSuccess(o: Any)

    fun onFail(t: String)

}
