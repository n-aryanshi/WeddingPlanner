package com.example.weddingplanner.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weddingplanner.R
import com.example.weddingplanner.ui.theme.Ivor
import com.example.weddingplanner.ui.theme.RosyPink
import com.example.weddingplanner.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit
) {
    val viewModel: AuthViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }


    LaunchedEffect(uiState.errorMessage, uiState.isSuccess) {
        when {
            uiState.errorMessage != null -> {
                snackBarHostState.showSnackbar(
                    message = uiState.errorMessage ?: "Unknown error",
                    withDismissAction = true
                )
            }
            uiState.isSuccess -> {
                snackBarHostState.showSnackbar(
                    message = "Login successful 🎉",
                    withDismissAction = true
                )
                onLoginSuccess() // navigate to Home
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            LoginBackgroundCard(
                viewModel = viewModel,
                onSignUpClick = onSignUpClick
            )
        }
    }
}


@Composable
fun LoginBackgroundCard(
    viewModel: AuthViewModel,
    onSignUpClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Ivor),
        contentAlignment = Alignment.Center
    ) {
        // Background card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.60f)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
            colors = CardDefaults.cardColors(containerColor = RosyPink),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoginScreenView(viewModel = viewModel, onSignUpClick = onSignUpClick)
            }
        }

            Image(
                painter = painterResource(R.drawable.couple),
                contentDescription = "couple",
                modifier = Modifier
                    .width(354.dp)
                    .height(390.dp)
                    .offset(10.dp, -195.dp)
            )



    }
}

// ---------------------------------------------------------
// UI → Login Form
// ---------------------------------------------------------
@Composable
fun LoginScreenView(viewModel: AuthViewModel,onSignUpClick: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome Back!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            color = Color.White,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Gray,
                    offset = Offset(4f, 4f),
                    blurRadius = 6f
                )
            ),
            modifier = Modifier.padding(bottom = 18.dp)
        )

        // Email
        OutlinedTextField(
            value = uiState.email,
            onValueChange = { viewModel.onEmailChange(it) },
            placeholder = {
                Text(
                    text = "Email",
                    color = RosyPink,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                )
            },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            ),
            singleLine = true,
            modifier = Modifier
                .width(270.dp)
                .height(49.dp)
                .shadow(8.dp, RoundedCornerShape(50)),
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Ivor,
                unfocusedContainerColor = Ivor,
                disabledContainerColor = Ivor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Password
        OutlinedTextField(
            value = uiState.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            placeholder = {
                Text(
                    text = "Password",
                    color = RosyPink,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            },
            singleLine = true,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            ),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = @Composable {

                val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = "Toggle password visibility")
                }


            },
            modifier = Modifier
                .width(270.dp)
                .height(49.dp)
                .shadow(8.dp, RoundedCornerShape(50)),
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Ivor,
                unfocusedContainerColor = Ivor,
                disabledContainerColor = Ivor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Login button
        Button(
            onClick = { viewModel.login() },
            enabled = !uiState.isLoading,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .height(42.dp)
                .width(210.dp)
                .shadow(10.dp, RoundedCornerShape(50))
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
            } else {
                Text("Login", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Bottom text
        Row {
            Text(
                "Don't have an account? ",
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Gray,
                        offset = Offset(4f, 4f),
                        blurRadius = 6f
                    )
                )
            )
            Text(
                "Sign Up",
                fontSize = 17.sp,
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    onSignUpClick() // Navigate to signup
                }
            )
        }
    }
}


