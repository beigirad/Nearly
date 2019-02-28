package ir.beigirad.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.beigirad.cache.dao.VenueDao
import ir.beigirad.cache.model.VenueModel

@Database(entities = [VenueModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun venueDao(): VenueDao

}