package com.away0x.eyepetizer.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.away0x.eyepetizer.R
import com.away0x.eyepetizer.common.extension.setOnClickListener
import com.away0x.eyepetizer.common.extension.showToast
import com.away0x.eyepetizer.common.ui.BaseActivity
import com.away0x.eyepetizer.common.utils.GlobalUtil
import com.away0x.eyepetizer.common.utils.logD
import com.away0x.eyepetizer.common.worker.DialogAppraiseTipsWorker
import com.away0x.eyepetizer.event.MessageEvent
import com.away0x.eyepetizer.event.RefreshEvent
import com.away0x.eyepetizer.event.SwitchPagesEvent
import com.away0x.eyepetizer.ui.community.CommunityFragment
import com.away0x.eyepetizer.ui.community.commend.CommendFragment as CommunityCommendFragment
import com.away0x.eyepetizer.ui.home.HomeFragment
import com.away0x.eyepetizer.ui.login.LoginActivity
import com.away0x.eyepetizer.ui.mine.MineFragment
import com.away0x.eyepetizer.ui.notification.NotificationFragment
import kotlinx.android.synthetic.main.layout_bottom_navigation_bar.*
import org.greenrobot.eventbus.EventBus

/**
 * Eyepetizer的主界面。
 */
class MainActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    private var backPressTime = 0L

    private var homePageFragment: HomeFragment? = null
    private var communityFragment: CommunityFragment? = null
    private var notificationFragment: NotificationFragment? = null
    private var mineFragment: MineFragment? = null
    private val fragmentManager: FragmentManager by lazy { supportFragmentManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun setupViews() {
        observe()

        setOnClickListener(btnHomePage, btnCommunity, ivRelease, btnNotification, btnMine) {
            when (this) {
                btnHomePage -> {
                    notificationUiRefresh(0)
                    setTabSelection(0)
                }
                btnCommunity -> {
                    notificationUiRefresh(1)
                    setTabSelection(1)
                }
                btnNotification -> {
                    notificationUiRefresh(2)
                    setTabSelection(2)
                }
                ivRelease -> {
                    LoginActivity.start(this@MainActivity)
                }
                btnMine -> {
                    notificationUiRefresh(3)
                    setTabSelection(3)
                }
            }
        }

        setTabSelection(0)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            processBackPressed()
        }
    }

    override fun onMessageEvent(messageEvent: MessageEvent) {
        super.onMessageEvent(messageEvent)
        when {
            messageEvent is SwitchPagesEvent && CommunityCommendFragment::class.java == messageEvent.activityClass -> {
                btnCommunity.performClick()
            }
            else -> {}
        }
    }

    private fun setTabSelection(index: Int) {
        clearAllSelected()
        fragmentManager.beginTransaction().apply {
            hideFragments(this)
            when (index) {
                1 -> {
                    ivCommunity.isSelected = true
                    tvCommunity.isSelected = true
                    if (communityFragment == null) {
                        communityFragment = CommunityFragment()
                        add(R.id.homeActivityFragContainer, communityFragment!!)
                    } else {
                        show(communityFragment!!)
                    }
                }
                2 -> {
                    ivNotification.isSelected = true
                    tvNotification.isSelected = true
                    if (notificationFragment == null) {
                        notificationFragment = NotificationFragment()
                        add(R.id.homeActivityFragContainer, notificationFragment!!)
                    } else {
                        show(notificationFragment!!)
                    }
                }
                3 -> {
                    ivMine.isSelected = true
                    tvMine.isSelected = true
                    if (mineFragment == null) {
                        mineFragment = MineFragment.newInstance()
                        add(R.id.homeActivityFragContainer, mineFragment!!)
                    } else {
                        show(mineFragment!!)
                    }
                }
                else -> {
                    ivHome.isSelected = true
                    tvHomePage.isSelected = true
                    if (homePageFragment == null) {
                        homePageFragment = HomeFragment.newInstance()
                        add(R.id.homeActivityFragContainer, homePageFragment!!)
                    } else {
                        show(homePageFragment!!)
                    }
                }
            }
        }.commitAllowingStateLoss()
    }

    private fun clearAllSelected() {
        ivHome.isSelected = false
        tvHomePage.isSelected = false
        ivCommunity.isSelected = false
        tvCommunity.isSelected = false
        ivNotification.isSelected = false
        tvNotification.isSelected = false
        ivMine.isSelected = false
        tvMine.isSelected = false
    }

    private fun hideFragments(transaction: FragmentTransaction) {
        transaction.run {
            if (homePageFragment != null) hide(homePageFragment!!)
            if (communityFragment != null) hide(communityFragment!!)
            if (notificationFragment != null) hide(notificationFragment!!)
            if (mineFragment != null) hide(mineFragment!!)
        }
    }

    private fun notificationUiRefresh(selectionIndex: Int) {
        when (selectionIndex) {
            0 -> {
                if (ivHome.isSelected) EventBus.getDefault().post(RefreshEvent(HomeFragment::class.java))
            }
            1 -> {
                if (ivCommunity.isSelected) EventBus.getDefault().post(RefreshEvent(CommunityFragment::class.java))
            }
            2 -> {
                if (ivNotification.isSelected) EventBus.getDefault().post(RefreshEvent(NotificationFragment::class.java))
            }
            3 -> {
                if (ivMine.isSelected) EventBus.getDefault().post(RefreshEvent(MineFragment::class.java))
            }
        }
    }

    private fun processBackPressed() {
        val now = System.currentTimeMillis()
        if (now - backPressTime > 2000) {
            String.format(GlobalUtil.getString(R.string.press_again_to_exit), GlobalUtil.appName).showToast()
            backPressTime = now
        } else {
            super.onBackPressed()
        }
    }

    private fun observe() {
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(DialogAppraiseTipsWorker.showDialogWorkRequest.id).observe(this, Observer { workInfo ->
            logD(TAG, "observe: workInfo.state = ${workInfo.state}")
            if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                WorkManager.getInstance(this).cancelAllWork()
            } else if (workInfo.state == WorkInfo.State.RUNNING) {
                if (isActive) {
                    DialogAppraiseTipsWorker.showDialog(this)
                    WorkManager.getInstance(this).cancelAllWork()
                }
            }
        })
    }

}