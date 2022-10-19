package com.example.sportscoreboard.screens.scoreboard

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sportscoreboard.domain.ResultState
import com.example.sportscoreboard.domain.Participant
import com.example.sportscoreboard.domain.filters.ParticipantFilter
import com.example.sportscoreboard.others.composable.Searchbar
import org.koin.androidx.compose.viewModel

@Composable
fun ParticipantsListScreen() {
    val viewModel by viewModel<ParticipantsViewModel>()
    val scoreRecords = viewModel.scoreRecords.observeAsState()
    val focusManager = LocalFocusManager.current

    Scaffold(topBar = {
        Column(modifier = Modifier.background(MaterialTheme.colors.primary)) {
            TopAppBar() {
                Searchbar(viewModel.searchedText,
                    onSearchClick = {
                        focusManager.clearFocus()
                    },
                    onTextChange = {viewModel.setSearchedText(it)}
                )
            }
            Row(Modifier.padding(horizontal = 5.dp)) {
                ParticipantFilter.values().forEach {
                    ParticipantTypeChip(
                        text = it.title,
                        onClick = { viewModel.setParticipantType(it) },
                        selected = (viewModel.participantType == it))
                }
            }
        }
    }) { padding ->
        Box(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
        ){
            Column(
                Modifier
                    .padding(top = 10.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())) {
                scoreRecords.value?.let {
                    when(it){
                        is ResultState.Error -> Log.i("Scoreboard", it.message ?: "Error")
                        is ResultState.Loading -> if(it.isLoading)  LoadingScreen()
                        is ResultState.Success -> it.data?.let { it1 -> ScoreRecordsList(records = it1) }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingScreen(){
    Box(Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier
            .fillMaxSize(0.25f)
            .align(Alignment.Center)
            .padding(top = 50.dp)
        )
    }
}


@Composable
fun ScoreRecordsList(records: List<Participant>){
    val bySport = records.groupBy { it.sport }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 5.dp)
    ) {
        bySport.forEach{ (sport, list) ->
            SportHeader(sport = sport)
            Column(Modifier.padding(10.dp)) {
                list.forEach {
                    ParticipantPreview(participant = it)
                }
            }
        }
    }
}

@Composable
fun SportHeader(sport: String){
    Box(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.secondary)
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        Text(
            text = sport,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onSecondary
        )
    }
}

@Composable
fun ParticipantPreview(participant: Participant){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ){
        var model:Any = participant.defaultImageSource
        if(participant.images.isNotEmpty())
            model = participant.getImagePath(0)

        AsyncImage(model = model,
            contentDescription = null,
            error = painterResource(id = participant.defaultImageSource),
            modifier = Modifier.height(30.dp))

        Text(text = participant.name, fontSize = 15.sp, modifier = Modifier.padding(horizontal = 20.dp))
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ParticipantTypeChip(text: String, onClick: ()->Unit, selected: Boolean){
    FilterChip(
        selected = selected,
        onClick = { onClick() },
        colors = ChipDefaults.filterChipColors(
            backgroundColor = MaterialTheme.colors.onPrimary,
            selectedBackgroundColor = MaterialTheme.colors.secondary
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(horizontal = 5.dp)
    ) {
        Text(
            text = text,
            color = if(selected) MaterialTheme.colors.onSecondary else MaterialTheme.colors.onSurface
        )
    }
}

