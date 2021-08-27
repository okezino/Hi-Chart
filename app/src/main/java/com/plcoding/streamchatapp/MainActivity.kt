package com.plcoding.streamchatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.plcoding.streamchatapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.offline.ChatDomain
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var  client : ChatClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ChatDomain.Builder(client, applicationContext).build()

    }
}