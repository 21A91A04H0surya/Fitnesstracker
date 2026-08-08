package com.hanuman.strengthtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import com.hanuman.strengthtracker.navigation.AppNavigation
import com.hanuman.strengthtracker.ui.ExerciseViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: ExerciseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StrengthTrackerApp(viewModel)
        }
    }
}

@Composable
fun StrengthTrackerApp(viewModel: ExerciseViewModel) {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AppNavigation(viewModel = viewModel)
        }
    }
}
