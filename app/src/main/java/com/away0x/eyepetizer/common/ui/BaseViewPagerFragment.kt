package com.away0x.eyepetizer.common.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.away0x.eyepetizer.R
import com.away0x.eyepetizer.common.extension.setOnClickListener
import com.away0x.eyepetizer.common.extension.showToast
import com.away0x.eyepetizer.ui.search.SearchFragment
import com.flyco.tablayout.CommonTabLayout
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener

/**
 * Fragment基类，适用场景：页面含有ViewPager+TabLayout的界面。
 */
abstract class BaseViewPagerFragment : BaseFragment() {

    protected var viewPager: ViewPager2? = null
    protected var tabLayout: CommonTabLayout? = null
    protected var pageChangeCallback: PageChangeCallback? = null
    protected val adapter: VpAdapter by lazy { VpAdapter(getActivity()!!).apply { addFragments(createFragments) } }
    protected var offscreenPageLimit = 1

    abstract val createTitles: ArrayList<CustomTabEntity>
    abstract val createFragments: Array<Fragment>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        pageChangeCallback?.run { viewPager?.unregisterOnPageChangeCallback(this) }
        pageChangeCallback = null
    }

    /**
     * TODO
     */
    open fun setupViews() {
        initVeiwPager()
//        setOnClickListener(ivCalendar, ivSearch) {
//            if (this == ivCalendar) {
//                R.string.currently_not_supported.showToast()
//            } else if (this == ivSearch) {
//                SearchFragment.switchFragment(activity)
//            }
//        }
    }

    protected fun initVeiwPager() {
        viewPager = rootView?.findViewById(R.id.viewPager)
        tabLayout = rootView?.findViewById(R.id.tabLayout)

        viewPager?.offscreenPageLimit = offscreenPageLimit
        viewPager?.adapter = adapter
        tabLayout?.setTabData(createTitles)
        tabLayout?.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) { viewPager?.currentItem = position }
            override fun onTabReselect(position: Int) {}
        })
        pageChangeCallback = PageChangeCallback()
        viewPager?.registerOnPageChangeCallback(pageChangeCallback!!)
        Log.d("asd", 123.toString())
    }

    inner class PageChangeCallback : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            tabLayout?.currentTab = position
        }
    }

    inner class VpAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        private val fragments = mutableListOf<Fragment>()
        fun addFragments(fragmentArr: Array<Fragment>) { fragments.addAll(fragmentArr) }
        override fun getItemCount() = fragments.size
        override fun createFragment(position: Int) = fragments[position]
    }

}