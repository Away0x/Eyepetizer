package com.away0x.eyepetizer.event

/**
 * EventBus通知Tab页切换界面。
 */
open class SwitchPagesEvent(var activityClass: Class<*>? = null) : MessageEvent()