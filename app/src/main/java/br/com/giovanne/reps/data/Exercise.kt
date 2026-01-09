package br.com.giovanne.reps.data

data class Exercise(
    val name: String,
    val series: Int,
    val repetitions: Int,
    val load: Float,
    val note: String
) {
    fun getFormattedReps(): String {
        return "$series x $repetitions"
    }

    fun getFormattedLoad(): String {
        return "$load kg"
    }
}
