package com.ishim.playmusic.compose_layout

import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ishim.playmusic.R
import com.ishim.playmusic.Song
import com.ishim.playmusic.timeFormat
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId

@Composable
fun AlbumArtAndInfo(song: Song) {
    Card(
        shape = MaterialTheme.shapes.large,
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
            .scale(.95f)
            .clip(RoundedCornerShape(24.dp))
            .animateContentSize()
    ) {
        Column(modifier = Modifier
            .clickable {}
            .aspectRatio(.8f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.ic_album
                ),
                contentDescription = "Album default image",
                modifier = Modifier
                    .aspectRatio(1f)
                    .border(1.dp, MaterialTheme.colors.primary, RoundedCornerShape(24.dp))
                    .clip(RoundedCornerShape(24.dp))
                    .shadow(2.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            )

            Text(
                text = song.name,
                style = MaterialTheme.typography.h6
            )

            Text(
                text = song.artist,
                style = MaterialTheme.typography.body1,
                maxLines = 1,
                modifier = Modifier
                    .padding(4.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = song.album,
                style = MaterialTheme.typography.body1,
                maxLines = 1,
                modifier = Modifier
                    .padding(4.dp)
            )
        }

    }
}

@Composable
fun MediaButtonAndProgressBar(duration: Long) {
    val timeElapsed by remember {
        mutableStateOf(0)
    }
    val instantElapsed = Instant.ofEpochMilli(timeElapsed.toLong())
    val durElapsed = LocalTime.ofInstant(instantElapsed, ZoneId.systemDefault())

    val instant = Instant.ofEpochMilli(duration)
    val dur = LocalTime.ofInstant(instant, ZoneId.systemDefault())

    Card(
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
            .scale(.95f)
            .clip(RoundedCornerShape(24.dp))
            .animateContentSize()
            .fillMaxHeight(.5f)
//            .wrapContentSize()
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            var sliderValue by remember { mutableStateOf(0f) }

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(2f)
                    .weight(.3f)
                    .padding(end = 8.dp, start = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = timeFormat.format(durElapsed),
                    style = MaterialTheme.typography.body2,
                    maxLines = 1,
                    color = MaterialTheme.colors.onSecondary,
                    modifier = Modifier.weight(.15f)
                )

                Slider(
                    value = sliderValue,
                    onValueChange = { newValue -> sliderValue = newValue },
                    colors = SliderDefaults.colors(),
                    modifier = Modifier.weight(.8f)
                )

                Text(
                    text = timeFormat.format(dur),
                    style = MaterialTheme.typography.body2,
                    maxLines = 1,
                    color = MaterialTheme.colors.onSecondary,
                    modifier = Modifier.weight(.15f)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(.7f)
                    .padding(start = 8.dp, end = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.repeat),
                        contentDescription = "Repeat",
                        Modifier
                            .scale(.5f)
//                            .padding(end = 32.dp)
                    )
                }

                Spacer(modifier = Modifier.width(48.dp))

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.skip_previous),
                        contentDescription = "Previous",
//                        modifier = Modifier
//                            .clip(RoundedCornerShape(.5.dp))
//                            .padding(start = 8.dp, end = 8.dp)
                    )
                }

//                Spacer(modifier = Modifier.width(16.dp))

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.play_arrow),
                        contentDescription = "Play",
                        modifier = Modifier
                            .scale(1.25f)
                            .background(MaterialTheme.colors.surface)
                            .border(.5.dp, MaterialTheme.colors.primary, CircleShape)
                            .clip(CircleShape)
//                            .padding(start = 8.dp, end = 8.dp)
                    )
                }

//                Spacer(modifier = Modifier.width(16.dp))

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.skip_next),
                        contentDescription = "Next",
//                        modifier = Modifier
//                            .clip(RoundedCornerShape(.5.dp))
//                            .padding(start = 8.dp, end = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.width(48.dp))

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.shuffle),
                        contentDescription = "Shuffle",
                        Modifier
                            .scale(.5f)
//                            .padding(start = 32.dp)
                    )
                }
            }
        }

    }
}

@Composable
fun PlayingView(song: Song) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            AlbumArtAndInfo(song = song)

            Spacer(modifier = Modifier.width(18.dp))

            MediaButtonAndProgressBar(duration = song.duration)
        }
}

@Preview
@Composable
fun AlbumArtAndInfoPreview() {
    val song = Song(
        123456789,
        Uri.EMPTY,
        "Scream?",
        "Dreamcatcher?",
        "1st Album?",
        "Dummy",
        180000,
        "Dummy data"
    )
    PlayingView(song = song)
}