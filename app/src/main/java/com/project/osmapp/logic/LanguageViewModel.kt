package com.project.osmapp.logic

import android.content.Context
import android.content.res.Configuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class LanguageViewModel(private val context: Context) : ViewModel() {

    private val _language = MutableStateFlow("es") // Idioma por defecto: Español
    val language: StateFlow<String> get() = _language

    init {
        // Cargar el idioma desde SharedPreferences al iniciar el ViewModel
        val sharedPreferences =
            context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val savedLanguage =
            sharedPreferences.getString("selected_language", "es") // "es" es el valor por defecto
        setLanguage(savedLanguage ?: "es")
    }

    fun setLanguage(lang: String) {
        viewModelScope.launch {
            _language.value = lang
            updateResources(lang)
        }
    }

    private fun updateResources(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        // Guardar el idioma seleccionado en SharedPreferences
        val sharedPreferences =
            context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("selected_language", languageCode).apply()

        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}