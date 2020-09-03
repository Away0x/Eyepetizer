package com.away0x.eyepetizer.ui.home.daily

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.away0x.eyepetizer.R
import com.away0x.eyepetizer.common.ui.BaseFragment

/**
 * 首页-日报列表界面。
 */
class DailyFragment : BaseFragment() {

    companion object {
        fun newInstance() = DailyFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater.inflate(R.layout.fragment_daily, container, false))
    }

}