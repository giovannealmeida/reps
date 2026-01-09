package br.com.giovanne.reps.data.dto

import com.google.firebase.firestore.DocumentReference

data class TrainingDTO(
    val name: String = "",
    val color: Long = 0,
    val times: List<Long> = emptyList(),
    val exercises: List<DocumentReference> = emptyList(),
    val current: Boolean = false,
    val order: Int = 0
)