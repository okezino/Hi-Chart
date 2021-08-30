package com.plcoding.streamchatapp.ui.channel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.call.await
import io.getstream.chat.android.client.models.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChannelViewModel @Inject constructor(
    private val client: ChatClient
) : ViewModel() {

    private val _createChannelEvent = MutableSharedFlow<CreateChannel>()
    val createChannelEvent : SharedFlow<CreateChannel>
        get() = _createChannelEvent



    fun createChannel(name: String) {

        viewModelScope.launch {
            if (name.trim().isEmpty()) {
                _createChannelEvent.emit(CreateChannel.Error("Cant create and Empty Channel"))
                return@launch
            }


            var result = client.channel("messaging", UUID.randomUUID().toString())
                .create(mapOf("name" to name.trim())).await()

            if(result.isError){
                _createChannelEvent.emit(CreateChannel.Error(result.error().message ?: "Something when Wrong"))
                return@launch
            }

            _createChannelEvent.emit(CreateChannel.Success)

        }
    }

    fun logout() {
        client.disconnect()
    }

    fun getCurrentUser(): User? {
        return client.getCurrentUser()
    }
    sealed class CreateChannel{
        data class Error(val message : String) : CreateChannel()
        object Success : CreateChannel()
    }
}