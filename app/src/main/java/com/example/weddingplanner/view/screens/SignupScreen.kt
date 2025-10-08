package com.example.weddingplanner.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    onLoginClick: () -> Unit

) {
    val viewModel: AuthViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()



    // Create Snackbar host state
    val snackbarHostState = remember { SnackbarHostState() }

    // Show snackbar when error/success changes
    LaunchedEffect(uiState.errorMessage, uiState.isSuccess) {
        when {
            uiState.errorMessage != null -> {
                snackbarHostState.showSnackbar(
                    message = uiState.errorMessage ?: "Unknown error",
                    withDismissAction = true
                )
            }
            uiState.isSuccess -> {
                snackbarHostState.showSnackbar(
                    message = "Sign up successful 🎉",
                    withDismissAction = true
                )
                onSignUpSuccess() // ✅ Navigate to login after signup
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            SignUpBackgroundCard(viewModel = viewModel,  onLoginClick = onLoginClick)
        }
    }
}

@Composable
fun SignUpScreenView(
    viewModel: AuthViewModel,
    onLoginClick: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Bottom
    ) {

//        Spacer(modifier = Modifier.height(136.dp))
        // Title
        Text(
            text = "Create Your Account",
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            color = Color.White,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Gray,
                    offset = Offset(4f, 4f),
                    blurRadius = 6f
                ),
            ),
            modifier = Modifier.padding(bottom = 18.dp)
        )

        // Username field
        OutlinedTextField(
            value = uiState.username,
            onValueChange = {viewModel.onUsernameChange(it)},
            placeholder = {
                Text(
                    text = "Username",
                    color = RosyPink,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,

                )
            },
            textStyle = TextStyle(
                color = Color.Black,           // same color as placeholder
                fontSize = 14.sp,           // same font size
                fontWeight = FontWeight.Bold // same font weight
            ),
            singleLine = true,
            modifier = Modifier
                .width(270.dp).height(49.dp)
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

        // Email field
        OutlinedTextField(
            value = uiState.email,
            onValueChange = {viewModel.onEmailChange(it)},
            placeholder = {
                Text(
                    text = "Email",
                    color = RosyPink,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,

                )
            },
            textStyle = TextStyle(
                color = Color.Black,           // same color as placeholder
                fontSize = 14.sp,           // same font size
                fontWeight = FontWeight.Bold // same font weight
            ),
            singleLine = true,
            modifier = Modifier
                .width(270.dp).height(49.dp)
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

        // New - Password field
        OutlinedTextField(
            value = uiState.password,
            onValueChange = {viewModel.onPasswordChange(it)},
            placeholder = {
                Text(
                    text = "Password",
                    color = RosyPink,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,

                )
            },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = "Toggle password visibility")
                }
            },
                textStyle = TextStyle(
                color = Color.Black,           // same color as placeholder
                fontSize = 14.sp,           // same font size
                fontWeight = FontWeight.Bold // same font weight
            ),
            modifier = Modifier
                .shadow(8.dp, RoundedCornerShape(50))
                .width(270.dp).height(49.dp),
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

        // Confirm - Password field
        OutlinedTextField(
            value = uiState.confirmPassword,
            onValueChange = {viewModel.onConfirmPasswordChange(it)},
            placeholder = {
                    Text(
                        text = "Confirm Password",
                        color = RosyPink,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                    )

            },textStyle = TextStyle(
                color = Color.Black,           // same color as placeholder
                fontSize = 14.sp,           // same font size
                fontWeight = FontWeight.Bold // same font weight
            ),
            singleLine = true,
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(imageVector = image, contentDescription = "Toggle password visibility")
                }
            },

            modifier = Modifier
                .shadow(8.dp, RoundedCornerShape(50))
                .width(270.dp).height(49.dp),
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

        // sign button
        Button(
            onClick = {viewModel.signUp() },
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
                Text("Sign Up", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Sign Up
        Row {
            Text(
                "Already have an account? ",
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Gray,
                        offset = Offset(4f, 4f),
                        blurRadius = 6f
                    ),
                ),
            )
            Text(
                "Log In",
                fontSize = 17.sp,
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .clickable {
                        onLoginClick() // ✅ Go to login screen
                    }
            )
        }

        Spacer(modifier = Modifier.height(14.dp))
    }
}

@Composable
fun SignUpBackgroundCard(viewModel: AuthViewModel, onLoginClick: () -> Unit){
//    val viewModel: AuthViewModel = viewModel()
    Box(
        modifier = Modifier.fillMaxSize().background(Ivor),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .fillMaxHeight(0.75f),
            shape = RoundedCornerShape(
                topStart = 32.dp,
                topEnd = 32.dp,
                bottomStart = 32.dp,
                bottomEnd = 32.dp
            ),
            colors = CardDefaults.cardColors(containerColor = RosyPink),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.hands),
                    contentDescription = "couple",
                    modifier = Modifier
                        .width(154.dp)
                        .height(180.dp)
                        //.align(Alignment.BottomCenter)

                )
                SignUpScreenView(viewModel = viewModel,  onLoginClick = onLoginClick)
            }
        }
    }
}