package ir.beigirad.cache.db

import android.content.Context
import androidx.room.Room
import javax.inject.Inject

class DatabaseFactory @Inject constructor() {

    private var INSTANCE: AppDatabase? = null
    private val LOCK = Any()

    fun getInstance(context: Context): AppDatabase {

        if (INSTANCE == null) {
            synchronized(LOCK) {
                if (INSTANCE == null)
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "VenueApp.db"
                    )
//                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()

                return INSTANCE as AppDatabase
            }
        }

        return INSTANCE as AppDatabase

    }
}