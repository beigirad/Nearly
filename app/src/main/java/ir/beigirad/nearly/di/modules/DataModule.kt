package ir.beigirad.nearly.di.modules

import dagger.Binds
import dagger.Module
import ir.beigirad.data.DataRepository
import ir.beigirad.domain.repository.NearlyRepository

/**
 * Created by Farhad Beigirad on 3/1/19.
 */
@Module
abstract class DataModule {

    @Binds
    abstract fun bindDataRepository(dataRepository: DataRepository): NearlyRepository
}