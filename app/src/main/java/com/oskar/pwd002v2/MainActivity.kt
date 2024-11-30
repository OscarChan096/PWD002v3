package com.oskar.pwd002v2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.google.gson.GsonBuilder
import com.oskar.pwd002v2.controller.DataPassAdapter
import com.oskar.pwd002v2.obj.dataPass
import com.oskar.pwd002v2.retrofit.ApiService
import com.oskar.pwd002v2.ui.theme.PWD002V2Theme
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    var idUserData = mutableStateOf("")
    var idData: MutableState<String?> = mutableStateOf("")
    var titleData: MutableState<String?> = mutableStateOf("")
    var bodyData: MutableState<String?> = mutableStateOf("")
    var dataPwd: List<dataPass>? = null

    /*val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()*/
    val gson = GsonBuilder()
        .registerTypeAdapter(dataPass::class.java, DataPassAdapter())
        .create()
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.233:5000/api/pwd/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService = retrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("onCreate", "Inicio del onCreate")
        lifecycleScope.launch {
            try {
                dataPwd = apiService.getPWDbyId("activision")
                Log.d("mostrar datos obtenidos", "Post: ${dataPwd}")
                /*post.forEach{data ->
                    idUserData.value = data.ID.toString()
                    titleData.value = data.TITLE
                }*/
            } catch (e: Exception) {
                Log.e("ERROR", "No se obtuvo datos: ${e.message}")
            }
        }
        enableEdgeToEdge()
        setContent {
            PWD002V2Theme {
                Greeting(
                    dataPwd
                )
            }
        }
    }
}

@Composable
fun Greeting(dataPwd: List<dataPass>?) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(dataPwd){pwds->
            text()
        }
    }
}

fun text(){

}

/*@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PWD002V2Theme {
        Greeting("Android")
    }
}*/