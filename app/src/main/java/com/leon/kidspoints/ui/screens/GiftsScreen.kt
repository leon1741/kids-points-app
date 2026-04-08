package com.leon.kidspoints.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.leon.kidspoints.domain.model.Gift
import com.leon.kidspoints.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GiftsScreen(
    onNavigateBack: () -> Unit,
    viewModel: GiftsViewModel = hiltViewModel()
) {
    val totalPoints by viewModel.totalPoints.collectAsState()
    val gifts by viewModel.availableGifts.collectAsState()
    var showAddGiftDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🎁 礼品兑换") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Text("←", fontSize = 28.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Secondary,
                    titleContentColor = OnPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddGiftDialog = true },
                containerColor = Primary
            ) {
                Text("+", fontSize = 28.sp)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // 当前积分显示
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Primary.copy(alpha = 0.1f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "💰 当前积分",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "$totalPoints 分",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                }
            }

            if (gifts.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "🎁", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "还没有礼品，点击右下角添加吧！")
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(gifts) { gift ->
                        GiftCard(
                            gift = gift,
                            currentPoints = totalPoints,
                            onRedeem = { viewModel.redeemGift(gift) }
                        )
                    }
                }
            }
        }
    }

    if (showAddGiftDialog) {
        AddGiftDialog(
            onConfirm = { gift ->
                viewModel.addGift(gift)
                showAddGiftDialog = false
            },
            onDismiss = { showAddGiftDialog = false }
        )
    }
}

@Composable
fun GiftCard(
    gift: Gift,
    currentPoints: Int,
    onRedeem: () -> Unit
) {
    val canAfford = currentPoints >= gift.cost

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = gift.icon,
                    fontSize = 40.sp
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = gift.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${gift.cost} 积分",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (canAfford) HappyGreen else WarningOrange
                    )
                }
            }

            Button(
                onClick = onRedeem,
                enabled = canAfford,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (canAfford) Secondary else SadRed.copy(alpha = 0.3f)
                )
            ) {
                Text(if (canAfford) "兑换" else "积分不足")
            }
        }
    }
}

@Composable
fun AddGiftDialog(
    onConfirm: (Gift) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var icon by remember { mutableStateOf("🎁") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("添加礼品") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("礼品名称") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = cost,
                    onValueChange = { if (it.all { c -> c.isDigit() }) cost = it },
                    label = { Text("所需积分") },
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "选择图标：")
                Row {
                    listOf("🎁", "🧸", "🍭", "📚", "🎮", "🎨", "🍦", "🚴").forEach { emoji ->
                        IconButton(onClick = { icon = emoji }) {
                            Text(
                                text = emoji,
                                fontSize = 24.sp
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank() && cost.isNotBlank()) {
                        onConfirm(
                            Gift(
                                name = name,
                                cost = cost.toIntOrNull() ?: 0,
                                icon = icon
                            )
                        )
                    }
                },
                enabled = name.isNotBlank() && cost.isNotBlank()
            ) {
                Text("添加")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("取消")
            }
        }
    )
}
