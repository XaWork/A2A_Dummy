package com.a2a.app.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.a2a.app.data.model.AppDataModel
import com.a2a.app.data.model.HomeModel
import com.a2a.app.data.model.SettingsModel
import com.a2a.app.data.model.VerifyOtpModel
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.w3c.dom.Text
import javax.inject.Inject

class AppUtils @Inject constructor(@ApplicationContext private val context: Context) {
    val MY_PREFS_NAME = "StarterApp"

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("A2A data store")
        val USER_TOKEN_KEY = stringPreferencesKey("user_token")
        val USER_KEY = stringPreferencesKey("user")
        val HOME_SCREEN_KEY = stringPreferencesKey("home")
        val SETTING_KEY = stringPreferencesKey("setting")

        private val KEY_TOKEN = stringPreferencesKey("key_token")
    }

    val getToken1 : Flow<String?> = context.dataStore.data
        .map {preference ->
            preference[USER_TOKEN_KEY]?:""
        }

    suspend fun saveToken1(token: String){
        context.dataStore.edit { preference ->
            preference[USER_TOKEN_KEY] = token
            Log.e("apputils", "token saved and token is : $token")
        }
    }
    // At the top level of your kotlin file:

    /*private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_DATASTORE)

    suspend fun saveToken(token: String){
        context.dataStore.edit{preferences ->
            preferences[KEY_TOKEN] = token
        }
    }

    val getToken: Flow<String?>
    get() = context.dataStore.data.map {preferences ->
        preferences[KEY_TOKEN]
    }*/

    private val _myPrefName = "sharePrefName"
    private val home = "home page data"
    private val setting = "settings"
    private val user = "user info"

    fun saveToken(token: String?) {
        val editor =
            context.getSharedPreferences(_myPrefName, Context.MODE_PRIVATE).edit()
        editor.putString("token", token)
        editor.apply()
    }

    fun getToken(): String? {
        val prefs = context.getSharedPreferences(_myPrefName, Context.MODE_PRIVATE)
        return prefs.getString("token", "")
    }

    fun saveUser(user: VerifyOtpModel.Data) {
        val editor: SharedPreferences.Editor =
            context.getSharedPreferences(_myPrefName, Context.MODE_PRIVATE).edit()
        editor.putString("user", Gson().toJson(user, VerifyOtpModel.Data::class.java))
        Log.d("user", "User saved and user is -> \n$user")
        editor.apply()
    }

    fun getUser(): VerifyOtpModel.Data? {
        val prefs = context.getSharedPreferences(_myPrefName, Context.MODE_PRIVATE)
        val userGson = prefs.getString("user", "")
        Log.d("user", "get user is -> \n$userGson")
        return if (TextUtils.isEmpty(userGson))
            null
        else
            Gson().fromJson(userGson, VerifyOtpModel.Data::class.java)
    }

    fun logOut() {
        val editor = context.getSharedPreferences(_myPrefName, Context.MODE_PRIVATE).edit()
        editor.clear()
        editor.apply()
    }

    /*fun getAppData(): AppDataModel? {
        val appDataStr: String =
            context.getSharedPreferences(_myPrefName, Context.MODE_PRIVATE).getString("appData", "")

        return if (!TextUtils.isEmpty(appDataStr)) {
            Gson().fromJson(appDataStr, AppDataModel::class.java)
        } else null
    }*/


    fun saveHomePage(home: HomeModel.Result) {
        val editor: SharedPreferences.Editor =
            context.getSharedPreferences(_myPrefName, Context.MODE_PRIVATE).edit()
        editor.putString("home", Gson().toJson(home, HomeModel.Result::class.java))
        //Log.d("user", "User saved and user is -> \n$home")
        editor.apply()
    }

    fun getHome(): HomeModel.Result? {
        val prefs = context.getSharedPreferences(_myPrefName, Context.MODE_PRIVATE)
        val home = prefs.getString("home", "")
        // Log.d("user", "get user is -> \n$userGson")
        return if (TextUtils.isEmpty(home))
            null
        else
            Gson().fromJson(home, HomeModel.Result::class.java)
    }

    fun clearHomePage() {
        val editor = context.getSharedPreferences(_myPrefName, Context.MODE_PRIVATE).edit()
        editor.putString("home", null)
        editor.apply()
    }

    fun saveSettings(settings: SettingsModel) {
        val preferencesEditor =
            context.getSharedPreferences(_myPrefName, Context.MODE_PRIVATE).edit()
        preferencesEditor.putString(setting, Gson().toJson(settings, SettingsModel::class.java))
        preferencesEditor.apply()
    }

    fun getSettings(): SettingsModel? {
        val preferences = context.getSharedPreferences(_myPrefName, Context.MODE_PRIVATE)
        val settings = preferences.getString(setting, "")

        return if (TextUtils.isEmpty(settings))
            null
        else
            Gson().fromJson(settings, SettingsModel::class.java)
    }

    fun clearSettings(){
        val editor = context.getSharedPreferences(_myPrefName, Context.MODE_PRIVATE).edit()
        editor.putString(setting, null)
        editor.apply()
    }
}