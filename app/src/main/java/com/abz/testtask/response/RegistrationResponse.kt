package com.abz.testtask.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistrationResponse(

    @SerialName("success")
    val success: Boolean?,
    @SerialName("user_id")
    val userId: Int?=-1,
    @SerialName("message")
    val message: String,
    @SerialName("fails")
    val fails: Fails? = null
)

@Serializable
data class Fails(
    @SerialName("name")
    val name: ArrayList<String>?=null,
    @SerialName("email")
    val email: ArrayList<String>?=null,
    @SerialName("phone")
    val phone:ArrayList<String>?=null,
    @SerialName("position_id")
    val positionId: ArrayList<String>?=null,
    @SerialName("photo")
    val photo: ArrayList<String>?=null
)