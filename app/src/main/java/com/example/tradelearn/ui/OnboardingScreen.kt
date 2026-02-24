package com.example.tradelearn.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tradelearn.ui.theme.PrimaryIndigo
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
        viewModel: AuthViewModel = viewModel(), // 1. Inject the ViewModel
        onFinishOnboarding: (String) -> Unit
) {
        val pagerState = rememberPagerState(pageCount = { 3 })
        val coroutineScope = rememberCoroutineScope()

        // Listen to what the Auth Engine is doing
        val authState by viewModel.authState.collectAsState()

        var userName by remember { mutableStateOf("") }
        var userEmail by remember { mutableStateOf("") }
        var userPassword by remember { mutableStateOf("") }
        var otpCode by remember { mutableStateOf("") } // For the 6-digit code
        var isLoginMode by remember { mutableStateOf(false) }

        // If verification is successful, launch into the map!
        LaunchedEffect(authState) {
                if (authState is AuthState.Success) {
                        onFinishOnboarding(userName.ifBlank { "Trader" })
                }
        }

        Column(
                modifier =
                        Modifier.fillMaxSize()
                                .background(Color.White)
                                .statusBarsPadding()
                                .navigationBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {

                // --- UI SWAP LOGIC ---
                // If the email was sent, show the OTP Screen instead of the Pager
                // Also show it if we are already on the OTP screen but got an error verifying it
                val showOtpScreen =
                        authState is AuthState.OtpSent ||
                                (authState is AuthState.Error &&
                                        (authState as AuthState.Error).message == "Invalid Code") ||
                                (authState is AuthState.Loading &&
                                        pagerState.currentPage ==
                                                2 /* This was probably not why, but keep it robust */)

                // However, since we don't track *which* screen we were on properly in state,
                // let's create a local boolean to track if we've successfully reached OTP stage
                var hasReachedOtp by remember { mutableStateOf(false) }

                LaunchedEffect(authState) {
                        if (authState is AuthState.OtpSent) {
                                hasReachedOtp = true
                        }
                }

                if (hasReachedOtp) {
                        Column(
                                modifier = Modifier.weight(1f).padding(32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                        ) {
                                Text(
                                        "Check Your Email",
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.ExtraBold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                        "We sent a 6-digit code to $userEmail",
                                        color = Color.Gray,
                                        textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(32.dp))

                                OutlinedTextField(
                                        value = otpCode,
                                        onValueChange = { if (it.length <= 6) otpCode = it },
                                        placeholder = { Text("123456", color = Color.LightGray) },
                                        keyboardOptions =
                                                KeyboardOptions(keyboardType = KeyboardType.Number),
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(16.dp),
                                        singleLine = true
                                )

                                if (authState is AuthState.Error) {
                                        Text(
                                                text = (authState as AuthState.Error).message,
                                                color = Color.Red,
                                                modifier = Modifier.padding(top = 8.dp)
                                        )
                                }

                                Spacer(modifier = Modifier.height(24.dp))

                                Button(
                                        onClick = {
                                                viewModel.verifyOtp(userEmail, otpCode, userName)
                                        },
                                        modifier = Modifier.fillMaxWidth().height(56.dp),
                                        colors =
                                                ButtonDefaults.buttonColors(
                                                        containerColor = PrimaryIndigo
                                                ),
                                        enabled =
                                                otpCode.length ==
                                                        6 // Only enable if they typed 6 digits
                                ) { Text("VERIFY & LOGIN") }
                        }
                } else {
                        // Otherwise, show the normal Swipeable Pages
                        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page
                                ->
                                when (page) {
                                        0 -> WelcomePage()
                                        1 -> HowItWorksPage()
                                        2 ->
                                                AccountCreationPage(
                                                        name = userName,
                                                        onNameChange = { userName = it },
                                                        email = userEmail,
                                                        onEmailChange = { userEmail = it },
                                                        password = userPassword,
                                                        onPasswordChange = { userPassword = it },
                                                        isLoginMode = isLoginMode,
                                                        onToggleLoginMode = {
                                                                isLoginMode = !isLoginMode
                                                        } // Flips the switch!
                                                )
                                }
                        }

                        // The Bottom Section (Indicators and Button)
                        Column(
                                modifier = Modifier.fillMaxWidth().padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                                // Custom Page Indicators
                                Row(horizontalArrangement = Arrangement.Center) {
                                        repeat(3) { index ->
                                                val isSelected = pagerState.currentPage == index
                                                Box(
                                                        modifier =
                                                                Modifier.padding(horizontal = 4.dp)
                                                                        .height(8.dp)
                                                                        .width(
                                                                                if (isSelected)
                                                                                        24.dp
                                                                                else 8.dp
                                                                        )
                                                                        .clip(CircleShape)
                                                                        .background(
                                                                                if (isSelected)
                                                                                        PrimaryIndigo
                                                                                else
                                                                                        Color(
                                                                                                0xFFE0E0E0
                                                                                        )
                                                                        )
                                                )
                                        }
                                }

                                Spacer(modifier = Modifier.height(24.dp))

                                Button(
                                        onClick = {
                                                if (pagerState.currentPage < 2) {
                                                        coroutineScope.launch {
                                                                pagerState.animateScrollToPage(
                                                                        pagerState.currentPage + 1
                                                                )
                                                        }
                                                } else {
                                                        // Check if they are logging in or signing
                                                        // up!
                                                        if (isLoginMode) {
                                                                if (userEmail.isNotBlank() &&
                                                                                userPassword
                                                                                        .isNotBlank()
                                                                ) {
                                                                        viewModel.login(
                                                                                userEmail,
                                                                                userPassword
                                                                        ) // Trigger Login!
                                                                }
                                                        } else {
                                                                if (userEmail.isNotBlank() &&
                                                                                userPassword
                                                                                        .length >= 6
                                                                ) {
                                                                        viewModel.signUp(
                                                                                userEmail,
                                                                                userPassword,
                                                                                userName
                                                                        ) // Trigger Signup!
                                                                }
                                                        }
                                                }
                                        },
                                        modifier = Modifier.fillMaxWidth().height(56.dp),
                                        shape = RoundedCornerShape(16.dp),
                                        colors =
                                                ButtonDefaults.buttonColors(
                                                        containerColor = PrimaryIndigo
                                                ),
                                        enabled = authState !is AuthState.Loading
                                ) {
                                        Text(
                                                text =
                                                        when {
                                                                authState is AuthState.Loading ->
                                                                        "PLEASE WAIT..."
                                                                pagerState.currentPage == 2 ->
                                                                        if (isLoginMode) "LOG IN"
                                                                        else "START LEARNING"
                                                                else -> "CONTINUE"
                                                        },
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold
                                        )
                                }
                        }
                }
        }
}

// --- INDIVIDUAL PAGE DESIGNS ---

@Composable
fun WelcomePage() {
        Column(
                modifier = Modifier.fillMaxSize().padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
        ) {
                Box(
                        modifier = Modifier.size(100.dp).background(Color(0xFFF0F0FF), CircleShape),
                        contentAlignment = Alignment.Center
                ) {
                        Text("✨", fontSize = 40.sp) // You can replace with a real icon later
                }
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                        "Welcome to TradeLearn",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF1A1A1A)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                        "Master Crypto Trading and Technical Analysis through bite-sized, interactive lessons.",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                )
        }
}

