package ru.alexandrkutashov.onetimesecret.json

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import ru.alexandrkutashov.onetimesecret.repository.model.BurnResponse
import ru.alexandrkutashov.onetimesecret.repository.model.BurnState

/**
 * Test for [BurnResponse]
 *
 * @author Alexandr Kutashov
 *         on 23.02.2018
 */

class BurnResponseJsonSerializeTest {

    private val json = """{"state":{"custid":"anon","metadata_key":"metadataKey","secret_key":"","ttl":1209600,"metadata_ttl":1209600,"secret_ttl":1209600,"state":"burned","updated":1234,"created":1234,"recipient":[]},"secret_shortkey":"secretShortKey"}"""

    @Test
    fun serialize() {
        val actual = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                .adapter(BurnResponse::class.java)
                .toJson(BurnResponse(
                        state = BurnState(
                                custId = "anon",
                                metadataKey = "metadataKey",
                                secretKey = "",
                                ttl = 1209600,
                                metadataTtl = 1209600,
                                secretTtl = 1209600,
                                state = "burned",
                                recipient = emptyList(),
                                dateUpdated = 1234,
                                dateCreated = 1234
                        ),
                        secretShortKey = "secretShortKey"
                ))

        val expected = json

        assertThat(actual, `is`(expected))
    }

    @Test
    fun deserialize() {
        val actual = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                .adapter(BurnResponse::class.java).fromJson(json)

        val expected = BurnResponse(
                state = BurnState(
                        custId = "anon",
                        metadataKey = "metadataKey",
                        secretKey = "",
                        ttl = 1209600,
                        metadataTtl = 1209600,
                        secretTtl = 1209600,
                        state = "burned",
                        recipient = emptyList(),
                        dateUpdated = 1234,
                        dateCreated = 1234
                ),
                secretShortKey = "secretShortKey"
        )

        assertThat(actual, `is`(expected))
    }
}