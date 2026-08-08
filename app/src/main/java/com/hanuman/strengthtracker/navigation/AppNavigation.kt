package com.hanuman.strengthtracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hanuman.strengthtracker.ui.ExerciseViewModel
import com.hanuman.strengthtracker.ui.screens.AddExerciseScreen
import com.hanuman.strengthtracker.ui.screens.ExerciseDetailScreen
import com.hanuman.strengthtracker.ui.screens.HomeScreen

private const val ROUTE_HOME = "home"
private const val ROUTE_ADD = "add_exercise"
private const val ROUTE_DETAIL = "exercise_detail/{exerciseId}"

@Composable
fun AppNavigation(viewModel: ExerciseViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ROUTE_HOME) {

        composable(ROUTE_HOME) {
            HomeScreen(
                viewModel = viewModel,
                onExerciseClick = { id -> navController.navigate("exercise_detail/$id") },
                onAddExerciseClick = { navController.navigate(ROUTE_ADD) }
            )
        }

        composable(ROUTE_ADD) {
            AddExerciseScreen(
                viewModel = viewModel,
                onDone = { navController.popBackStack() }
            )
        }

        composable(
            route = ROUTE_DETAIL,
            arguments = listOf(navArgument("exerciseId") { type = NavType.LongType })
        ) { backStackEntry ->
            val exerciseId = backStackEntry.arguments?.getLong("exerciseId") ?: 0L
            ExerciseDetailScreen(
                exerciseId = exerciseId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
