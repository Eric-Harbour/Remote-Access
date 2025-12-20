@echo off

:: --- Define Paths ---
:: Path to the JavaFX SDK JARs (for compilation/runtime)
set JAVAFX_SDK_LIB=lib 

:: Path to the JavaFX JMODs (for jpackage)
set JAVAFX_JMODS=jmods

:: Output directory for compiled .class files
set OUTPUT_DIR=classes

:: Your application details
set MAIN_CLASS=it386Project.honorsproject.RemoteAccess.Launcher
set APP_NAME="Honors Contract Remote Connection"

:: --- 1. Compile ---
echo --- Compiling Source Files ---
mkdir %OUTPUT_DIR% 2>nul

:: Compiling the package (change dir to java root and then compile)
cd src\main\java
javac -cp ..\..\..\lib --module-path ..\..\..\%JAVAFX_SDK_LIB% --add-modules javafx.controls,javafx.fxml -d ..\..\..\%OUTPUT_DIR% it386Project\honorsproject\javafx\*.java
cd ..\..\..

:: --- 2. Package JAR ---
echo --- Creating Application JAR ---
cd %OUTPUT_DIR%
jar cvf %APP_NAME%.jar *
cd ..

:: --- 3. Package Native Installer ---
echo --- Creating Native Installer (App Image) ---
jpackage ^
    --name %APP_NAME% ^
    --app-version "1.0" ^
    --dest output ^
    --input %OUTPUT_DIR% ^
    --main-jar %APP_NAME%.jar ^
    --main-class %MAIN_CLASS% ^
    --module-path %JAVAFX_JMODS% ^
    --add-modules javafx.controls,javafx.fxml ^
    --type app-image