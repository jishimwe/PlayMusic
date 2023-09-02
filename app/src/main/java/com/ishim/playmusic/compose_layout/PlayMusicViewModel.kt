package com.ishim.playmusic.compose_layout

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ishim.playmusic.CurrentlyPlaying
import com.ishim.playmusic.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private val TAG = "PlayMusicViewModel"
data class CurrentlyPlayingState (
    var currentlyPlaying: CurrentlyPlaying = CurrentlyPlaying(),
    var song: MutableState<Song> = mutableStateOf(DEFAULT_SONG)
)

/*class CurrentlyPlayingState (
    var currentlyPlaying: CurrentlyPlaying = CurrentlyPlaying(),
    currentSong: Song = DEFAULT_SONG
) {
    var song by mutableStateOf(currentSong)
}*/

class PlayMusicViewModel : ViewModel() {
    private var _uiState = MutableStateFlow(CurrentlyPlayingState())
    var uiState: StateFlow<CurrentlyPlayingState> = _uiState.asStateFlow()

    init {
        Log.d(TAG, "Created")
    }

    fun whenSongPlayedChange(newSong: Song) {
        Log.d(TAG, "BEFORE $newSong.name -- ${_uiState.value.song.value.name}")
        _uiState.update { currentState ->
            currentState.copy(
                song = mutableStateOf(newSong)
            )
        }
        Log.d(TAG, "AFTER $newSong.name -- ${_uiState.value.song.value.name}")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "Cleared -${_uiState.value.song.value.name}")
    }
}
