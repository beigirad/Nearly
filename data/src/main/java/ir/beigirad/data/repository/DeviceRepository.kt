package ir.beigirad.data.repository

import android.Manifest
import androidx.annotation.RequiresPermission
import io.reactivex.Observable
import ir.beigirad.data.model.GpsLocationEntity

/**
 * Created by Farhad Beigirad on 3/1/19.
 */
interface DeviceRepository {

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun getLocation(): Observable<GpsLocationEntity>

}