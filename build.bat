@ECHO OFF
rem this is required for the prompt to work
setlocal EnableDelayedExpansion

ECHO [ Script: %~nx0 %* ]

rem Set working directory
pushd %~dp0

rem The path to the Ant executable
set ANT_EXECUTABLE=bin\ant\bin\ant.bat
rem The location of the build file
set BUILD_FILE=build.xml
rem Java path
set DEFAULT_JAVACMD_HOME=C:\Program Files (x86)\Java\jdk1.7.0_67
rem initialise
set JAVACMD= 

rem Check for help request
IF [%1]==[?] GOTO :Help
IF [%1]==[-?] GOTO :Help
IF [%1]==[/?] GOTO :Help

ECHO Discovering Java path.
IF "%JAVACMD_HOME%"=="" (
	ECHO WARNING: JAVACMD_HOME not set.  Using default path defined in script...
	set JAVACMD_HOME=!DEFAULT_JAVACMD_HOME!
)

ECHO Checking for Java executable at %JAVACMD_HOME%\bin\java.exe
if NOT EXIST "%JAVACMD_HOME%\bin\java.exe" (
	ECHO FAILED: Java executable not found.
	exit /B 2
)
ECHO   Success: Java executable found.

rem It must be a JDK directory
ECHO Setting environment variable JAVACMD for use by Ant.
set JAVACMD=%JAVACMD_HOME%\bin\java.exe

:Main
set CMD=%ANT_EXECUTABLE% -buildfile %BUILD_FILE% %*
ECHO Executing Ant build file: %CMD%
ECHO.
call %CMD%

rem Revert directory
popd

GOTO :End

:Help
ECHO.
ECHO ------------------------------------------------------------------------------
ECHO %~nx0
ECHO.
ECHO DESCRIPTION:
ECHO   Execute the Ant build script (build.xml) in the same directory.
ECHO.
ECHO   Requires:
ECHO   * Ant in %ANT_DIR%
ECHO   * Ant build file at %BUILD_FILE%
ECHO   * Java at %DEFAULT_JAVACMD_HOME% (or as defined in JAVACMD_HOME)
ECHO.
ECHO SYNTAX: 
ECHO   %~n0 [^<target 1^> [^<target 2^>] [...]]
ECHO.
ECHO     ^<target n^>      A target in the build file.
ECHO.
ECHO     If no targets are supplied the default target in the build file is used.
ECHO.
ECHO EXAMPLES:
ECHO     %~n0 release
ECHO.
ECHO v1.0.0    Dec-2013    Daniel J. Carr
ECHO ------------------------------------------------------------------------------
ECHO.

:End