package br.com.giovanne.reps.data

data class Training(
    val name: String = "",
    val color: Long = 0, // Default color
    val times: List<Long> = emptyList(),
    val exercises: List<Exercise> = emptyList(),
    val current: Boolean = false,
    val order: Int = 0
)

fun userTrainingsMock(): List<Training> {
    val exercisesA = listOf(
        Exercise(
            name = "Supino Reto",
            series = 4,
            repetitions = 10,
            load = 80.0f,
            note = "Aumentar a carga na próxima semana.",
            categories = listOf(mockCatPeito, mockCatOmbros, mockCatTriceps)
        ),
        Exercise(
            name = "Crucifixo Inclinado",
            series = 3,
            repetitions = 12,
            load = 30.0f,
            note = "Focar na contração do peitoral.",
            categories = listOf(mockCatPeito)
        ),
        Exercise(
            name = "Desenvolvimento com Halteres",
            series = 4,
            repetitions = 8,
            load = 20.0f,
            note = "",
            categories = listOf(mockCatOmbros)
        ),
        Exercise(
            name = "Elevação Lateral",
            series = 3,
            repetitions = 15,
            load = 10.0f,
            note = "Movimento controlado.",
            categories = listOf(mockCatOmbros)
        )
    )

    val exercisesB = listOf(
        Exercise(
            name = "Agachamento Livre",
            series = 4,
            repetitions = 8,
            load = 100.0f,
            note = "Manter a postura.",
            categories = listOf(mockCatQuadriceps, mockCatGluteos, mockCatPosteriores)
        ),
        Exercise(
            name = "Leg Press 45",
            series = 3,
            repetitions = 10,
            load = 150.0f,
            note = "",
            categories = listOf(mockCatQuadriceps, mockCatGluteos)
        ),
        Exercise(
            name = "Cadeira Extensora",
            series = 3,
            repetitions = 15,
            load = 60.0f,
            note = "Pico de contração.",
            categories = listOf(mockCatQuadriceps),
        ),
        Exercise(
            name = "Mesa Flexora",
            series = 3,
            repetitions = 12,
            load = 50.0f,
            note = "Fase excêntrica lenta.",
            categories = listOf(mockCatPosteriores)
        )
    )

    val exercisesC = listOf(
        Exercise(
            name = "Barra Fixa",
            series = 4,
            repetitions = 5,
            load = 0.0f,
            note = "Usar peso do corpo. Tentar 6 na próxima.",
            categories = listOf(mockCatCostas, mockCatBiceps)
        ),
        Exercise(
            name = "Remada Curvada",
            series = 4,
            repetitions = 8,
            load = 60.0f,
            note = "",
            categories = listOf(mockCatCostas),
        ),
        Exercise(
            name = "Puxada Alta",
            series = 3,
            repetitions = 10,
            load = 55.0f,
            note = "Puxar com as costas.",
            categories = listOf(mockCatCostas),
        ),
        Exercise(
            name = "Rosca Direta",
            series = 3,
            repetitions = 10,
            load = 20.0f,
            note = "",
            categories = listOf(mockCatBiceps)
        )
    )

    return listOf(
        Training("A", 0xFF448AFF, listOf(630000L, 720000L, 810000L), exercisesA, true),
        Training("B", 0xFFFF5252, listOf(900000L, 1215000L), exercisesB),
        Training("C", 0xFF4CAF50, exercises = exercisesC),
    )
}
