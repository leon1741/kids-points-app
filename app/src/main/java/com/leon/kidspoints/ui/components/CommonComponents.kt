package com.leon.kidspoints.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leon.kidspoints.ui.theme.HappyGreen
import com.leon.kidspoints.ui.theme.SadRed
import com.leon.kidspoints.ui.theme.TextPrimary

/**
 * 积分显示组件
 */
@Composable
fun PointsBadge(
    points: Int,
    modifier: Modifier = Modifier
) {
    val color = if (points >= 0) HappyGreen else SadRed
    val sign = if (points > 0) "+" else ""

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = "$sign$points",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = " 积分",
            fontSize = 14.sp,
            color = TextPrimary
        )
    }
}

/**
 * 带 Emoji 的卡片项
 */
@Composable
fun EmojiCardItem(
    emoji: String,
    title: String,
    subtitle: String = "",
    onClick: () -> Unit
) {
    androidx.compose.material3.Card(
        onClick = onClick,
        modifier = Modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = emoji,
                fontSize = 32.sp
            )
            Spacer(modifier = Modifier.width(12.dp))
            androidx.compose.foundation.layout.Column {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                if (subtitle.isNotEmpty()) {
                    Text(
                        text = subtitle,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
