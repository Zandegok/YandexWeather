package com.example.yandexweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var buttonLoadWeather: Button
    private lateinit var textViewJSON: TextView

    private lateinit var apiWorker: ApiWorker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Volley.newRequestQueue(applicationContext)
        var url = "https://api.weather.yandex.ru/v2/forecast?lat=53.243562&lon=34.363425"
        buttonLoadWeather = findViewById(R.id.buttonLoadWeather)
        textViewJSON = findViewById(R.id.textViewJSON)
        apiWorker = ApiWorker(applicationContext)
        var headers = hashMapOf<String, String>()
        headers["X-Yandex-API-Key"] = "a6d5c190-e8b0-4128-b9bc-6d50fb1ebb90"
        buttonLoadWeather.setOnClickListener {
            apiWorker.makeGetRequest(url, ::updateTextViewJSON,headers)
        }
    }

    fun updateTextViewJSON(data: String) {
        textViewJSON.text = data
    }

}