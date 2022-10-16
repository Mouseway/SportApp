package com.example.sportscoreboard.screens.scoreboard

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sportscoreboard.domain.ResultState
import com.example.sportscoreboard.domain.Participant
import org.koin.androidx.compose.viewModel

@Composable
fun ScoreboardScreen() {
    val viewModel by viewModel<ScoreboardViewModel>()
    val scoreRecords = viewModel.scoreRecords.observeAsState()

    Scaffold(topBar = {
        TopAppBar() {
            
        }
    }) { padding ->
        Box(
            Modifier
                .padding(padding)
                .fillMaxSize()){
            Column(
                Modifier
                    .padding(top = 10.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())) {
                scoreRecords.value?.let {
                    when(it){
                        is ResultState.Error -> Log.i("Scoreboard", "Error")
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
                    ParticipantView(participant = it)
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
fun ParticipantView(participant: Participant){
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ){
        Text(participant.name)
    }
}