package com.project.osmapp.logic

import android.content.SharedPreferences
import android.content.Context
class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    companion object {
        private const val SELECTED_LANGUAGE_KEY = "selected_language"
    }

    // Guarda el idioma seleccionado
    fun saveLanguage(languageCode: String) {
        val editor = sharedPreferences.edit()
        editor.putString(SELECTED_LANGUAGE_KEY, languageCode)
        editor.apply()
    }

    // Recupera el idioma seleccionado
    fun getSavedLanguage(): String {
        return sharedPreferences.getString(SELECTED_LANGUAGE_KEY, "es") ?: "es" // "es" es el valor por defecto
    }
}