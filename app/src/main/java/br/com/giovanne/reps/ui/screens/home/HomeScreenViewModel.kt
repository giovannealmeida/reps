package br.com.giovanne.reps.ui.screens.home

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
class HomeScreenViewModel @Inject constructor(
    private val trainingsRepository: TrainingsRepository
) : ViewModel() {

    private val _trainings = MutableStateFlow<List<Training>>(emptyList())
    val trainings: StateFlow<List<Training>> = _trainings.asStateFlow()

    init {
        loadUserTrainings()
    }

    private fun loadUserTrainings() {
        viewModelScope.launch {
            _trainings.value = trainingsRepository.getUserTrainings()
        }
    }

    fun reloadUserTrainings() {
        loadUserTrainings()
    }
}
