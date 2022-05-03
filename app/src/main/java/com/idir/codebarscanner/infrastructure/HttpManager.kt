package com.idir.codebarscanner.infrastructure

import android.content.Context
import android.os.Handler
import android.os.Message
import android.widget.Toast
import com.idir.codebarscanner.R
import com.idir.codebarscanner.data.Settings
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody


class HttpManager(
    private val settings: Settings,
    ) {

    companion object{
        const val SUCCESS = 0
        const val FAIL = -1
    }

    private val jsonType : MediaType = "application/json; charset=utf-8".toMediaType()
    private val client : OkHttpClient = OkHttpClient()


    fun sendData(handler: Handler){
        Thread {
            val json = Provider.barcodesManager.encodeActiveToJson()
            val responseCode = postData(json)

            if((responseCode != null) && (responseCode == 200)){
                onSuccess(handler)
            }else{
                onFail(handler)
            }
        }.start()
    }

    private fun postData(data: String) : Int?{
        try {
            val body: RequestBody = data.toRequestBody(jsonType)
            val request: Request = Request.Builder()
                .url(settings.host.value)
                .post(body)
                .build()
            client.newCall(request).execute().use { response ->
                return response.code }
            }
            catch (exceptions:Exception){
                return null
            }
    }

    private fun onFail(handler: Handler){
        val message: Message = handler.obtainMessage()
        message.sendToTarget()
    }
    private fun onSuccess(handler: Handler){
        val message: Message = handler.obtainMessage()
        message.sendToTarget()
    }

}