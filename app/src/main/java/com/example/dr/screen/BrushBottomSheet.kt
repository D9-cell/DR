package com.example.dr.screen
import ImageSelectionRow
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.content.Intent
import android.net.Uri
import com.example.dr.R
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrushBottomSheet(
    onDismiss: () -> Unit,
    drawingView: DrawingView?,
    colors: List<Color>,
    backgroundColors: List<Color>,
    currentBrushSize: Float,
    currentColorIndex: Int,
    currentBackgroundColorIndex: Int,
    changeBrushSize: (Float) -> Unit,
    changeBrushColor: (Color) -> Unit,
    changeBackgroundColor: (Color) -> Unit,
    updateCurrentColorIndex: (Int) -> Unit,
    updateCurrentBackgroundColorIndex: (Int) -> Unit,
    // Inject the ViewModel
) {
    val context = LocalContext.current // Get context for Toast

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Brush Size Selector
            Text(
                text = "Select Brush Size",
                fontSize = 18.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(start = 9.dp, top = 14.dp)
            )
            Slider(
                value = currentBrushSize,
                onValueChange = { size -> changeBrushSize(size) },
                valueRange = 2f..18f,
                steps = 16
            )
            Spacer(modifier = Modifier.height(9.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(colorResource(id = R.color.lightGray))
                    .horizontalScroll(rememberScrollState())
            ) {
                // Brush Color Selection
                Text(
                    text = "Select Brush Color",
                    fontSize = 18.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(start = 9.dp, top = 14.dp)
                )
                colors.forEachIndexed { index, color ->
                    ColorButton(
                        buttonNumber = index,
                        isSelected = index == currentColorIndex,
                        colors = colors,
                        onColorSelected = { buttonIndex ->
                            changeBrushColor(colors[buttonIndex])
                            updateCurrentColorIndex(buttonIndex)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(9.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(colorResource(id = R.color.lightGray))
                    .horizontalScroll(rememberScrollState())
            ) {
                // Background Color Selection
                Text(
                    text = "Select Background Color",
                    fontSize = 18.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(start = 9.dp, top = 14.dp)
                )
                backgroundColors.forEachIndexed { index, color ->
                    ColorButton(
                        buttonNumber = index,
                        isSelected = index == currentBackgroundColorIndex,
                        colors = backgroundColors,
                        onColorSelected = { buttonIndex ->
                            changeBackgroundColor(backgroundColors[buttonIndex])
                            updateCurrentBackgroundColorIndex(buttonIndex)
                        }
                    )
                }
            }

            // Bottom bar with buttons like Undo, Redo, Clear, Brush Size, Change Background
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = { drawingView?.onClickUndo() }) {
                    Icon(
                        painter = painterResource(R.drawable.undo),
                        contentDescription = "Undo",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(32.dp)
                    )
                }
                IconButton(onClick = { drawingView?.onClickRedo() }) {
                    Icon(
                        painter = painterResource(R.drawable.undo),
                        contentDescription = "Redo",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(32.dp)
                            .graphicsLayer(rotationY = 180f)
                    )
                }
                IconButton(onClick = { drawingView?.clearCanvas() }) {
                    Icon(
                        painter = painterResource(R.drawable.clear),
                        contentDescription = "Clear",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(32.dp)
                    )
                }
                IconButton(
                    onClick = {
                        drawingView?.let { view ->
                            val bitmap = view.getBitmap(backgroundColors[currentBackgroundColorIndex])
                            val uri =
                                drawingView.saveBitmap(context, bitmap)
                            if (uri != null) {
                                shareBitmap(context, uri)
                            }
                        }
                    },
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.share),
                        contentDescription = "Share Drawing",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(32.dp)
                    )
                }

            }

            // New Image selection row
            ImageSelectionRow()
        }
    }
}


private fun shareBitmap(context: Context, uri: Uri) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_STREAM, uri)
        type = "image/png"
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share Drawing"))
}
/*
private fun saveBitmapAndShare(context: Context, bitmap: Bitmap) {
    val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Drawing_${System.currentTimeMillis()}.png")

    try {
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
        }

        // Share the image
        val uri: Uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            type = "image/png"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share Drawing"))
    } catch (e: Exception) {
        Toast.makeText(context, "Error sharing drawing: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}*/

@Preview(showBackground = true)
@Composable
fun BrushBottomSheetPreview() {
    BrushBottomSheet(
        onDismiss = {},
        drawingView = null, // Cannot provide a real DrawingView in preview
        colors = listOf(Color.Black, Color.Red, Color.Green, Color.Blue),
        backgroundColors = listOf(Color.White, Color.LightGray, Color.Yellow),
        currentBrushSize = 5f,
        currentColorIndex = 0,
        currentBackgroundColorIndex = 0,
        changeBrushSize = {},
        changeBrushColor = {},
        changeBackgroundColor = {},
        updateCurrentColorIndex = {},
        updateCurrentBackgroundColorIndex = {},
    )
}
