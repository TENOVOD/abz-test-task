package com.abz.testtask.model

import java.io.File


data class RegistrationData(
    val name:String,
    val email:String,
    val phone:String,
    val positionId: Int,
    val photo: File
) {
}