package com.example.finaltermdatabase.ui.theme

import android.widget.Toast
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.addSvg
import androidx.compose.ui.layout.onFirstVisible
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ActiveModifierSample() {
    val context= LocalContext.current
    val infiniteTransition= rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000)
        )
    )
    Box(
        modifier = Modifier.background(Color.White).fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = Color.Magenta,
                    shape = PathShape()
                )
                .size(150.dp)
                .rotate(rotation)

        )
    }
}

class PathShape: Shape{
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path= Path().apply {
            moveTo(0f,0f)
            quadraticTo(size.width/2,size.height/2,size.width,0f)
            quadraticTo(size.width/2,size.height/2,size.width,size.height)
            quadraticTo(size.width/2,size.height/2,0f,size.height)
            quadraticTo(size.width/2,size.height/2,0f,0f)
            close()
        }
        return Outline.Generic(path)
    }

}