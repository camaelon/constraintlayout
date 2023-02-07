package com.example.examplescomposemotionlayout

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import kotlin.random.Random


/**
 * A demo of using MotionLayout as a collapsing Toolbar using JSON to define the MotionScene
 */

@Preview(group = "scroll", device = "spec:shape=Normal,width=480,height=800,unit=dp,dpi=440")
@Composable
fun ManyGraphs() {
    val rand = Random
    val count = 100
    val graphs = mutableListOf<List<Float>>()
    for (i in 0..100) {
        val values = FloatArray(10) { rand.nextInt(100).toFloat() + 10f }.asList()
        graphs.add(values)
    }
    LazyColumn() {
        items(100) {
            Box(modifier = Modifier
                .padding(3.dp)
                .height(200.dp)) {
                DynamicGraph(graphs[it])
            }
        }
    }
}

@OptIn(ExperimentalMotionApi::class)
@Preview(group = "scroll", device = "spec:shape=Normal,width=480,height=800,unit=dp,dpi=440")
@Composable
fun DynamicGraph(values: List<Float> = listOf<Float>(12f, 32f, 21f, 32f, 2f), max: Int = 100) {
    val scale = values.map { (it * 0.8f) / max }
    val count = values.size
    val widthPercent = 1 / (count * 2f)
    val tmpNames = arrayOfNulls<String>(count)
    for (i in tmpNames.indices) {
        tmpNames[i] = "foo$i"
    }
    val names: List<String> = tmpNames.filterNotNull()
    var scene = MotionScene() {
        val cols = names.map { createRefFor(it) }.toTypedArray()
        val start1 = constraintSet {
            createHorizontalChain(elements = cols)
            for (i in names.indices) {
                constrain(cols[i]) {
                    width = Dimension.percent(widthPercent)
                    height = Dimension.value(1.dp)
                    bottom.linkTo(parent.bottom, 16.dp)
                }
            }
        }

        val end1 = constraintSet {
            createHorizontalChain(elements = cols)
            for (i in names.indices) {
                constrain(cols[i]) {
                    width = Dimension.percent(widthPercent)
                    height = Dimension.percent(scale[i])
                    bottom.linkTo(parent.bottom, 16.dp)
                }
            }
        }
        transition("default", start1, end1) {
        }
    }
    var animateToEnd by remember { mutableStateOf(true) }
    val progress = remember { Animatable(0f) }
    LaunchedEffect(animateToEnd) {
        progress.animateTo(
            if (animateToEnd) 1f else 0f,
            animationSpec = tween(800)
        )
    }
    MotionLayout(
        modifier = Modifier
            .background(Color(0xFF221010))
            .fillMaxSize().clickable{animateToEnd = !animateToEnd}
            .padding(1.dp),
        motionScene = scene,
        progress = progress.value
    ) {
        for (i in 0..count) {
            Box(
                modifier = Modifier
                    .layoutId("foo$i")
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.hsv(i*240f/count,0.6f,0.6f))
            )

        }
    }

}
