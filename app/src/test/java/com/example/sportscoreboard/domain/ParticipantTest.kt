package com.example.sportscoreboard.domain

import com.example.sportscoreboard.others.Constants
import com.google.common.truth.Truth.assertThat
import org.junit.Assert.assertThrows
import org.junit.Test
import java.lang.IllegalArgumentException

internal class ParticipantTest {

    @Test
    fun getImagePath() {
        val img = "image"

        val participant = Participant.Team(
            _name = "",
            _sport = "",
            _images = listOf(img+0, img+1, img+2),
        )
        for(i in 0..2 )
            assertThat(participant.getImagePath(i)).isEqualTo(Constants.API_IMAGE_PATH+img+i)

        assertThrows(IllegalArgumentException::class.java) { participant.getImagePath(3) }
        assertThrows(IllegalArgumentException::class.java) { participant.getImagePath(-1) }
    }
}