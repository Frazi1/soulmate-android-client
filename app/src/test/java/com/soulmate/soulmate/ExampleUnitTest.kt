package com.soulmate.soulmate

import com.soulmate.dtos.UserAccountDto
import com.soulmate.soulmate.api.AuthApi
import okhttp3.ResponseBody
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:8080")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
        get

    private val api = retrofit.create(AuthApi::class.java)
        get

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun retrofit() {
        val call: Response<ResponseBody> = api.getHelloWorldResponse().execute()
        val body: ResponseBody? = call.body()
        val res = body!!.string()
        assertEquals("Hello world", res)
    }

    @Test
    fun getDto() {
        val users: Response<Iterable<UserAccountDto>> = api.getAllUsers().execute()
        val dmitry = users.body()!!.find { it.firstName == "dmitry" }
        assertTrue(dmitry!!.firstName == "dmitry")
    }
}
