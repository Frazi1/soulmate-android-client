package com.soulmate.soulmate

import com.soulmate.soulmate.api.TestApi
import okhttp3.ResponseBody
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun retrofit() {
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("http://localhost:8080")
                .addConverterFactory(JacksonConverterFactory.create())
                .build()

        val r = retrofit.create(TestApi::class.java)
        val call: Response<ResponseBody> = r.getHelloWorldResponse().execute()
        val res = call.body()!!.string()
        assertEquals("Hello world", res)
    }
}
