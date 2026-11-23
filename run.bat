@echo off
chcp 65001 >nul
set "JDK_BIN=C:\Program Files\JetBrains\IntelliJ IDEA 2026.2.2\jbr\bin"
if not exist out mkdir out
"%JDK_BIN%\javac.exe" -encoding UTF-8 -d out src\grid\model\*.java src\grid\factory\*.java src\grid\decorator\*.java src\grid\observer\*.java src\grid\service\*.java src\grid\storage\*.java src\grid\Main.java
if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    pause
    exit /b %ERRORLEVEL%
)
"%JDK_BIN%\java.exe" -Dfile.encoding=UTF-8 -cp out grid.Main
