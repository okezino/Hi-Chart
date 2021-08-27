package com.plcoding.streamchatapp.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.livedata.ChatDomain
import javax.inject.Inject

@HiltAndroidApp
class ChatApp: Application() {
    @Inject lateinit var  client : ChatClient

    override fun onCreate() {
        super.onCreate()
        ChatDomain.Builder(client = client, applicationContext).build()
    }
}