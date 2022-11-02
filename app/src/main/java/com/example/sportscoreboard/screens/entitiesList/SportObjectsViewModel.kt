package com.example.sportscoreboard.screens.entitiesList

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.sportscoreboard.data.repositories.SportObjectsRepositoryI
import com.example.sportscoreboard.domain.ResultState
import com.example.sportscoreboard.domain.SportObject
import com.example.sportscoreboard.domain.filters.SportObjectTypeFilter
import kotlinx.coroutines.launch

class SportObjectsViewModel(private val repository: SportObjectsRepositoryI) : ViewModel() {

    private val favoriteAll: MutableLiveData<ResultState<List<SportObject>>> = MutableLiveData()
    private val _sportObjects: MutableLiveData<ResultState<List<SportObject>>> = MutableLiveData()

    val sportObjects: LiveData<ResultState<List<SportObject>>> = MediatorLiveData<ResultState<List<SportObject>>>()
        .apply {
            fun update(){
                val sportObjects = _sportObjects.value ?: return
                val favorite = favoriteAll.value ?: return

                value = if(sportObjects is ResultState.Success && favorite is ResultState.Success){
                    val data = sportObjects.data?.map { obj ->
                        if(favorite._data?.find { it.id == obj.id} != null){
                            obj.copy(favorite = true)
                        }else{
                            obj
                        }
                    }
                    ResultState.Success(data)
                }else {
                    sportObjects
                }
            }

            addSource(_sportObjects){update()}
            addSource(favoriteAll){update()}

            update()
        }

    private val _sportObjectFilter = MutableLiveData(SportObjectTypeFilter.ALL)
    val sportObjectFilter: LiveData<SportObjectTypeFilter>
        get() = _sportObjectFilter

    private val _showFavorite: MutableLiveData<Boolean> = MutableLiveData(true)
    val showFavorite: LiveData<Boolean>
        get() = _showFavorite

    private val _searchedText = mutableStateOf("")
    val searchedText: String
        get() = _searchedText.value



    val favorite: LiveData<ResultState<List<SportObject>>> = MediatorLiveData<ResultState<List<SportObject>>>()
            .apply {
                fun update(){
                    val filter = _sportObjectFilter.value
                    val state = favoriteAll.value ?: return
                    value = if(state is ResultState.Success && filter != SportObjectTypeFilter.ALL){
                        val data = state.data?.filter {
                            (it.filter.id == filter?.id)
                        }
                        ResultState.Success(data)
                    }else{
                        state
                    }
                }

                addSource(_sportObjectFilter){update()}
                addSource(favoriteAll){update()}

                update()
            }

    init {
        viewModelScope.launch {
            repository.getFavoriteSportObjects().collect{
                favoriteAll.value = it
            }
        }
    }

    fun setSearchedText(newText: String){
        _searchedText.value = newText
    }

    fun setSportObjectFilter(type: SportObjectTypeFilter){
        _sportObjectFilter.value = type
        loadData()
    }

    fun filterByText(){
        loadData()
    }

    fun swapFavorite(sportObject: SportObject){
        viewModelScope.launch {
            if (sportObject.favorite){
                repository.removeFromFavorite(sportObject)
            }else{
                repository.addToFavorite(sportObject)
            }
        }
    }

    fun reload(){
        loadData()
    }


    private fun loadData(){
        viewModelScope.launch {
            if(searchedText.isNotEmpty()){
                _showFavorite.value = false
                repository.getFilteredSportObjects(
                    _searchedText.value,
                    _sportObjectFilter.value ?: SportObjectTypeFilter.ALL
                ).collect{
                    _sportObjects.value = it
                }
            }else{
                _showFavorite.value = true
            }
        }
    }
}