package com.sarang.torang.di.torang

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sryang.torang.ui.TorangTheme

@Composable
fun ProvideTheme(
    contents: @Composable () -> Unit
) {
    TorangTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
            content = contents
        )
    }
}