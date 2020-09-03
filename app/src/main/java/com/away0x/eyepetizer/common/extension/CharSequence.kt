package com.away0x.eyepetizer.common.extension

import android.widget.Toast
import com.away0x.eyepetizer.EyepetizerApplication

/**
 * 弹出Toast提示。
 *
 * @param duration 显示消息的时间  Either {@link #LENGTH_SHORT} or {@link #LENGTH_LONG}
 */
fun CharSequence.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(EyepetizerApplication.context, this, duration).show()
}

/** TODO:
 * VasSonic预加载session。
 *
 * @param CharSequence 预加载url
 */
//fun CharSequence.preCreateSession(): Boolean {
//    if (!SonicEngine.isGetInstanceAllowed()) {
//        SonicEngine.createInstance(SonicRuntimeImpl(EyepetizerApplication.context), SonicConfig.Builder().build())
//    }
//    val sessionConfigBuilder = SonicSessionConfig.Builder().apply { setSupportLocalServer(true) }
//    val preloadSuccess = SonicEngine.getInstance().preCreateSession(this.toString(), sessionConfigBuilder.build())
//    logD("preCreateSession()", "${this}\t:${if (preloadSuccess) "Preload start up success!" else "Preload start up fail!"}")
//    return preloadSuccess
//}