package com.example.ipmsg20

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.ipmsg20.customobjects.Sender
import com.example.ipmsg20.room.Database
import com.example.ipmsg20.ui.theme.IPMSG20Theme
import com.example.ipmsg20.utils.Data
import com.example.ipmsg20.utils.senderItem

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IPMSG20Theme {
                val db = Room.databaseBuilder(
                    applicationContext,
                    Database::class.java, "database-name"
                ).build()

                Data.intitate(this).setDB(db);

                // A surface container using the 'background' color from the theme
                Column() {
                    Dashboard(
                        modifier = Modifier.weight(1f)
                    )
                    BottomNav()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun Dashboard( modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(5.dp, 10.dp)
            .wrapContentHeight(Alignment.Bottom),
    ) {

//        var sender_list by remember {
//            mutableListOf()
//        }
        senderItem(
            modifier =
                Modifier
                    .weight(1f),
            sender_list = listOf(),
            isLoading = true
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            var search_text by remember {mutableStateOf("")};
            var search_active by remember {mutableStateOf(false)};
            SearchBar(
                query = search_text,
                onQueryChange = {
                    search_text = it
                },
                onSearch = {
                    search_text = it
                },
                active = search_active,
                onActiveChange = {
                    search_active = it
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Search Message")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Logo")
                }
            ) {

            }
        }
    }
}

@Composable
fun BottomNav() {
    var selected_item by remember {mutableStateOf(0)}
    val item_list = listOf("Home","Favorite","Settings")
    val item_icon_list = listOf(Icons.Default.Home,Icons.Default.Favorite,Icons.Default.Settings)
    Row() {
        NavigationBar(
        ) {
            item_list.forEachIndexed { index, s ->
                NavigationBarItem(
                    selected = selected_item == index,
                    onClick = { selected_item = index },
                    icon = { Icon(imageVector = item_icon_list[index], contentDescription = s)},
                    label = { Text(text = s)}
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IPMSG20Theme {
        Column {
            Dashboard(
                modifier = Modifier
                    .weight(1f)
            )
            BottomNav()
        }
    }
}