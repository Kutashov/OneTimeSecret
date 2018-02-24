package ru.alexandrkutashov.onetimesecret.json

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import ru.alexandrkutashov.onetimesecret.repository.model.GenerateResponse

/**
 * Test for [GenerateResponse]
 *
 * @author Alexandr Kutashov
 *         on 23.02.2018
 */

class GenerateResponseJsonSerializeTest {

    private val json = """{"custid":"anon","value":"someValue","metadata_key":"metadata_key","secret_key":"secret_key","metadata_ttl":1209600,"secret_ttl":1209600,"ttl":1209600,"state":"new","created":1234567890,"updated":1234567890,"passphrase_required":true}"""

    @Test
    fun serialize() {
        val actual = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                .adapter(GenerateResponse::class.java)
                .toJson(GenerateResponse(
                        custId = "anon",
                        value = "someValue",
                        metadataKey = "metadata_key",
                        secretKey = "secret_key",
                        ttl = 1209600,
                        metadataTtl = 1209600,
                        secretTtl = 1209600,
                        dateCreated = 1234567890,
                        dateUpdated = 1234567890,
                        isPassphraseRequired = true,
                        state = "new"
                ))

        val expected = json

        assertThat(actual, `is`(expected))
    }

    @Test
    fun deserialize() {
        val actual = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                .adapter(GenerateResponse::class.java).fromJson(json)

        val expected = GenerateResponse(
                custId = "anon",
                value = "someValue",
                metadataKey = "metadata_key",
                secretKey = "secret_key",
                ttl = 1209600,
                metadataTtl = 1209600,
                secretTtl = 1209600,
                dateCreated = 1234567890,
                dateUpdated = 1234567890,
                isPassphraseRequired = true,
                state = "new"
        )

        assertThat(actual, `is`(expected))
    }
}