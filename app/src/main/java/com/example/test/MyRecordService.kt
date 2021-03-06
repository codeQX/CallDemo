package com.example.test

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaRecorder
import android.os.*
import android.text.format.DateFormat
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.PathUtils
import timber.log.Timber
import java.io.File
import java.util.*

/**
 * =================================================================================================
 * 默认描述
 *
 * Author: qixin
 * Date: 2021/6/25 下午 5:35
 * =================================================================================================
 */
class MyRecordService : Service() {
    val RECORD_TIME = 60 * 60 * 1000L
    val MSG_STOP = 1
    val MSG_CHECK = 2


    lateinit var mediaRecorder: MediaRecorder


    override fun onCreate() {
        super.onCreate()
        Timber.d("创建录音服务")

        //8.0以上系统需要创建前台服务
        newForegroundService()

        //初始化录音
        initMediaRecorder()

        //发送延迟消息销毁服务
        handler.sendEmptyMessageDelayed(MSG_STOP, RECORD_TIME)
    }

    private fun newForegroundService() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "channel_id_record"
        val channel = NotificationChannel(channelId, "前台通知", NotificationManager.IMPORTANCE_HIGH)
        manager.createNotificationChannel(channel)

        val intent = Intent(this, MyDialerActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, 0)
        val notification = NotificationCompat.Builder(this, "r service")
            .setChannelId(channelId)//要给通知设置channel id
            .setContentTitle("title")
            .setContentText("content")
            .setSmallIcon(R.drawable.calendar_icon)
            .setContentIntent(pi)
            .build()
        startForeground(1, notification)
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("开始录音服务")
        startRecord()
        handler.sendEmptyMessage(MSG_CHECK)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null;
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("销毁录音服务")


    }

    var file: File? = null

    private fun initMediaRecorder() {
        mediaRecorder = MediaRecorder()

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)

        val fileName = DateFormat.format("yyyyMMdd_HHmmss", Calendar.getInstance(Locale.CHINA))
        file = File(PathUtils.getExternalAppFilesPath() + fileName + ".m4a")

        mediaRecorder.setOutputFile(file)
    }

    private fun startRecord() {
        //开始录音
        mediaRecorder.prepare()
        mediaRecorder.start()

    }

    private fun stopRecord() {
        mediaRecorder.stop()
        mediaRecorder.release()
    }


    private val handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                MSG_STOP -> {
                    removeMessages(MSG_CHECK)

                    stopRecord()

                    stopSelf()

                    postDelayed({
                        //启动一个新的当前服务
                        startService(Intent(this@MyRecordService, MyRecordService::class.java))
                    }, 1000)
                }
                MSG_CHECK -> {
                    Timber.d("file size:${FileUtils.getFileSize(file)}")
                    sendEmptyMessageDelayed(MSG_CHECK, 5 * 1000)
                }
            }
        }
    }

}