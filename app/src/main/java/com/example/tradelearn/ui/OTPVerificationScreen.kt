package com.example.tradelearn.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tradelearn.ui.theme.PrimaryIndigo

@Composable
fun OTPVerificationScreen(email: String, onVerifyClick: (String) -> Unit) {
    var otpCode by remember { mutableStateOf("") }

    Column(
            modifier =
                    Modifier.fillMaxSize()
                            .background(Color.White)
                            .statusBarsPadding()
                            .navigationBarsPadding()
                            .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
    ) {
        Text(
                text = "Verify your email",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF1A1A1A)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
                text = "Enter the 6-digit code we sent to\n$email",
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(48.dp))

        OutlinedTextField(
                value = otpCode,
                onValueChange = {
                    if (it.length <= 6) {
                        otpCode = it
                    }
                },
                placeholder = { Text("000000", color = Color.LightGray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors =
                        OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryIndigo,
                                unfocusedBorderColor = PrimaryIndigo.copy(alpha = 0.5f)
                        ),
                singleLine = true,
                textStyle =
                        LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center,
                                fontSize = 24.sp,
                                letterSpacing = 8.sp,
                                fontWeight = FontWeight.Bold
                        )
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
                onClick = { onVerifyClick(otpCode) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors =
                        ButtonDefaults.buttonColors(
                                containerColor =
                                        if (otpCode.length == 6) PrimaryIndigo else Color.LightGray
                        ),
                enabled = otpCode.length == 6
        ) { Text(text = "VERIFY", fontSize = 16.sp, fontWeight = FontWeight.Bold) }
    }
}
