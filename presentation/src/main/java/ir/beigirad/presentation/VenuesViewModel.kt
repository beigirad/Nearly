package ir.beigirad.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.observers.DisposableObserver
import ir.beigirad.domain.interactor.usecase.GetMyLocation
import ir.beigirad.domain.interactor.usecase.GetVenues
import ir.beigirad.domain.model.GpsLocation
import ir.beigirad.domain.model.Venue
import ir.beigirad.domain.model.VenuePagination
import ir.beigirad.presentation.mapper.VenueMapper
import ir.beigirad.presentation.model.VenueView
import ir.beigirad.presentation.state.Resource
import ir.beigirad.presentation.state.ResourceState
import timber.log.Timber
import javax.inject.Inject

class VenuesViewModel @Inject constructor(
    private val getVenues: GetVenues,
    private val getMyLocation: GetMyLocation,
    private val venueMapper: VenueMapper
) : ViewModel() {

    private val venuesLiveData = MutableLiveData<Resource<List<VenueView>>>()

    override fun onCleared() {
        getVenues.dispose()
        getMyLocation.dispose()
        super.onCleared()
    }

    fun fetchMyLocation() {
        venuesLiveData.postValue(
            Resource(
                status = ResourceState.LOADING,
                data = venuesLiveData.value?.data,
                message = null
            )
        )
        getMyLocation.execute(LocationSubscriber(), GetMyLocation.Param())
    }

    private inner class LocationSubscriber : DisposableObserver<GpsLocation>() {
        override fun onComplete() {}

        override fun onNext(gpsLocation: GpsLocation) {
            when (gpsLocation) {
                is GpsLocation.Success -> {
                    fetchVenues(gpsLocation.location.latLng)
                }
                is GpsLocation.Error -> {
                    venuesLiveData.postValue(
                        Resource(
                            status = ResourceState.ERROR,
                            data = venuesLiveData.value?.data,
                            message = gpsLocation.message
                        )
                    )
                }
                is GpsLocation.Loading -> {
                    venuesLiveData.postValue(
                        Resource(
                            status = ResourceState.LOADING,
                            data = venuesLiveData.value?.data,
                            message = null
                        )
                    )
                }
            }
        }

        override fun onError(e: Throwable) {
            Timber.d(e)
            venuesLiveData.postValue(
                Resource(
                    status = ResourceState.ERROR,
                    data = venuesLiveData.value?.data,
                    message = e.localizedMessage
                )
            )
        }

    }

    fun getVenues(): LiveData<Resource<List<VenueView>>> {
        return venuesLiveData
    }

    private fun fetchVenues(latLng: Pair<Double, Double>) {
        venuesLiveData.postValue(
            Resource(
                status = ResourceState.LOADING,
                data = venuesLiveData.value?.data,
                message = null
            )
        )
        getVenues.execute(VenueSubscriber(),
                VenuePagination(
                        latLng = latLng,
                        radius = 2_000,
                        limit = 15,
                        offset = venuesLiveData.value?.data?.size ?: 0
                ))
    }

    private inner class VenueSubscriber : DisposableObserver<List<Venue>>() {
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
            Timber.d(e)
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
