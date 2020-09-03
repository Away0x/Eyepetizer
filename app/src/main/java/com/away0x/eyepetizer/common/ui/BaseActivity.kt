package com.away0x.eyepetizer.common.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import com.away0x.eyepetizer.R
import com.away0x.eyepetizer.event.MessageEvent
import com.away0x.eyepetizer.common.utils.ActivityCollector
import com.away0x.eyepetizer.common.utils.logD
import com.gyf.immersionbar.ImmersionBar
import com.umeng.analytics.MobclickAgent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.ref.WeakReference

/** 应用程序中所有Activity的基类。*/
open class BaseActivity : AppCompatActivity() {

    /** 日志输出标志 */
    protected val TAG = this.javaClass.simpleName
    /** 判断当前Activity是否在前台。*/
    protected var isActive = false
    /** 当前Activity的实例。 */
    protected var activity: Activity? = null
    /** 当前Activity的弱引用，防止内存泄露 */
    private var activityWR: WeakReference<Activity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity = this
        activityWR = WeakReference(activity!!)
        ActivityCollector.pushTask(activityWR)

        logD(
            TAG,
            "BaseActivity-->onCreate()"
        )
        EventBus.getDefault().register(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        logD(
            TAG,
            "BaseActivity-->onSaveInstanceState()"
        )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        logD(
            TAG,
            "BaseActivity-->onRestoreInstanceState()"
        )
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        logD(
            TAG,
            "BaseActivity-->onNewIntent()"
        )
    }

    override fun onRestart() {
        super.onRestart()
        logD(
            TAG,
            "BaseActivity-->onRestart()"
        )
    }

    override fun onStart() {
        super.onStart()
        logD(
            TAG,
            "BaseActivity-->onStart()"
        )
    }

    override fun onResume() {
        super.onResume()
        logD(
            TAG,
            "BaseActivity-->onResume()"
        )
        isActive = true
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        logD(
            TAG,
            "BaseActivity-->onPause()"
        )
        isActive = false
        MobclickAgent.onPause(this)
    }

    override fun onStop() {
        super.onStop()
        logD(
            TAG,
            "BaseActivity-->onStop()"
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        activity = null
        ActivityCollector.removeTask(activityWR)
        logD(
            TAG,
            "BaseActivity-->onDestroy()"
        )
        EventBus.getDefault().unregister(this)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setStatusBarBackground(R.color.colorPrimaryDark)
        setupViews()
    }

    override fun setContentView(layoutView: View) {
        super.setContentView(layoutView)
        setStatusBarBackground(R.color.colorPrimaryDark)
        setupViews()
    }

    /**
     * TODO:
     */
    protected open fun setupViews() {
//        val navigateBefore = findViewById<ImageView>(R.id.ivNavigateBefore)
//        val tvTitle = findViewById<TextView>(R.id.tvTitle)
//        navigateBefore?.setOnClickListener { finish() }
//        tvTitle?.isSelected = true  //获取焦点，实现跑马灯效果。
    }

    /**
     * 设置状态栏背景色
     */
    open fun setStatusBarBackground(@ColorRes statusBarColor: Int) {
        ImmersionBar.with(this)
            .autoStatusBarDarkModeEnable(true, 0.2f) // 自动状态栏字体变色
            .statusBarColor(statusBarColor) // 状态栏颜色，不写默认透明色
            .fitsSystemWindows(true) // 解决状态栏和布局重叠问题
            .init()
    }

    /**
     * 接收 event bus 事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageEvent(messageEvent: MessageEvent) {}

    /**TODO:
     * 调用系统原生分享
     *
     * @param shareContent 分享内容
     * @param shareType SHARE_MORE=0，SHARE_QQ=1，SHARE_WECHAT=2，SHARE_WEIBO=3，SHARE_QQZONE=4
     */
    protected fun share(shareContent: String, shareType: Int) {
        // ShareUtil.share(this, shareContent, shareType)
    }

    /**TODO:
     * 弹出分享对话框
     *
     * @param shareContent 分享内容
     */
    protected fun showDialogShare(shareContent: String) {
        // com.eyepetizer.android.extension.showDialogShare(this, shareContent)
    }

}