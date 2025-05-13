package com.samantha.burnboard.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.samantha.burnboard.navigation.ROUT_ABOUT
import com.samantha.burnboard.navigation.ROUT_LOGIN

// FitLog Theme Colors
private val BackgroundColor = Color(0xFF121212)
private val CardColor = Color(0xFF1F1F1F)
private val AccentColor = Color(0xFF00BFA5)
private val TextColor = Color.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    var darkModeEnabled by remember { mutableStateOf(false) }
    var notificationsEnabled by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Settings", color = TextColor)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CardColor)
            )
        },
        containerColor = BackgroundColor
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SettingsItem(icon = Icons.Default.Person, title = "Profile") {
                navController.navigate("profile")
            }

            SettingsSwitchItem(
                icon = Icons.Default.Notifications,
                title = "Notifications",
                isChecked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )

            SettingsSwitchItem(
                icon = Icons.Default.DarkMode,
                title = "Dark Mode",
                isChecked = darkModeEnabled,
                onCheckedChange = { darkModeEnabled = it }
            )

            SettingsItem(icon = Icons.Default.Info, title = "About") {
                navController.navigate(ROUT_ABOUT)
            }

            SettingsItem(icon = Icons.Default.Logout, title = "Logout") {
                navController.navigate(ROUT_LOGIN)
            }
        }
    }
}

@Composable
fun SettingsItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        color = CardColor,
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 3.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = AccentColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = title,
                color = TextColor,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun SettingsSwitchItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = CardColor,
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 3.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = AccentColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = title,
                    color = TextColor,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = AccentColor,
                    checkedTrackColor = AccentColor.copy(alpha = 0.5f),
                    uncheckedThumbColor = Color.Gray
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(navController = rememberNavController())
}
