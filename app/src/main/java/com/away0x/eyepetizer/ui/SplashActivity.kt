package com.away0x.eyepetizer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.away0x.eyepetizer.R

/**
 * 闪屏页面，应用程序首次启动入口。
 */
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}