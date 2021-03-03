package com.tutorialsbuzz.fcmdemo

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class PreferenceManager internal constructor(context: Context) {

    private val mPrefs: SharedPreferences

    init {
        mPrefs = context.getSharedPreferences(PREFERENCES, 0)
    }

    companion object {
        private const val PREFERENCES = "settings"
        private var mPreferenceManager: PreferenceManager? = null
        fun getInstance(context: Context): PreferenceManager? {
            if (mPreferenceManager == null) {
                mPreferenceManager = PreferenceManager(context)
            }
            return mPreferenceManager
        }
    }

    fun getStoredJSONOptimization(): List<Model>? {
        val gson = Gson()
        val response = mPrefs.getString(
            Name.JSON_DATA,
            null
        )
            ?: return null
        return gson.fromJson(response, Array<Model>::class.java).toList()
    }

    fun storeJSONOptimization(value: String?) {
        mPrefs.edit().putString(
            Name.JSON_DATA,
            value
        ).apply()
    }

    object Name {
        const val JSON_DATA = "JSON_DATA"
    }

}