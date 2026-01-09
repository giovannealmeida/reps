package br.com.giovanne.reps.data.repositories

import br.com.giovanne.reps.data.Exercise
import br.com.giovanne.reps.data.ExerciseCategory
import br.com.giovanne.reps.data.dto.ExerciseDTO
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ExerciseRepository @Inject constructor() {
    suspend fun getExercises(): List<Exercise> {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("exercises").orderBy("name").get().await()
                .toObjects(ExerciseDTO::class.java)
                .map { dto ->
                    Exercise(
                        name = dto.name,
                        categories = dto.categories.map { categoryRef ->
                            categoryRef.get().await().toObject(ExerciseCategory::class.java)
                                ?: ExerciseCategory()
                        },
                    )
                }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}