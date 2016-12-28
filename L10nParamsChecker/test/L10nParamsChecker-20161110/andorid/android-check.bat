@echo off

set branch=B_35_7196
::set branch=trunk

call L10nParamsChecker.bat -p android -dr D:\SVN\%branch%\Client\src\application\Android\videobox\res\values\strings.xml -cr D:\SVN\%branch%\Client\src\application\Android\zoom\res\values-zh-rCN\strings.xml -o .\android-check-cn.txt
call L10nParamsChecker.bat -p android -dr D:\SVN\%branch%\Client\src\application\Android\videobox\res\values\strings.xml -cr D:\SVN\%branch%\Client\src\application\Android\zoom\res\values-zh-rTW\strings.xml -o .\android-check-tw.txt
call L10nParamsChecker.bat -p android -dr D:\SVN\%branch%\Client\src\application\Android\videobox\res\values\strings.xml -cr D:\SVN\%branch%\Client\src\application\Android\zoom\res\values-de\strings.xml -o .\android-check-de.txt
call L10nParamsChecker.bat -p android -dr D:\SVN\%branch%\Client\src\application\Android\videobox\res\values\strings.xml -cr D:\SVN\%branch%\Client\src\application\Android\zoom\res\values-es\strings.xml -o .\android-check-es.txt
call L10nParamsChecker.bat -p android -dr D:\SVN\%branch%\Client\src\application\Android\videobox\res\values\strings.xml -cr D:\SVN\%branch%\Client\src\application\Android\zoom\res\values-fr\strings.xml -o .\android-check-fr.txt
call L10nParamsChecker.bat -p android -dr D:\SVN\%branch%\Client\src\application\Android\videobox\res\values\strings.xml -cr D:\SVN\%branch%\Client\src\application\Android\zoom\res\values-ja\strings.xml -o .\android-check-ja.txt

pause