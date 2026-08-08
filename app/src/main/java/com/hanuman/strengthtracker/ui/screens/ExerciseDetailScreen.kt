package com.hanuman.strengthtracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hanuman.strengthtracker.data.Exercise
import com.hanuman.strengthtracker.data.ExerciseSet
import com.hanuman.strengthtracker.ui.ExerciseViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailScreen(
    exerciseId: Long,
    viewModel: ExerciseViewModel,
    onBack: () -> Unit
) {
    var exercise by remember { mutableStateOf<Exercise?>(null) }
    LaunchedEffect(exerciseId) {
        exercise = viewModel.getExerciseById(exerciseId)
    }

    val sets by viewModel.getSetsForExercise(exerciseId).collectAsState(initial = emptyList())
    val pr by viewModel.getPersonalRecord(exerciseId).collectAsState(initial = null)

    var reps by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var isClean by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(exercise?.name ?: "Exercise") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Back") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Personal Record card
            PrCard(pr)

            Spacer(modifier = Modifier.height(16.dp))
            Text("Log a set", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = reps,
                    onValueChange = { reps = it.filter { c -> c.isDigit() } },
                    label = { Text("Reps") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it.filter { c -> c.isDigit() || c == '.' } },
                    label = { Text("Weight (kg)") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = isClean, onCheckedChange = { isClean = it })
                Text("Clean reps (good form)")
            }

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    val repsInt = reps.toIntOrNull()
                    val weightDouble = weight.toDoubleOrNull()
                    if (repsInt != null && repsInt > 0 && weightDouble != null) {
                        viewModel.logSet(exerciseId, repsInt, weightDouble, isClean)
                        reps = ""
                        weight = ""
                        isClean = true
                    }
                },
                enabled = reps.toIntOrNull() != null && weight.toDoubleOrNull() != null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Log Set")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("History", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                items(sets) { set -> SetRow(set) }
            }
        }
    }
}

@Composable
private fun PrCard(pr: ExerciseSet?) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Personal Record", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.height(4.dp))
            if (pr == null) {
                Text("No clean sets logged yet")
            } else {
                Text(
                    "${pr.weightKg} kg × ${pr.reps} reps",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun SetRow(set: ExerciseSet) {
    val dateFormat = remember { SimpleDateFormat("MMM d, h:mm a", Locale.getDefault()) }
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("${set.weightKg} kg × ${set.reps} reps")
                Text(
                    dateFormat.format(Date(set.loggedAt)),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                if (set.isClean) "Clean" else "Not Clean",
                color = if (set.isClean) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
            )
        }
    }
}
