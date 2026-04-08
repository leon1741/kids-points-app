# 积分小能手 - 儿童积分管理应用

一款帮助家长管理儿童日常行为规范的安卓应用，通过积分奖励机制培养孩子良好习惯。

## 功能特点

- 📋 **每日任务管理** - 家长可自定义规则，如按时刷牙、完成作业等
- ✅ **完成奖励** - 孩子完成任务后获得相应积分
- ❌ **违规扣分** - 违反规则时扣除积分
- 🎁 **礼品兑换** - 累积积分可兑换预设礼品
- 📊 **积分记录** - 查看所有积分变动历史
- 🔐 **家长模式** - 密码保护，防止孩子修改规则

## 技术栈

- **语言**: Kotlin
- **UI**: Jetpack Compose + Material Design 3
- **数据库**: Room
- **本地存储**: DataStore
- **依赖注入**: Hilt
- **架构**: MVVM

## 项目结构

```
app/src/main/java/com/leon/kidspoints/
├── data/
│   ├── local/
│   │   ├── dao/          # 数据访问对象
│   │   ├── entity/       # 数据库实体
│   │   └── AppDatabase.kt
│   └── repository/       # 数据仓库
├── di/
│   └── AppModule.kt      # 依赖注入配置
├── domain/
│   └── model/            # 领域模型
├── ui/
│   ├── components/       # 可复用组件
│   ├── screens/          # 页面和 ViewModel
│   └── theme/            # 主题和样式
├── MainActivity.kt
└── KidsPointsApplication.kt
```

## 构建说明

### 环境要求
- Android Studio Hedgehog 或更高版本
- JDK 17
- Android SDK 34

### 构建步骤

1. 用 Android Studio 打开项目
2. 同步 Gradle 文件
3. 运行到模拟器或真机

### 生成 APK

```bash
./gradlew assembleDebug
```

APK 文件将生成在 `app/build/outputs/apk/debug/` 目录

## 使用说明

### 首次使用

1. 启动应用后，进入「设置」
2. 点击「设置密码」，设置 4-6 位数字密码
3. 进入「规则管理」，添加每日任务规则

### 日常使用

1. 孩子完成任务 → 点击「完成」→ 获得积分
2. 孩子违反规则 → 点击「违规」→ 扣除积分
3. 积分足够 → 进入「礼品兑换」→ 兑换礼品

## 数据存储

- 所有数据存储在本地 SQLite 数据库
- 家长密码加密存储在 DataStore
- 无需网络连接，完全离线使用

## 后续优化建议

- [ ] 添加数据统计图表
- [ ] 支持多个孩子账户
- [ ] 添加任务提醒功能
- [ ] 导出积分报告
- [ ] 自定义主题颜色
