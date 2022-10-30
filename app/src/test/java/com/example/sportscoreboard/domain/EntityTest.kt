package com.example.sportscoreboard.domain

import com.example.sportscoreboard.others.Constants
import com.google.common.truth.Truth.assertThat
import org.junit.Test

internal class EntityTest {

    @Test
    fun getImagePath() {
        val img = "image"

        val entity = Entity.Team(
            _name = "abc",
            _sport = "abc",
            _image = img,
            _gender = "abc",
            _country = "abc"
        )
        assertThat(entity.getImagePath()).isEqualTo(Constants.API_IMAGE_PATH+img)
    }
}