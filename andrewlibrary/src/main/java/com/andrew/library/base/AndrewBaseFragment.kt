package com.andrew.library.base

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.andrew.library.utils.AndrewAppUtils

abstract class AndrewBaseFragment : Fragment() {

    private var permissions: Array<out String>? = null
    private var requestCode: Int = -100
    private var description: String = ""
    private var activityRequestPermission = false
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        Logger.e("#########################   onAttach(context: Context) ")
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        Logger.e("#########################  onCreate(savedInstanceState: Bundle?)")
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        Logger.e("#########################   onCreateView #########1##################")
//        return super.onCreateView(inflater, container, savedInstanceState)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        Logger.e("#########################   onViewCreated ###########2################")
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        Logger.e("#########################   onActivityCreated ###########2################")
//    }
//
//    override fun onStart() {
//        super.onStart()
//        Logger.e("#########################   onStart ###########2################")
//    }
//
//    override fun onResume() {
//        super.onResume()
//        Logger.e("#########################   onResume ###########2################")
//    }
//
//    override fun onPause() {
//        super.onPause()
//        Logger.e("#########################   onPause ###########2################")
//    }
//
//    override fun onStop() {
//        super.onStop()
//        Logger.e("#########################   onStop ###########2################")
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        Logger.e("#########################   onDestroy ###########2################")
//    }

    private fun checkDangerousPermissions(permissions: Array<out String>): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        activity?.let {
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(
                        it,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(it, permission)
                ) {
                    return false
                }
            }
        }
        return true
    }

    open fun handlePermissionResult(requestCode: Int, success: Boolean) {}

    open fun requestDangerousPermissions(
        permissions: Array<out String>, requestCode: Int,
        permissionsDescription: String
    ) {
        this.permissions = permissions
        this.requestCode = requestCode
        this.description = permissionsDescription
        if (checkDangerousPermissions(permissions)) {
            handlePermissionResult(requestCode, true)
            return
        }
//        ActivityCompat.requestPermissions(this, permissions, requestCode)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activityRequestPermission = true
            this.requestPermissions(permissions, requestCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (activityRequestPermission) {
            var granted = true
            var deniedNoHit = false//??????????????????????????????
//        ?????????????????????????????????????????????????????????true
//        ???????????????????????????true
//        ??????????????????????????????????????????false
//        ??????????????????false
            grantResults.forEachIndexed { index, value ->
                if (value != PackageManager.PERMISSION_GRANTED) {
                    granted = false
                    if (!shouldShowRequestPermissionRationale(permissions[index])) {// ??????????????????????????????????????????false
                        deniedNoHit = true
                    }
                }
            }
            if (granted) {
                handlePermissionResult(requestCode, true)
            } else {
                activity?.let { activity ->
                    if (deniedNoHit) { //??????????????????/?????????????????????
                        val alertDialog = AlertDialog.Builder(activity).setTitle("??????")
                            .setMessage("????????????" + description + "??????????????????????????????????????????")
                            .setNegativeButton("??????") { dialog, _ ->
                                dialog.dismiss()
                                handlePermissionResult(requestCode, false)
                            }
                            .setPositiveButton("?????????") { dialog, _ ->
                                dialog.dismiss()
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                val uri = Uri.fromParts(
                                    "package",
                                    activity.packageName,
                                    null
                                ) //????????????"package",???????????????????????????
                                intent.data = uri
                                startActivityForResult(intent, requestCode)
                            }.create()
                        alertDialog.setCancelable(false)
                        alertDialog.setCanceledOnTouchOutside(false)
                        alertDialog.show()
                    } else {   //????????????
                        val alertDialog = AlertDialog.Builder(activity).setTitle("????????????")
                            .setMessage("????????????" + description + "??????????????????????????????????????????")
                            .setNegativeButton("??????") { dialog, _ ->
                                dialog.dismiss()
                                handlePermissionResult(requestCode, false)
                            }
                            .setPositiveButton("?????????") { dialog, _ ->
                                dialog.dismiss()
                                requestDangerousPermissions(permissions, requestCode, description)
                            }.create()
                        alertDialog.setCancelable(false)
                        alertDialog.setCanceledOnTouchOutside(false)
                        alertDialog.show()
                    }
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
        activityRequestPermission = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == this.requestCode) {
            this.permissions?.let { requestDangerousPermissions(it, this.requestCode,description) }
            this.permissions = null
        }
    }

    override fun startActivity(intent: Intent) {
        if (AndrewAppUtils.checkIntentActivity(intent)) {
            super.startActivity(intent)
        }
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        if (AndrewAppUtils.checkIntentActivity(intent)) {
            super.startActivityForResult(intent, requestCode)
        }
    }
}