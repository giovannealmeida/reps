package br.com.giovanne.reps.data.repositories

import br.com.giovanne.reps.data.Training
import br.com.giovanne.reps.data.dto.TrainingDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TrainingsRepository @Inject constructor() {
    suspend fun getTrainings(): List<Training> {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("trainings").orderBy("name").get().await().toObjects(TrainingDTO::class.java)
                .map { dto ->
                    Training(
                        name = dto.name,
                        color = dto.color
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

    suspend fun getUserTrainings(): List<Training> {
        val user = FirebaseAuth.getInstance().currentUser ?: return emptyList()
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("users").document(user.uid).collection("trainings")
                .orderBy("order")
                .get()
                .await()
                .toObjects(TrainingDTO::class.java)
                .map { dto ->
                    Training(
                        name = dto.name,
                        color = dto.color,
                        times = dto.times,
                        current = dto.current,
                        order = dto.order
                    )
                }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun addTraining(training: Training) {
        val user = FirebaseAuth.getInstance().currentUser ?: return
        val db = FirebaseFirestore.getInstance()
        try {
            db.collection("users").document(user.uid).collection("trainings")
                .add(training)
                .await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
