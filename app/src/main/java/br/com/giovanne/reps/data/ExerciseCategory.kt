package br.com.giovanne.reps.data

data class ExerciseCategory(
    val id: Int,
    val name: String
)

val mockCatPeito = ExerciseCategory(1, "Peito")
val mockCatOmbros = ExerciseCategory(2, "Ombros")
val mockCatTriceps = ExerciseCategory(3, "Tríceps")
val mockCatQuadriceps = ExerciseCategory(4, "Quadríceps")
val mockCatPosteriores = ExerciseCategory(5, "Posteriores")
val mockCatGluteos = ExerciseCategory(6, "Glúteos")
val mockCatCostas = ExerciseCategory(7, "Costas")
val mockCatBiceps = ExerciseCategory(8, "Bíceps")

fun mockCategories(): List<ExerciseCategory> {
    return listOf(
        mockCatPeito,
        mockCatOmbros,
        mockCatTriceps,
        mockCatQuadriceps,
        mockCatPosteriores,
        mockCatGluteos,
        mockCatCostas,
        mockCatBiceps
    )
}
