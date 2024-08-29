package kr.co.are.searchcocktail.core.youtubeplayer.bridge

import android.webkit.JavascriptInterface
import timber.log.Timber

class WebViewYoutubePlayerBridge(
    private val onUpdatedYoutubePlayTime: (Float) -> Unit
) {
    @JavascriptInterface
    fun onUpdatedPlayTime(time:Float) {
        onUpdatedYoutubePlayTime(time)
    }
}