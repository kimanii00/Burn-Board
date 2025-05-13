package com.samantha.burnboard.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.samantha.burnboard.R

// BurnBoard Theme Colors
val BackgroundColor = Color(0xFF121212)
val CardColor = Color(0xFF1F1F1F)
val AccentColor = Color(0xFF00BFA5)
private val TextColor = Color.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        containerColor = BackgroundColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        color = AccentColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CardColor)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Avatar
            Surface(
                shape = CircleShape,
                color = Color.Gray,
                modifier = Modifier.size(110.dp),
                tonalElevation = 4.dp
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            // Name & Email
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Samantha", color = TextColor, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("samantha@gmail.com", color = Color.LightGray, fontSize = 16.sp)
            }

            // Fitness Stats Card
            InfoCard(title = "Your Stats") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem("Workouts", "125")
                    StatItem("Calories", "58,300")

                }
            }

            // Edit Profile Section
            InfoCard(title = "Account Settings") {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Keep your profile up-to-date so your fitness insights remain personalized.",
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )
                    ProfileActionButton(
                        text = "Edit Profile",
                        icon = Icons.Default.Edit,
                        color = AccentColor
                    ) {
                        navController.navigate(com.samantha.burnboard.navigation.ROUTE_EDIT_PROFILE)
                    }

                }
            }
        }
    }
}

@Composable
fun InfoCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Surface(
        color = CardColor,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = 3.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, color = TextColor, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, color = AccentColor, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(label, color = Color.LightGray, fontSize = 14.sp)
    }
}

@Composable
fun ProfileActionButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        color = CardColor,
        shape = RoundedCornerShape(10.dp),
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = text, tint = color, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                color = color,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(navController = rememberNavController())
}
