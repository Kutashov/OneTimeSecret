package ru.alexandrkutashov.onetimesecret.json

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import ru.alexandrkutashov.onetimesecret.repository.model.RetrieveResponse

/**
 * Test for [RetrieveResponse]
 *
 * @author Alexandr Kutashov
 *
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