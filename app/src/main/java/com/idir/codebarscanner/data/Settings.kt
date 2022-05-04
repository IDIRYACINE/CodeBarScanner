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
data class Settings(val host:MutableState<String>  = mutableStateOf(""),
                    val username:MutableState<String>  = mutableStateOf(""),
                    val password:MutableState<String>  = mutableStateOf(""),
                    val vibrate:MutableState<Boolean> = mutableStateOf(false),
                    val playSound:MutableState<Boolean> = mutableStateOf(false),
                    val continuousScan : MutableState<Boolean> = mutableStateOf(true),
                    val manualScan : MutableState<Boolean> = mutableStateOf(false),
                    val duplicateGroup : MutableState<Boolean> = mutableStateOf(false),
                    val duplicateScan : MutableState<Boolean> = mutableStateOf(false)

)