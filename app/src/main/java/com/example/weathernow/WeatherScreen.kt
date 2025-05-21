package com.example.weathernow

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weathernow.model.WeatherResponse
import com.example.weathernow.network.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun WeatherScreen(onNavigateToCityInfo: (String) -> Unit) {
    var city by remember { mutableStateOf("") }
    var weather by remember { mutableStateOf<WeatherResponse?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()
    val apiKey = "02cf3cbc893bb12723e2fac08c9379f9"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Введите город") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            scope.launch {
                try {
                    val result = RetrofitClient.api.getWeatherByCity(city, apiKey)
                    weather = result
                    error = null
                } catch (e: Exception) {
                    error = "Ошибка загрузки погоды"
                    weather = null
                }
            }
        }) {
            Text("Показать погоду")
        }

        Spacer(modifier = Modifier.height(16.dp))

        weather?.let {
            Text("Город: ${it.name}")
            Text("Температура: ${it.main.temp}°C")
            Text("Описание: ${it.weather[0].description}")

            Spacer(modifier = Modifier.height(16.dp))

            // Кнопка для перехода на экран описания города
            Button(onClick = { onNavigateToCityInfo(it.name) }) {
                Text("Подробнее о городе")
            }
        }

        error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }
}


