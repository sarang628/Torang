package com.example.commonwidgets.torangcomposepack

import android.widget.RatingBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun AndroidViewRatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    isSmall: Boolean = false,
    changable: Boolean = true
) {
    // Adds view to Compose
    AndroidView(
        modifier = modifier,
        factory = { context ->
            // Creates view
            if (isSmall) {
                RatingBar(context, null, android.R.attr.ratingBarStyleSmall).apply {
                    // Sets up listeners for View -> Compose communication
                    this.rating = rating
                    setIsIndicator(!changable)
                }
            } else {
                RatingBar(context).apply {
                    // Sets up listeners for View -> Compose communication
                    this.rating = rating
                    setIsIndicator(!changable)

                }
            }
        },
        update = { view ->
            // View's been inflated or state read in this block has been updated
            // Add logic here if necessary

            // As selectedItem is read here, AndroidView will recompose
            // whenever the state changes
            // Example of Compose -> View communication
        }
    )
}

@Preview
@Composable
fun PreviewAndroidViewRatingBar() {
    AndroidViewRatingBar(Modifier, 3.2f, isSmall = true, changable = false)
}