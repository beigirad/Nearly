package ir.beigirad.domain.interactor.usecase

import io.reactivex.Observable
import ir.beigirad.domain.executor.PostExecutionThread
import ir.beigirad.domain.interactor.ObservableUseCase
import ir.beigirad.domain.model.Venue
import ir.beigirad.domain.model.VenuePagination
import ir.beigirad.domain.repository.NearlyRepository
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
class GetVenues @Inject constructor(
    private val repository: NearlyRepository,
    postExecutionThread: PostExecutionThread
) :
        ObservableUseCase<List<Venue>, VenuePagination>(postExecutionThread) {
    override fun buildUseCaseObservable(params: VenuePagination): Observable<List<Venue>> {
        return repository.getVenues(params)
    }

    data class Param(
        val currentLatLng: Pair<Double, Double>
    )
}