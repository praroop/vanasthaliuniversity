package com.example.biz_intelligence.Utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.ekdleaveapplication.R

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Chirag on 22-11-2018.
 */

class Utility {

    companion object {

        var decimalFormat = DecimalFormat(".##")

        var mKeyboardStatus : Boolean = false

        // use method for internet is available or not
        fun hasInternet(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
            return if (connectivityManager is ConnectivityManager) {
                val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
                networkInfo?.isConnected ?: false
            } else false
        }

        fun hasInternet1(context: Activity): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
            return if (connectivityManager is ConnectivityManager) {
                val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
                networkInfo?.isConnected ?: false
            } else false
        }
        fun progressDialog(context: Context): Dialog {
            val dialog = Dialog(context)
            val inflate = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
            val img_progress: ImageView = inflate.findViewById(R.id.img_progress)
         /*   Glide.with(context)
                .load(R.drawable.testgif)
                .thumbnail(0.1f)
                .into(img_progress)
            dialog.setContentView(inflate)*/
            dialog.setCancelable(false)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            return dialog
        }
        // show toast message custom
        fun showToast(mContext: Context, sMessage: String, toastDuration: Int): Toast? {

            if (mContext != null) {
                val toast = Toast.makeText(mContext, sMessage, toastDuration)
                val view = toast?.view
                view?.setBackgroundResource(R.drawable.toast_border)
                val tvMessage = view?.findViewById<TextView>(android.R.id.message)
                tvMessage?.setTextColor(mContext.resources.getColor(android.R.color.white))
                tvMessage?.textSize = 12f
                tvMessage?.gravity = Gravity.CENTER
                tvMessage?.setPadding(20, 5, 20, 5)
                toast?.view = view
                toast?.setGravity(Gravity.CENTER, 0, 0)
                return toast
            } else {
                return null
            }
        }
     /*   // show progress dialog
        fun progressDialog(context: Context): Dialog {
            val dialog = Dialog(context)
            val inflate = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
            val img_progress: ImageView = inflate.findViewById(R.id.img_progress)
            Glide.with(context)
                .load(R.drawable.buffer_loading)
                .thumbnail(0.1f)
                .into(img_progress)
            dialog.setContentView(inflate)
            dialog.setCancelable(false)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            return dialog
        }*/

        // hide keyboard
        fun hideKeyboard(context: Context, view: View) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
            mKeyboardStatus = false
        }

        // show keyboard
        fun showKeyboard(context: Context) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
            mKeyboardStatus = true
        }

        // get current date time
        fun getCurrentDateTime(): String {
            var calendar = Calendar.getInstance()
            var simpleDF = SimpleDateFormat("yyyy-MM-dd HH:mm")
            //var simpleDF = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
            var dateShow = simpleDF.format(calendar.time)
            return dateShow
        }

        fun checkUsernameValidation(username: String): Boolean {
            if (username.contains("@")) {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()
            } else {
                return android.util.Patterns.PHONE.matcher(username).matches() && username.length >= 10
            }
        }

        fun checkValidEmail(username: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()
        }

        fun checkValidNo(username: String): Boolean {
            return android.util.Patterns.PHONE.matcher(username).matches() && username.length >= 10
        }

        fun sendAppEmail(contextApp: Context) {
            val emailIntent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "eapps@margcompusoft.com", null
                )
            )
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Help - eRetail App")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "")
            contextApp.startActivity(Intent.createChooser(emailIntent, "Send email..."))
        }

        @RequiresApi(Build.VERSION_CODES.M)
        @SuppressLint("MissingPermission")
        fun sendAppCall(contextApp: Activity) {
            if (!hasPermissions(contextApp, * PERMISSIONSCALL)) {
                contextApp.requestPermissions(Utility.PERMISSIONSCALL, 7)
            } else {
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "01130969648"))
                contextApp.startActivity(intent)
            }
        }

        @RequiresApi(Build.VERSION_CODES.M)
        @SuppressLint("MissingPermission")
        fun sendCall(contextApp: Activity, mobileNumber: String) {
            if (!hasPermissions(contextApp, * PERMISSIONSCALL)) {
                contextApp.requestPermissions(Utility.PERMISSIONSCALL, 7)
            } else {
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobileNumber))
                contextApp.startActivity(intent)
            }
        }

        fun forcefullyOpenSettings(contextApp: Activity, permissions: Array<String>, message: String) {
            var showRational: Boolean = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                contextApp.shouldShowRequestPermissionRationale(permissions[0])
            } else {
                TODO("VERSION.SDK_INT < M")
            }
            if (!showRational) {
                val alertDialogBuilder = AlertDialog.Builder(contextApp)
                alertDialogBuilder.setTitle("Permissions Required")
                    .setMessage("You have forcefully denied location permission. Please open settings, go to permissions and allow them.")
                    .setPositiveButton("Settings", DialogInterface.OnClickListener { dialog, which ->
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", contextApp.packageName, null)
                        )
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        contextApp.startActivity(intent)
                    })
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> })
                    .setCancelable(false)
                    .create()
                    .show()
            } else {
                //Utility.showToast(contextApp, message, Toast.LENGTH_SHORT)?.show()
            }
        }



        var PERMISSIONSCALL = arrayOf(android.Manifest.permission.CALL_PHONE)
        var PERMISSIONS = arrayOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
        )

        fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
            if (context != null && permissions != null) {
                for (permission in permissions) {
                    if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                        return false
                    }
                }
            }
            return true
        }











        fun replaceZero(value: String?): String? {
            if (value != null && value.length > 0) {
                val decimalFormat = DecimalFormat()
                return decimalFormat.format(value.toDouble())
            } else {
                return "0"
            }
        }



        fun replaceNull(input: String?): String {
            return input ?: ""
        }



        fun convertDotsValue(svalue: String): String {
            var value: String? = svalue
            if (value != null && value.length > 0) {
                if (value.contains(".")) {
                    val arrValues = value.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val val1 = arrValues[0]
                    val val2 = arrValues[1]
                    try {
                        if (java.lang.Double.valueOf(val2) > 0) {
                            return value
                        } else {
                            value = val1
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            } else {
                value = ""
            }
            return value!!
        }

        fun NumberFormate(inputString: String): String {
            var resultString = ""
            try {
                val litersOfPetrol = java.lang.Float.parseFloat(inputString)
                val df = DecimalFormat("0.00")
                df.maximumFractionDigits = 2
                resultString = df.format(litersOfPetrol)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return resultString
        }

        /***Note :
         * (A) First check User select pick from counter or deliver to store.
         *     If user select pick from counter then no condition need to check else check
         * (B) When user select deliver to store then check
         *     1. User is Registered in marg or not (Via staus)
         *     2. After that check total amount of the user is not less than that came in registered or unregistered (POSITION  2 OR 3)
         *     3. If not less than that registered or unregistered then don't add Delivery amount else Add Delivery Amount (POSITION 6)
         *     4. After all of above then check Priority. If priority is HIGH then add priority amount else not add priority amount. (POSITION 12)
         *     ***/


    }

}