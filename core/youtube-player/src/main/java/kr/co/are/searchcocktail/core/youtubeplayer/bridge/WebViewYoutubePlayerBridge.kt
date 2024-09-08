package kr.co.are.searchcocktail.core.youtubeplayer.bridge

import android.webkit.JavascriptInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WebViewYoutubePlayerBridge(
    private val onUpdatedYoutubePlayTime: (Float) -> Unit
) {
    @JavascriptInterface
    fun onUpdatedPlayTime(time:Float) {
        CoroutineScope(Dispatchers.Main).launch {
            onUpdatedYoutubePlayTime(time)
        }
    }
}