package br.com.giovanne.reps.data

import com.google.firebase.firestore.Exclude

data class Exercise(
    var name: String = "",
    var series: Int = 0,
    var repetitions: Int = 0,
    var categories: List<String> = emptyList(),
    var order: Int = 0,
    @get:Exclude
    var load: Float = 0f,
    @get:Exclude
    var note: String = "",
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
