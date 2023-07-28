package com.example.ipmsg20.utils

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.ipmsg20.customobjects.Sender
import java.text.DecimalFormat

@Composable
fun profileIcon(modifier: Modifier, name: String, image: ImageBitmap? = null, size: Dp = 10.dp, isLoading: Boolean = true) {
    if (isLoading) {
        Column(
            modifier = modifier
                .padding(1.dp)
                .width(size)
                .height(size)
                .clip(CircleShape)
                .shimmerEffect()
        ) {

        }
    } else if (image != null) {

    } else {
        Column(
            modifier = modifier
                .padding(1.dp)
                .width(size)
                .height(size)
                .clip(CircleShape)
        ) {
            val nameSegments = name.split(" ");
            var nameInit = ""
            for (i in nameSegments) {
                nameInit.plus(i.uppercase());
            }
            Text(text = nameInit)
        }
    }
}

@Composable
fun senderItem(
    modifier: Modifier,
    sender_list: List<Sender>,
    isLoading: Boolean
)  {

    val localDensity = LocalDensity.current
    var columnHeight by remember {
        mutableStateOf(0.dp)
    }
    var loadingElementCountFloat by remember {
        mutableStateOf(0f)
    }
    var loadingElementCount by remember {
        mutableStateOf(0)
    }

    LazyColumn(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                columnHeight = with(localDensity) { coordinates.size.height.toDp() }
                loadingElementCountFloat = columnHeight.minus(50.dp).value;
                val df = DecimalFormat("##")
                loadingElementCount = df.format(loadingElementCountFloat).toInt();
                if (!columnHeight.minus(50.dp).equals(0)) {
                    loadingElementCount.plus(1)
                }
            },
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
         if (isLoading) {
             items(loadingElementCount)  {
                Row (
                    modifier = Modifier
                        .padding(2.dp, 5.dp)
                        .wrapContentHeight(Alignment.CenterVertically)
                        )
                {
                    profileIcon(
                        modifier = Modifier,
                        name = "praenth poojary",
                        size = 50.dp
                        )
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .shimmerEffect()
                                .fillMaxWidth()
                                .height(20.dp)
                        ) {}
                        Row(
                            modifier = Modifier
                                .shimmerEffect()
                                .fillMaxWidth()
                                .height(10.dp)
                                .absolutePadding(0.dp, 10.dp, 0.dp, 0.dp)
                        ) {}
                    }
                }
             }
         } else {

         }
    }

}

@Preview(showBackground = true)
@Composable
fun preview() {

    val sender_list = listOf<Sender>()
    senderItem(modifier = Modifier, sender_list = sender_list, isLoading = false)
}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        )
    )
    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFB8B5B5),
                Color(0xFF8F8B8B),
                Color(0xFFB8B5B5),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}