package com.ekdleaveapplication.model

import com.google.gson.annotations.SerializedName

/**
 *
 * Created by praroop on 26-11-2018.
 */
 // base modal to use set status and message
open class BaseModels {

    @SerializedName("Status")
    var Message: String? = null

    @SerializedName("Message")
    var Status: String? = null

}

