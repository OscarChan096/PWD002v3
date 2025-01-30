package com.oskar.pwd002v2.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import com.oskar.pwd002v2.obj.dataPass
import com.oskar.pwd002v2.retrofit.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class pwdViewModel : ViewModel() {
    private val _dataPwd = MutableStateFlow<List<dataPass>?>(null)
    var dataPwd: StateFlow<List<dataPass>?> = _dataPwd

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            // Simula la obtención de datos de un servicio
            val data = getDataFromService()
            _dataPwd.value = data
        }
    }

    private suspend fun getDataFromService(): List<dataPass> {
        // Aquí iría tu lógica para obtener los datos
        val gson = GsonBuilder()
            .registerTypeAdapter(dataPass::class.java, DataPassAdapter())
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.119:5000/api/pwd/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)

        return apiService.getPWDbyId("activision")
    }
}