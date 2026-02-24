package com.example.tradelearn.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tradelearn.ui.theme.PrimaryIndigo

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = viewModel()) {
        // Listen to the database!
        val profile by viewModel.userProfile.collectAsState()

        Column(
                modifier =
                        Modifier.fillMaxSize()
                                .background(Color(0xFFFAFAFA)) // Off-white background
                                .padding(horizontal = 20.dp)
                                .statusBarsPadding(), // Breathing room at the top
        ) {
                Spacer(modifier = Modifier.height(16.dp))

                // 1. Top Header: Name and Level
                Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                ) {
                        Text(
                                text = "Hi, ${profile?.username ?: "Loading..."}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF1A1A1A)
                        )

                        // Level Badge
                        Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Level",
                                        tint = Color(0xFFE68A00), // Orange star
                                        modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                        text = "Lvl 1",
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFE68A00)
                                )
                        }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // 2. Progress Title & Rank Badge
                Text(
                        text = "${profile?.username ?: "Your"} progress so far",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1A1A)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                        color = Color(0xFFE0E0E0), // Gray pill
                        shape = CircleShape
                ) {
                        Text(
                                text = "Novice",
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                                fontWeight = FontWeight.Medium,
                                color = Color.DarkGray
                        )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // 3. Stats Grid (2x2)
                Row(modifier = Modifier.fillMaxWidth()) {
                        StatCard(
                                modifier = Modifier.weight(1f),
                                icon = Icons.Default.CheckCircle,
                                iconTint = Color(0xFF00C853), // Green
                                value = "100%",
                                label = "Accuracy"
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        StatCard(
                                modifier = Modifier.weight(1f),
                                icon = Icons.Default.ThumbUp,
                                iconTint = Color(0xFFFFB300), // Yellow/Orange
                                value = "${profile?.currentStreak ?: 0} Days",
                                label = "Streak"
                        )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                        StatCard(
                                modifier = Modifier.weight(1f),
                                icon = Icons.Default.Star,
                                iconTint = PrimaryIndigo,
                                value = "${profile?.totalXp ?: 0}",
                                label = "Total XP"
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        StatCard(
                                modifier = Modifier.weight(1f),
                                icon = Icons.Default.Info,
                                iconTint = PrimaryIndigo,
                                value = "0",
                                label = "Lessons"
                        )
                }

                Spacer(modifier = Modifier.height(40.dp))

                // 4. Completed Modules Section
                Text(
                        text = "Completed Modules",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4A4A4A)
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Empty State
                Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                ) {
                        Box(
                                modifier =
                                        Modifier.size(64.dp)
                                                .background(Color(0xFFE0E0E0), CircleShape),
                                contentAlignment = Alignment.Center
                        ) {
                                Icon(
                                        Icons.Default.Info,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(32.dp)
                                )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                                text = "No Lessons Completed Yet",
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray
                        )
                        Text(
                                text = "Start learning to earn XP and rank up!",
                                color = Color.LightGray,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                        )
                }
        }
}

// Custom Composable for the rounded Stat Cards
@Composable
fun StatCard(
        modifier: Modifier = Modifier,
        icon: ImageVector,
        iconTint: Color,
        value: String,
        label: String
) {
        Card(
                modifier = modifier.height(80.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
                Row(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                ) {
                        // Faded background behind the icon
                        Box(
                                modifier =
                                        Modifier.size(40.dp)
                                                .clip(CircleShape)
                                                .background(iconTint.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                        ) { Icon(imageVector = icon, contentDescription = null, tint = iconTint) }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                                Text(
                                        text = value,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 18.sp,
                                        color = Color(0xFF1A1A1A)
                                )
                                Text(text = label, color = Color.Gray, fontSize = 12.sp)
                        }
                }
        }
}
