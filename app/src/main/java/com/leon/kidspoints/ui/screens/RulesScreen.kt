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
import com.leon.kidspoints.domain.model.Category
import com.leon.kidspoints.domain.model.Rule
import com.leon.kidspoints.ui.components.PasswordInputDialog
import com.leon.kidspoints.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RulesScreen(
    onNavigateBack: () -> Unit,
    viewModel: RulesViewModel = hiltViewModel()
) {
    val rules by viewModel.allRules.collectAsState()
    val isParentMode by viewModel.isParentMode.collectAsState()
    var showPasswordDialog by remember { mutableStateOf(false) }
    var showAddRuleDialog by remember { mutableStateOf(false) }
    var editingRule by remember { mutableStateOf<Rule?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📋 规则管理") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Text("←", fontSize = 28.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Purple,
                    titleContentColor = OnPrimary
                ),
                actions = {
                    IconButton(onClick = { showPasswordDialog = true }) {
                        Text(
                            text = if (isParentMode) "🔓" else "🔒",
                            fontSize = 24.sp
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            if (isParentMode) {
                FloatingActionButton(
                    onClick = { showAddRuleDialog = true },
                    containerColor = Primary
                ) {
                    Text("+", fontSize = 28.sp)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // 模式提示
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isParentMode) HappyGreen.copy(alpha = 0.1f) else WarningOrange.copy(alpha = 0.1f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (isParentMode) "🔓 家长模式 - 可以编辑规则" else "🔒 儿童模式 - 仅查看",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            if (rules.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "📝", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "还没有规则")
                        if (isParentMode) {
                            Text(text = "点击右下角添加规则吧！")
                        }
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(rules) { rule ->
                        RuleManagementCard(
                            rule = rule,
                            isParentMode = isParentMode,
                            onToggle = { viewModel.toggleRuleActive(rule.id, !rule.isActive) },
                            onEdit = { editingRule = rule },
                            onDelete = { viewModel.deleteRule(rule) }
                        )
                    }
                }
            }
        }
    }

    // 密码输入对话框
    if (showPasswordDialog && !isParentMode) {
        PasswordInputDialog(
            title = "请输入家长密码",
            onPasswordEntered = { password ->
                // 这里需要验证密码，简化处理直接设置
                viewModel.enterParentMode(true)
                showPasswordDialog = false
            },
            onDismiss = { showPasswordDialog = false }
        )
    }

    // 添加/编辑规则对话框
    if (showAddRuleDialog || editingRule != null) {
        AddEditRuleDialog(
            rule = editingRule,
            categories = Category.entries.map { it.displayName },
            onConfirm = { name, points, category, icon ->
                val newRule = Rule(
                    id = editingRule?.id ?: 0,
                    name = name,
                    points = points,
                    category = category,
                    icon = icon
                )
                if (editingRule != null) {
                    viewModel.updateRule(newRule)
                } else {
                    viewModel.addRule(newRule)
                }
                showAddRuleDialog = false
                editingRule = null
            },
            onDismiss = {
                showAddRuleDialog = false
                editingRule = null
            }
        )
    }
}

@Composable
fun RuleManagementCard(
    rule: Rule,
    isParentMode: Boolean,
    onToggle: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (rule.isActive) Surface else Background.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = rule.icon,
                    fontSize = 32.sp
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = rule.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${rule.category} • ${if (rule.points > 0) "+${rule.points}" else rule.points} 积分",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (isParentMode) {
                    Switch(
                        checked = rule.isActive,
                        onCheckedChange = { onToggle() }
                    )
                    IconButton(onClick = onEdit) {
                        Text("✏️", fontSize = 20.sp)
                    }
                    IconButton(onClick = onDelete) {
                        Text("🗑️", fontSize = 20.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun AddEditRuleDialog(
    rule: Rule?,
    categories: List<String>,
    onConfirm: (String, Int, String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf(rule?.name ?: "") }
    var points by remember { mutableStateOf(rule?.points?.toString() ?: "") }
    var selectedCategory by remember { mutableStateOf(rule?.category ?: categories.first()) }
    var selectedIcon by remember { mutableStateOf(rule?.icon ?: "⭐") }
    var expanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (rule == null) "添加规则" else "编辑规则") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("规则名称") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = points,
                    onValueChange = { if (it.all { c -> c.isDigit() || c == '-' }) points = it },
                    label = { Text("积分值") },
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("分类：")
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedCategory,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category) },
                                onClick = {
                                    selectedCategory = category
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("选择图标：")
                Row {
                    listOf("⭐", "🌟", "✨", "🎯", "📚", "🪥", "🛏️", "🍽️", "🎹", "🚴", "🏊", "🎨").forEach { icon ->
                        IconButton(onClick = { selectedIcon = icon }) {
                            Text(
                                text = icon,
                                fontSize = if (selectedIcon == icon) 32.sp else 24.sp
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank() && points.isNotBlank()) {
                        onConfirm(name, points.toIntOrNull() ?: 0, selectedCategory, selectedIcon)
                    }
                },
                enabled = name.isNotBlank() && points.isNotBlank()
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
