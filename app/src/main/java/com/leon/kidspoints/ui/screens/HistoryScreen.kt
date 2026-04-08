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
import com.leon.kidspoints.domain.model.PointRecord
import com.leon.kidspoints.domain.model.RecordType
import com.leon.kidspoints.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    onNavigateBack: () -> Unit,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val records by viewModel.allRecords.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📊 积分记录") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Text("←", fontSize = 28.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SkyBlue,
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
            if (records.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "📝", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "还没有积分记录")
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(records) { record ->
                        RecordCard(record = record)
                    }
                }
            }
        }
    }
}

@Composable
fun RecordCard(record: PointRecord) {
    val (icon, bgColor, textColor) = when (record.type) {
        RecordType.COMPLETE -> Triple("✅", HappyGreen.copy(alpha = 0.1f), HappyGreen)
        RecordType.VIOLATION -> Triple("❌", SadRed.copy(alpha = 0.1f), SadRed)
        RecordType.REDEEM -> Triple("🎁", WarningOrange.copy(alpha = 0.1f), WarningOrange)
    }

    val dateFormat = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
    val dateStr = dateFormat.format(Date(record.date))

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = bgColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = icon,
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = when (record.type) {
                            RecordType.COMPLETE -> "完成奖励"
                            RecordType.VIOLATION -> "违规扣分"
                            RecordType.REDEEM -> "礼品兑换"
                        },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = dateStr,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary
                )
                if (record.note.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = record.note,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            val sign = if (record.points > 0) "+" else ""
            Text(
                text = "$sign${record.points}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }
    }
}
