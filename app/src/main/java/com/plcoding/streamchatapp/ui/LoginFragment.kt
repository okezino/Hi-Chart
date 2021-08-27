package com.plcoding.streamchatapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.plcoding.streamchatapp.R
import com.plcoding.streamchatapp.databinding.FragmentLoginBinding
import com.plcoding.streamchatapp.util.Const
import com.plcoding.streamchatapp.util.safeNavigation
import com.plcoding.streamchatapp.viewModel.LoginEvent
import com.plcoding.streamchatapp.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlin.concurrent.thread

@AndroidEntryPoint
class LoginFragment : BindingFragment<FragmentLoginBinding>() {

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentLoginBinding::inflate
    private val viewModel : LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnConfirm.setOnClickListener {
            connectingState()
            viewModel.connectUsers(binding.etUsername.text.toString())
        }

        binding.etUsername.addTextChangedListener {
            binding.etUsername.error = null
        }
        subscribeOnLoginState()

    }

    private fun connectingState(){
       binding.progressBar.isVisible = true
       binding.btnConfirm.isEnabled = false
    }

    private fun subscribeOnLoginState(){
        lifecycleScope.launchWhenStarted {
            viewModel.loginEvent.collect {
                when(it){
                   is LoginEvent.ErrorUserData ->{
                       idleState()
                       binding.etUsername.error = getString(R.string.error_username_too_short, Const.MIN_USER_NAME)
                    }
                   is LoginEvent.Success ->{
                       idleState()
                       findNavController().safeNavigation(R.id.action_loginFragment_to_channelFragment)
                       Toast.makeText(requireContext(), "Successful Login", Toast.LENGTH_SHORT).show()

                    }
                   is  LoginEvent.ErrorLogin -> {
                         idleState()
                       Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun idleState(){
        binding.progressBar.isVisible = false
        binding.btnConfirm.isEnabled = true
    }
}