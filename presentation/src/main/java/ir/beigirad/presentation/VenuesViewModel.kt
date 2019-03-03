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
    // should handle with PagingLibrary
    private val venueList = mutableListOf<VenueView>()
    private var subscribedLocation = false
    private var lastLocation: Pair<Double, Double>? = null

    override fun onCleared() {
        getVenues.dispose()
        getMyLocation.dispose()
        locationSubscriber.dispose()
        Timber.d("onCleared $subscribedLocation")
        super.onCleared()
    }

    fun fetchMyLocation() {
        Timber.d("fetchMyLocation ")
        venuesLiveData.postValue(
            Resource(
                status = ResourceState.LOADING,
                data = venueList,
                message = null
            )
        )

        if (!subscribedLocation)
            getMyLocation.execute(locationSubscriber, GetMyLocation.Param())
        else
            lastLocation?.let { fetchVenues(it) }
    }

    private var locationSubscriber = LocationSubscriber()

    private inner class LocationSubscriber : DisposableObserver<GpsLocation>() {
        override fun onStart() {
            super.onStart()
            subscribedLocation = true
        }

        override fun onComplete() {
            subscribedLocation = false
        }

        override fun onNext(gpsLocation: GpsLocation) {
            Timber.d("LocationSubscriber $gpsLocation")
            when (gpsLocation) {
                is GpsLocation.Success -> {

                    if (lastLocation?.first != gpsLocation.location.latLng.first || lastLocation?.second == gpsLocation.location.latLng.second)
                        venueList.clear()

                    lastLocation = gpsLocation.location.latLng
                    lastLocation?.let { fetchVenues(it) }
                }
                is GpsLocation.Error -> {
                    venuesLiveData.postValue(
                        Resource(
                            status = ResourceState.ERROR,
                            data = venueList,
                            message = gpsLocation.message
                        )
                    )
                }
                is GpsLocation.Loading -> {
                    venuesLiveData.postValue(
                        Resource(
                            status = ResourceState.LOADING,
                            data = venueList,
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
                    data = venueList,
                    message = e.localizedMessage
                )
            )
        }

    }

    fun getVenues(): LiveData<Resource<List<VenueView>>> {
        return venuesLiveData
    }

    private fun fetchVenues(latLng: Pair<Double, Double>) {
        Timber.d("fetchVenues ")
        venuesLiveData.postValue(
            Resource(
                status = ResourceState.LOADING,
                data = venueList,
                message = null
            )
        )
        getVenues.execute(VenueSubscriber(),
                VenuePagination(
                        latLng = latLng,
                        radius = 2_000,
                        limit = 15,
                    offset = venueList.size
                ))
    }

    private inner class VenueSubscriber : DisposableObserver<List<Venue>>() {
        override fun onComplete() {
        }

        override fun onNext(t: List<Venue>) {
            Timber.d("VenueSubscriber ${t.size}")
            venueList.addAll(t.map { venueMapper.mapToView(it) })

            venuesLiveData.postValue(
                Resource(
                    ResourceState.SUCCESS,
                    venueList,
                    null
                )
            )
        }

        override fun onError(e: Throwable) {
            Timber.d(e)
            venuesLiveData.postValue(
                Resource(
                    ResourceState.ERROR,
                    venueList,
                    e.localizedMessage
                )
            )
        }

    }
}
