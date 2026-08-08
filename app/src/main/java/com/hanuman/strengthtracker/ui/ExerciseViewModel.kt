package com.hanuman.strengthtracker.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hanuman.strengthtracker.data.AppDatabase
import com.hanuman.strengthtracker.data.Exercise
import com.hanuman.strengthtracker.data.ExerciseRepository
import com.hanuman.strengthtracker.data.ExerciseSet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ExerciseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ExerciseRepository

    init {
        val dao = AppDatabase.getInstance(application).exerciseDao()
        repository = ExerciseRepository(dao)
    }

    val allExercises: Flow<List<Exercise>> = repository.getAllExercises()

    fun addExercise(name: String) {
        if (name.isBlank()) return
        viewModelScope.launch {
            repository.addExercise(name.trim())
        }
    }

    fun getSetsForExercise(exerciseId: Long): Flow<List<ExerciseSet>> =
        repository.getSetsForExercise(exerciseId)

    fun getPersonalRecord(exerciseId: Long): Flow<ExerciseSet?> =
        repository.getPersonalRecord(exerciseId)

    fun logSet(exerciseId: Long, reps: Int, weightKg: Double, isClean: Boolean) {
        viewModelScope.launch {
            repository.logSet(exerciseId, reps, weightKg, isClean)
        }
    }

    suspend fun getExerciseById(exerciseId: Long): Exercise? =
        repository.getExerciseById(exerciseId)
}
