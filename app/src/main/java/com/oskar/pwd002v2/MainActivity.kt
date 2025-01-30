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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.GsonBuilder
import com.oskar.pwd002v2.controller.DataPassAdapter
import com.oskar.pwd002v2.controller.pwdViewModel
import com.oskar.pwd002v2.obj.dataPass
import com.oskar.pwd002v2.retrofit.ApiService
import com.oskar.pwd002v2.ui.theme.PWD002V2Theme
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    var dataPwd: List<dataPass>? = null

    /*val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()*/
    val gson = GsonBuilder()
        .registerTypeAdapter(dataPass::class.java, DataPassAdapter())
        .create()
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.119:5000/api/pwd/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService = retrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*Log.d("onCreate", "Inicio del onCreate")
        lifecycleScope.launch {
            try {
                dataPwd = apiService.getPWDbyId("activision")
                Log.d("mostrar datos obtenidos", "Post: ${dataPwd}")
            } catch (e: Exception) {
                Log.e("ERROR", "No se obtuvo datos: ${e.message}")
            }
        }*/
        enableEdgeToEdge()
        setContent {
            PWD002V2Theme {
                GreetingScreen(
                )
            }
        }
    }
}

@Composable
fun GreetingScreen() {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
    val viewModel: pwdViewModel = viewModel(
        viewModelStoreOwner = viewModelStoreOwner!!,
        key = "pwdViewModel",
        factory = ViewModelProvider.NewInstanceFactory()
    )
    val dataPwd by viewModel.dataPwd.collectAsState()

    Greeting(dataPwd)
}

@Composable
fun Greeting(dataPwd: List<dataPass>?) {
    Log.d("INFO", "Longitud del dataPWD: ${dataPwd?.size}")
    val nonNullDataPwd = dataPwd ?: emptyList()
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(nonNullDataPwd){ pwd->
            ItemRow(pwd)
        }
    }
}
@Composable
fun ItemRow(pwd: dataPass){
    Column (modifier = Modifier.padding(5.dp)){
        Text(text = "ID > "+pwd.ID.toString())
        Text(text = "Date > "+pwd.FECHMODIF)
        Text(text = "TITLE > "+pwd.TITLE)
        Text(text = "USER > "+pwd.USERNAME)
        Text(text = "Password > "+pwd.USERPASSWORD)
    }
}

/*@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PWD002V2Theme {
        Greeting("Android")
    }
}*/