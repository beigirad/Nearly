package ir.beigirad.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.observers.DisposableObserver
import ir.beigirad.domain.interactor.usecase.GetVenues
import ir.beigirad.domain.model.Venue
import ir.beigirad.presentation.mapper.VenueMapper
import ir.beigirad.presentation.model.VenueView
import ir.beigirad.presentation.state.Resource
import ir.beigirad.presentation.state.ResourceState
import javax.inject.Inject

class VenuesViewModel @Inject constructor(
    private val getVenues: GetVenues,
    private val venueMapper: VenueMapper
) : ViewModel() {

    private val venuesLiveData = MutableLiveData<Resource<List<VenueView>>>()

    override fun onCleared() {
        getVenues.dispose()
        super.onCleared()
    }

    fun getLoads(): LiveData<Resource<List<VenueView>>> {
        return venuesLiveData
    }

    fun fetchLoads(latLng: Pair<Double, Double>) {
        venuesLiveData.postValue(
            Resource(
                status = ResourceState.LOADING,
                data = venuesLiveData.value?.data,
                message = null
            )
        )
        getVenues.execute(VenueSubscriber(), GetVenues.Param(latLng))
    }

    inner class VenueSubscriber : DisposableObserver<List<Venue>>() {
        override fun onComplete() {
        }

        override fun onNext(t: List<Venue>) {
            venuesLiveData.postValue(
                Resource(
                    ResourceState.SUCCESS,
                    t.map { venueMapper.mapToView(it) },
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
