package com.ishim.playmusic.compose_layout

import android.content.Context
import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ishim.playmusic.MusicDB
import com.ishim.playmusic.SongCardInfo
import com.ishim.playmusic.R

// Defining Colors
val darkSkyBlue = Color(0xff98c1d9)
val blueVioletCrayola = Color(0xFF6969B3)
val purpleMountain = Color(0xff9b82c4)
val palatinatePurple = Color(0xff4b244a)
val gunMetal = Color(0xff223843)
val errorColor = Color(0xD3B00020)

private val DarkColors = darkColors(
    primary = blueVioletCrayola,
    secondary = purpleMountain,
    secondaryVariant = darkSkyBlue
)

private val LightColors = lightColors(
    primary = palatinatePurple,
    secondary = gunMetal,
    secondaryVariant = darkSkyBlue
)

@Composable
fun MyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}

@Composable
fun SongCard(song: SongCardInfo) {
//    Row (modifier = Modifier.padding(all = 8.dp)){
//        Image(
//            painter = painterResource(id = R.drawable.ic_album),
//            contentDescription = "Contact profile picture",
//            modifier = Modifier
//                .size(50.dp, 50.dp)
//                .clip(CircleShape)
//                .border(1.dp, MaterialTheme.colors.secondary, CircleShape)
//        )
//    }
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colors.surface,
        elevation = 4.dp,
        modifier = Modifier
            .animateContentSize()
            .padding(1.dp)) {


        Row(modifier = Modifier.padding(all = 8.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_album),
                contentDescription = "Album default image",
                modifier = Modifier
                    .size(50.dp, 50.dp)
                    .clip(CircleShape)
                    .border(1.dp, MaterialTheme.colors.secondary, CircleShape)
                    .align(Alignment.CenterVertically)
            )

            Column(modifier = Modifier
                .clickable { }
                .padding(4.dp)) {
                Text(
                    text = song.name,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(4.dp))

                Row (modifier = Modifier.fillMaxWidth()){
                    Text(
                        text = song.artist,
                        style = MaterialTheme.typography.body1,
                        maxLines = 1,
                        modifier = Modifier
                            .padding(4.dp)
                            .weight(.5f))

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = song.album,
                        style = MaterialTheme.typography.body1,
                        maxLines = 1,
                        modifier = Modifier
                            .padding(4.dp)
                            .weight(.5f))
                }

            }
        }
    }
}

@Composable
fun TrackList(context: Context) {
//    TODO Ask for permissions
//    PermissionsChecker()
    val listState = rememberLazyListState()
    val songs = MusicDB.getTracks(context)
    LazyColumn(
        state = listState
    ) {
        items(songs) { track -> SongCard(song = track)}
    }
}

@Composable
@Preview
fun PreviewSongCard() {
    val song = SongCardInfo(Uri.EMPTY,"Scream", "Dreamcatcher", "Dystopia : The Tree of Language")
    SongCard(song)
}

class MyTheme {
}