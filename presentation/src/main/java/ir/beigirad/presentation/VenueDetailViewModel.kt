package ir.beigirad.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.observers.DisposableObserver
import ir.beigirad.domain.interactor.usecase.GetVenueDetail
import ir.beigirad.domain.model.VenueDetail
import ir.beigirad.presentation.mapper.VenueDetailMapper
import ir.beigirad.presentation.model.VenueDetailView
import ir.beigirad.presentation.state.Resource
import ir.beigirad.presentation.state.ResourceState
import javax.inject.Inject

class VenueDetailViewModel @Inject constructor(
    private val getVenues: GetVenueDetail,
    private val detailMapper: VenueDetailMapper
) : ViewModel() {

    private val venuesLiveData = MutableLiveData<Resource<VenueDetailView>>()

    override fun onCleared() {
        getVenues.dispose()
        super.onCleared()
    }

    fun getLoads(): LiveData<Resource<VenueDetailView>> {
        return venuesLiveData
    }

    fun fetchLoads(venueId: String) {
        venuesLiveData.postValue(
            Resource(
                status = ResourceState.LOADING,
                data = venuesLiveData.value?.data,
                message = null
            )
        )
        getVenues.execute(DetailSubscriber(), GetVenueDetail.Param(venueId))
    }

    inner class DetailSubscriber : DisposableObserver<VenueDetail>() {
        override fun onComplete() {
        }

        override fun onNext(t: VenueDetail) {
            venuesLiveData.postValue(
                Resource(
                    ResourceState.SUCCESS,
                    detailMapper.mapToView(t),
                    null
                )
            )
        }

        override fun onError(e: Throwable) {
            venuesLiveData.postValue(
                Resource(
                    ResourceState.ERROR,
                    null,
                    e.localizedMessage
                )
            )
        }

    }
}
