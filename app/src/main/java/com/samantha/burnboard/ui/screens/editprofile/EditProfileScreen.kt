package com.samantha.burnboard.ui.screens.editprofile



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.samantha.burnboard.ui.screens.profile.AccentColor
import com.samantha.burnboard.ui.screens.profile.BackgroundColor
import com.samantha.burnboard.ui.screens.profile.CardColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavController) {
    var username by remember { mutableStateOf("Samantha") }
    var email by remember { mutableStateOf("samantha@gmail.com") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Edit Profile", color = AccentColor)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = AccentColor)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CardColor)
            )
        },
        containerColor = BackgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Username Field
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username", color = Color.LightGray) },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AccentColor,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = AccentColor
                )
            )

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = Color.LightGray) },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Color.Gray) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AccentColor,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = AccentColor
                )
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Save Button
            Button(
                onClick = {
                    // Save logic goes here
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AccentColor)
            ) {
                Icon(Icons.Default.Save, contentDescription = "Save", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Save Changes", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}
