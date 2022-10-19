package com.example.sportscoreboard.screens.scoreboard

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportscoreboard.data.repositories.ParticipantsRepository
import com.example.sportscoreboard.domain.ResultState
import com.example.sportscoreboard.domain.Participant
import com.example.sportscoreboard.others.Constants
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ScoreboardViewModel(private val repository: ParticipantsRepository) : ViewModel() {

    private val _scoreRecords: MutableLiveData<ResultState<List<Participant>>> = MutableLiveData(ResultState.Loading(isLoading = false))
    val scoreRecords: LiveData<ResultState<List<Participant>>> = _scoreRecords

    private val _searchedText = mutableStateOf(Constants.DEFAULT_SEARCHED_TEXT)
    val searchedText: String
        get() = _searchedText.value


    init {
        viewModelScope.launch {
            repository.getFilteredParticipants(_searchedText.value).collect{
                _scoreRecords.value = it
            }
        }
    }

    fun setSearchedText(newText: String){
        _searchedText.value = newText
        viewModelScope.launch {
            repository.getFilteredParticipants(newText).collect{
                _scoreRecords.value = it
            }
        }
    }
}