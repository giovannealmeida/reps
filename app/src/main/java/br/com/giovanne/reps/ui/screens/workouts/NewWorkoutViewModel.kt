package br.com.giovanne.reps.ui.screens.workouts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.giovanne.reps.data.Exercise
import br.com.giovanne.reps.data.Workout
import br.com.giovanne.reps.data.repositories.ExerciseRepository
import br.com.giovanne.reps.data.repositories.WorkoutsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UiState {
    object Idle : UiState()
    object Saving : UiState()
    object Success : UiState()
    data class Error(val message: String) : UiState()
}

@HiltViewModel
class NewWorkoutViewModel @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    private val workoutsRepository: WorkoutsRepository
) : ViewModel() {

    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> = _exercises.asStateFlow()

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        loadExercises()
    }

    fun loadExercises() {
        viewModelScope.launch {
            try {
                _exercises.value = exerciseRepository.getExercises()
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to load exercises")
            }
        }
    }

    fun saveWorkout(workout: Workout?, exercises: List<Exercise>) {
        if (workout == null) {
            _uiState.value = UiState.Error("Escolha um workout")
            return
        }

        if (exercises.isEmpty()) {
            _uiState.value = UiState.Error("Adicione pelo menos 1 exercício")
            return
        }

        viewModelScope.launch {
            try {
                _uiState.value = UiState.Saving
                workout.apply {
                    this.exercises = exercises
                    this.order = workoutsRepository.getLastWorkoutOrder() + 1
                    this.current = this.order == 0
                }
                workoutsRepository.addWorkout(workout)
                _uiState.value = UiState.Success
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to save workout")
            }
        }
    }

    fun resetUiState() {
        _uiState.value = UiState.Idle
    }
}
