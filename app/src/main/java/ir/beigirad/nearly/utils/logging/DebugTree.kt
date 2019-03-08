package ir.beigirad.nearly.utils.logging

import timber.log.Timber

/**
 * Created by Farhad Beigirad on 3/8/19.
 */
class DebugTree : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, "tlog $tag", message, t)
    }
}