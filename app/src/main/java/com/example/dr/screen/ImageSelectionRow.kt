import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dr.R // Make sure this import is correct
import com.example.dr.screen.CustomImageButton

@Composable
fun ImageSelectionRow() {

    val imageOptions = remember { listOf(
            R.drawable.a,R.drawable.aa,R.drawable.ada,R.drawable.an,
            R.drawable.ba,R.drawable.bha,R.drawable.bi,
            R.drawable.c,R.drawable.ch,R.drawable.cn,
            R.drawable.da,R.drawable.dda,R.drawable.ddh,R.drawable.dha,R.drawable.dra,
            R.drawable.e,R.drawable.en,
            R.drawable.g,R.drawable.gh,
            R.drawable.ha,
            R.drawable.i,R.drawable.ii,
            R.drawable.ja,R.drawable.jh,
            R.drawable.k,R.drawable.kh,R.drawable.kt,
            R.drawable.la,
            R.drawable.ma,R.drawable.mn,R.drawable.msa,
            R.drawable.na,
            R.drawable.o,R.drawable.oi,R.drawable.ou,
            R.drawable.pa,R.drawable.pha,
            R.drawable.ra,R.drawable.ri,
            R.drawable.s,R.drawable.sa,
            R.drawable.t,R.drawable.ta,R.drawable.tha,R.drawable.tta,
            R.drawable.u,R.drawable.un,R.drawable.uu,
            R.drawable.y,R.drawable.ya
        ) }

    var currentSelectedImageIndex by remember { mutableIntStateOf(0) } // State to track selected image

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(colorResource(id = R.color.white)) // Assuming you have lightGray color
            .horizontalScroll(rememberScrollState())
    ) {
        // Image Selection Title
        Text(
            text = "Select Image",
            fontSize = 18.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(start = 9.dp, top = 14.dp)
        )
        imageOptions.forEachIndexed { index, imageResId ->
            CustomImageButton(
                buttonNumber = index,
                isSelected = index == currentSelectedImageIndex,
                imageResIds = imageOptions, // Pass the list of all image resource IDs
                onImageSelected = { buttonIndex ->
                    currentSelectedImageIndex = buttonIndex
                    println("Selected image with ID: ${imageOptions[buttonIndex]}")
                }
            )
        }
    }
}