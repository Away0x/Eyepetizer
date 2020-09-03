package com.away0x.eyepetizer.ui

import android.Manifest
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import com.away0x.eyepetizer.R
import com.away0x.eyepetizer.common.extension.edit
import com.away0x.eyepetizer.common.extension.sharedPreferences
import com.away0x.eyepetizer.common.ui.BaseActivity
import com.away0x.eyepetizer.common.utils.GlobalUtil
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 闪屏页面，应用程序首次启动入口。
 */
class SplashActivity : BaseActivity() {

    private val job by lazy { Job() }

    private val splashDuration = 3 * 1000L

    private val alphaAnimation by lazy {
        AlphaAnimation(0.5f, 1.0f).apply {
            duration = splashDuration
            fillAfter = true
        }
    }

    private val scaleAnimation by lazy {
        ScaleAnimation(1f, 1.05f, 1f, 1.05f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
            duration = splashDuration
            fillAfter = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWriteExternalStoragePermission()
    }

    override fun setupViews() {
        super.setupViews()

        // 动画
        ivSlogan.startAnimation(alphaAnimation)
        ivSplashPicture.startAnimation(scaleAnimation)

        // 时间到了，跳转页面
        CoroutineScope(job).launch {
            delay(splashDuration)
//            MainActivity.start(this@SplashActivity)
//            finish()
        }

        isFirstEntryApp = false
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun requestWriteExternalStoragePermission() {
        PermissionX.init(this@SplashActivity).permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .onExplainRequestReason { scope, deniedList ->
                val message = GlobalUtil.getString(R.string.request_permission_picture_processing)
                scope.showRequestReasonDialog(deniedList, message, GlobalUtil.getString(R.string.ok), GlobalUtil.getString(R.string.cancel))
            }
            .onForwardToSettings { scope, deniedList ->
                val message = GlobalUtil.getString(R.string.request_permission_picture_processing)
                scope.showForwardToSettingsDialog(deniedList, message, GlobalUtil.getString(R.string.settings), GlobalUtil.getString(R.string.cancel))
            }
            .request { _, _, _ ->
                requestReadPhoneStatePermission()
            }
    }

    private fun requestReadPhoneStatePermission() {
        PermissionX.init(this@SplashActivity).permissions(Manifest.permission.READ_PHONE_STATE)
            .onExplainRequestReason { scope, deniedList ->
                val message = GlobalUtil.getString(R.string.request_permission_access_phone_info)
                scope.showRequestReasonDialog(deniedList, message, GlobalUtil.getString(R.string.ok), GlobalUtil.getString(R.string.cancel))
            }
            .onForwardToSettings { scope, deniedList ->
                val message = GlobalUtil.getString(R.string.request_permission_access_phone_info)
                scope.showForwardToSettingsDialog(deniedList, message, GlobalUtil.getString(R.string.settings), GlobalUtil.getString(R.string.cancel))
            }
            .request { _, _, _ ->
                setContentView(R.layout.activity_splash) // 加载页面
            }
    }

    companion object {

        /**
         * 是否首次进入APP应用
         */
        var isFirstEntryApp: Boolean
            get() = sharedPreferences.getBoolean("is_first_entry_app", true)
            set(value) = sharedPreferences.edit { putBoolean("is_first_entry_app", value) }
    }
}