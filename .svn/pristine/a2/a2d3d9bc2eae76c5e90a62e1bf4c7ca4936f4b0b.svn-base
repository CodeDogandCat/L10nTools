@echo off

set cnt=0
set params=#
:loop
if "%1"=="" (
java -jar L10nValueChecker.jar  %params%

)  else ( 
set "params=%params%%1#"
set /a cnt+=1 
shift /1 
goto :loop
)

