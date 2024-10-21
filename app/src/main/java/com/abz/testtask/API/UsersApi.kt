package com.abz.testtask.API


import com.abz.testtask.model.RegistrationData
import com.abz.testtask.response.PositionResponse
import com.abz.testtask.response.RegistrationResponse
import com.abz.testtask.response.TokenResponse
import com.abz.testtask.response.UsersResponse

interface UsersApi {

    suspend fun fetchUsers(page: Int = 1, count:Int = 6): UsersResponse

    suspend fun getPosition(): PositionResponse

    suspend fun getToken(): TokenResponse

    suspend fun registrationUser(token:String,user: RegistrationData):RegistrationResponse?
}