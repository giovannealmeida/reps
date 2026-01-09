package br.com.giovanne.reps.data.dto

import com.google.firebase.firestore.DocumentReference

data class ExerciseDTO(
    val name: String = "",
    val series: Int = 0,
    val repetitions: Int = 0,
    val load: Float = 0f,
    val note: String = "",
    val categories: List<DocumentReference> = emptyList()
)