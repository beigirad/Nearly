package ir.beigirad.nearly

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri


/**
 * Created by Farhad Beigirad on 3/8/19.
 */
object IntentHelper {

    fun dialTo(context: Context, tel: CharSequence) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$tel")
        context.startActivity(intent)
    }

    fun directTo(context: Context, latLng: Pair<Double, Double>) {
        val url = "https://www.google.com/maps/search/?api=1&query=${latLng.first},${latLng.second}"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.component = ComponentName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity")
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

}