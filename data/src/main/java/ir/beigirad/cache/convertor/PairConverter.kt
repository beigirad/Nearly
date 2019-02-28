package ir.beigirad.cache.convertor

import android.util.Pair
import androidx.room.TypeConverter

object PairConverter {

    @JvmStatic
    @TypeConverter
    fun deserializePair(value: String): Pair<Double, Double>? {
        val latLng = value.split(",")
        return Pair(latLng[0].toDouble(), latLng[1].toDouble())
    }

    @JvmStatic
    @TypeConverter
    fun serializePair(value: Pair<Double, Double>?): String {
        return "${value?.first},${value?.second}"
    }
}