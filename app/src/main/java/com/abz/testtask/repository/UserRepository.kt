package com.abz.testtask.repository

import com.abz.testtask.API.UsersApi
import com.abz.testtask.model.RegistrationData
import com.abz.testtask.response.PositionResponse
import com.abz.testtask.response.RegistrationResponse
import com.abz.testtask.response.TokenResponse
import com.abz.testtask.response.UsersResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.utils.io.errors.IOException


class UserRepository(
   private val client: HttpClient
):UsersApi {

    val BASE_URL = "https://frontend-test-assignment-api.abz.agency/api/v1"


    override suspend fun fetchUsers(page: Int, count:Int): UsersResponse {
        return client.get("$BASE_URL/users"){
            parameter("page",page)
            parameter("count",count)
        }.body()
    }

    override suspend fun getPosition(): PositionResponse {
        return client.get("$BASE_URL/positions").body()
    }

    override suspend fun getToken(): TokenResponse {
        return client.get("$BASE_URL/token").body()
    }

    override suspend fun registrationUser(token:String, user: RegistrationData): RegistrationResponse? {
//        val response:RegistrationResponse =  client.post("$BASE_URL/users"){
//            parameter("Token", token)
//            contentType(ContentType.Application.Json)
//            setBody(user)
//        }.body()
//        println(response)
//        return  response
        return try {

            val response: RegistrationResponse = client.post("$BASE_URL/users") {
                header("Token", token)
                setBody(MultiPartFormDataContent(
                    formData {
                        append("name", user.name)
                        append("email", user.email)
                        append("phone", user.phone)
                        append("position_id", user.positionId)
                        append("photo", user.photo.readBytes(),
                            headers = Headers.build {
                                append(HttpHeaders.ContentDisposition, "form-data; name=\"photo\"; filename=\"${user.photo.name}\"")
                                append(HttpHeaders.ContentType, ContentType.Image.JPEG.toString())
                            })
                    },
                    boundary = "WebAppBoundary"
                )
                )

            }.body()
            println(response)
            response
        } catch (e: ClientRequestException) {
            // Помилки клієнта (4xx)
            val errorResponse = e.response
            val errorBody = errorResponse.bodyAsText()
            println("ClientRequestException: ${errorResponse.status}")
            println("Error body: $errorBody")
             null
        } catch (e: ResponseException) {
            // Інші HTTP помилки
            val errorResponse = e.response
            val errorBody = errorResponse.bodyAsText()
            println("ResponseException: ${errorResponse.status}")
            println("Error body: $errorBody")
            null
        } catch (e: IOException) {
            // Помилки мережі
            println("IOException: ${e.message}")
            null
        } catch (e: Exception) {
            // Інші непередбачені помилки
            println("Exception: ${e.message}")
            null
        }
    }
}