@Composable
fun HowItWorksPage() {
        Column(
                modifier = Modifier.fillMaxSize().padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
        ) {
                Text(
                        "How it Works",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF1A1A1A)
                )
                Spacer(modifier = Modifier.height(32.dp))

                FeatureCard("🧠", "Learn Crypto Concepts easily")
                Spacer(modifier = Modifier.height(16.dp))
                FeatureCard("✨", "Interact with 3D cards & quizzes")
                Spacer(modifier = Modifier.height(16.dp))
                FeatureCard("🏆", "Earn XP, streak, and rank up")
        }
}

@Composable
fun FeatureCard(emoji: String, text: String) {
        Card(
                modifier = Modifier.fillMaxWidth().height(72.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA)),
                elevation = CardDefaults.cardElevation(0.dp)
        ) {
                Row(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                ) {
                        Text(emoji, fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A1A))
                }
        }
}

@Composable
fun AccountCreationPage(
        name: String,
        onNameChange: (String) -> Unit,
        email: String,
        onEmailChange: (String) -> Unit,
        password: String,
        onPasswordChange: (String) -> Unit,
        isLoginMode: Boolean, // NEW: Tells the UI if we are logging in or signing up
        onToggleLoginMode: () -> Unit // NEW: The action when they click the toggle button
) {
        Column(
                modifier = Modifier.fillMaxSize().padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
        ) {
                // Dynamically change title based on mode
                Text(
                        text = if (isLoginMode) "Welcome Back" else "Create Account",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF1A1A1A)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                        text =
                                if (isLoginMode) "Log in to continue learning."
                                else "Save your XP and rank up.",
                        fontSize = 16.sp,
                        color = Color.Gray
                )
                Spacer(modifier = Modifier.height(32.dp))

                // Only show the Name field if they are SIGNING UP
                if (!isLoginMode) {
                        OutlinedTextField(
                                value = name,
                                onValueChange = onNameChange,
                                placeholder = { Text("Display Name", color = Color.LightGray) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors =
                                        OutlinedTextFieldDefaults.colors(
                                                focusedTextColor = Color(0xFF1A1A1A),
                                                unfocusedTextColor = Color(0xFF1A1A1A),
                                                focusedBorderColor = PrimaryIndigo,
                                                unfocusedBorderColor =
                                                        PrimaryIndigo.copy(alpha = 0.5f)
                                        ),
                                singleLine = true
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                }

                OutlinedTextField(
                        value = email,
                        onValueChange = onEmailChange,
                        placeholder = { Text("Email address", color = Color.LightGray) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors =
                                OutlinedTextFieldDefaults.colors(
                                        focusedTextColor = Color(0xFF1A1A1A),
                                        unfocusedTextColor = Color(0xFF1A1A1A),
                                        focusedBorderColor = PrimaryIndigo,
                                        unfocusedBorderColor = PrimaryIndigo.copy(alpha = 0.5f)
                                ),
                        singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                        value = password,
                        onValueChange = onPasswordChange,
                        placeholder = { Text("Password", color = Color.LightGray) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors =
                                OutlinedTextFieldDefaults.colors(
                                        focusedTextColor = Color(0xFF1A1A1A),
                                        unfocusedTextColor = Color(0xFF1A1A1A),
                                        focusedBorderColor = PrimaryIndigo,
                                        unfocusedBorderColor = PrimaryIndigo.copy(alpha = 0.5f)
                                ),
                        singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                // The Toggle Button!
                TextButton(onClick = onToggleLoginMode) {
                        Text(
                                text =
                                        if (isLoginMode) "Don't have an account? Sign Up"
                                        else "Already have an account? Log In",
                                color = PrimaryIndigo,
                                fontWeight = FontWeight.Bold
                        )
                }
        }
}
