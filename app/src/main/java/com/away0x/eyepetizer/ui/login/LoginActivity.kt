package com.away0x.eyepetizer.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.away0x.eyepetizer.R
import com.away0x.eyepetizer.common.extension.*
import com.away0x.eyepetizer.common.ui.BaseActivity
import com.away0x.eyepetizer.common.utils.GlobalUtil
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_title_bar.*

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
        setStatusBarBackground(R.color.black)
    }

    override fun setupViews() {
        super.setupViews()

        initTitleBar()
        initForm()
        initListener()
    }

    /**
     * 修改 title bar 的样式
     */
    private fun initTitleBar() {
        titleBar.layoutParams.height = resources.getDimensionPixelSize(R.dimen.actionBarSizeSecondary)
        titleBar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent)) // 透明背景

        val padding = dp2px(9f)
        ivNavigateBefore.setPadding(padding, padding, padding, padding)
        ivNavigateBefore.setImageResource(R.drawable.ic_close_white_24dp)

        tvRightText.visible()
        tvRightText.text = GlobalUtil.getString(R.string.forgot_password)
        tvRightText.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.white))
        tvRightText.textSize = 12f

        divider.gone()
    }

    private fun initForm() {
        etPhoneNumberOrEmail.setDrawable(ContextCompat.getDrawable(this, R.drawable.ic_person_white_18dp), 18f, 18f, 0)
        etPassWord.setDrawable(ContextCompat.getDrawable(this, R.drawable.ic_password_white_lock_18dp), 18f, 18f, 0)
    }

    private fun initListener() {
        setOnClickListener(tvRightText, tvUserLogin, tvUserRegister, tvAuthorLogin, tvUserAgreement, tvUserLogin, ivWechat, ivSina, ivQQ) {
            when (this) {
                tvUserLogin, ivWechat, ivSina, ivQQ -> {
                    R.string.currently_not_supported.showToast()
                }
                else -> {}
            }
        }
    }

}