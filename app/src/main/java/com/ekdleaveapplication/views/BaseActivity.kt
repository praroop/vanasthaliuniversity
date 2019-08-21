package com.ekdleaveapplication.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*

/**
 * Created by Praroop on 20-05-2019.
 */

abstract class BaseActivity : AppCompatActivity(), Observer {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val model = getModel()
        model.addObserver(this)
    }

    abstract fun getModel(): Observable

}
