package com.example.reloj.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.reloj.ui.ClockColorPreset
import com.example.reloj.ui.ClockFaceType
import com.example.reloj.ui.ClockStyleSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "clock_settings")

class ClockSettingsRepository(private val context: Context) {

    private object Keys {
        val SELECTED_FACE = stringPreferencesKey("selected_face")
        val SHOW_SECONDS = booleanPreferencesKey("show_seconds")
        val SHOW_DATE = booleanPreferencesKey("show_date")
        val IS_24H = booleanPreferencesKey("is_24h")
        val COLOR_PRESET = stringPreferencesKey("color_preset")
        val SHOW_ANALOG_NUMBERS = booleanPreferencesKey("show_analog_numbers")
        val SHOW_ANALOG_MARKS = booleanPreferencesKey("show_analog_marks")
    }

    data class UserPreferences(
        val selectedClockFace: ClockFaceType,
        val styleSettings: ClockStyleSettings
    )

    val userPreferencesFlow: Flow<UserPreferences> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { prefs ->
            val faceName = prefs[Keys.SELECTED_FACE] ?: ClockFaceType.DIGITAL_MINIMAL.name
            val face = try {
                ClockFaceType.valueOf(faceName)
            } catch (e: Exception) {
                ClockFaceType.DIGITAL_MINIMAL
            }

            val colorName = prefs[Keys.COLOR_PRESET] ?: ClockColorPreset.DEFAULT.name
            val preset = try {
                ClockColorPreset.valueOf(colorName)
            } catch (e: Exception) {
                ClockColorPreset.DEFAULT
            }

            UserPreferences(
                selectedClockFace = face,
                styleSettings = ClockStyleSettings(
                    showSeconds = prefs[Keys.SHOW_SECONDS] ?: true,
                    showDate = prefs[Keys.SHOW_DATE] ?: true,
                    is24HourFormat = prefs[Keys.IS_24H] ?: true,
                    colorPreset = preset,
                    showAnalogNumbers = prefs[Keys.SHOW_ANALOG_NUMBERS] ?: true,
                    showAnalogMinuteMarks = prefs[Keys.SHOW_ANALOG_MARKS] ?: true
                )
            )
        }

    suspend fun updateSelectedFace(type: ClockFaceType) {
        context.dataStore.edit { it[Keys.SELECTED_FACE] = type.name }
    }

    suspend fun updateShowSeconds(v: Boolean) {
        context.dataStore.edit { it[Keys.SHOW_SECONDS] = v }
    }

    suspend fun updateShowDate(v: Boolean) {
        context.dataStore.edit { it[Keys.SHOW_DATE] = v }
    }

    suspend fun updateIs24H(v: Boolean) {
        context.dataStore.edit { it[Keys.IS_24H] = v }
    }

    suspend fun updateColorPreset(v: ClockColorPreset) {
        context.dataStore.edit { it[Keys.COLOR_PRESET] = v.name }
    }

    suspend fun updateShowAnalogNumbers(v: Boolean) {
        context.dataStore.edit { it[Keys.SHOW_ANALOG_NUMBERS] = v }
    }

    suspend fun updateShowAnalogMarks(v: Boolean) {
        context.dataStore.edit { it[Keys.SHOW_ANALOG_MARKS] = v }
    }
}
