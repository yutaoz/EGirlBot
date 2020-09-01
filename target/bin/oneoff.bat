@REM ----------------------------------------------------------------------------
@REM Copyright 2001-2004 The Apache Software Foundation.
@REM
@REM Licensed under the Apache License, Version 2.0 (the "License");
@REM you may not use this file except in compliance with the License.
@REM You may obtain a copy of the License at
@REM
@REM      http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing, software
@REM distributed under the License is distributed on an "AS IS" BASIS,
@REM WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM See the License for the specific language governing permissions and
@REM limitations under the License.
@REM ----------------------------------------------------------------------------
@REM

@echo off

set ERROR_CODE=0

:init
@REM Decide how to startup depending on the version of windows

@REM -- Win98ME
if NOT "%OS%"=="Windows_NT" goto Win9xArg

@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" @setlocal

@REM -- 4NT shell
if "%eval[2+2]" == "4" goto 4NTArgs

@REM -- Regular WinNT shell
set CMD_LINE_ARGS=%*
goto WinNTGetScriptDir

@REM The 4NT Shell from jp software
:4NTArgs
set CMD_LINE_ARGS=%$
goto WinNTGetScriptDir

:Win9xArg
@REM Slurp the command line arguments.  This loop allows for an unlimited number
@REM of arguments (up to the command line limit, anyway).
set CMD_LINE_ARGS=
:Win9xApp
if %1a==a goto Win9xGetScriptDir
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto Win9xApp

:Win9xGetScriptDir
set SAVEDIR=%CD%
%0\
cd %0\..\.. 
set BASEDIR=%CD%
cd %SAVEDIR%
set SAVE_DIR=
goto repoSetup

:WinNTGetScriptDir
set BASEDIR=%~dp0\..

:repoSetup


if "%JAVACMD%"=="" set JAVACMD=java

if "%REPO%"=="" set REPO=%BASEDIR%\repo

set CLASSPATH="%BASEDIR%"\etc;"%REPO%"\net\dv8tion\JDA\3.5.0_329\JDA-3.5.0_329.jar;"%REPO%"\com\google\code\findbugs\jsr305\3.0.2\jsr305-3.0.2.jar;"%REPO%"\org\apache\commons\commons-collections4\4.1\commons-collections4-4.1.jar;"%REPO%"\org\json\json\20160810\json-20160810.jar;"%REPO%"\net\sf\trove4j\trove4j\3.0.3\trove4j-3.0.3.jar;"%REPO%"\net\java\dev\jna\jna\4.4.0\jna-4.4.0.jar;"%REPO%"\com\neovisionaries\nv-websocket-client\2.2\nv-websocket-client-2.2.jar;"%REPO%"\com\squareup\okhttp3\okhttp\3.8.1\okhttp-3.8.1.jar;"%REPO%"\com\squareup\okio\okio\1.13.0\okio-1.13.0.jar;"%REPO%"\org\goldrenard\ab\1.0.7\ab-1.0.7.jar;"%REPO%"\joda-time\joda-time\2.9.9\joda-time-2.9.9.jar;"%REPO%"\org\apache\httpcomponents\httpclient\4.5.5\httpclient-4.5.5.jar;"%REPO%"\org\apache\httpcomponents\httpcore\4.4.9\httpcore-4.4.9.jar;"%REPO%"\commons-logging\commons-logging\1.2\commons-logging-1.2.jar;"%REPO%"\commons-codec\commons-codec\1.10\commons-codec-1.10.jar;"%REPO%"\net\reduls\sanmoku\sanmoku\0.0.5\sanmoku-0.0.5.jar;"%REPO%"\net\reduls\sanmoku\sanmoku-feature-ex\0.0.1\sanmoku-feature-ex-0.0.1.jar;"%REPO%"\org\apache\commons\commons-lang3\3.7\commons-lang3-3.7.jar;"%REPO%"\org\slf4j\slf4j-api\1.7.5\slf4j-api-1.7.5.jar;"%REPO%"\org\slf4j\slf4j-log4j12\1.7.5\slf4j-log4j12-1.7.5.jar;"%REPO%"\log4j\log4j\1.2.17\log4j-1.2.17.jar;"%REPO%"\org\example\EGirlBot\1.0-SNAPSHOT\EGirlBot-1.0-SNAPSHOT.jar
set EXTRA_JVM_ARGUMENTS=
goto endInit

@REM Reaching here means variables are defined and arguments have been captured
:endInit

%JAVACMD% %JAVA_OPTS% %EXTRA_JVM_ARGUMENTS% -classpath %CLASSPATH_PREFIX%;%CLASSPATH% -Dapp.name="oneoff" -Dapp.repo="%REPO%" -Dbasedir="%BASEDIR%" OneOffProcess %CMD_LINE_ARGS%
if ERRORLEVEL 1 goto error
goto end

:error
if "%OS%"=="Windows_NT" @endlocal
set ERROR_CODE=1

:end
@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" goto endNT

@REM For old DOS remove the set variables from ENV - we assume they were not set
@REM before we started - at least we don't leave any baggage around
set CMD_LINE_ARGS=
goto postExec

:endNT
@endlocal

:postExec

if "%FORCE_EXIT_ON_ERROR%" == "on" (
  if %ERROR_CODE% NEQ 0 exit %ERROR_CODE%
)

exit /B %ERROR_CODE%
