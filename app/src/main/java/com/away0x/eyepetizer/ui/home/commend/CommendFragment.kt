package com.away0x.eyepetizer.ui.home.commend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.away0x.eyepetizer.R
import com.away0x.eyepetizer.common.ui.BaseFragment

/**
 * 首页-推荐列表界面。
 */
class CommendFragment : BaseFragment() {

    companion object {
        fun newInstance() = CommendFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater.inflate(R.layout.fragment_commend2, container, false))
    }

}