package com.bondidos.feature_auth.auth_screen

import androidx.lifecycle.ViewModel
import com.bondidos.navigation_api.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val appNavigator: AppNavigator
) : ViewModel() {
    //todo refactor to MVI
}
