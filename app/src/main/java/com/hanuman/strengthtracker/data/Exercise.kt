package com.hanuman.strengthtracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents one exercise the user tracks, e.g. "Bench Press", "Pull Up".
 */
@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val createdAt: Long = System.currentTimeMillis()
)
