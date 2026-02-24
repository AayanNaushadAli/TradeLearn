package com.example.tradelearn.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Module(
        val id: Int,
        val title: String,
        val description: String,
        @SerialName("display_order") val displayOrder: Int
)

@Serializable
data class Chapter(
        val id: Int,
        @SerialName("module_id") val moduleId: Int,
        val title: String,
        @SerialName("display_order") val displayOrder: Int,
        @SerialName("xp_reward") val xpReward: Int
)

// 1. Represents a single row in your Supabase 'lessons' table
@Serializable
data class LessonRow(
        val id: Int,
        @SerialName("chapter_id") val chapterId: Int,
        @SerialName("content_payload") val contentPayload: List<LessonItem>
)

// 2. The Sealed Class handling the different types of UI cards
@Serializable
sealed class LessonItem {
        abstract val id: String
}

@Serializable
@SerialName("theory_block")
data class TheoryBlock(
        override val id: String,
        val title: String,
        val content: String,
        @SerialName("image_asset") val imageAsset: String
) : LessonItem()

@Serializable
@SerialName("multiple_choice")
data class MultipleChoice(
        override val id: String,
        val question: String,
        val options: List<Option>,
        val explanation: String
) : LessonItem()

@Serializable
@SerialName("true_false")
data class TrueFalse(
        override val id: String,
        val question: String,
        val options: List<Option>,
        val explanation: String
) : LessonItem()

@Serializable
@SerialName("flashcard")
data class Flashcard(override val id: String, val question: String, val explanation: String) :
        LessonItem()

// The sub-model for quiz options
@Serializable
data class Option(
        val id: String,
        val text: String,
        @SerialName("is_correct") val isCorrect: Boolean
)

@Serializable
data class UserProfile(
        val id: String,
        val username: String? = "Trader",
        @SerialName("total_xp") val totalXp: Int = 0,
        @SerialName("current_streak") val currentStreak: Int = 0,
        val hearts: Int = 5
)
