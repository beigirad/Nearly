package ir.beigirad.nearly.di.modules

import dagger.Binds
import dagger.Module
import ir.beigirad.domain.executor.PostExecutionThread
import ir.beigirad.nearly.di.UiThread

/**
 * Created by Farhad Beigirad on 3/1/19.
 */
@Module
abstract class UiModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread
    
}