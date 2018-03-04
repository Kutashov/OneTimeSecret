package ru.alexandrkutashov.onetimesecret.data.repository.model

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

/**
 * Test for [StatusResponse]
 *
 * @author Alexandr Kutashov
 *         on 23.02.2018
 */

class StatusResponseJsonSerializeTest {

    private val json = """{"status":"nominal","locale":"en"}"""

    @Test
    fun serialize() {
        val actual = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                .adapter(StatusResponse::class.java)
                .toJson(StatusResponse(
                        status = "nominal",
                        locale = "en"
                ))

        val expected = json

        assertThat(actual, `is`(expected))
    }

    @Test
    fun deserialize() {
        val actual = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                .adapter(StatusResponse::class.java).fromJson(json)

        val expected = StatusResponse(
                status = "nominal",
                locale = "en"
        )

        assertThat(actual, `is`(expected))
    }
}