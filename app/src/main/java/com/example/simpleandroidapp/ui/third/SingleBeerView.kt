package com.example.simpleandroidapp.ui.third

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import dev.chrisbanes.accompanist.coil.CoilImage

@Preview
@Composable
@SuppressWarnings("FunctionNaming")
fun SinglePreviewBeerScreen() {
    SingleBeerScreen("name: String", "", "https://images.punkapi.com/v2/10.png")
}

@Composable
@SuppressWarnings("MagicNumber", "FunctionNaming")
fun SingleBeerScreen(name: String, foodPairing: String, imageUrl: String) {
    ScrollableColumn() {
        Text(text = name)
        Text(text = foodPairing)
        CoilImage(
            data = imageUrl as Any,
            modifier = Modifier
                .padding(bottom = 15.dp, top = 15.dp)
        )
    }
}
