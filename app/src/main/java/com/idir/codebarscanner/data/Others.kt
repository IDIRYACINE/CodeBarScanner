@file:UseSerializers(MutableStateSerializer::class)

package com.idir.codebarscanner.data

import android.content.Context
import android.os.Parcelable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.idir.codebarscanner.R
import com.idir.codebarscanner.infrastructure.Provider
import com.idir.codebarscanner.infrastructure.serializers.MutableStateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class Settings(val host:MutableState<String>  ,
                    val username:MutableState<String> ,
                    val password:MutableState<String> ,
)  {
    companion object{

        fun save(context: Context,json: String){
            val directory = context.filesDir.absolutePath +'/'+ context.getString(R.string.file_settings)
            Provider.storageManager.saveToFile(json , directory)
        }

        fun load(context: Context) : Settings{
            val directory = context.filesDir.absolutePath +'/'+ context.getString(R.string.file_settings)
            return try {
                Provider.storageManager.loadFromFile(directory)
            } catch (excetion:Exception){
                Settings(mutableStateOf(""), mutableStateOf(""), mutableStateOf(""))
            }
        }

    }
}
