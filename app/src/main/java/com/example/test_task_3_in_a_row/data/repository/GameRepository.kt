package com.example.test_task_3_in_a_row.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "game_preferences")

@Singleton
class GameRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val dataStore = context.dataStore

    companion object {
        val COINS = intPreferencesKey("coins")
        val BEST_SCORE = intPreferencesKey("best_score")
        val COMPLETED_LEVELS = intPreferencesKey("completed_levels")
        val UNLOCKED_ACHIEVEMENTS = stringSetPreferencesKey("unlocked_achievements")
        val OWNED_SHOP_ITEMS = stringSetPreferencesKey("owned_shop_items")
        val EQUIPPED_SHOP_ITEM = stringPreferencesKey("equipped_shop_item")

        val MUSIC_ENABLED = booleanPreferencesKey("music_enabled")
        val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
        val VIBRATION_ENABLED = booleanPreferencesKey("vibration_enabled")
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
        val LANGUAGE = stringPreferencesKey("language")
    }

    val coinsFlow: Flow<Int> = dataStore.data.map { it[COINS] ?: 0 }
    val bestScoreFlow: Flow<Int> = dataStore.data.map { it[BEST_SCORE] ?: 0 }
    val completedLevelsFlow: Flow<Int> = dataStore.data.map { it[COMPLETED_LEVELS] ?: 0 }
    val unlockedAchievementsFlow: Flow<Set<String>> = dataStore.data.map { it[UNLOCKED_ACHIEVEMENTS] ?: emptySet() }
    val ownedShopItemsFlow: Flow<Set<String>> = dataStore.data.map { it[OWNED_SHOP_ITEMS] ?: setOf("1") }
    val equippedShopItemFlow: Flow<String> = dataStore.data.map { it[EQUIPPED_SHOP_ITEM] ?: "1" }

    val musicEnabledFlow: Flow<Boolean> = dataStore.data.map { it[MUSIC_ENABLED] ?: true }
    val soundEnabledFlow: Flow<Boolean> = dataStore.data.map { it[SOUND_ENABLED] ?: true }
    val vibrationEnabledFlow: Flow<Boolean> = dataStore.data.map { it[VIBRATION_ENABLED] ?: false }
    val notificationsEnabledFlow: Flow<Boolean> = dataStore.data.map { it[NOTIFICATIONS_ENABLED] ?: true }
    val languageFlow: Flow<String> = dataStore.data.map { it[LANGUAGE] ?: "EN" }

    suspend fun updateCoins(amount: Int) {
        dataStore.edit { it[COINS] = (it[COINS] ?: 0) + amount }
    }

    suspend fun spendCoins(amount: Int): Boolean {
        var success = false
        dataStore.edit { prefs ->
            val current = prefs[COINS] ?: 0
            if (current >= amount) {
                prefs[COINS] = current - amount
                success = true
            }
        }
        return success
    }

    suspend fun updateBestScore(newScore: Int) {
        dataStore.edit { prefs ->
            val currentBest = prefs[BEST_SCORE] ?: 0
            if (newScore > currentBest) prefs[BEST_SCORE] = newScore
        }
    }

    suspend fun completeLevel(levelId: Int) {
        dataStore.edit { prefs ->
            val currentMax = prefs[COMPLETED_LEVELS] ?: 0
            if (levelId > currentMax) prefs[COMPLETED_LEVELS] = levelId
        }
    }

    suspend fun unlockAchievement(achievementId: String) {
        dataStore.edit { prefs ->
            val current = prefs[UNLOCKED_ACHIEVEMENTS] ?: emptySet()
            prefs[UNLOCKED_ACHIEVEMENTS] = current + achievementId
        }
    }

    suspend fun buyShopItem(itemId: String) {
        dataStore.edit { prefs ->
            val current = prefs[OWNED_SHOP_ITEMS] ?: emptySet()
            prefs[OWNED_SHOP_ITEMS] = current + itemId
        }
    }

    suspend fun equipShopItem(itemId: String) {
        dataStore.edit { it[EQUIPPED_SHOP_ITEM] = itemId }
    }

    suspend fun setMusic(enabled: Boolean) = dataStore.edit { it[MUSIC_ENABLED] = enabled }
    suspend fun setSound(enabled: Boolean) = dataStore.edit { it[SOUND_ENABLED] = enabled }
    suspend fun setVibration(enabled: Boolean) = dataStore.edit { it[VIBRATION_ENABLED] = enabled }
    suspend fun setNotifications(enabled: Boolean) = dataStore.edit { it[NOTIFICATIONS_ENABLED] = enabled }
    suspend fun setLanguage(lang: String) = dataStore.edit { it[LANGUAGE] = lang }
}