package br.com.giovanne.reps.ui.screens.workouts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.giovanne.reps.data.Workout
import br.com.giovanne.reps.data.repositories.WorkoutsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutsViewModel @Inject constructor(
    private val workoutsRepository: WorkoutsRepository
) : ViewModel() {

    private val _userWorkouts = MutableStateFlow<List<Workout>>(emptyList())
    val userWorkouts: StateFlow<List<Workout>> = _userWorkouts.asStateFlow()

    init {
        loadUserWorkouts()
    }

    fun reloadUserWorkouts() {
        loadUserWorkouts()
    }

    fun deleteWorkout(workoutId: String) {
        viewModelScope.launch {
            workoutsRepository.deleteWorkout(workoutId)
            loadUserWorkouts()
        }
    }

    private fun loadUserWorkouts() {
        viewModelScope.launch {
            _userWorkouts.value = workoutsRepository.getUserWorkouts()
        }
    }
}
