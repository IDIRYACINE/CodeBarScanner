package com.idir.codebarscanner.data

import kotlinx.serialization.Serializable


@Serializable
data class Settings(var host:String = "" ,
                    val username:String = "" ,
                    var password:String = "",

                    )