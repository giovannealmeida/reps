package br.com.giovanne.reps.data.repositories

import br.com.giovanne.reps.data.Workout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
        try {
            db.collection("users").document(user.uid).collection("workouts")
                .document(workoutId)
                .delete()
                .await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
