package com.example.vehicleapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val VehicleColorScheme = lightColorScheme(
    primary = VehicleBlue,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    primaryContainer = VehicleBlueContainer,
    onPrimaryContainer = VehicleBlue,
    secondary = VehicleNeutral,
    tertiary = VehicleGreen,
    onTertiary = androidx.compose.ui.graphics.Color.White,
    tertiaryContainer = VehicleGreenContainer,
    onTertiaryContainer = VehicleGreen,
    errorContainer = androidx.compose.ui.graphics.Color(0xFFF8E3E1),
    onErrorContainer = androidx.compose.ui.graphics.Color(0xFF7A2925)
)

@Composable
fun VehicleAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = VehicleColorScheme,
        typography = Typography,
        content = content
    )
}
