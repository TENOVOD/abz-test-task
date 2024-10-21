package com.abz.testtask.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    @SerialName("success")
    val success:Boolean,
    @SerialName("token")
    val token: String
)