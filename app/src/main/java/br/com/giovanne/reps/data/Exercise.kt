package br.com.giovanne.reps.data

import com.google.firebase.firestore.DocumentReference

data class Exercise(
    val name: String = "",
    val series: Int = 0,
    val repetitions: Int = 0,
    val load: Float = 0f,
    val note: String = "",
    val categories: List<ExerciseCategory> = emptyList()
) {
    fun getFormattedReps(): String {
        return "$series x $repetitions"
    }

    fun getFormattedLoad(): String {
        return "$load kg"
    }
}
