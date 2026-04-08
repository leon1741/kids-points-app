package com.leon.kidspoints.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.leon.kidspoints.ui.components.PasswordInputDialog
import com.leon.kidspoints.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateToRules: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val isPasswordSet by viewModel.isPasswordSet.collectAsState()
    var showSetPasswordDialog by remember { mutableStateOf(false) }
    var showChangePasswordDialog by remember { mutableStateOf(false) }
    var showClearPasswordDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("⚙️ 设置") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Text("←", fontSize = 28.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primary,
                    titleContentColor = OnPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // 家长密码设置
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "🔐 家长密码",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (isPasswordSet) "已设置密码" else "未设置密码",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (isPasswordSet) {
                            Button(onClick = { showChangePasswordDialog = true }) {
                                Text("修改密码")
                            }
                            Button(
                                onClick = { showClearPasswordDialog = true },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = SadRed
                                )
                            ) {
                                Text("删除密码")
                            }
                        } else {
                            Button(onClick = { showSetPasswordDialog = true }) {
                                Text("设置密码")
                            }
                        }
                    }
                }
            }

            // 规则管理入口
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "📋 规则管理",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "添加、编辑、删除每日任务规则",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = onNavigateToRules,
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("进入规则管理")
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 应用信息
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "积分小能手 v1.0",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
                Text(
                    text = "帮助孩子养成良好的习惯",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }
    }

    // 设置密码对话框
    if (showSetPasswordDialog) {
        SetPasswordDialog(
            isChange = false,
            onConfirm = { password ->
                viewModel.setPassword(password)
                showSetPasswordDialog = false
            },
            onDismiss = { showSetPasswordDialog = false }
        )
    }

    // 修改密码对话框
    if (showChangePasswordDialog) {
        SetPasswordDialog(
            isChange = true,
            onConfirm = { password ->
                viewModel.setPassword(password)
                showChangePasswordDialog = false
            },
            onDismiss = { showChangePasswordDialog = false },
            viewModel = viewModel
        )
    }

    // 删除密码确认对话框
    if (showClearPasswordDialog) {
        AlertDialog(
            onDismissRequest = { showClearPasswordDialog = false },
            title = { Text("删除密码") },
            text = { Text("确定要删除家长密码吗？删除后任何人都可以进入家长模式。") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.clearPassword()
                        showClearPasswordDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SadRed
                    )
                ) {
                    Text("确定删除")
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearPasswordDialog = false }) {
                    Text("取消")
                }
            }
        )
    }
}

@Composable
fun SetPasswordDialog(
    isChange: Boolean,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit,
    viewModel: SettingsViewModel? = null
) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (isChange) "修改密码" else "设置密码") },
        text = {
            Column {
                if (isChange) {
                    // 验证原密码
                    var oldPassword by remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = oldPassword,
                        onValueChange = { oldPassword = it },
                        label = { Text("原密码") },
                        visualTransformation = if (showPassword) androidx.compose.ui.text.input.VisualTransformation.None else androidx.compose.ui.text.input.PasswordVisualTransformation(),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                OutlinedTextField(
                    value = password,
                    onValueChange = { if (it.all { c -> c.isDigit() }) password = it },
                    label = { Text("新密码") },
                    visualTransformation = if (showPassword) androidx.compose.ui.text.input.VisualTransformation.None else androidx.compose.ui.text.input.PasswordVisualTransformation(),
                    singleLine = true,
                    isError = showError.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth()
                )
                if (showError.isNotEmpty()) {
                    Text(
                        text = showError,
                        color = SadRed,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { if (it.all { c -> c.isDigit() }) confirmPassword = it },
                    label = { Text("确认密码") },
                    visualTransformation = if (showPassword) androidx.compose.ui.text.input.VisualTransformation.None else androidx.compose.ui.text.input.PasswordVisualTransformation(),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Checkbox(
                        checked = showPassword,
                        onCheckedChange = { showPassword = it }
                    )
                    Text("显示密码")
                }
                Text(
                    text = "密码为 4-6 位数字",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    when {
                        password.length !in 4..6 -> {
                            showError = "密码长度为 4-6 位"
                        }
                        password != confirmPassword -> {
                            showError = "两次输入的密码不一致"
                        }
                        else -> {
                            onConfirm(password)
                        }
                    }
                }
            ) {
                Text("确认")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("取消")
            }
        }
    )
}
