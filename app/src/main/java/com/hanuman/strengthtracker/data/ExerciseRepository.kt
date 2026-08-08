package com.hanuman.strengthtracker.data

import kotlinx.coroutines.flow.Flow

class ExerciseRepository(private val dao: ExerciseDao) {

    fun getAllExercises(): Flow<List<Exercise>> = dao.getAllExercises()

    suspend fun addExercise(name: String): Long =
        dao.insertExercise(Exercise(name = name))

    fun getSetsForExercise(exerciseId: Long): Flow<List<ExerciseSet>> =
        dao.getSetsForExercise(exerciseId)

    fun getPersonalRecord(exerciseId: Long): Flow<ExerciseSet?> =
        dao.getPersonalRecord(exerciseId)

    suspend fun logSet(exerciseId: Long, reps: Int, weightKg: Double, isClean: Boolean) {
        dao.insertSet(
            ExerciseSet(
                exerciseId = exerciseId,
                reps = reps,
                weightKg = weightKg,
                isClean = isClean
            )
        )
    }

    suspend fun getExerciseById(exerciseId: Long): Exercise? = dao.getExerciseById(exerciseId)
}
