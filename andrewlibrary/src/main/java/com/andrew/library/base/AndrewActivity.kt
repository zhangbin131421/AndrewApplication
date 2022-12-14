package com.andrew.library.base

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.andrew.library.R
import com.andrew.library.observer.LoadingObserver
import com.andrew.library.utils.AndrewAppUtils
import com.blankj.utilcode.util.BarUtils


abstract class AndrewActivity : AppCompatActivity() {
    abstract val layoutId: Int
    var immersiveStatusBar = false
    var statusBarColor = R.color.theme_color
    var fontIconDark = false
    val loadingState = MutableLiveData<Boolean>()

    private var permissions: Array<out String>? = null
    private var requestCode: Int = -100
    private var description: String = ""
    private var activityRequestPermission = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (immersiveStatusBar){
            BarUtils.transparentStatusBar(this)
        }else{
            BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, statusBarColor))
            BarUtils.setNavBarLightMode(this,fontIconDark)
        }
        setContentView(layoutId)
        loadingState.observe(this, LoadingObserver(this))
    }

    private fun checkDangerousPermissions(permissions: Array<out String>): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    permission
                )
            ) {
                return false
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
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            permissions[index]
                        )
                    ) {// ??????????????????????????????????????????false
                        deniedNoHit = true
                    }
                }
            }
            if (granted) {
                handlePermissionResult(requestCode, true)
            } else {
                if (deniedNoHit) { //??????????????????/?????????????????????
                    val alertDialog = AlertDialog.Builder(this).setTitle("??????")
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
                                this.packageName,
                                null
                            ) //????????????"package",???????????????????????????
                            intent.data = uri
                            startActivityForResult(intent, requestCode)
                        }.create()
                    alertDialog.setCancelable(false)
                    alertDialog.setCanceledOnTouchOutside(false)
                    alertDialog.show()
                } else {   //????????????
                    val alertDialog = AlertDialog.Builder(this).setTitle("????????????")
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
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
        activityRequestPermission = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == this.requestCode) {
            this.permissions?.let { requestDangerousPermissions(it, this.requestCode, description) }
            this.permissions = null
        }
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        if (AndrewAppUtils.checkIntentActivity(intent)) {
            super.startActivityForResult(intent, requestCode)
        }
    }
}