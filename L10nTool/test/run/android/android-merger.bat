@echo off

call ..\l10nTool.bat -p android -f merger -dr D:\SVN\trunk\Client\src\application\Android\videobox\res\values\strings.xml -cr D:\SVN\trunk\Client\src\application\Android\zoom\res\values-de\strings.xml -nr .\de.xml -o .\android_merger_de.xml

pause