package com.example.dr.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.dr.R

@Composable
fun CustomImageButton(
    buttonNumber: Int,
    isSelected: Boolean,
    imageResIds: List<Int>,
    onImageSelected: (Int) -> Unit
) {
    val imageResId = if (imageResIds.isNotEmpty()) imageResIds[buttonNumber % imageResIds.size] else R.drawable.a // Use a placeholder if list is empty

    IconButton(
        onClick = { onImageSelected(buttonNumber) },
        modifier = Modifier
            .size(48.dp) // Give the IconButton a fixed size
    ) {
        Box(
            modifier = Modifier
                .size(if (isSelected) 48.dp else 40.dp) // Adjust size of the circular background based on selection
                .clip(CircleShape) // Clip the Box to a circle
                .background(Color.White) // Give it a background color (you can change this)
                .graphicsLayer( // Apply scaling for selected state to the Box
                    scaleX = if (isSelected) 1.2f else 1f,
                    scaleY = if (isSelected) 1.2f else 1f
                ),
            contentAlignment = Alignment.Center // Center the icon inside the box
        ) {
            Icon(
                painter = painterResource(id = imageResId),
                contentDescription = "Image Button $buttonNumber",
                tint = Color.Unspecified, // Keep Unspecified to retain original image colors
                modifier = Modifier
                    .size(if (isSelected) 32.dp else 24.dp) // Adjust icon size within the circular background
            )
        }
    }
}