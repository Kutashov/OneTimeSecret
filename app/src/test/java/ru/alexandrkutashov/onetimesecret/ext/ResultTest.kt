package ru.alexandrkutashov.onetimesecret.ext

import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Test for [Result]
 *
 * @author Alexandr Kutashov
 * on 03.06.2018
 */

class ResultTest {

    @Test
    fun dataSuccess() {
        val expected = "someData"
        val result = Result.Success(expected) as Result<*>

        val failure: () -> Unit = mockk {}
        val actual = result.data { failure() }
        verify(inverse = true) { failure() }
        assertEquals(expected, actual)
    }

    @Test
    fun dataFailure() {
        val expected = null
        val result = Result.Error(Exception()) as Result<*>

        val failure: Result.Error.() -> Unit = mockk {}
        every { failure(any()) } just Runs

        val actual = result.data { failure(this) }
        verify { failure(result as Result.Error) }
        assertEquals(expected, actual)
    }
}