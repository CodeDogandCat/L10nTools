@echo off

set jarfile=%~dp0%\L10nTool.jar

set cnt=0
set params=#
:loop
if "%1"=="" (
java -jar %jarfile%  %params%

)  else ( 
set "params=%params%%1#"
set /a cnt+=1 
shift /1 
goto :loop
)