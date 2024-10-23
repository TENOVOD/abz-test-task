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

    //Registration values
    val positions: StateFlow<List<Position>> get() = _positions
    val name = mutableStateOf("")
    val email = mutableStateOf("")
    val phone = mutableStateOf("")
    val positionId = mutableStateOf<Int?>(null)
    val photoName = mutableStateOf("")
    val photo = mutableStateOf<File?>(null)

    //Error for registration values, when field validation is incorrect
    val nameError = mutableStateOf(false)
    val emailError = mutableStateOf(false)
    val phoneError = mutableStateOf(false)
    val positionIdError = mutableStateOf(false)
    val photoError = mutableStateOf(false)

    //Registration status
    val isLoading = mutableStateOf(false)
    val registrationSuccess = mutableStateOf(false)
    val registrationErrors = mutableStateOf(mutableListOf<String>())

    //Permission for showing registration form
    val hasShowedRegistrationResult = mutableStateOf(false)

    //loading position and start network listening
    init {
        loadPositions()
        connectivityObserver.start()
    }

    //stop network listener after closing screen
    override fun onCleared() {
        super.onCleared()
        connectivityObserver.stop()
    }

    //change registration values
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
        positionId.value = newPositionId
        if (positionIdError.value) validatePosition()
    }


    //set photo name for photo textfield
    fun setPhotoName(newPhoto: String) {
        photoName.value = newPhoto
    }

    //set link to photo/image
    fun setPhotoUri(context: Context, uri: Uri?) {
        viewModelScope.launch {
            if (uri!=null){
                photo.value = getFileFromUri(context = context, uri = uri)
                validatePhoto()
            }

        }

    }

    // Validation
    private fun validateName() {
        nameError.value = !(name.value.length in 2..60)
    }

    private fun validateEmail() {
        val emailPattern = Patterns.EMAIL_ADDRESS
        emailError.value = !emailPattern.matcher(email.value).matches()
    }

    private fun validatePhone() {
        phoneError.value = !(phone.value.startsWith("+380") && phone.value.length == 13)
    }

    private fun validatePosition() {
        positionIdError.value = positionId.value == null
    }

    private fun validatePhoto() {
        val photoFile = photo.value
        if (photoFile != null) {
            //validating image size <5MB
            val isValidSize = photoFile.length() <= 5 * 1024 * 1024
            //validating image format only jpg and jpeg
            val isValidFormat = photoFile.extension.lowercase() in listOf("jpg", "jpeg")
            //validating image size min 70x70 px
            val hasValidDimensions = checkPhotoDimensions(photoFile)

            photoError.value = !(isValidSize && isValidFormat && hasValidDimensions)
        } else {
            photoError.value = true
        }
    }


    //Load positions from server
    fun loadPositions() {
        viewModelScope.launch {
            if (networkStatus.value == ConnectivityObserver.Status.Available) {
                val response = userApi.getPosition()
                _positions.value = response.positions
            }
        }

    }

    //Get token from server for registration
    private suspend fun getToken(): String {
        val response = userApi.getToken()
        return response.token
    }

    //Validation all values
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

    //Registration
    fun registerUser() {
        //validating all values
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
                val token = getToken()
                val response = userApi.registrationUser(token,userData)
                if (response?.userId==-1){
                    registrationSuccess.value = false
                    val fails =response.fails
                    if(fails!=null){
                        if (fails.name!=null) registrationErrors.value.add(fails.name[0])
                        if (fails.email!=null) registrationErrors.value.add(fails.email[0])
                        if (fails.phone!=null) registrationErrors.value.add(fails.phone[0])
                        if (fails.positionId!=null) registrationErrors.value.add(fails.positionId[0])
                        if (fails.photo!=null) registrationErrors.value.add(fails.photo[0])
                    }else{
                        registrationErrors.value.add(response.message)
                    }
                }else{
                    registrationSuccess.value = true
                }
            } catch (e: Exception) {
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

    //validating image size min 70x70 px
    private fun checkPhotoDimensions(photoFile: File): Boolean {
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeFile(photoFile.absolutePath, options)
        val width = options.outWidth
        val height = options.outHeight

        return width >= 70 && height >= 70
    }

    //take Uri
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

    //get File by Uri and set photo name to textfield
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

    //get file name by Uri
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