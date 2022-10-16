package com.example.sportscoreboard.screens.scoreboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportscoreboard.data.repositories.ParticipantsRepository
import com.example.sportscoreboard.domain.ResultState
import com.example.sportscoreboard.domain.Participant
import kotlinx.coroutines.launch

class ScoreboardViewModel(private val repository: ParticipantsRepository) : ViewModel() {

    private val _scoreRecords: MutableLiveData<ResultState<List<Participant>>> = MutableLiveData(ResultState.Loading(isLoading = false))
    val scoreRecords: LiveData<ResultState<List<Participant>>> = _scoreRecords

    init {
        viewModelScope.launch {
            repository.getALlScores().collect{
                _scoreRecords.value = it
            }
        }
    }
}