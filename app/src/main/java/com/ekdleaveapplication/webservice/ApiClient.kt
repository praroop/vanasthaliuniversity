package com.ekdleaveapplication.webservice

import com.example.meetingschedule.Webservice.ServiceInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Praroop on 20-05-2019.
 */

object ApiClient {

    private var retrofit: Retrofit? = null
    private val REQUEST_TIMEOUT = 300
    private var okHttpClient: OkHttpClient? = null

    val client: Retrofit?
        get() {

            if (okHttpClient == null)
                initOkHttp()
            okHttpClient?.dispatcher()?.cancelAll()

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(ServiceInterface.BASE_URL)

                        .client(okHttpClient!!)
                    .addConverterFactory(ScalarsConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())

                        .build()
            }
            return retrofit
        }

    private fun initOkHttp() {
        val httpClient = OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        httpClient.addInterceptor(interceptor)

        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")

            // Adding Authorization token (API Key)
            // Requests will be denied without API key
            /*if (!TextUtils.isEmpty(PrefUtils.getApiKey(context))) {
                    requestBuilder.addHeader("Authorization", PrefUtils.getApiKey(context));
              }*/

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        okHttpClient = httpClient.build()
    }
}