package com.example.yandexweather

import com.example.yandexweather.WeatherData
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.Volley
import com.caverock.androidsvg.SVG

/**
 * Главный класс, обладающий наибольшим доступом к функциям приложения и инициализирующий его
 *
 */
class MainActivity : AppCompatActivity() {
    private lateinit var buttonLoadWeather: Button
    private lateinit var textViewJSON: TextView
    private lateinit var imageView: ImageView
    private lateinit var apiWorker: ApiWorker

    /**
     * *Вызывается при создании приложения*
     *
     * Привязывает элементы формы к локальным свойствам,
     * привязывает к нажатию на кнопку вызов зарпоса погоды и обработки его результата через [updateTextViewJSON]
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

    /**
     * Распаковывает JSON строку как объект [WeatherData] и кладёт
     * её параметры в поля, а затем подгружает картинку по ссылке и отображает её
     */
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