package com.example.test

import android.app.Application
import com.blankj.utilcode.util.Utils
import timber.log.Timber
import timber.log.Timber.DebugTree

class App:Application() {
    override fun onCreate() {
        super.onCreate()

        Utils.init(this)
        //Timber日志打印
        //Timber 是一个日志框架容器,外部使用统一的Api,内部可以动态的切换成任何日志框架(打印策略)进行日志打印
        //并且支持添加多个日志框架(打印策略),做到外部调用一次 Api,内部却可以做到同时使用多个策略
        //比如添加三个策略,一个打印日志,一个将日志保存本地,一个将日志上传服务器
        Timber.plant(DebugTree())


    }
}