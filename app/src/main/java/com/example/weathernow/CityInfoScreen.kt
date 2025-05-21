package com.example.weathernow

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URLEncoder
import java.net.URL
import org.json.JSONObject

@Composable
fun CityinfoScreen(city: String, onBack: () -> Unit) {
    var info by remember { mutableStateOf("Загрузка...") }

    LaunchedEffect(city) {
        info = try {
            getCityDescription(city)
        } catch (e: Exception) {
            "Ошибка при загрузке информации о городе."
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Информация о городе: $city", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text(info)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onBack) {
            Text("Назад")
        }
    }
}

suspend fun getCityDescription(city: String): String = withContext(Dispatchers.IO) {
    val encodedCity = URLEncoder.encode(city, "UTF-8")
    val url =
        "https://en.wikipedia.org/api/rest_v1/page/summary/$encodedCity"

    val json = URL(url).readText()
    val obj = JSONObject(json)

    return@withContext obj.optString("extract", "Информация не найдена.")
}
