package br.com.giovanne.reps.data

import br.com.giovanne.reps.data.dto.TrainingDTO
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

suspend fun getTrainings(): List<Training> {
    val db = FirebaseFirestore.getInstance()
    return try {
        db.collection("trainings").orderBy("order").get().await().toObjects(TrainingDTO::class.java)
            .map { dto ->
                Training(
                    name = dto.name,
                    color = dto.color,
                    times = dto.times,
                    current = dto.current,
                    order = dto.order
//                    exercises = firestoreTraining.exercises.map { exerciseRef ->
//                        exerciseRef.get().await().toObject(Exercise::class.java) ?: Exercise()
//                    },
                )
            }
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }
}
