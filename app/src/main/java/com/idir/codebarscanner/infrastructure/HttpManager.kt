package com.idir.codebarscanner.infrastructure

import com.idir.codebarscanner.data.Settings
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody.Part.Companion.create
import okhttp3.RequestBody.Companion.toRequestBody


class HttpManager(
    val settings: Settings,
    ) {

    private val jsonType : MediaType = "application/json; charset=utf-8".toMediaType()
    private val client : OkHttpClient = OkHttpClient()

    fun sendData(data:String){
            postData(data)
    }

    private fun postData(data: String) : ResponseBody{
        val body: RequestBody = data.toRequestBody(jsonType)
        val request: Request = Request.Builder()
            .url(settings.host)
            .post(body)
            .build()
        client.newCall(request).execute().use { response -> return response.body!! }
    }

}