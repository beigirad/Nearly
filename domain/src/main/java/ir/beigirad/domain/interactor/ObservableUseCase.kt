package ir.beigirad.domain.interactor

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import ir.beigirad.domain.executor.PostExecutionThread

abstract class ObservableUseCase<T, Params>(
    private val postExecutionThread: PostExecutionThread
) {
    private val disposables = CompositeDisposable()

    abstract fun buildUseCaseObservable(params: Params): Observable<T>

    protected open val beMultiThread = true

    fun execute(observer: DisposableObserver<T>, params: Params) {
        val observable =
            if (beMultiThread)
                this.buildUseCaseObservable(params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(postExecutionThread.scheduler)
            else
                this.buildUseCaseObservable(params)

        addDisposable(observable.subscribeWith(observer))
    }

    fun dispose() {
        disposables.dispose()
    }

    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun hasSubscriber(): Boolean {
        return disposables.size() > 0
    }
}