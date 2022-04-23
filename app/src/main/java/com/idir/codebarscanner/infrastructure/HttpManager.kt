package com.idir.codebarscanner.infrastructure

import android.util.Log
import com.idir.codebarscanner.data.Settings
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody


class HttpManager(
    private val settings: Settings,
    ) {

    private val jsonType : MediaType = "application/json; charset=utf-8".toMediaType()
    private val client : OkHttpClient = OkHttpClient()
    private val dataSource = Provider.barcodeBroadcaster

    fun sendData(){
        Thread {
            val json = Json.encodeToString(dataSource.getSubscribersAsMap())
            postData(json)
        }.start()
    }

    private fun postData(data: String) : ResponseBody?{
        try {
            val body: RequestBody = data.toRequestBody(jsonType)
            val request: Request = Request.Builder()
                .url(settings.host.value)
                .post(body)
                .build()
            client.newCall(request).execute().use { response ->
                return response.body }

        }
            catch (exceptions:Exception){
                return null
            }
    }

}