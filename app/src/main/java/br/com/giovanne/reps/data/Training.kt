package br.com.giovanne.reps.data

import com.google.firebase.firestore.DocumentId

data class Training(
    @DocumentId val id: String = "",
    val name: String = "",
    val color: Long = 0, // Default color
    val times: List<Long> = emptyList(),
    val exercises: List<Exercise> = emptyList(),
    val current: Boolean = false,
    val order: Int = 0
)
