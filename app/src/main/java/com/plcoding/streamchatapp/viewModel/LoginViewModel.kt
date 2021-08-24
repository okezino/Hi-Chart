package com.plcoding.streamchatapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.streamchatapp.util.Const.MIN_USER_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.call.await
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val chatClient: ChatClient
) : ViewModel() {

    private val _loginEvent = MutableSharedFlow<LoginEvent>()
    val loginEvent = _loginEvent.asSharedFlow()

    private fun isValidUserName(username: String) = username.length >= MIN_USER_NAME

    fun connectUsers(username: String) {
        viewModelScope.launch {
            if (isValidUserName(username = username)) {
                Log.d("VIEWMODEL","FUCK")
                Log.d("VIEWMODEL","FUCK$MIN_USER_NAME")
                Log.d("VIEWMODEL","FUCK${username}")

                val result = chatClient.connectGuestUser(
                    userId = username.trim(),
                    username = username.trim()
                ).await()
            if(result.isError){
                _loginEvent.emit(LoginEvent.ErrorLogin(result.error().message ?: "Unknown Error"))
                return@launch
            }
                _loginEvent.emit(LoginEvent.Success)
            }else {
                _loginEvent.emit(LoginEvent.ErrorUserData)
            }
        }
    }
}

sealed class LoginEvent{
    object ErrorUserData : LoginEvent()
    data class ErrorLogin(val message: String) : LoginEvent()
    object Success : LoginEvent()
}
