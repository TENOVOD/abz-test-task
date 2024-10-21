package com.abz.testtask.viewmodel

import android.content.ContentValues
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abz.testtask.API.UsersApi
import com.abz.testtask.internet.ConnectivityObserver
import com.abz.testtask.response.Position
import com.abz.testtask.model.RegistrationData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userApi: UsersApi,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    val networkStatus = connectivityObserver.status
    private val _positions: MutableStateFlow<List<Position>> =
        MutableStateFlow<List<Position>>(emptyList())
    private var token = ""
    val positions: StateFlow<List<Position>> get() = _positions
    val name = mutableStateOf("")
    val email = mutableStateOf("")
    val phone = mutableStateOf("")
    val positionId = mutableStateOf<Int?>(null)
    val photoName = mutableStateOf("")
    val photo = mutableStateOf<File?>(null)

    val nameError = mutableStateOf(false)
    val emailError = mutableStateOf(false)
    val phoneError = mutableStateOf(false)
    val positionIdError = mutableStateOf(false)
    val photoError = mutableStateOf(false)

    val isLoading = mutableStateOf(false)
    val registrationSuccess = mutableStateOf(false)
    val hasShowedRegistrationResult = mutableStateOf(false)

    init {
        loadPositions()
        connectivityObserver.start()
    }

    override fun onCleared() {
        super.onCleared()
        connectivityObserver.stop()
    }


    fun onNameChanged(newName: String) {
        name.value = newName
        if (nameError.value) validateName()
    }

    fun onEmailChanged(newEmail: String) {
        email.value = newEmail
        if (emailError.value) validateEmail()
    }

    fun onPhoneChanged(newPhone: String) {
        phone.value = newPhone
        if (phoneError.value) validatePhone()
    }

    fun onPositionSelected(newPositionId: Int) {
        println("WORK new pos $newPositionId ")
        positionId.value = newPositionId
        if (positionIdError.value) validatePosition()
    }

    fun setPhotoName(newPhoto: String) {
        println("WORK WORK")
        photoName.value = newPhoto
    }

    fun setPhotoUri(context: Context, uri: Uri?) {
        viewModelScope.launch {
            Log.d("TESTURI",uri.toString())
            if (uri!=null){
                photo.value = getFileFromUri(context = context, uri = uri)
                validatePhoto()
            }

        }

    }

    // Функції валідації
    private fun validateName() {
        nameError.value = !(name.value.length in 2..60)
        println("Name is valid = ${nameError.value}")
    }

    private fun validateEmail() {
        val emailPattern = Patterns.EMAIL_ADDRESS
        emailError.value = !emailPattern.matcher(email.value).matches()
        println("Email is valid = ${emailError.value}")
    }

    private fun validatePhone() {
        phoneError.value = !(phone.value.startsWith("+380") && phone.value.length == 13)
        println("Phone is valid = ${phoneError.value}")
    }

    private fun validatePosition() {
        positionIdError.value = positionId.value == null
        println("Position is valid = ${positionIdError.value}")
    }

    private fun validatePhoto() {
        val photoFile = photo.value
        if (photoFile != null) {
            val isValidSize = photoFile.length() <= 5 * 1024 * 1024
            val isValidFormat = photoFile.extension.lowercase() in listOf("jpg", "jpeg")
            val hasValidDimensions = checkPhotoDimensions(photoFile)

            photoError.value = !(isValidSize && isValidFormat && hasValidDimensions)
        } else {
            photoError.value = true
        }
    }

    fun loadPositions() {
        viewModelScope.launch {
            println("1" + networkStatus.value)
            if (networkStatus.value == ConnectivityObserver.Status.Available) {
                val response = userApi.getPosition()
                _positions.value = response.positions
            }
            println("3" + networkStatus.value)
        }

    }

    private suspend fun getToken(): String {
        val response = userApi.getToken()
        return response.token
    }

    private fun isFormValid(): Boolean {
        validateName()
        validateEmail()
        validatePhone()
        validatePosition()
        validatePhoto()
        return listOf(
            nameError.value,
            emailError.value,
            phoneError.value,
            positionIdError.value,
            photoError.value
        ).contains(true)
    }

    fun registerUser() {
        println("FORM ${isFormValid()}")
        if (isFormValid()) return
        isLoading.value=true
        viewModelScope.launch {
            try {
                isLoading.value = true
                val userData = RegistrationData(
                    name = name.value,
                    email = email.value,
                    phone = phone.value,
                    positionId = positionId.value!!,
                    photo = photo.value!!
                )
                println("VM1")
                val token = getToken()
                println("VM2 + $token")
                val response = userApi.registrationUser(token,userData)
                println(response)
                if (response?.userId==-1){
                    registrationSuccess.value = false
                }else{
                    registrationSuccess.value = true
                }

                Log.d("TEST", " SUCCESS $userData")
            } catch (e: Exception) {
                Log.d("TEST", " ERROR")
                registrationSuccess.value = false
            } finally {
                isLoading.value = false
                hasShowedRegistrationResult.value = true
            }
        }
    }
    fun closeRegistrationResult(){
        hasShowedRegistrationResult.value=false
    }

    private fun checkPhotoDimensions(photoFile: File): Boolean {
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeFile(photoFile.absolutePath, options)
        val width = options.outWidth
        val height = options.outHeight

        return width >= 70 && height >= 70
    }

    fun createImageUri(context: Context): Uri? {
        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "image_${System.currentTimeMillis()}.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/Camera")
            }
        }
        return resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    }

    private fun getFileFromUri(context: Context, uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val fileName = getFileName(context, uri)
            setPhotoName(fileName)
            val tempFile = File(context.cacheDir, fileName)
            tempFile.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
            tempFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getFileName(context: Context, uri: Uri): String {
        var name = "file_${System.currentTimeMillis()}"
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    name = it.getString(nameIndex)
                }
            }
        }
        return name
    }
}