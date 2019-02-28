package ir.beigirad.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ir.beigirad.cache.convertor.PairConverter
import ir.beigirad.cache.dao.VenueDao
import ir.beigirad.cache.model.VenueModel

@Database(entities = [VenueModel::class], version = 1, exportSchema = false)
@TypeConverters(value = [PairConverter::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun venueDao(): VenueDao

}