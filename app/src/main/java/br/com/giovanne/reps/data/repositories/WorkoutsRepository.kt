package br.com.giovanne.reps.data.repositories

import br.com.giovanne.reps.data.Workout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WorkoutsRepository @Inject constructor() {

    suspend fun getUserWorkouts(): List<Workout> {
        val user = FirebaseAuth.getInstance().currentUser ?: return emptyList()
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("users").document(user.uid).collection("workouts")
                .orderBy("order")
                .get()
                .await()
                .toObjects(Workout::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun addWorkout(workout: Workout) {
        val user = FirebaseAuth.getInstance().currentUser ?: return
        val db = FirebaseFirestore.getInstance()
        try {
            db.collection("users").document(user.uid).collection("workouts")
                .add(workout)
                .await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun deleteWorkout(workoutId: String) {
        val user = FirebaseAuth.getInstance().currentUser ?: return
        val db = FirebaseFirestore.getInstance()
        val workoutsRef = db.collection("users").document(user.uid).collection("workouts")

        try {
            val snapshot = workoutsRef.orderBy("order").get().await()
            val batch = db.batch()
            val docToDelete = workoutsRef.document(workoutId)
            batch.delete(docToDelete)

            // Reorder remaining workouts
            val remainingDocs = snapshot.documents.filter { it.id != workoutId }
            remainingDocs.forEachIndexed { index, doc ->
                val isFirst = index == 0
                batch.update(doc.reference, mapOf(
                    "order" to index,
                    "current" to isFirst
                ))
            }
            batch.commit().await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getLastWorkoutOrder(): Int {
        val user = FirebaseAuth.getInstance().currentUser ?: return 0
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("users").document(user.uid).collection("workouts")
                .orderBy("order", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .await()
                .firstOrNull()
                ?.getLong("order")
                ?.toInt() ?: -1
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }
    }
}
