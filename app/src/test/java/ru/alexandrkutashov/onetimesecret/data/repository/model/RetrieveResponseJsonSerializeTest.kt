package ru.alexandrkutashov.onetimesecret.data.repository.model

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

/**
 * Test for [RetrieveResponse]
 *
 * @author Alexandr Kutashov
 *         on 23.02.2018
 */

class RetrieveResponseJsonSerializeTest {

    private val json = """{"value":"some_value","secret_key":"some_secret"}"""

    @Test
    fun serialize() {
        val actual = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                .adapter(RetrieveResponse::class.java)
                .toJson(RetrieveResponse(
                        value = "some_value",
                        secretKey = "some_secret"
                ))

        val expected = json

        assertThat(actual, `is`(expected))
    }

    @Test
    fun deserialize() {
        val actual = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                .adapter(RetrieveResponse::class.java).fromJson(json)

        val expected = RetrieveResponse(
                value = "some_value",
                secretKey = "some_secret"
        )

        assertThat(actual, `is`(expected))
    }
}