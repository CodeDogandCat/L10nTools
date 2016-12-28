@echo off

call l10nTool.bat -p android -f checker -dr ../android/En.xml -cr ../android/De.xml -o ../android/android_checker_En_De.txt 
call l10nTool.bat -p android -f merger -dr ../android/strings.xml -cr ../android/strings2.xml -nr ../android/patch.xml -o ../android/android_merger.xml
call l10nTool.bat -p android -f history-compare -dr ../android/strings.xml -revision 36 -svnpath  https://10.100.56.178/svn/zoom/trunk/tools/l10n/L10nTool/test/android/strings.xml   -o ../android/android_his_change.txt
         
         
call l10nTool.bat -p windows -f checker -dr ../windows/language_en.xml -cr ../windows/language_zh_cn.xml -o ../windows/windows_checker.txt
call l10nTool.bat -p windows -f merger -dr ../windows/language_en.xml -cr ../windows/language_zh_cn.xml -nr ../windows/language_zh_patch.xml -o ../windows/windows_merger.xml
call l10nTool.bat -p windows -f history-compare -dr ../windows/language_en.xml -revision 36 -svnpath  https://10.100.56.178/svn/zoom/trunk/tools/l10n/L10nTool/test/windows/language_en2.xml   -o ../windows/windows_his_change.txt
         
         
call l10nTool.bat -p ios -f checker -dr ../ios/Localizable-en.strings -cr ../ios/Localizable-cn.strings -o ../ios/ios_checker.txt 
call l10nTool.bat -p ios -f merger -dr ../ios/Localizable-en.strings -cr ../ios/Localizable-cn.strings -nr ../ios/Localizable-cn-patch.strings -o ../ios/ios_merger.xml
call l10nTool.bat -p ios -f history-compare -dr ../ios/Localizable-cn.strings -revision 47 -svnpath  https://10.100.56.178/svn/zoom/trunk/tools/l10n/L10nTool/test/ios/Localizable-cn.strings -o ../ios/ios_his_change.txt
         
         
         
call l10nTool.bat -p mac -f checker -dr ../mac/Localizable-en.strings -cr ../mac/Localizable-cn.strings -o ../mac/mac_checker.txt 
call l10nTool.bat -p mac -f merger -dr ../mac/Localizable-en.strings -cr ../mac/Localizable-cn.strings -nr ../mac/Localizable-cn-patch.strings -o ../mac/mac_merger.xml
call l10nTool.bat -p mac -f history-compare -dr ../mac/Localizable-cn.strings -revision 49 -svnpath  https://10.100.56.178/svn/zoom/trunk/tools/l10n/L10nTool/test/mac/Localizable-cn.strings -o ../mac/mac_his_change.txt

pause