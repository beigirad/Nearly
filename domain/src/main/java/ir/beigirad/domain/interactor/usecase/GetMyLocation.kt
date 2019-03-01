package ir.beigirad.domain.interactor.usecase

import io.reactivex.Observable
import ir.beigirad.domain.executor.PostExecutionThread
import ir.beigirad.domain.interactor.ObservableUseCase
import ir.beigirad.domain.model.GpsLocation
import ir.beigirad.domain.repository.NearlyRepository
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 3/1/19.
 */
class GetMyLocation @Inject constructor(
        private val repository: NearlyRepository,
        postExecutionThread: PostExecutionThread
) :
    ObservableUseCase<GpsLocation, GetMyLocation.Param>(postExecutionThread) {

    override val beMultiThread: Boolean
        get() = false   // Cause getting location should be on UiThread!

    override fun buildUseCaseObservable(params: Param): Observable<GpsLocation> {
        return repository.getMyLocation()
    }

    class Param
}