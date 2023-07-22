package com.ishim.playmusic.compose_layout

import androidx.lifecycle.ViewModel
import com.ishim.playmusic.CurrentlyPlaying
import com.ishim.playmusic.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CurrentlyPlayingState (
    val currentlyPlaying: CurrentlyPlaying = CurrentlyPlaying(),
    val song: Song? = null
)

class PlayMusicViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CurrentlyPlayingState())
    val uiState: StateFlow<CurrentlyPlayingState> = _uiState.asStateFlow()

    fun whenSongPlayedChange(newSong: Song) {
        _uiState.update { currentState ->
            currentState.copy(
                song = newSong
            )
        }
    }
}