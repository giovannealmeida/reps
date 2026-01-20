package br.com.giovanne.reps.data.repositories

import br.com.giovanne.reps.data.Exercise
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ExerciseRepository @Inject constructor() {
    suspend fun getExercises(): List<Exercise> {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("exercises").orderBy("name").get().await()
                .toObjects(Exercise::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}