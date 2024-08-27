package kr.co.are.searchcocktail.core.youtubeplayer.component

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import kr.co.are.searchcocktail.core.youtubeplayer.bridge.WebViewYoutubePlayerBridge

@Composable
fun YoutubePlayer(
    modifier: Modifier,
    bridgeName:String,
    videoId: String,
    height:String = "250",
    onPlayTimeUpdated: (Float) -> Unit
) {
    val webView = WebView(LocalContext.current).apply {
        settings.javaScriptEnabled = true
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        webViewClient = WebViewClient()
        addJavascriptInterface(WebViewYoutubePlayerBridge(onPlayTimeUpdated), bridgeName)
    }

    val htmlData = getHtmlYoutube(bridgeName, videoId, height)
    Box(modifier = modifier.fillMaxSize()) {
        AndroidView(
            modifier = modifier.fillMaxSize(),
            factory = { webView }) { view ->
            view.loadDataWithBaseURL(
                "https://www.youtube.com",
                htmlData,
                "text/html",
                "UTF-8",
                null
            )
        }
    }


}

fun getHtmlYoutube(bridgeName: String, videoId: String, height:String): String {
    return """
        <!DOCTYPE html>
            <html>
              <meta name="viewport" content="width=device-width, initial-scale=1">
              <style>
                body {
                  margin: 0;
                  padding: 0;
                }
                </style>
              <body>
                <div id="player"></div>
                <script>
                  var tag = document.createElement('script');
            
                  tag.src = "https://www.youtube.com/iframe_api";
                  var firstScriptTag = document.getElementsByTagName('script')[0];
                  firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
            
                  var player;
                  var timeUpdater; // setInterval을 저장할 변수
            
                  function onYouTubeIframeAPIReady() {
                    player = new YT.Player('player', {
                      height: '${height}',
                      width: '100%',
                      videoId: '${videoId}',
                      events: {
                        'onReady': onPlayerReady,
                        'onStateChange': onPlayerStateChange
                      }
                    });
                  }
            
                  function onPlayerReady(event) {
                    // 플레이어가 준비되었을 때 초기 작업을 여기에 추가할 수 있습니다.
                  }
            
                  function onPlayerStateChange(event) {
                    if (event.data == YT.PlayerState.PLAYING) { // 동영상이 재생 중일 때
                      startUpdatingTime(); // 재생 시간을 업데이트하는 타이머 시작
                    } else {
                      stopUpdatingTime(); // 재생이 멈추면 타이머 중지
                    }
                  }
            
                  function startUpdatingTime() {
                    timeUpdater = setInterval(updateCurrentTime, 100); // 0.1초마다 현재 재생 시간을 업데이트
                  }
            
                  function stopUpdatingTime() {
                    clearInterval(timeUpdater); // 타이머 중지
                  }
            
                  function updateCurrentTime() {
                    var currentTime = player.getCurrentTime();
                    ${bridgeName}.onUpdatedPlayTime(currentTime);
                  }
                </script>
              </body>
            </html>
    """.trimIndent()}
