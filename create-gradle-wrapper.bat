@echo off
echo ============================================
echo    创建 Gradle Wrapper
echo ============================================

:: 如果有 gradle，直接生成 wrapper
where gradle >nul 2>&1
if %errorlevel% equ 0 (
    gradle wrapper
    echo [OK] Gradle Wrapper 已创建
    pause
    exit /b 0
)

:: 否则下载 gradle wrapper jar
set GRADLE_VERSION=8.4
set WRAPPER_URL=https://raw.githubusercontent.com/gradle/gradle/v%GRADLE_VERSION%.0/gradle/wrapper/gradle-wrapper.jar
set WRAPPER_DEST=gradle\wrapper\gradle-wrapper.jar

if not exist "gradle\wrapper" mkdir gradle\wrapper

echo [INFO] 下载 gradle-wrapper.jar...
curl -L -o "%WRAPPER_DEST%" "%WRAPPER_URL%"

if exist "%WRAPPER_DEST%" (
    echo [OK] 已下载 gradle-wrapper.jar
) else (
    echo [警告] 无法自动下载，请手动安装 gradle
)

:: 创建 gradlew.bat
(
echo @rem Gradle startup script for Windows
echo @if "%DEBUG%"=="" @echo off
echo setlocal
echo set DIRNAME=%%~dp0
echo if not defined GRADLE_OPTS set GRADLE_OPTS=-Xmx2048m
echo set CLASSPATH=%DIRNAME%gradle\wrapper\gradle-wrapper.jar
echo java %%GRADLE_OPTS%% -jar "%%CLASSPATH%%" -p "%%DIRNAME%%" %%*
echo endlocal
) > gradlew.bat

echo [OK] gradlew.bat 已创建
pause
