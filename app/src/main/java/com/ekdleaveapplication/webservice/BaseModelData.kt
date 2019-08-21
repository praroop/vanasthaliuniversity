package com.example.meetingschedule.Webservice

import java.util.*

/**
 *  * Created by Praroop on 20-05-2019.

 */

open class BaseModelData : Observable() {

    override fun notifyObservers(arg: Any?) {
        setChanged()
        super.notifyObservers(arg)
    }

    override fun notifyObservers() {
        setChanged()
        super.notifyObservers()
    }
}