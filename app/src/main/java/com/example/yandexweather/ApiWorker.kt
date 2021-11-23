package com.example.yandexweather

import android.content.Context
import android.graphics.Bitmap
import android.media.Image
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class ApiWorker(
    var applicationContext: Context,
) {
    private var volleyQueue = Volley.newRequestQueue(applicationContext)
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
    fun makeGetImageRequest(
        url: String,
        function: (Bitmap) -> Unit,
        headers: MutableMap<String, String> = hashMapOf(),
    ) {
        val request = object : ImageRequest(
            url,
            Response.Listener(function),
            0,0,null,
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