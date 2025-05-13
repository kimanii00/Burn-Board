package com.samantha.burnboard.ui.screens.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.*
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Settings
import com.samantha.burnboard.navigation.ROUT_DASHBOARD

import com.samantha.burnboard.navigation.ROUT_SETTINGS
import com.samantha.burnboard.navigation.ROUT_WORKOUT

import kotlinx.coroutines.launch

// ==========================
// Room Database Components
// ==========================
@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val stepCount: Int
)

@Dao
interface WorkoutDao {
    @Insert
    suspend fun insertWorkout(workout: Workout)

    @Query("SELECT * FROM workouts ORDER BY id DESC LIMIT 1")
    suspend fun getLastWorkout(): Workout?
}

@Database(entities = [Workout::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "workout_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

// ==========================
// UI Constants
// ==========================
val Teal = Color(0xFF00BFA5)
val DeepBlue = Color(0xFF0D47A1)

// ==========================
// Main Composable
// ==========================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    var stepCount by remember { mutableStateOf(0) }

    val workoutDao = remember { AppDatabase.getDatabase(context).workoutDao() }
    val coroutineScope = rememberCoroutineScope()

    // Load previous workout
    LaunchedEffect(Unit) {
        val lastWorkout = workoutDao.getLastWorkout()
        lastWorkout?.let { stepCount = it.stepCount }
    }

    // Save workout
    fun saveWorkout() {
        val workout = Workout(stepCount = stepCount)
        coroutineScope.launch {
            workoutDao.insertWorkout(workout)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "BurnBoard",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = DeepBlue)
            )
        },
        bottomBar = {
            Surface(
                shadowElevation = 8.dp,
                color = DeepBlue,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Filled.Home, contentDescription = "Home", tint = Color.White)
                            Text("Home", color = Color.White, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                    IconButton(onClick = { navController.navigate(ROUT_WORKOUT) }) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Filled.FitnessCenter, contentDescription = "Workout", tint = Color.White)
                            Text("Workout", color = Color.White, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                    IconButton(onClick = { navController.navigate(ROUT_DASHBOARD) }) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Filled.Dashboard, contentDescription = "Dashboard", tint = Color.White)
                            Text("Dashboard", color = Color.White, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                    IconButton(onClick = { navController.navigate(ROUT_SETTINGS) }) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Filled.Settings, contentDescription = "Settings", tint = Color.White)
                            Text("Settings", color = Color.White, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        },




                content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF121212))
                    .padding(paddingValues)  // Use padding from Scaffold
                    .padding(16.dp),  // Optional extra padding
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Header Banner
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Brush.horizontalGradient(listOf(Teal, DeepBlue)))
                        .padding(24.dp)
                ) {
                    Column {
                        Text(
                            text = "Welcome to BurnBoard 👋",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Log. Track. Transform.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))




                // Step Summary Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Today's Step Count", style = MaterialTheme.typography.titleMedium, color = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "$stepCount steps",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.Cyan,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = stepCount / 10000f,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = Teal,
                            trackColor = Color.Gray
                        )
                    }
                }

// Weekly Summary Placeholder
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Weekly Summary", color = Color.White, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Coming soon: calories burned, active time, progress trends.", color = Color.Gray)
                    }
                }

// Motivational Tip
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF263238))
                ) {
                    Text(
                        text = "🔥 Tip of the Day: Consistency beats intensity. Keep moving daily!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )
                }

// Quick Start Button
                Button(
                    onClick = { navController.navigate(ROUT_WORKOUT) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Teal)
                ) {
                    Text("Start a Quick Workout", color = Color.White)
                }





                Spacer(modifier = Modifier.height(24.dp))

                // Footer Quote
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF1F1F1F),
                    tonalElevation = 4.dp
                ) {
                    Text(
                        text = "\"Fitness is not about being better than someone else... it's about being better than you used to be.\"",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFB0BEC5),
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }

                // Save Progress Button
                Button(
                    onClick = {
                        saveWorkout()
                        stepCount = 0
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
                ) {
                    Text("Save Progress", color = Color.White)
                }
            }
        }
    )
}

// ==========================
// Reusable Button Composable
// ==========================
@Composable
fun ActionButton(label: String, backgroundColor: Color, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        color = backgroundColor
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// ==========================
// Preview
// ==========================
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
