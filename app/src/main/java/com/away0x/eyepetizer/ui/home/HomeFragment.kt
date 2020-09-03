package com.away0x.eyepetizer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.away0x.eyepetizer.R
import com.away0x.eyepetizer.common.ui.BaseViewPagerFragment
import com.away0x.eyepetizer.common.utils.GlobalUtil
import com.away0x.eyepetizer.event.MessageEvent
import com.away0x.eyepetizer.event.RefreshEvent
import com.away0x.eyepetizer.event.SwitchPagesEvent
import com.away0x.eyepetizer.logic.model.TabEntity
import com.away0x.eyepetizer.ui.home.commend.CommendFragment
import com.away0x.eyepetizer.ui.home.daily.DailyFragment
import com.away0x.eyepetizer.ui.home.discovery.DiscoveryFragment
import com.flyco.tablayout.listener.CustomTabEntity
import kotlinx.android.synthetic.main.layout_main_page_title_bar.*
import org.greenrobot.eventbus.EventBus

/**
 * 首页主界面。
 */
class HomeFragment : BaseViewPagerFragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override val createTitles = ArrayList<CustomTabEntity>().apply {
        add(TabEntity(GlobalUtil.getString(R.string.discovery)))
        add(TabEntity(GlobalUtil.getString(R.string.commend)))
        add(TabEntity(GlobalUtil.getString(R.string.daily)))
    }

    override val createFragments: Array<Fragment> = arrayOf(DiscoveryFragment.newInstance(), CommendFragment.newInstance(), DailyFragment.newInstance())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater.inflate(R.layout.fragment_view_pager_container, container, false))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ivCalendar.visibility = View.VISIBLE
        viewPager?.currentItem = 1
    }

    override fun onMessageEvent(messageEvent: MessageEvent) {
        super.onMessageEvent(messageEvent)

        if (messageEvent is RefreshEvent && this::class.java == messageEvent.activityClass) {
            when (viewPager?.currentItem) {
                0 -> EventBus.getDefault().post(RefreshEvent(DiscoveryFragment::class.java))
                1 -> EventBus.getDefault().post(RefreshEvent(CommendFragment::class.java))
                2 -> EventBus.getDefault().post(RefreshEvent(DailyFragment::class.java))
                else -> {}
            }
        } else if (messageEvent is SwitchPagesEvent) {
            when (messageEvent.activityClass) {
                DiscoveryFragment::class.java -> viewPager?.currentItem = 0
                CommendFragment::class.java -> viewPager?.currentItem = 1
                DailyFragment::class.java -> viewPager?.currentItem = 2
                else -> {}
            }
        }
    }

}