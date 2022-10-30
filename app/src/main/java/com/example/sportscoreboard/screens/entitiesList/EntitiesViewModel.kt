package com.example.sportscoreboard.screens.entitiesList

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportscoreboard.data.repositories.ParticipantsRepositoryI
import com.example.sportscoreboard.domain.Entity
import com.example.sportscoreboard.domain.ResultState
import com.example.sportscoreboard.domain.filters.EntityFilter
import com.example.sportscoreboard.others.Constants
import kotlinx.coroutines.launch

class EntitiesViewModel(private val repository: ParticipantsRepositoryI) : ViewModel() {

    private val _scoreRecords: MutableLiveData<ResultState<List<Entity>>> = MutableLiveData()
    val scoreRecords: LiveData<ResultState<List<Entity>>>
        get() = _scoreRecords

    private val _searchedText = mutableStateOf(Constants.DEFAULT_SEARCHED_TEXT)
    val searchedText: String
        get() = _searchedText.value

    private val _participantType = mutableStateOf(EntityFilter.ALL)
    val participantType: EntityFilter
        get() = _participantType.value

    init {
        loadData()
    }

    fun setSearchedText(newText: String){
        _searchedText.value = newText
        if(_searchedText.value.length >= 2) {
            loadData()
        }
    }

    fun setParticipantType(type: EntityFilter){
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