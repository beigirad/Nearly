package ir.beigirad.nearly.di

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import ir.beigirad.domain.executor.PostExecutionThread
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 3/1/19.
 */
class UiThread @Inject constructor() : PostExecutionThread {
    override val scheduler: Scheduler
        get() = AndroidSchedulers.mainThread()

}