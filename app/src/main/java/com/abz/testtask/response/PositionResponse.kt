package com.abz.testtask.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PositionResponse (
    @SerialName("success")
    val success:String,

    @SerialName("positions")
    val positions: List<Position>
)

@Serializable
data class Position(
    @SerialName("id")
    val id:Int,

    @SerialName("name")
    val name: String
)