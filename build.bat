@echo off
setlocal enabledelayedexpansion

echo ============================================
echo    编译 积分小能手 APK
echo ============================================
echo.

:: 设置环境变量
set ANDROID_HOME=C:\Android
set JAVA_HOME=C:\Program Files\Java\jdk-17
set GRADLE_OPTS=-Dorg.gradle.daemon=false

:: 检查环境变量
if not exist "%ANDROID_HOME%" (
    echo [错误] Android SDK 不存在：%ANDROID_HOME%
    echo 请先运行 setup-android.bat 进行安装
    pause
    exit /b 1
)

:: 检查 Gradle
where gradle >nul 2>&1
if %errorlevel% neq 0 (
    echo [INFO] 使用 Gradle Wrapper...
    set GRADLE=gradlew.bat
) else (
    set GRADLE=gradle
)

:: 检查本地 gradlew
if exist "gradlew.bat" (
    set GRADLE=gradlew.bat
)

echo [INFO] 开始编译...
echo.

:: 清理并编译
call %GRADLE% clean assembleDebug

if %errorlevel% neq 0 (
    echo.
    echo [错误] 编译失败！
    pause
    exit /b 1
)

echo.
echo ============================================
echo    编译成功!
echo ============================================
echo.
echo APK 文件位置:
echo   app\build\outputs\apk\debug\app-debug.apk
echo.
echo 可以将 APK 安装到手机进行测试
echo.
pause
