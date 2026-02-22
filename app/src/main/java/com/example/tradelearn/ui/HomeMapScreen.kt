package com.example.tradelearn.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tradelearn.data.Chapter
import com.example.tradelearn.ui.theme.PrimaryIndigo

@Composable
fun HomeMapScreen(viewModel: HomeViewModel = viewModel(), onChapterClick: (Int) -> Unit) {
        // 1. Listen to BOTH tables from Supabase
        val modules by viewModel.modules.collectAsState()
        val chapters by viewModel.chapters.collectAsState()

        LazyColumn(
                modifier =
                        Modifier.fillMaxSize()
                                .background(Color(0xFFFAFAFA))
                                .padding(horizontal = 20.dp),
                contentPadding = PaddingValues(top = 40.dp, bottom = 100.dp)
        ) {
                // Main Screen Header
                item {
                        Text(
                                text = "Your Learning Path",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF1A1A1A)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                                text = "Master Crypto Trading, one byte at a time.",
                                fontSize = 16.sp,
                                color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                }

                // 2. Loop through the Modules
                modules.forEach { module ->
                        // Module Header
                        item {
                                Text(
                                        text =
                                                module.title
                                                        .uppercase(), // e.g., "CRYPTO FOUNDATIONS"
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = PrimaryIndigo,
                                        modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
                                )
                        }

                        // 3. Filter chapters to only show ones belonging to THIS module
                        val moduleChapters = chapters.filter { it.moduleId == module.id }

                        items(moduleChapters) { chapter ->
                                // For testing: Only Module 1 is unlocked
                                val isUnlocked = chapter.moduleId == 1

                                ChapterCard(
                                        chapter = chapter,
                                        isUnlocked = isUnlocked,
                                        onClick = { onChapterClick(chapter.id) }
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                        }
                }
        }
}

// 4. The Chapter Card (Cleaned up since we don't need the Module ID text inside it anymore)
@Composable
fun ChapterCard(chapter: Chapter, isUnlocked: Boolean, onClick: () -> Unit) {
        val containerColor = if (isUnlocked) Color.White else Color(0xFFF8F9FA)
        val textColor = if (isUnlocked) Color(0xFF1A1A1A) else Color(0xFFA0A0A0)
        val iconBgColor = if (isUnlocked) PrimaryIndigo.copy(alpha = 0.1f) else Color(0xFFEAEAEA)
        val iconColor = if (isUnlocked) PrimaryIndigo else Color(0xFFA0A0A0)
        val xpColor = if (isUnlocked) Color(0xFFE68A00) else Color(0xFFA0A0A0)

        Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = containerColor),
                border = BorderStroke(1.dp, Color(0xFFEFEFEF)),
                elevation =
                        CardDefaults.cardElevation(
                                defaultElevation = if (isUnlocked) 2.dp else 0.dp
                        ),
                modifier =
                        Modifier.fillMaxWidth()
                                .clip(RoundedCornerShape(20.dp))
                                .clickable(enabled = isUnlocked, onClick = onClick)
        ) {
                Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                ) {
                        Box(
                                modifier =
                                        Modifier.size(56.dp).background(iconBgColor, CircleShape),
                                contentAlignment = Alignment.Center
                        ) {
                                Icon(
                                        imageVector =
                                                if (isUnlocked) Icons.Default.PlayArrow
                                                else Icons.Default.Lock,
                                        contentDescription = null,
                                        tint = iconColor,
                                        modifier = Modifier.size(28.dp)
                                )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                                Text(
                                        text = chapter.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = textColor
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                                text = "LESSON",
                                                color = Color.Gray,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                                text = "  •  ",
                                                color = Color.LightGray,
                                                fontSize = 12.sp
                                        )
                                        Text(
                                                text = "${chapter.xpReward} XP",
                                                color = xpColor,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold
                                        )
                                }
                        }
                }
        }
}
