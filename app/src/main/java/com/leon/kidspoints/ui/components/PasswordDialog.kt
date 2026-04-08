package com.leon.kidspoints.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.leon.kidspoints.ui.theme.Primary
import com.leon.kidspoints.ui.theme.TextPrimary

/**
 * 数字密码输入键盘
 */
@Composable
fun PasswordInputDialog(
    title: String,
    onPasswordEntered: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 密码显示框
                OutlinedTextField(
                    value = password,
                    onValueChange = { if (it.length <= 6) password = it },
                    label = { Text("密码") },
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    singleLine = true,
                    keyboardType = KeyboardType.NumberPassword,
                    modifier = Modifier.fillMaxWidth()
                )

                TextButton(onClick = { showPassword = !showPassword }) {
                    Text(if (showPassword) "隐藏密码" else "显示密码")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 数字键盘
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    (1..3).forEach { row ->
                        Row {
                            ((row - 1) * 3 + 1)..((row - 1) * 3 + 3) forEach { num ->
                                Button(
                                    onClick = {
                                        if (password.length < 6) {
                                            password += num
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Primary.copy(alpha = 0.8f)
                                    ),
                                    modifier = Modifier
                                        .width(80.dp)
                                        .padding(4.dp)
                                ) {
                                    Text(
                                        text = "$num",
                                        fontSize = 20.sp
                                    )
                                }
                            }
                        }
                    }
                    Row {
                        Button(
                            onClick = onDismiss,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            ),
                            modifier = Modifier
                                .width(80.dp)
                                .padding(4.dp)
                        ) {
                            Text("取消", fontSize = 16.sp)
                        }
                        Button(
                            onClick = {
                                if (password.length in 4..6) {
                                    onPasswordEntered(password)
                                }
                            },
                            enabled = password.length in 4..6,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Primary
                            ),
                            modifier = Modifier
                                .width(164.dp)
                                .padding(4.dp)
                        ) {
                            Text("确认", fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }
}
