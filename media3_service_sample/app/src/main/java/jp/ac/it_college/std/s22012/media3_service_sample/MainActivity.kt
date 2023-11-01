package jp.ac.it_college.std.s22012.media3_service_sample

import android.content.ComponentName
import android.media.browse.MediaBrowser
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
import androidx.media3.common.Player.Listener
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import jp.ac.it_college.std.s22012.media3_service_sample.databinding.ActivityMainBinding
import java.security.AccessController

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var controllerFuture: ListenableFuture<MediaController>
    private val controller: MediaController?
        get() = if (controllerFuture.isDone) controllerFuture.get() else null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.BtPlay.setOnClickListener{ playSample()}
        binding.BtStop.setOnClickListener{ pauseSample()}
    }

    override fun onStart() {
        super.onStart()
        controllerFuture = MediaController.Builder(
            this,
            SessionToken(this, ComponentName(this, MediaPlaybackService::class.java))
        ).buildAsync()
    }

    override fun onStop() {
        MediaController.releaseFuture(controllerFuture)
        super.onStop()
    }
    private fun playSample() {
        val controller = this.controller ?: return

        // 再生したいデータを指定
        controller.setMediaItem(
            MediaItem.fromUri("android.resource://${packageName}/${R.raw.cheen}")
        )
        // 指定した番号のデータに切り替えつつ、デフォルト(多分先頭)に再生位置をセット
        controller.seekToDefaultPosition(0)
        // データの再生を指示
        controller.play()
    }

    private fun pauseSample() {
        val controller = this.controller ?: return
        // データの再生を停止
        controller.stop()
    }

    }


