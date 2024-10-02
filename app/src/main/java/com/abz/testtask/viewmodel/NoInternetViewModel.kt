package com.abz.testtask.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoInternetViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _isFindingInternet = MutableStateFlow(false)
    var isFindingInternet: StateFlow<Boolean> = _isFindingInternet

    private val _isOnline = MutableStateFlow(false)
    var isOnline: StateFlow<Boolean> = _isOnline

    fun startProcessFindingInternetConnection() {
        viewModelScope.launch(Dispatchers.IO) {
            _isFindingInternet.value = true
            if (findInternetConnection(context = context)) {
                delay(1000)
                _isOnline.value = true
                _isFindingInternet.value = false
            } else {
                delay(1000)
                _isOnline.value = false
                _isFindingInternet.value = false
            }
        }
    }

    private fun findInternetConnection(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

}