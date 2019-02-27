package ir.beigirad.presentation.mapper

import ir.beigirad.domain.model.VenueDetail
import ir.beigirad.presentation.model.LocationView
import ir.beigirad.presentation.model.VenueDetailView
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
class VenueDetailMapper @Inject constructor() : Mapper<VenueDetailView, VenueDetail> {
    override fun mapToView(domain: VenueDetail): VenueDetailView {
        return VenueDetailView(
            id = domain.id,
            secondaryName = domain.secondaryName,
            primaryName = domain.primaryName,
            description = domain.description,
            photoUrl = domain.photoUrl,
            location = LocationView(
                latLng = domain.location.latLng,
                distance = domain.location.distance,
                address = domain.location.address
            ),
            follower = domain.follower,
            likes = domain.likes,
            rating = domain.rating,
            ratingColor = domain.ratingColor,
            photos = domain.photos?.map {
                VenueDetailView.PhotoView(
                    id = it.id,
                    height = it.height,
                    url = it.url,
                    width = it.width

                )
            })
    }

}