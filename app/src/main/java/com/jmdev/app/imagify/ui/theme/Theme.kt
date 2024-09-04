package com.jmdev.app.imagify.ui.theme

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topBarColors() = TopAppBarColors(
    scrolledContainerColor = MaterialTheme.colorScheme.background,
    containerColor = MaterialTheme.colorScheme.background,
    actionIconContentColor = MaterialTheme.colorScheme.primary,
    navigationIconContentColor = MaterialTheme.colorScheme.primary,
    titleContentColor = MaterialTheme.colorScheme.onSurface
)

@Composable
fun textFieldColors() = TextFieldColors(
    focusedContainerColor = MaterialTheme.colorScheme.onSecondaryContainer,
    unfocusedLabelColor = MaterialTheme.colorScheme.onSecondary,
    unfocusedContainerColor = MaterialTheme.colorScheme.onSecondaryContainer,
    focusedIndicatorColor = Color.Unspecified,
    unfocusedIndicatorColor = Color.Unspecified,
    disabledIndicatorColor = Color.Unspecified,
    errorIndicatorColor = Color.Unspecified,
    cursorColor = MaterialTheme.colorScheme.background,
    focusedTrailingIconColor = Color.Unspecified,
    unfocusedTrailingIconColor = Color.Unspecified,
    disabledTrailingIconColor = Color.Unspecified,
    errorTrailingIconColor = Color.Unspecified,
    focusedLeadingIconColor = Color.Unspecified,
    unfocusedLeadingIconColor = Color.Unspecified,
    disabledLeadingIconColor = Color.Unspecified,
    errorLeadingIconColor = Color.Unspecified,
    disabledTextColor = Color.Unspecified,
    errorCursorColor = Color.Unspecified,
    errorLabelColor = Color.Unspecified,
    errorPlaceholderColor = Color.Unspecified,
    disabledLabelColor = Color.Unspecified,
    focusedLabelColor = Color.Unspecified,
    errorTextColor = Color.Unspecified,
    disabledPlaceholderColor = Color.Unspecified,
    disabledContainerColor = Color.Unspecified,
    disabledPrefixColor = Color.Unspecified,
    disabledSuffixColor = Color.Unspecified,
    disabledSupportingTextColor = Color.Unspecified,
    errorPrefixColor = Color.Unspecified,
    errorSuffixColor = Color.Unspecified,
    errorContainerColor = Color.Unspecified,
    errorSupportingTextColor = Color.Unspecified,
    focusedTextColor = Color.Unspecified,
    focusedPrefixColor = Color.Unspecified,
    focusedSuffixColor = Color.Unspecified,
    focusedPlaceholderColor = Color.Unspecified,
    focusedSupportingTextColor = Color.Unspecified,
    unfocusedPrefixColor = Color.Unspecified,
    unfocusedSuffixColor = Color.Unspecified,
    unfocusedTextColor = Color.Unspecified,
    unfocusedPlaceholderColor = Color.Unspecified,
    unfocusedSupportingTextColor = Color.Unspecified,
    textSelectionColors = TextSelectionColors(
        handleColor = Color.Unspecified,
        backgroundColor = Color.Unspecified
    )
)

@Composable
fun ImagifyTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = darkScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}