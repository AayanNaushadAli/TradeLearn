package com.example.tradelearn.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.tradelearn.data.*
import com.example.tradelearn.ui.theme.PrimaryIndigo
import kotlin.math.roundToInt

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LessonScreen(viewModel: LessonViewModel) {
    // 1. Observe the states from our Game Engine
    val uiState by viewModel.uiState.collectAsState()
    val currentIndex by viewModel.currentIndex.collectAsState()
    val hearts by viewModel.hearts.collectAsState()
    val isGameOver by viewModel.isGameOver.collectAsState()
    val isLessonFinished by viewModel.isLessonFinished.collectAsState()

    // 2. Handle Game Over and Victory States
    if (isGameOver) {
        GameOverScreen(onRetry = { viewModel.loadLesson(1) }) // Pass chapterId 1 for now
        return
    }
    if (isLessonFinished) {
        VictoryScreen()
        return
    }

    // 3. Render the Main Game Screen
    Scaffold(
            modifier =
                    Modifier.fillMaxSize()
                            .statusBarsPadding() // FIX: Adds space for the clock/battery icons
                            .navigationBarsPadding(), // FIX: Adds space for the bottom gesture bar
            topBar = {
                if (uiState is LessonUiState.Success) {
                    val totalItems = (uiState as LessonUiState.Success).lessonItems.size
                    GameTopBar(
                            currentIndex = currentIndex,
                            totalItems = totalItems,
                            hearts = hearts
                    )
                }
            }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when (val state = uiState) {
                is LessonUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is LessonUiState.Error -> {
                    Text("Error: ${state.message}", color = MaterialTheme.colorScheme.error)
                }
                is LessonUiState.Success -> {
                    val currentCard = state.lessonItems[currentIndex]

                    // AnimatedContent gives that smooth Duolingo slide-in effect
                    AnimatedContent(targetState = currentCard, label = "card_animation") { card ->
                        Column(
                                modifier = Modifier.fillMaxSize().padding(16.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                        ) {

                            // 4. THE MAGIC: Check the card type and render the correct UI
                            Box(modifier = Modifier.weight(1f)) {
                                when (card) {
                                    is TheoryBlock -> TheoryBlockUI(card)
                                    is MultipleChoice -> MultipleChoiceUI(card, viewModel)
                                    is TrueFalse -> TrueFalseUI(card, viewModel)
                                    is Flashcard ->
                                            FlashcardUI(
                                                    card,
                                                    viewModel
                                            ) // Updated to pass viewModel
                                }
                            }

                            // The global "Next" button for Theory blocks
                            if (card is TheoryBlock) {
                                Button(
                                        onClick = { viewModel.onNextCard() },
                                        modifier = Modifier.fillMaxWidth().height(56.dp)
                                ) { Text("CONTINUE") }
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- THE TOP PROGRESS BAR ---
@Composable
fun GameTopBar(currentIndex: Int, totalItems: Int, hearts: Int) {
    Row(
            modifier =
                    Modifier.fillMaxWidth()
                            .padding(
                                    horizontal = 20.dp,
                                    vertical = 10.dp
                            ), // Added extra side breathing room
            verticalAlignment = Alignment.CenterVertically
    ) {
        // Progress Bar
        LinearProgressIndicator(
                progress = (currentIndex + 1) / totalItems.toFloat(),
                modifier = Modifier.weight(1f).height(12.dp),
                color = Color(0xFF58CC02) // Duolingo Green
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Hearts Counter
        Icon(Icons.Filled.Favorite, contentDescription = "Hearts", tint = Color.Red)
        Text(text = hearts.toString(), style = MaterialTheme.typography.titleLarge)
    }
}

// --- THE THEORY BLOCK UI ---
@Composable
fun TheoryBlockUI(card: TheoryBlock) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = card.title, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        // In the future, you can load card.imageAsset here with Coil
        Text(text = card.content, style = MaterialTheme.typography.bodyLarge)
    }
}

// --- THE MULTIPLE CHOICE UI ---
@Composable
fun MultipleChoiceUI(card: MultipleChoice, viewModel: LessonViewModel) {
    var selectedOption by remember { mutableStateOf<Option?>(null) }
    var isChecked by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = card.question, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(24.dp))

        // Draw each option
        card.options.forEach { option ->
            val backgroundColor =
                    when {
                        !isChecked -> MaterialTheme.colorScheme.surfaceVariant
                        option.isCorrect -> Color(0xFFD7FFD4) // Green if correct
                        selectedOption == option && !option.isCorrect ->
                                Color(0xFFFFD4D4) // Red if wrong guess
                        else -> MaterialTheme.colorScheme.surfaceVariant
                    }

            Card(
                    onClick = { if (!isChecked) selectedOption = option },
                    colors = CardDefaults.cardColors(containerColor = backgroundColor),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) { Text(text = option.text, modifier = Modifier.padding(16.dp)) }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Check Answer Button
        Button(
                onClick = {
                    if (!isChecked) {
                        isChecked = true
                        if (selectedOption?.isCorrect == false) {
                            viewModel.onWrongAnswer()
                        }
                    } else {
                        // Reset and move to next card
                        isChecked = false
                        selectedOption = null
                        viewModel.onNextCard()
                    }
                },
                enabled = selectedOption != null,
                modifier = Modifier.fillMaxWidth().height(56.dp)
        ) { Text(if (!isChecked) "CHECK" else "CONTINUE") }

        // Show explanation if wrong
        if (isChecked && selectedOption?.isCorrect == false) {
            Text(
                    text = card.explanation,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Composable
fun TrueFalseUI(card: TrueFalse, viewModel: LessonViewModel) {
    var selectedOption by remember { mutableStateOf<Option?>(null) }
    var isChecked by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "True or False?", color = PrimaryIndigo, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = card.question, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(32.dp))

        // Draw the True/False buttons side-by-side using a Row
        Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp) // Space between buttons
        ) {
            card.options.forEach { option ->
                val backgroundColor =
                        when {
                            !isChecked -> MaterialTheme.colorScheme.surfaceVariant
                            option.isCorrect -> Color(0xFFD7FFD4) // Green if correct
                            selectedOption == option && !option.isCorrect ->
                                    Color(0xFFFFD4D4) // Red if wrong guess
                            else -> MaterialTheme.colorScheme.surfaceVariant
                        }

                Card(
                        onClick = { if (!isChecked) selectedOption = option },
                        colors = CardDefaults.cardColors(containerColor = backgroundColor),
                        modifier =
                                Modifier.weight(
                                                1f
                                        ) // Makes both buttons take up equal half of the screen
                                        .height(80.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                                text = option.text,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Check Answer Button
        Button(
                onClick = {
                    if (!isChecked) {
                        isChecked = true
                        if (selectedOption?.isCorrect == false) {
                            viewModel.onWrongAnswer()
                        }
                    } else {
                        // Reset and move to next card
                        isChecked = false
                        selectedOption = null
                        viewModel.onNextCard()
                    }
                },
                enabled = selectedOption != null,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryIndigo)
        ) { Text(if (!isChecked) "CHECK" else "CONTINUE") }

        // Show explanation if they guessed wrong
        if (isChecked && selectedOption?.isCorrect == false) {
            Text(
                    text = card.explanation,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Composable
fun FlashcardUI(card: Flashcard, viewModel: LessonViewModel) {
    CandleSwipeGame(patternName = card.question) { isBullish ->
        // Handle the result here. For now, simply proceed.
        viewModel.onNextCard()
    }
}

@Composable
fun CandleSwipeGame(patternName: String, onResult: (Boolean) -> Unit) {
    var offsetX by remember { mutableStateOf(0f) }

    Box(
            modifier =
                    Modifier.fillMaxSize().pointerInput(Unit) {
                        detectHorizontalDragGestures(
                                onDragEnd = {
                                    if (offsetX > 300) onResult(true) // Swiped Right (Bullish)
                                    else if (offsetX < -300)
                                            onResult(false) // Swiped Left (Bearish)
                                    offsetX = 0f
                                },
                                onHorizontalDrag = { change, dragAmount ->
                                    change.consume()
                                    offsetX += dragAmount
                                }
                        )
                    },
            contentAlignment = Alignment.Center
    ) {
        Card(
                modifier = Modifier.offset { IntOffset(offsetX.roundToInt(), 0) }.size(250.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
            ) {
                // Here you would draw a Canvas candle
                Text(text = patternName, style = MaterialTheme.typography.headlineLarge)
                Text("Swipe Right for Bullish, Left for Bearish")
            }
        }
    }
}

@Composable
fun GameOverScreen(onRetry: () -> Unit) {
    Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Out of Hearts! \uD83D\uDC94", style = MaterialTheme.typography.headlineLarge)
        Button(onClick = onRetry) { Text("Try Again") }
    }
}

@Composable
fun VictoryScreen() {
    Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Lesson Complete! \uD83C\uDF89", style = MaterialTheme.typography.headlineLarge)
        Text("+50 XP", color = Color(0xFF58CC02), style = MaterialTheme.typography.titleLarge)
    }
}
