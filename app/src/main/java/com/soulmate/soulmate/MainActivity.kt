package com.soulmate.soulmate

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.soulmate.soulmate.api.TestApi
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class MainActivity : AppCompatActivity() {

    val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:8080")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val api: TestApi = retrofit.create(TestApi::class.java)
        val hello = api.getHelloWorldResponse();
    }
}
