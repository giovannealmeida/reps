package br.com.giovanne.reps.data

data class Training(
    val name: String,
    val color: Long,
    val times: List<Long> = listOf(),
    val exercises: List<Exercise> = listOf(),
    val isCurrent: Boolean = false
)

// Todo: move to database
val trainingsMock = listOf(
    Training("A", 0xFF448AFF),
    Training("B", 0xFFFF5252),
    Training("C", 0xFF4CAF50),
    Training("D", 0xFF03A9F4),
    Training("E", 0xFFE91E63),
    Training("F", 0xFF8BC34A),
    Training("G", 0xFF607D8B),
    Training("H", 0xFF3F51B5)
)
