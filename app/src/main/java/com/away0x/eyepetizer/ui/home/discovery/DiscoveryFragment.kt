package com.away0x.eyepetizer.ui.home.discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.away0x.eyepetizer.R
import com.away0x.eyepetizer.common.ui.BaseFragment

/**
 * 首页-发现列表界面。
 */
class DiscoveryFragment : BaseFragment() {

    companion object {
        fun newInstance() = DiscoveryFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater.inflate(R.layout.fragment_discovery, container, false))
    }

}