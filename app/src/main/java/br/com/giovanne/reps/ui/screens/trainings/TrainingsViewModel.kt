package br.com.giovanne.reps.ui.screens.trainings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.giovanne.reps.data.Training
import br.com.giovanne.reps.data.repositories.TrainingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainingsViewModel @Inject constructor(
    private val trainingsRepository: TrainingsRepository
) : ViewModel() {

    private val _userTrainings = MutableStateFlow<List<Training>>(emptyList())
    val userTrainings: StateFlow<List<Training>> = _userTrainings.asStateFlow()

    init {
        loadUserTrainings()
    }

    fun reloadUserTrainings() {
        loadUserTrainings()
    }

    fun deleteTraining(trainingId: String) {
        viewModelScope.launch {
            trainingsRepository.deleteTraining(trainingId)
            loadUserTrainings()
        }
    }

    private fun loadUserTrainings() {
        viewModelScope.launch {
            _userTrainings.value = trainingsRepository.getUserTrainings()
        }
    }
}
