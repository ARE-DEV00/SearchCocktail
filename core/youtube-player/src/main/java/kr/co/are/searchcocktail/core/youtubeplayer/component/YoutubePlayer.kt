package kr.co.are.searchcocktail.core.youtubeplayer.component

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.viewinterop.AndroidView
import kr.co.are.searchcocktail.core.youtubeplayer.bridge.WebViewYoutubePlayerBridge
import timber.log.Timber

@Composable
fun YoutubePlayer(
    modifier: Modifier,
    bridgeName: String,
    videoUrl: String,
    onPlayTimeUpdated: (Float) -> Unit,
    onSetPlayTime: (setPlayTime: (Float) -> Unit) -> Unit
) {
    val activity = LocalView.current.context as Activity

    var isFullScreen by remember { mutableStateOf(false) }
    var customView: View? by remember { mutableStateOf(null) }
    var customViewCallback: WebChromeClient.CustomViewCallback? by remember { mutableStateOf(null) }
    val context = LocalContext.current

    val webView = remember {
        WebView(context).apply {
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            webChromeClient = object : WebChromeClient() {
                override fun onShowCustomView(view: View, callback: CustomViewCallback) {
                    // 전체 화면 모드 시작
                    super.onShowCustomView(view, callback)
                    isFullScreen = true

                    if (customView != null) {
                        onHideCustomView()
                        return
                    }

                    customView = view
                    (activity.window.decorView as FrameLayout).addView(
                        customView,
                        FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                        ),
                    )
                }

                override fun onHideCustomView() {
                    // 전체 화면 모드 종료
                    isFullScreen = false
                    (activity.window.decorView as FrameLayout).removeView(customView)
                    customView = null
                }
            }
            addJavascriptInterface(WebViewYoutubePlayerBridge(onPlayTimeUpdated), bridgeName)
        }
    }

    LaunchedEffect(key1 = Unit) {
        // setPlayTime 함수를 내부에서 생성 후 외부로 전달
        val setPlayerTime: (Float) -> Unit = { time ->
            webView.evaluateJavascript("setPlayTime($time);", null)
        }

        onSetPlayTime(setPlayerTime)
    }

    extractYouTubeId(videoUrl)?.let { videoId ->
        val htmlData = getHtmlYoutube(bridgeName, videoId)
        Box(modifier = modifier.fillMaxWidth()) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
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
    if (url.contains("v=")) {
        val split = url.split("v=")
        //Timber.d("#### split : ${split[1]}")
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
                  
                  function setPlayTime(time) {
                    if (player != null) {
                      player.seekTo(time, true);
                      updateCurrentTime();//시간 변경 후 현재 시간 업데이트
                    }
                  }
                  
                </script>
              </body>
            </html>
    """.trimIndent()
}
