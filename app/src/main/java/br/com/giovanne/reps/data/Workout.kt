package br.com.giovanne.reps.data

import com.google.firebase.firestore.DocumentId

data class Workout(
    @DocumentId var id: String = "",
    var name: String = "",
    var color: Long = 0, // Default color
    var times: List<Long> = emptyList(),
    var exercises: List<Exercise> = emptyList(),
    var current: Boolean = false,
    var order: Int = 0
)
