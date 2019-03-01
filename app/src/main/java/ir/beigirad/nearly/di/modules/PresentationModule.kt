package ir.beigirad.nearly.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import ir.beigirad.nearly.di.ViewModelFactory
import ir.beigirad.presentation.VenuesViewModel
import kotlin.reflect.KClass

/**
 * Created by Farhad Beigirad on 3/1/19.
 */
@Module
abstract class PresentationModule {

    @Binds
    abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelKey(VenuesViewModel::class)
    abstract fun bindSplashViewModel(splashViewModel: VenuesViewModel): ViewModel


}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)
