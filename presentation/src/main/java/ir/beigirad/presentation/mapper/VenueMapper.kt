package ir.beigirad.presentation.mapper

import ir.beigirad.domain.model.Venue
import ir.beigirad.presentation.model.VenueView
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
class VenueMapper @Inject constructor() : Mapper<VenueView, Venue> {
    override fun mapToView(domain: Venue): VenueView {
        return VenueView(
            id = domain.id,
            secondaryName = domain.secondaryName,
            primaryName = domain.primaryName,
            description = domain.description,
            photoUrl = domain.photoUrl,
                location = "< ${domain.location.distance}m | ${domain.location.address.orEmpty()}",
                userCount = domain.vote,
                ratingColor = domain.ratingColor,
                rating = domain.rating,
            category = domain.category?.let {
                VenueView.CategoryView(
                    id = it.id,
                    iconUrl = it.iconUrl,
                    title = it.title
                )
            }
        )
    }

}