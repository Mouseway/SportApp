package com.example.sportscoreboard.screens.entitiesList

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportscoreboard.data.repositories.ParticipantsRepository
import com.example.sportscoreboard.domain.Participant
import com.example.sportscoreboard.domain.ResultState
import com.example.sportscoreboard.domain.filters.ParticipantFilter
import com.example.sportscoreboard.others.Constants
import kotlinx.coroutines.launch

class EntitiesViewModel(private val repository: ParticipantsRepository) : ViewModel() {

    private val _scoreRecords: MutableLiveData<ResultState<List<Participant>>> = MutableLiveData(ResultState.Loading(isLoading = false))
    val scoreRecords: LiveData<ResultState<List<Participant>>> = _scoreRecords

    private val _searchedText = mutableStateOf(Constants.DEFAULT_SEARCHED_TEXT)
    val searchedText: String
        get() = _searchedText.value

    private val _participantType = mutableStateOf(ParticipantFilter.ALL)
    val participantType: ParticipantFilter
        get() = _participantType.value

    init {
        loadData()
    }

    fun setSearchedText(newText: String){
        _searchedText.value = newText
        loadData()
    }

    fun setParticipantType(type: ParticipantFilter){
        _participantType.value = type
        loadData()
    }

    private fun loadData(){
        viewModelScope.launch {
            repository.getFilteredParticipants(_searchedText.value, _participantType.value).collect{
                _scoreRecords.value = it
            }
        }
    }
}