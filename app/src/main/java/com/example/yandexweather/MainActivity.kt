package com.example.yandexweather

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.Volley
import com.caverock.androidsvg.SVG

class MainActivity : AppCompatActivity() {
    private lateinit var buttonLoadWeather: Button
    private lateinit var textViewJSON: TextView
    private lateinit var imageView: ImageView

    private lateinit var apiWorker: ApiWorker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Volley.newRequestQueue(applicationContext)
        val url = "https://jsonplaceholder.typicode.com/todos/1"
        buttonLoadWeather = findViewById(R.id.buttonLoadWeather)
        textViewJSON = findViewById(R.id.textViewJSON)
        imageView = findViewById(R.id.imageView)
        apiWorker = ApiWorker(applicationContext)
        val headers = hashMapOf<String, String>()
        headers["X-Yandex-API-Key"] = "a6d5c190-e8b0-4128-b9bc-6d50fb1ebb90"
        buttonLoadWeather.setOnClickListener {
            apiWorker.makeGetRequest(url, ::updateTextViewJSON, headers)
        }

    }

    private fun updateTextViewJSON(data: String) {
        with(WeatherData.parseFromString(data)) {
            textViewJSON.text = "Температура:$temperature\nДата:${date.toPrettyString}\nПогода:$conditionValue\n"
            apiWorker.makeGetRequest(iconURL,::setSVG)
        }


    }

    private fun setSVG(data: String) {
        val svg=SVG.getFromString(data)
        svg.documentWidth=100F
        svg.documentHeight=100F
        val image=Bitmap.createBitmap(100, 100,Bitmap.Config.ARGB_8888)
        val canvas= Canvas(image)
        svg.renderToCanvas(canvas)
        imageView.setImageBitmap(image)
    }
}