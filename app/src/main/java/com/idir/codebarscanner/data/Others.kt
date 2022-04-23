@file:UseSerializers(MutableStateSerializer::class)

package com.idir.codebarscanner.data

import androidx.compose.runtime.MutableState
import com.idir.codebarscanner.infrastructure.serializers.MutableStateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
data class Settings(val host:MutableState<String>  ,
                    val username:MutableState<String> ,
                    val password:MutableState<String> ,
)
