package com.example.weddingplanner.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AuthUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)

class AuthViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _uiState = MutableStateFlow(
        AuthUiState(
            username = savedStateHandle["username"] ?: "",
            email = savedStateHandle["email"] ?: "",
            password = savedStateHandle["password"] ?: "",
            confirmPassword = savedStateHandle["confirmPassword"] ?: ""
        )
    )
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    // ----------- UI update methods -----------

    fun onUsernameChange(newValue: String) {
        _uiState.update { it.copy(username = newValue) }
        savedStateHandle["username"] = newValue
    }

    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(email = newValue) }
        savedStateHandle["email"] = newValue
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue) }
        savedStateHandle["password"] = newValue
    }

    fun onConfirmPasswordChange(newValue: String) {
        _uiState.update { it.copy(confirmPassword = newValue) }
        savedStateHandle["confirmPassword"] = newValue
    }

    // ----------- Login Simulation -----------

    fun login() {
        val state = _uiState.value
        if (state.email.isBlank() || state.password.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Email or Password cannot be empty") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            delay(1000) // simulate network delay
            if (state.email == "test@gmail.com" && state.password == "123456") {
                _uiState.update { it.copy(isSuccess = true, isLoading = false) }
            } else {
                _uiState.update { it.copy(isLoading = false, errorMessage = "Invalid credentials") }
            }
        }
    }

    // ----------- Sign Up Simulation -----------

    fun signUp() {
        val state = _uiState.value

        if (state.username.isBlank() || state.email.isBlank() ||
            state.password.isBlank() || state.confirmPassword.isBlank()
        ) {
            _uiState.update { it.copy(errorMessage = "All fields are required") }
            return
        }

        if (state.password != state.confirmPassword) {
            _uiState.update { it.copy(errorMessage = "Passwords do not match") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            delay(1500) // simulate network delay
            _uiState.update { it.copy(isSuccess = true, isLoading = false) }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
