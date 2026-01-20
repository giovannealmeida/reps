package br.com.giovanne.reps.data.dto

import com.google.firebase.firestore.DocumentId

data class TrainingDTO(
    @DocumentId val id: String = "",
    val name: String = "",
    val color: Long = 0,
    val times: List<Long> = emptyList(),
    val exercises: List<ExerciseDTO> = emptyList(),
    val current: Boolean = false,
    val order: Int = 0
)