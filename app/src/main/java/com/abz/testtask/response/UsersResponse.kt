package com.abz.testtask.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsersResponse(

    @SerialName("success")
    val success:Boolean,

    @SerialName("page")
    val page: Int,

    @SerialName("total_pages")
    val totalPages:Int,

    @SerialName("total_users")
    val totalUsers:Int,

    @SerialName("count")
    val count:Int,

    @SerialName("links")
    val links: Links,

    @SerialName("users")
    val users: List<User>
)

@Serializable
data class Links (
    @SerialName("next_url")
    val nextUrl: String?,

    @SerialName("prev_url")
    val prevUrl: String?
)

@Serializable
data class User(

    @SerialName("id")
    val id: String,

    @SerialName("name")
    val name: String,

    @SerialName("email")
    val email: String,

    @SerialName("phone")
    val phone: String,

    @SerialName("position")
    val position: String,

    @SerialName("position_id")
    val positionId: String,

    @SerialName("registration_timestamp")
    val registrationTimestamp: Long,

    @SerialName("photo")
    val photo: String
)