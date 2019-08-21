package com.example.meetingschedule.Webservice


import com.ekdleaveapplication.model.UserDetailModel
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * Created by Praroop on 20-05-2019.
 */

interface ServiceInterface {

    companion object {

       val BASE_URL = "http://ekdantainfosoft.com:8086/StudentLeaveApllication/"
    }
    @FormUrlEncoded
    @POST("OTPGen")
        fun getLogin(@Field("MobileNo") MobileNo: String,
                     @Field("DeviceId") DeviceId: String,
                     @Field("DeviceType") DeviceType: String,
                     @Field("APPVersion") APPVersion: String):
            Observable<UserDetailModel>



}

