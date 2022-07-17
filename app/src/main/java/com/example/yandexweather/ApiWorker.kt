package com.example.yandexweather

import android.content.Context
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

/**
 * Вызывает get запросы к серверу
 *
 * @property applicationContext
 */
class ApiWorker(
    var applicationContext: Context,
) {
    private var volleyQueue = Volley.newRequestQueue(applicationContext)

    /**
     * Вызывает get запрос без тела
     *
     * @param url адрес запроса
     * @param function функция, вызываемая в случае успешного запроса
     * @param headers заголовки запроса
     */
    fun makeGetRequest(
        url: String,
        function: (String) -> Unit,
        headers: MutableMap<String, String> = hashMapOf(),
    ) {
        val request = object : StringRequest(
            Method.GET,
            url,
            Response.Listener(function),
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return headers
            }
        }
        volleyQueue.add(request)
    }


}