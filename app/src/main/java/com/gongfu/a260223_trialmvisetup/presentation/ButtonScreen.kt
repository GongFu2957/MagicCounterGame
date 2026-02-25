package com.gongfu.a260223_trialmvisetup.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gongfu.a260223_trialmvisetup.ui.theme._260223_TrialMVISetupTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ButtonScreenRoot(
    modifier: Modifier = Modifier,
) {
    val viewModel: ButtonViewModel = koinViewModel()
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.events) {
        viewModel.events.collect { event ->
            when (event) {
                is ButtonEvent.Error -> {
                    Toast.makeText(
                        context, event.error,
                        Toast.LENGTH_SHORT
                        ).show()
                }
            }
        }
    }
    ButtonScreen(
        state = state,
        onAction = { action -> viewModel.onAction(action) },
        modifier = modifier
    )
}

@Composable
fun ButtonScreen(
    state: ButtonState,
    onAction: (ButtonAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val contentColor = if (isSystemInDarkTheme()) {
        Color.White
    } else Color.Black

    // Random phrases for new game
    val resetPhrases = listOf(
        "New game starting...",
        "Generating magic number...",
        "Round 2 begins!",
        "Fresh challenge ahead!",
        "Resetting counter...",
        "New mystery awaits...",
        "Game reloaded!",
        "Here we go again!"
    )

    val randomPhrase = resetPhrases.random()

    if (state.isResetting) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(72.dp)
                    .padding(4.dp),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 4.dp,
                trackColor = MaterialTheme.colorScheme.surfaceVariant.copy(.3f)
            )
            Spacer(modifier.height(20.dp))
            Text(
                text = randomPhrase,
                fontSize = 24.sp,
                fontFamily = FontFamily.Monospace,
                color = contentColor,
                textAlign = TextAlign.Center
            )
        }
    } else {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(25.dp)
        ) {
            Text(
                text = "Can you guess the magic number? (1-10)",
                fontSize = 24.sp,
                fontFamily = FontFamily.Monospace,
                color = contentColor,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "${state.counter}",
                fontSize = 50.sp,
                fontFamily = FontFamily.Monospace,
                color = contentColor
            )
            HorizontalDivider(
                modifier = Modifier
                    .height(10.dp)
                    .padding(10.dp)
            )
            Column {
                Button(
                    onClick = { onAction(ButtonAction.onIncrement) },
                    modifier = Modifier
                        .padding(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
                Button(
                    onClick = { onAction(ButtonAction.onDecrement) },
                    modifier = Modifier
                        .padding(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }

            }
        }

    }
}

@PreviewLightDark
@Composable
private fun PreviewButtonScreen() {
    _260223_TrialMVISetupTheme {
        ButtonScreen(
            state = ButtonState(isResetting = true),
            onAction = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
        )
    }
}