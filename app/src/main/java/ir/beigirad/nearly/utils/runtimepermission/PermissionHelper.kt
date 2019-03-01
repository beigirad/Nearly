package ir.beigirad.nearly.utils.runtimepermission

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import ir.beigirad.zeroapplication.withArgs

/**
 * Created by Farhad-Beigirad on 7/14/18.
 */

class PermissionHelper : DialogFragment() {
    private var permissionLiveData = MutableLiveData<PermissionStatus>()

    fun getLiveData(): MutableLiveData<PermissionStatus> {
        return permissionLiveData
    }

    private var permissionRequestCode = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        arguments?.getString(ARG_PERMISSION)?.let { permission ->
            permissionRequestCode = Math.abs(permission.hashCode()) % 655

            val force = arguments?.getBoolean(ARG_FORCE, false) ?: false
            checkPermission(permission, force)
        }

    }

    private fun checkPermission(permission: String, force: Boolean) {
        if (ContextCompat.checkSelfPermission(context!!, permission) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(permission) && !force) {
                permissionLiveData.postValue(
                        PermissionStatus.Blocked(
                                permission
                        )
                )
                dismiss()
            } else {
                requestPermissions(arrayOf(permission), permissionRequestCode)
            }
        } else {
            permissionLiveData.postValue(PermissionStatus.Granted(permission))
            dismiss()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionRequestCode)
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissionLiveData.postValue(
                        PermissionStatus.Granted(
                                permissions[0]
                        )
                )
                dismiss()
            } else {
                permissionLiveData.postValue(
                        PermissionStatus.Denied(
                                permissions[0]
                        )
                )
                dismiss()
            }
    }

    fun request(fragmentManager: FragmentManager) {
        show(fragmentManager, "$permissionRequestCode")
    }


    companion object {
        private val ARG_PERMISSION = "ARG_PERMISSION"
        private val ARG_FORCE = "ARG_FORCE"

        fun newRequest(@Permission permissions: String, force: Boolean): PermissionHelper {
            return PermissionHelper().withArgs {
                putString(ARG_PERMISSION, permissions)
                putBoolean(ARG_FORCE, force)
            }
        }

        fun hasPermission(context: Context, @Permission permission: String): Boolean {
            return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }
    }
}

sealed class PermissionStatus {
    data class Granted(val permission: String) : PermissionStatus()
    data class Denied(val permission: String) : PermissionStatus()
    data class Blocked(val permission: String) : PermissionStatus()
}