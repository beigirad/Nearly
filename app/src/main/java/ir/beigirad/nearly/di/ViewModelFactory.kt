package ir.beigirad.nearly.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by Farhad Beigirad on 3/1/19.
 */

class ViewModelFactory :
        ViewModelProvider.Factory {
    private var creators: Map<Class<out ViewModel>, Provider<ViewModel>>

    @Inject
    constructor(creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) {
        this.creators = creators
    }


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var creator = creators[modelClass]

        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }

        if (creator == null) {
            throw IllegalArgumentException("unknown model class : $modelClass")
        }

        try {
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

}