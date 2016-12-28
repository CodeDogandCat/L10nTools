@echo off

call L10nMissRewriter.bat -p android  -dr ../android/En.xml -cr ../android/De.xml -db  ../android/Ge-updataC4v.xlsx  -sh  Sheet3  -o  ../android/temp.xml

call L10nMissRewriter.bat -p windows -dr ../windows/win-En.xml -cr ../windows/win-Fr.xml  -db  ../windows/Already-translated-Fr-all.xlsx  -sh  Sheet1 -o ../windows/temp.xml


call L10nMissRewriter.bat -p ios  -dr ../ios/IOS-En.strings -cr ../ios/IOS-Fr.strings  -db  ../ios/Already-translated-Fr-all.xlsx  -sh  Sheet1  -o ../ios/temp.xml

call L10nMissRewriter.bat -p mac  -dr ../mac/1.strings  -cr ../mac/2.strings  -db  ../mac/Fr-all.xlsx  -sh  Sheet1  -o ../mac/temp.xml

pause