package com.example.test_task_3_in_a_row.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test_task_3_in_a_row.ui.shared.Header
import com.example.test_task_3_in_a_row.viewModels.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF160808)) // Темний фон
            .padding(top = 48.dp, start = 16.dp, end = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(money = state.coins, onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "SETTINGS",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.sp
        )
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .width(40.dp)
                .height(4.dp)
                .background(Color(0xFFC94236), RoundedCornerShape(50.dp))
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            SettingsToggleItem(
                icon = Icons.Default.Star,
                iconBgColor = Color(0xFFD64E4E),
                title = "Music",
                isChecked = state.isMusicEnabled,
                onCheckedChange = { viewModel.toggleMusic() }
            )
            SettingsToggleItem(
                icon = Icons.Default.ChangeHistory,
                iconBgColor = Color(0xFF4699C8),
                title = "Sound Effects",
                isChecked = state.isSoundEffectsEnabled,
                onCheckedChange = { viewModel.toggleSoundEffects() }
            )

            SettingsToggleItem(
                icon = Icons.Default.RadioButtonUnchecked,
                iconBgColor = Color(0xFF62B056),
                title = "Vibration",
                isChecked = state.isVibrationEnabled,
                onCheckedChange = { viewModel.toggleVibration() }
            )

            SettingsToggleItem(
                icon = Icons.Default.Star,
                iconBgColor = Color(0xFFE2B441),
                title = "Notifications",
                isChecked = state.isNotificationsEnabled,
                onCheckedChange = { viewModel.toggleNotifications() }
            )

            SettingsValueItem(
                icon = Icons.Default.Add,
                iconBgColor = Color(0xFFA57CF0),
                title = "Language",
                value = state.language,
                onClick = { viewModel.changeLanguage() }
            )

            SettingsActionItem(
                icon = Icons.Default.AutoAwesome,
                iconBgColor = Color(0xFFE7658B),
                title = "Restore Purchases",
                onClick = {  }
            )
        }
    }
}


@Composable
fun SettingsHeader(coins: Int, onBackClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color(0xFF220E0A), RoundedCornerShape(12.dp))
                .clickable { onBackClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }

        Row(
            modifier = Modifier
                .background(Color(0xFF220E0A), RoundedCornerShape(50.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFFFFF1A1), Color(0xFFDCA825))
                        ),
                        shape = RoundedCornerShape(50.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "$", color = Color(0xFF5A3E00), fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "%,d".format(coins),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun BaseSettingsItem(
    icon: ImageVector,
    iconBgColor: Color,
    title: String,
    modifier: Modifier = Modifier,
    trailingContent: @Composable () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF1E0C0A), RoundedCornerShape(20.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(iconBgColor, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        trailingContent()
    }
}

@Composable
fun SettingsToggleItem(
    icon: ImageVector,
    iconBgColor: Color,
    title: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    BaseSettingsItem(icon = icon, iconBgColor = iconBgColor, title = title) {
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF5A9E66),
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color(0xFF110504),
                uncheckedBorderColor = Color.Transparent
            )
        )
    }
}

@Composable
fun SettingsValueItem(
    icon: ImageVector,
    iconBgColor: Color,
    title: String,
    value: String,
    onClick: () -> Unit
) {
    BaseSettingsItem(
        icon = icon,
        iconBgColor = iconBgColor,
        title = title,
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = value,
            color = Color(0xFFE5B5A8),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SettingsActionItem(
    icon: ImageVector,
    iconBgColor: Color,
    title: String,
    onClick: () -> Unit
) {
    BaseSettingsItem(
        icon = icon,
        iconBgColor = iconBgColor,
        title = title,
        modifier = Modifier.clickable { onClick() }
    ) {
        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = null,
            tint = Color(0xFFE5B5A8),
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    // ViewModel previews are supplied by the navigation host, not instantiated here.
}
