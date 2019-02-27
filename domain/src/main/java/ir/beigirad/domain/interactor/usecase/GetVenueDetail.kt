package ir.beigirad.domain.interactor.usecase

import io.reactivex.Observable
import ir.beigirad.domain.executor.PostExecutionThread
import ir.beigirad.domain.interactor.ObservableUseCase
import ir.beigirad.domain.model.VenueDetail
import ir.beigirad.domain.repository.NearlyRepository
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
class GetVenueDetail @Inject constructor(
    private val repository: NearlyRepository,
    postExecutionThread: PostExecutionThread
) :
    ObservableUseCase<VenueDetail, GetVenueDetail.Param>(postExecutionThread) {
    override fun buildUseCaseObservable(params: Param): Observable<VenueDetail> {
        return repository.getVenueDetail(params.venueId)
    }

    class Param(
        val venueId: String
    )
}