package com.soulmate.soulmate

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import com.soulmate.dtos.UserAccountDto
import com.soulmate.soulmate.api.TestApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class MainActivity : AppCompatActivity() {

    private val retrofit: Retrofit = Retrofit.Builder()
//            .baseUrl("http://localhost:8080")
            .baseUrl("http://192.168.0.12:8080/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    private val api = retrofit.create(TestApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.my_button)
        btn.setOnClickListener {
            api.getAllUsers().enqueue(object : Callback<Iterable<UserAccountDto>> {
                override fun onResponse(call: Call<Iterable<UserAccountDto>>?, response: Response<Iterable<UserAccountDto>>?) {
                    Toast.makeText(
                            applicationContext,
                            response?.body()
                                    ?.joinToString(" ") { it.firstName },
                            Toast.LENGTH_LONG).show()
                }

                override fun onFailure(call: Call<Iterable<UserAccountDto>>?, t: Throwable?) {
                    Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_LONG).show()
                }
            })
        }
    }

}
