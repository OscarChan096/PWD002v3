package com.oskar.pwd002v2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.oskar.pwd002v2.controller.pwdViewModel
import com.oskar.pwd002v2.obj.dataPass
import com.oskar.pwd002v2.ui.theme.PWD002V2Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    Card (
        modifier = Modifier.padding(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black,
            contentColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation()
    ){
            Text(text = "ID > "+pwd.ID.toString(), modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 5.dp))
            Text(text = "Date > "+pwd.FECHMODIF, modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 5.dp))
            Text(text = "TITLE > "+pwd.TITLE, modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 5.dp))
            Text(text = "USER > "+pwd.USERNAME, modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 5.dp))
            Text(text = "Password > "+pwd.USERPASSWORD, modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 5.dp))
    }
}

/*@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PWD002V2Theme {
        GreetingScreen()
    }
}*/