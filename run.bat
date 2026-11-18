@echo off
set "JDK_BIN=C:\Program Files\JetBrains\IntelliJ IDEA 2026.2.2\jbr\bin"
if not exist out mkdir out
"%JDK_BIN%\javac.exe" -d out src\grid\model\*.java src\grid\factory\*.java src\grid\decorator\*.java src\grid\observer\*.java src\grid\service\*.java src\grid\storage\*.java src\grid\Main.java
if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    pause
    exit /b %ERRORLEVEL%
)
"%JDK_BIN%\java.exe" -cp out grid.Main
