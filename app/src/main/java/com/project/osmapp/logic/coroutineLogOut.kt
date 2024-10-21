package com.project.osmapp.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SessionViewModel : ViewModel() {
    private var sessionTimeoutJob: Job? = null

    fun startSessionTimeout(onTimeout: () -> Unit) {
        sessionTimeoutJob?.cancel() // Cancelar cualquier trabajo anterior
        sessionTimeoutJob = viewModelScope.launch {
            delay(120_000) // Espera 2 minutos
            onTimeout() // Cierra sesion
        }
    }

    fun resetSessionTimeout() {
        sessionTimeoutJob?.cancel() // Reiniciar el temporizador si hay actividad
    }
}
