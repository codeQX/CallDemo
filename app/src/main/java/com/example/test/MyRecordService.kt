package com.example.test

import android.app.Service
import android.content.Intent
import android.media.MediaRecorder
import android.os.*
import android.text.format.DateFormat
import androidx.annotation.RequiresApi
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
        val RECORD_TIME = 4 * 60 * 60 * 1000L
//    val RECORD_TIME = 40 * 1000L
    val MSG_STOP = 1
    val MSG_CHECK = 2


    lateinit var mediaRecorder: MediaRecorder


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        Timber.d("创建录音服务")
        initMediaRecorder()

        handler.sendEmptyMessageDelayed(MSG_STOP, RECORD_TIME)
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

    @RequiresApi(Build.VERSION_CODES.O)
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
                }
                MSG_CHECK -> {
                    Timber.d("file size:${FileUtils.getFileSize(file)}")
                    sendEmptyMessageDelayed(MSG_CHECK, 10 * 1000)
                }
            }
        }
    }

}