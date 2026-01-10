package br.com.giovanne.reps.data

data class Training(
    val name: String = "",
    val color: Long = 0, // Default color
    val times: List<Long> = emptyList(),
    val exercises: List<Exercise> = emptyList(),
    val current: Boolean = false,
    val order: Int = 0
)
