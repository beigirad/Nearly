package ir.beigirad.remote.utils

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import retrofit2.Response
import timber.log.Timber

class NetworkErrorHandler<T> : Function<Throwable, ObservableSource<Response<T>>> {
    override fun apply(t: Throwable): ObservableSource<Response<T>> {
        Timber.e(t)
        return Observable.error(t)
    }
}