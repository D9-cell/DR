package com.example.dr.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.dr.R // Make sure this import is correct for your project

@Composable
fun CustomImageButton(
    buttonNumber: Int,
    isSelected: Boolean,
    imageResIds: List<Int>, // List of drawable resource IDs
    onImageSelected: (Int) -> Unit // Callback when an image is selected
) {
    // Determine the icon for the selection state (optional, can be removed if you just want to show the image)
    // For image selection, typically the image itself changes, not an overlay like a circle
    // If you still want a selection indicator, you might overlay another icon or change its appearance
    val selectionIndicatorIcon = if (isSelected) R.drawable.baseline_circle_24 else R.drawable.outline_circle_24 // Example selection indicator

    IconButton(onClick = { onImageSelected(buttonNumber) }) {
        // Use modulo to ensure the index stays within the bounds of the list
        val imageResId = if (imageResIds.isNotEmpty()) imageResIds[buttonNumber % imageResIds.size] else R.drawable.a // Use a placeholder if list is empty

        Icon(
            painter = painterResource(id = imageResId),
            contentDescription = "Image Button $buttonNumber, ${imageResId}",
            // You can optionally tint the image if it's a vector drawable and you want to change its color based on selection
            // tint = if (isSelected) Color.Blue else Color.Unspecified, // Example tint
            modifier = Modifier
                .padding(8.dp)
                .graphicsLayer(scaleX = if (isSelected) 1.2f else 1f, scaleY = if (isSelected) 1.2f else 1f)
        )

        // Optional: Add a selection indicator on top of the image
        if (isSelected) {
            Icon(
                painter = painterResource(id = selectionIndicatorIcon),
                contentDescription = "Selected",
                tint = Color.Green, // Color for the selection indicator
                modifier = Modifier.padding(4.dp) // Adjust padding as needed
            )
        }
    }
}