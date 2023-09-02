package com.ishim.playmusic.compose_layout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ishim.playmusic.Artist
import com.ishim.playmusic.R

@Composable
fun ArtistCard(
    artist: Artist,
    onClickArtist: () -> Unit = {}
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = 4.dp,
        modifier = Modifier.padding(8.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            /*modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()*/
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_album),
                contentDescription = "Default artist image",
                modifier = Modifier
                    .offset(y = 12.dp)
                    .clip(CircleShape)
                    .fillMaxWidth()
                    .border(2.dp, MaterialTheme.colors.primary, CircleShape)
                    .aspectRatio(1f)
            )

            Box(
//                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .offset(y = -(12.dp))
                    .clip(RoundedCornerShape(36.dp))
                    .background(MaterialTheme.colors.primary, RoundedCornerShape(36.dp))
                    .border(2.dp, MaterialTheme.colors.primary, RoundedCornerShape(36.dp))
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = artist.name,
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp, start = 12.dp, end = 12.dp)
//                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArtistsGridView(
    list: List<Artist>,
    padding: PaddingValues,
    onClickArtist: () -> Unit = {}) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = padding) {
        items(list) { artist ->
            ArtistCard(artist, onClickArtist)
        }
    }
}

@Preview
@Composable
fun PreviewArtistCard() {
    Column() {
        ArtistCard(artist = DUMMY_ARTIST)

        ArtistsGridView(list = DUMMY_ARTIST_LIST, PaddingValues(6.dp))
    }
}