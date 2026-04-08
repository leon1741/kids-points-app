@echo off
echo ============================================
echo    Android 编译环境安装脚本
echo ============================================
echo.

:: 检查 Java
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [错误] 未检测到 Java，请先安装 JDK 17
    echo 下载地址：https://adoptium.net/temurin/releases/?version=17
    echo.
    pause
    exit /b 1
)

echo [OK] Java 已安装
java -version
echo.

:: 创建 Android SDK 目录
set ANDROID_HOME=C:\Android
set SDKMANAGER=%ANDROID_HOME%\cmdline-tools\bin\sdkmanager.bat

if not exist "%ANDROID_HOME%" (
    mkdir "%ANDROID_HOME%"
    echo [INFO] 已创建 Android SDK 目录：%ANDROID_HOME%
)

:: 下载命令行工具
echo [INFO] 请下载 Android 命令行工具并解压到 %ANDROID_HOME%\cmdline-tools
echo 下载地址：https://dl.google.com/android/repository/commandlinetools-win-11076708_latest.zip
echo.
echo 解压后目录结构应该是:
echo   %ANDROID_HOME%\cmdline-tools\bin\sdkmanager.bat
echo   %ANDROID_HOME%\cmdline-tools\lib\...
echo.

pause
echo.

:: 接受许可证并安装必要组件
echo [INFO] 安装 Android SDK 组件...
"%SDKMANAGER%" --licenses --force >nul 2>&1
"%SDKMANAGER%" "platform-tools" "platforms;android-34" "build-tools;34.0.0" --verbose

echo.
echo ============================================
echo    安装完成！
echo ============================================
echo.
echo 请配置以下环境变量:
echo   ANDROID_HOME = C:\Android
echo   PATH = %PATH%;%%ANDROID_HOME%%\platform-tools;%%ANDROID_HOME%%\cmdline-tools\bin
echo.
echo 然后运行 build.bat 进行编译
pause
