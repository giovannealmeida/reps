package br.com.giovanne.reps.ui.screens.trainings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.giovanne.reps.data.Exercise
import br.com.giovanne.reps.data.Training
import br.com.giovanne.reps.data.repositories.ExerciseRepository
import br.com.giovanne.reps.data.repositories.TrainingsRepository
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
class NewTrainingViewModel @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    private val trainingsRepository: TrainingsRepository
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

    fun saveTraining(training: Training?, exercises: List<Exercise>) {
        if (training == null) {
            _uiState.value = UiState.Error("Escolha um treino")
            return
        }

        if (exercises.isEmpty()) {
            _uiState.value = UiState.Error("Adicione pelo menos 1 exercício")
            return
        }

        viewModelScope.launch {
            try {
                _uiState.value = UiState.Saving
                trainingsRepository.addTraining(training.copy(exercises = exercises))
                _uiState.value = UiState.Success
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to save training")
            }
        }
    }
    
    fun resetUiState() {
        _uiState.value = UiState.Idle
    }
}
