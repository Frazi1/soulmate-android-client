package com.soulmate.soulmate

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import com.soulmate.dtos.UserAccountDto
import com.soulmate.soulmate.api.TestApi
import dagger.android.DaggerActivity
import dagger.android.support.DaggerAppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var retrofit: Retrofit;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.component.inject(this)

        val btn = findViewById<Button>(R.id.my_button)
        btn.setOnClickListener {
            retrofit.create(TestApi::class.java).getAllUsers().enqueue(object : Callback<Iterable<UserAccountDto>> {
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
