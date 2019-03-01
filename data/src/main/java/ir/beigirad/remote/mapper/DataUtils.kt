package ir.beigirad.remote.mapper

import kotlin.random.Random

/**
 * Created by Farhad Beigirad on 3/1/19.
 */
object DataUtils {

    private const val iconSize = "64" //    32, 44, 64, and 88 are available
    private const val iconBackground = ""   // bg_ for gray background

    fun wrapIcon(prefix: String?, suffix: String?) = "$prefix$iconBackground$iconSize$suffix"

    private val colors = arrayOf("fb8c00", "ffb300", "fdd835","fdd835","ffb74d","ffd54f","ffeb3b", "cddc39", "8bc34a", "4caf50","4caf50")
    fun getRandomRate(): Pair<Float, String> {
        val rate = Random.nextFloat() * 10
        return Pair(rate, colors[rate.toInt()])
    }
}