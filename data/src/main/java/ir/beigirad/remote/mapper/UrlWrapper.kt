package ir.beigirad.remote.mapper

/**
 * Created by Farhad Beigirad on 3/1/19.
 */
object UrlWrapper {

    private const val iconSize = "64" //    32, 44, 64, and 88 are available
    private const val iconBackground = "bg_"   // bg_ for gray background

    fun wrapIcon(prefix: String?, suffix: String?) = "$prefix$iconBackground$iconSize$suffix"

}