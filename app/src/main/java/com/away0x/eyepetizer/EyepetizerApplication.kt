package com.away0x.eyepetizer

import android.app.Application
import android.content.Context
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure

/**
 * Eyepetizer 自定义 Application，在这里进行全局的初始化操作。
 */
class EyepetizerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        context = this

        initUMeng()
    }

    /**
     * 初始化友盟 sdk
     */
    private fun initUMeng() {
        UMConfigure.init(this, "5f504e69636b2b13182bacf2", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null)
        // UMConfigure.setLogEnabled(BuildConfig.DEBUG)
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
    }

    companion object {
        lateinit var context: Context
    }

}