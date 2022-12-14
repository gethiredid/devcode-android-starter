package devcode.android.starter.base

import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import org.koin.androidx.scope.ScopeActivity

abstract class BaseActivity : ScopeActivity() {
    protected var isAttached: Boolean = false

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        isAttached = true
    }

    fun isPermissionsAllowed(permissions: Array<String>, shouldRequestIfNotAllowed: Boolean = false, requestCode: Int = -1): Boolean {
        var isGranted = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in permissions) {
                isGranted = ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
                if (!isGranted)
                    break
            }
        }
        if (!isGranted && shouldRequestIfNotAllowed) {
            if (requestCode.equals(-1))
                throw RuntimeException("Send request code in third parameter")
            requestRequiredPermissions(permissions, requestCode)
        }

        return isGranted
    }

    private fun requestRequiredPermissions(permissions: Array<String>, requestCode: Int) {
        val pendingPermissions: ArrayList<String> = ArrayList()
        permissions.forEach { permission ->
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED)
                pendingPermissions.add(permission)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val array = arrayOfNulls<String>(pendingPermissions.size)
            pendingPermissions.toArray(array)
            requestPermissions(array, requestCode)
        }
    }

    fun isAllPermissionsGranted(grantResults: IntArray): Boolean {
        for (grantResult in grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false
            }
        }
        return true
    }
}
