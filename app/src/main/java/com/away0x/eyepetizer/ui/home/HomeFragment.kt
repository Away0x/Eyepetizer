package com.away0x.eyepetizer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.away0x.eyepetizer.R
import com.away0x.eyepetizer.common.ui.BaseViewPagerFragment
import com.away0x.eyepetizer.common.utils.GlobalUtil
import com.away0x.eyepetizer.logic.model.TabEntity
import com.away0x.eyepetizer.ui.home.commend.CommendFragment
import com.away0x.eyepetizer.ui.home.daily.DailyFragment
import com.away0x.eyepetizer.ui.home.discovery.DiscoveryFragment
import com.flyco.tablayout.listener.CustomTabEntity

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

    override val createFragments: Array<Fragment> = arrayOf(
        DiscoveryFragment(),
        CommendFragment(),
        DailyFragment()
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater.inflate(R.layout.fragment_view_pager_container, container, false))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewPager?.currentItem = 1
    }

}