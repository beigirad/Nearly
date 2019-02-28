package ir.beigirad.domain.interactor.usecase

import io.reactivex.Observable
import ir.beigirad.domain.executor.PostExecutionThread
import ir.beigirad.domain.interactor.ObservableUseCase
import ir.beigirad.domain.model.GpsLocation
import ir.beigirad.domain.model.Location
import ir.beigirad.domain.repository.NearlyRepository
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 3/1/19.
 */
class GetMyLocation @Inject constructor(
        private val repository: NearlyRepository,
        postExecutionThread: PostExecutionThread
) :
        ObservableUseCase<GpsLocation, Nothing>(postExecutionThread) {
    override fun buildUseCaseObservable(params: Nothing): Observable<GpsLocation> {
        return repository.getMyLocation()
    }
}