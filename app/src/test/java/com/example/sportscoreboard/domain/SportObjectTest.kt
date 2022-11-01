package com.example.sportscoreboard.domain

import com.example.sportscoreboard.others.Constants
import com.google.common.truth.Truth.assertThat
import org.junit.Test

internal class SportObjectTest {

    @Test
    fun getImagePath() {
        val img = "image"

        val sportObject = Team(
            id = "qwe",
            name = "abc",
            sport = "abc",
            image = img,
            gender = "abc",
            country = "abc",
            favorite = true
        )
        assertThat(sportObject.getImagePath()).isEqualTo(Constants.API_IMAGE_PATH+img)
    }
}