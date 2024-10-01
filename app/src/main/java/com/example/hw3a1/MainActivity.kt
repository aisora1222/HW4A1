package com.example.hw3a1

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalConfiguration
import com.example.hw3a1.ui.theme.HW3A1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HW3A1Theme {
                ShoppingApp()
            }
        }
    }
}

val products = listOf(
    Triple("Product A", "$100", "This is a great product A."),
    Triple("Product B", "$150", "This is product B with more features."),
    Triple("Product C", "$200", "Premium product C.")
)

fun whenItemSelected(
    product: Triple<String, String, String>,
    selectedItem: MutableState<Triple<String, String, String>?>
) {
    selectedItem.value = product
}

@Composable
fun itemList(
    products: List<Triple<String, String, String>>,
    selectedItem: MutableState<Triple<String, String, String>?>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(start = 20.dp, top = 20.dp, bottom = 20.dp)
    ) {
        items(products) { item ->
            Text(
                text = item.first,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .clickable {
                        whenItemSelected(item, selectedItem)
                    }
            )
        }
    }
}

@Composable
fun displayItem(product: Triple<String, String, String>?) {
    if (product == null) {
        Text(
            text = "Select a product to view details.",
            fontSize = 25.sp,
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(text = product.first, fontSize = 24.sp, modifier = Modifier.padding(bottom = 10.dp))
            Text(text = product.second, modifier = Modifier.padding(bottom = 10.dp))
            Text(text = product.third)
        }
    }
}

@Composable
fun ShoppingApp() {
    val selectedItem = remember { mutableStateOf<Triple<String, String, String>?>(null) }
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp)
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.5f)
            ) {
                itemList(
                    products = products,
                    selectedItem = selectedItem
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.5f)
            ) {
                displayItem(
                    product = selectedItem.value
                )
            }
        }
    } else {
        if (selectedItem.value == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp)
                    .padding(20.dp)
            ) {
                itemList(
                    products = products,
                    selectedItem = selectedItem
                )
            }

        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp)
                    .padding(20.dp)
            ) {
                displayItem(
                    product = selectedItem.value
                )
            }
        }
    }
}
