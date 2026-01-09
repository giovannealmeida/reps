package br.com.giovanne.reps

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.giovanne.reps.ui.screens.history.HistoryScreen
import br.com.giovanne.reps.ui.screens.home.HomeScreen
import br.com.giovanne.reps.ui.screens.login.LoginScreen
import br.com.giovanne.reps.ui.screens.login.SignUpScreen
import br.com.giovanne.reps.ui.screens.profile.ProfileScreen
import br.com.giovanne.reps.ui.screens.trainings.TrainingsScreen
import com.example.compose.REPSTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    private val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (!task.isSuccessful) {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                    // Success is handled by the auth state listener
                }
        } catch (e: ApiException) {
            // Google Sign In failed, update UI appropriately
            Toast.makeText(baseContext, "Google sign in failed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        auth = Firebase.auth
        firestore = Firebase.firestore

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        setContent {
            REPSTheme {
                val navController = rememberNavController()
                val user by auth.authStateChanges().collectAsState(initial = auth.currentUser)

                val startDestination = if (user != null) "main" else "login"

                LaunchedEffect(user) {
                    val targetRoute = if (user != null) "main" else "login"
                    if (navController.currentBackStackEntry?.destination?.route?.startsWith(targetRoute) == false) {
                        navController.navigate(targetRoute) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    }
                }

                NavHost(navController = navController, startDestination = startDestination) {
                    composable("login") {
                        LoginScreen(
                            onLoginWithEmail = { email, password ->
                                auth.signInWithEmailAndPassword(email, password)
                                    .addOnFailureListener {
                                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                                    }
                            },
                            onLoginWithGoogle = {
                                val signInIntent = googleSignInClient.signInIntent
                                googleSignInLauncher.launch(signInIntent)
                            },
                            onLoginAnonymously = {
                                auth.signInAnonymously()
                                    .addOnFailureListener {
                                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                                    }
                            },
                            onNavigateToSignUp = {
                                navController.navigate("signup")
                            }
                        )
                    }
                    composable("signup") {
                        SignUpScreen(
                           onSignUp = { email, password, fullName ->
                                auth.createUserWithEmailAndPassword(email, password)
                                    .addOnSuccessListener { authResult ->
                                        val firebaseUser = authResult.user
                                        firebaseUser?.let {
                                            val userData = hashMapOf(
                                                "fullName" to fullName,
                                                "email" to email
                                            )
                                            firestore.collection("users").document(it.uid)
                                                .set(userData)
                                                .addOnFailureListener { e ->
                                                    Toast.makeText(baseContext, "Failed to save user data.", Toast.LENGTH_SHORT).show()
                                                }
                                        }
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(baseContext, "Sign up failed: ${e.message}", Toast.LENGTH_LONG).show()
                                    }
                            }
                        )
                    }
                    composable("main") {
                        REPSApp(onLogout = {
                            auth.signOut()
                            googleSignInClient.signOut()
                            // Navigation is handled by the auth state listener
                        })
                    }
                }
            }
        }
    }
}

fun FirebaseAuth.authStateChanges(): Flow<FirebaseUser?> = callbackFlow {
    val authStateListener = FirebaseAuth.AuthStateListener { auth ->
        trySend(auth.currentUser)
    }
    addAuthStateListener(authStateListener)
    awaitClose { removeAuthStateListener(authStateListener) }
}


@Composable
fun REPSApp(onLogout: () -> Unit) {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            it.icon,
                            contentDescription = stringResource(it.labelRes)
                        )
                    },
                    label = { Text(stringResource(it.labelRes)) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            when (currentDestination) {
                AppDestinations.HOME -> HomeScreen(modifier = Modifier.padding(innerPadding))
                AppDestinations.FAVORITES -> TrainingsScreen(modifier = Modifier.padding(innerPadding))
                AppDestinations.HISTORY -> HistoryScreen(modifier = Modifier.padding(innerPadding))
                AppDestinations.PROFILE -> ProfileScreen(
                    modifier = Modifier.padding(innerPadding),
                    onLogout = onLogout
                )
            }
        }
    }
}

enum class AppDestinations(
    val labelRes: Int,
    val icon: ImageVector,
) {
    HOME(R.string.destination_home, Icons.Default.Home),
    FAVORITES(R.string.destination_training, Icons.Default.Favorite),
    HISTORY(R.string.destination_history, Icons.Default.Favorite),
    PROFILE(R.string.destination_profile, Icons.Default.AccountBox),
}

@Composable
fun Date() {
    val currentDay = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern(
        "EEE dd/MM",
        Locale.forLanguageTag("pt-BR")
    )
    val formattedDate = currentDay.format(formatter).uppercase(Locale.getDefault())

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 36.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = formattedDate, fontSize = 32.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun ContentPreview() {
    REPSTheme {
        HomeScreen()
    }
}
