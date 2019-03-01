package ir.beigirad.preferences

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Farhad Beigirad on 3/1/19.
 */
class PreferencesFactory {

    private var INSTANCE: SharedPreferences? = null
    private val LOCK = Any()

    fun getInstance(context: Context): SharedPreferences {
        if (INSTANCE == null) {
            synchronized(LOCK) {
                if (INSTANCE == null)
                    INSTANCE = context.getSharedPreferences("NearlyPreferences", Context.MODE_PRIVATE)

                return INSTANCE as SharedPreferences
            }
        }

        return INSTANCE as SharedPreferences

    }
}