package com.hanuman.strengthtracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    // ---- Exercises ----

    @Insert
    suspend fun insertExercise(exercise: Exercise): Long

    @Query("SELECT * FROM exercises ORDER BY name ASC")
    fun getAllExercises(): Flow<List<Exercise>>

    @Query("SELECT * FROM exercises WHERE id = :exerciseId")
    suspend fun getExerciseById(exerciseId: Long): Exercise?

    // ---- Sets ----

    @Insert
    suspend fun insertSet(set: ExerciseSet): Long

    @Query("SELECT * FROM exercise_sets WHERE exerciseId = :exerciseId ORDER BY loggedAt DESC")
    fun getSetsForExercise(exerciseId: Long): Flow<List<ExerciseSet>>

    // Personal record = heaviest CLEAN set logged for this exercise.
    // Clean reps only count toward a true PR, since sloppy form reps aren't a fair max.
    @Query(
        "SELECT * FROM exercise_sets WHERE exerciseId = :exerciseId AND isClean = 1 " +
        "ORDER BY weightKg DESC, reps DESC LIMIT 1"
    )
    fun getPersonalRecord(exerciseId: Long): Flow<ExerciseSet?>
}
