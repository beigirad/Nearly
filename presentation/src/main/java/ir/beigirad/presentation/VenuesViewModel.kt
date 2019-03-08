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
    //  TODO should handle with PagingLibrary
    private val venueList = mutableListOf<VenueView>()

    private var lastLocation: Pair<Double, Double>? = null

    private var initRequest = true


    override fun onCleared() {
        getVenues.dispose()
        getMyLocation.dispose()
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

        if (getMyLocation.hasSubscriber().not())
            getMyLocation.execute(LocationSubscriber(), GetMyLocation.Param())
        else
            lastLocation?.let { fetchVenues(it, false) }
    }

    private inner class LocationSubscriber : DisposableObserver<GpsLocation>() {
        override fun onComplete() {
            Timber.d("LocationSubscriber onComplete")
        }

        override fun onNext(gpsLocation: GpsLocation) {
            Timber.d("LocationSubscriber $gpsLocation")
            when (gpsLocation) {
                is GpsLocation.Success -> {
                    Timber.d("onNext locationChanged:${gpsLocation.locationChanged}")

                    if (gpsLocation.locationChanged || initRequest)
                        fetchVenues(gpsLocation.location, true)

                    initRequest = false
                    lastLocation = gpsLocation.location
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

    private fun fetchVenues(latLng: Pair<Double, Double>, locationChanged: Boolean) {
        Timber.d("fetchVenues ")
        venuesLiveData.postValue(
            Resource(
                status = ResourceState.LOADING,
                data = venueList,
                message = null
            )
        )

        if (locationChanged)
            venueList.clear()

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
            Timber.d("VenueSubscriber onNext ${t.size}")
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
