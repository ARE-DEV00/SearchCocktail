package kr.co.are.searchcocktail.core.youtubeplayer.bridge

import android.webkit.JavascriptInterface
import timber.log.Timber

class WebViewYoutubePlayerBridge(
    private val onUpdatedYoutubePlayTime: (Int) -> Unit
) {
    @JavascriptInterface
    fun onUpdatedPlayTime(time:Int) {
        onUpdatedYoutubePlayTime(time)
    }
}