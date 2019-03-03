package ir.beigirad.nearly.feature.venuelist

import ir.beigirad.presentation.model.VenueView

/**
 * Created by Farhad Beigirad on 3/3/19.
 */
interface IVenueListInteraction {

    fun onSelectVenue(venueView: VenueView)

}