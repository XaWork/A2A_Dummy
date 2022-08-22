package com.a2a.app.utils

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import com.a2a.app.data.model.AppDataModel
import com.a2a.app.data.model.VerifyOtpModel
import com.google.gson.Gson

class AppUtils(private val context: Context) {

    companion object {
        private val KEY_TOKEN = stringPreferencesKey("key_token")
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

    val MY_PREFS_NAME = "StarterApp"
    private val _myPrefName = "sharePrefName"

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
        val editor: SharedPreferences.Editor = context.getSharedPreferences(_myPrefName, Context.MODE_PRIVATE).edit()
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

    fun logOut(){
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


}