package br.com.giovanne.reps.data

import com.google.firebase.firestore.Exclude

data class Exercise(
    val name: String = "",
    val series: Int = 0,
    val repetitions: Int = 0,
    val categories: List<String> = emptyList(),
    @get:Exclude
    val load: Float = 0f,
    @get:Exclude
    val note: String = "",
) {
    @Exclude
    fun getFormattedReps(): String {
        return "$series x $repetitions"
    }
    @Exclude
    fun getFormattedLoad(): String {
        return "$load kg"
    }
}
