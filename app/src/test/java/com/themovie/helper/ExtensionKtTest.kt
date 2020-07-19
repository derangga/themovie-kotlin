package com.themovie.helper

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Test

class ExtensionKtTest{
    @Test
    fun `date converter with date string valid value`() {
        // given
        val data = "2003-11-14"

        // when
        val result = data.convertDate()

        // then
        assertThat(result, `is`("Nov 14, 2003"))
    }

    @Test
    fun `date converter with date string null value`() {
        // given
        val data = null

        // when
        val result = data.convertDate()

        // then
        assertThat(result.isEmpty(), `is`(true))
    }

    @Test
    fun `date converter with date string invalid format`() {
        // given
        val data = "abcd"

        // when
        val result = data.convertDate()

        // then
        assertThat(result.isEmpty(), `is`(true))
    }
}