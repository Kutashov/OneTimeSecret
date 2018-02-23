package ru.alexandrkutashov.onetimesecret.json

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import ru.alexandrkutashov.onetimesecret.repository.model.MetadataResponse

/**
 * Test for [MetadataResponse]
 *
 * @author Alexandr Kutashov
 *
 */

class MetadataResponseJsonSerializeTest {

    private val json = """{"custid":"anon","metadata_key":"someMeta","secret_key":"someKey","ttl":666,"metadata_ttl":660,"secret_ttl":327,"state":"viewed","updated":421,"created":3214,"recipient":[],"passphrase_required":true}"""

    @Test
    fun serialize() {
        val actual = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                .adapter(MetadataResponse::class.java)
                .toJson(MetadataResponse(
                        custId = "anon",
                        metadataKey = "someMeta",
                        secretKey = "someKey",
                        ttl = 666,
                        metadataTtl = 660,
                        secretTtl = 327,
                        state = "viewed",
                        dateCreated = 3214,
                        dateUpdated = 421,
                        recipient = emptyList(),
                        isPassphraseRequired = true
                ))

        val expected = json

        assertThat(actual, `is`(expected))
    }

    @Test
    fun deserialize() {
        val actual = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                .adapter(MetadataResponse::class.java).fromJson(json)

        val expected = MetadataResponse(
                custId = "anon",
                metadataKey = "someMeta",
                secretKey = "someKey",
                ttl = 666,
                metadataTtl = 660,
                secretTtl = 327,
                dateCreated = 3214,
                dateUpdated = 421,
                recipient = emptyList(),
                isPassphraseRequired = true,
                state = "viewed"
        )

        assertThat(actual, `is`(expected))
    }
}