package com.away0x.eyepetizer.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.away0x.eyepetizer.R
import com.away0x.eyepetizer.common.ui.BaseActivity

/**
 * 登录界面。
 */
class LoginActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}