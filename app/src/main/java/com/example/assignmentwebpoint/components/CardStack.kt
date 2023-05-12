package com.example.assignmentwebpoint.components

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.example.assignmentwebpoint.news.NewsResponse
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.math.sign

/**
 * Created by Sujan Rai on 12/05/2023.
 * 
 *
 *
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardStack(
    modifier: Modifier = Modifier,
    items: MutableList<NewsResponse.News?>,
    thresholdConfig: (Float, Float) -> ThresholdConfig = { _, _ -> FractionalThreshold(0.2f) },
    velocityThreshold: Dp = 125.dp,
    onSwipeLeft: (item: NewsResponse.News) -> Unit = {},
    onSwipeRight: (item: NewsResponse.News) -> Unit = {},
    onEmptyStack: (lastItem: NewsResponse.News) -> Unit = {}
) {
    var i by remember {
        mutableStateOf(items.size - 1)
    }

    if (i == -1) {
        items.last()?.let { onEmptyStack(it) }
    }

    val cardStackController = rememberCardStackController()

    cardStackController.onSwipeLeft = {
        items[i]?.let { onSwipeLeft(it) }
        i--
    }

    cardStackController.onSwipeRight = {
        items[i]?.let { onSwipeRight(it) }
        i--
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(0.dp)
    ) {
        val stack = createRef()

        Box(
            modifier = modifier
                .constrainAs(stack) {
                    top.linkTo(parent.top)
                }
                .draggableStack(
                    controller = cardStackController,
                    thresholdConfig = thresholdConfig,
                    velocityThreshold = velocityThreshold
                )
                .fillMaxHeight()
        ) {
            items.asReversed().forEachIndexed { index, item ->
                if (item != null) {
                    Card(
                        modifier = Modifier.moveTo(
                            x = if (index == i) cardStackController.offsetX.value else 0f,
                            y = if (index == i) cardStackController.offsetY.value else 0f
                        )
                            .visible(visible = index == i || index == i - 1)
                            .graphicsLayer(
                                rotationZ = if (index == i) cardStackController.rotation.value else 0f,
                                scaleX = if (index < i) cardStackController.scale.value else 1f,
                                scaleY = if (index < i) cardStackController.scale.value else 1f
                            ),
                        item,
                        cardStackController
                    )
                }
            }
        }
    }
}

@Composable
fun Card(
    modifier: Modifier = Modifier,
    item: NewsResponse.News,
    cardStackController: CardStackController
) {
    Box(modifier = modifier) {
        /*if (item.urlToStory != null) {
            AsyncImage(
                model = item.url,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = modifier.fillMaxSize()
            )
        }*/

        Column(
            modifier = modifier
                .align(Alignment.BottomStart)
                .padding(10.dp)
        ) {
            item.title?.let { Text(text = it, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 25.sp) }

            Row {
                IconButton(
                    modifier = modifier.padding(50.dp, 0.dp, 0.dp, 0.dp),
                    onClick = { cardStackController.swipeLeft() },
                ) {
                    Icon(
                        Icons.Default.Close, contentDescription = "", tint = Color.White, modifier =
                        modifier
                            .height(50.dp)
                            .width(50.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    modifier = modifier.padding(0.dp, 0.dp, 50.dp, 0.dp),
                    onClick = { cardStackController.swipeRight() }
                ) {
                    Icon(
                        Icons.Default.FavoriteBorder, contentDescription = "", tint = Color.White, modifier =
                        modifier.height(50.dp).width(50.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun rememberCardStackController(
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec
): CardStackController {
    val scope = rememberCoroutineScope()
    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }

    return remember {
        CardStackController(
            scope = scope,
            screenWidth = screenWidth,
            animationSpec = animationSpec
        )
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
fun Modifier.draggableStack(
    controller: CardStackController,
    thresholdConfig: (Float, Float) -> ThresholdConfig,
    velocityThreshold: Dp = 125.dp
): Modifier = composed {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current

    val velocityThresholdPx = with(density) {
        velocityThreshold.toPx()
    }

    val thresholds = { a: Float, b: Float ->
        with(thresholdConfig(a, b)) {
            density.computeThreshold(a, b)
        }
    }

    controller.threshold = thresholds(controller.center.x, controller.right.x)

    Modifier.pointerInput(Unit) {
        detectDragGestures(
            onDragEnd = {
                if (controller.offsetX.value <= 0f) {
                    if (controller.offsetX.value > -controller.threshold) {
                        controller.returnCenter()
                    } else {
                        controller.swipeLeft()
                    }
                } else {
                    if (controller.offsetX.value < controller.threshold) {
                        controller.returnCenter()
                    } else {
                        controller.swipeRight()
                    }
                }
            },
            onDrag = { change, dragAmount ->
                controller.scope.apply {
                    launch {
                        controller.offsetX.snapTo(controller.offsetX.value + dragAmount.x)
                        controller.offsetY.snapTo(controller.offsetY.value + dragAmount.y)

                        val targetRotation = normalize(
                            controller.center.x,
                            controller.right.x,
                            StrictMath.abs(controller.offsetX.value),
                            0f,
                            10f
                        )

                        controller.rotation.snapTo(targetRotation * -controller.offsetX.value.sign)

                        controller.scale.snapTo(
                            normalize(
                                controller.center.x,
                                controller.right.x / 3,
                                StrictMath.abs(controller.offsetX.value),
                                0.8f
                            )
                        )
                    }
                }
                change.consume()
            }
        )
    }
}

private fun normalize(
    min: Float,
    max: Float,
    v: Float,
    startRange: Float = 0f,
    endRange: Float = 1f
): Float {
    require(startRange < endRange) {
        "Start range is greater than end range"
    }

    val value = v.coerceIn(min, max)

    return (value - min) / (max - min) * (endRange + startRange) + startRange
}

fun Modifier.moveTo(
    x: Float,
    y: Float
) = this.then(Modifier.layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)

    layout(placeable.width, placeable.height) {
        placeable.placeRelative(x.roundToInt(), y.roundToInt())
    }
})

fun Modifier.visible(
    visible: Boolean = true
) = this.then(Modifier.layout{ measurable, constraints ->
    val placeable = measurable.measure(constraints)

    if (visible) {
        layout(placeable.width, placeable.height) {
            placeable.placeRelative(0, 0)
        }
    } else {
        layout(0, 0) {}
    }
})