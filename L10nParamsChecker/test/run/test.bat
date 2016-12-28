@echo off

call L10nParamsChecker.bat -p android  -dr ../android/strings.xml -cr ../android/strings2.xml -o android_checker.txt 

call L10nParamsChecker.bat -p windows  -dr ../windows/language_en.xml -cr ../windows/language_zh_cn.xml -o windows_checker.txt


call L10nParamsChecker.bat -p ios  -dr ../ios/Localizable-en.strings -cr ../ios/Localizable-cn.strings -o ios_checker.txt 

call L10nParamsChecker.bat -p mac  -dr ../mac/Localizable-en.strings -cr ../mac/Localizable-cn.strings -o mac_checker.txt 

pause