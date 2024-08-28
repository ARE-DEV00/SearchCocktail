package kr.co.are.searchcocktail.core.youtubeplayer.component

import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import kr.co.are.searchcocktail.core.youtubeplayer.bridge.WebViewYoutubePlayerBridge
import timber.log.Timber

@Composable
fun YoutubePlayer(
    modifier: Modifier,
    bridgeName:String,
    videoUrl: String,
    onPlayTimeUpdated: (Float) -> Unit
) {
    val webView = WebView(LocalContext.current).apply {
        settings.javaScriptEnabled = true
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        addJavascriptInterface(WebViewYoutubePlayerBridge(onPlayTimeUpdated), bridgeName)
    }

    extractYouTubeId(videoUrl)?.let { videoId ->
        val htmlData = getHtmlYoutube(bridgeName, videoId)
        Box(modifier = modifier.fillMaxWidth()) {
            AndroidView(
                modifier = modifier.fillMaxWidth(),
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
}

//Youtube ID 추출
fun extractYouTubeId(url: String): String? {
    if(url.contains("v=")){
        val split = url.split("v=")
        Timber.d("#### split : ${split[1]}")
        return split[1]
    }
    return null
}

fun getHtmlYoutube(bridgeName: String, videoId: String): String {
    return """
        <!DOCTYPE html>
            <html>
              <meta name="viewport" content="width=device-width, initial-scale=1">
              <style>
                body {
                  height: 100%;
                  width: 100%;
                  margin: 0;
                  padding: 0;
                }
                
                #player {
                 position: fixed;/*꽉 찬 화면*/
                 top: 0;
                 left: 0;
                 width: 100%;
                 height: 100%;
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
                      height: '100%',
                      width: '100%',
                      videoId: '${videoId}',
                      events: {
                        'onReady': onPlayerReady,
                        'onStateChange': onPlayerStateChange
                      }
                    });
                  }
            
                  function onPlayerReady(event) {
                    
                  }
            
                  function onPlayerStateChange(event) {
                    if (event.data == YT.PlayerState.PLAYING) { // 동영상이 재생 중일 때
                      startUpdatingTime(); // 재생 시간을 업데이트하는 타이머 시작
                    } else {
                      stopUpdatingTime(); // 재생이 멈추면 타이머 중지
                    }
                  }
            
                  function startUpdatingTime() {
                    updateCurrentTime();
                    timeUpdater = setInterval(updateCurrentTime, 1000); // 1초마다 현재 재생 시간을 업데이트
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
