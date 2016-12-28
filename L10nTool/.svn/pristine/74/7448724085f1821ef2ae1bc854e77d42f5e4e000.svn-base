l10n-tool.bat -p<platform> -f<function> -dr<default language resource file> -cr<compared language resource file> -nr<new translations resource file> 
-svnuser<svn user> -svnpass<svn password> -svnpath<resource svn path> -revision<svn revision of history resource file> [-o<out file>]


	

platform: 
	android, 
	windows, 
	mac, 
	ios
function: 
	checker,         -p <platform> -f <function> -dr <default language resource file> -cr <compared language resource file>  [-o <out file>]
	merger,          -p<platform> -f<function> -dr<default language resource file> -cr<compared language resource file> -nr<new translations resource file> [-o <out file>]
	history-compare  -p<platform> -f<function> -dr<default language resource file> -svnuser<svn user> -svnpass<svn password> -svnpath<resource svn path> -revision<svn revision of history resource file> [-o<out file>]

除了-p和-f其它参数是可选的，不同的功能需要不同的参数，-o 用来制定输出文件，没有代表输出到console


Example:
call l10n-tool.bat -p android -f checker -dr ../android/strings.xml -cr ../android/strings2.xml -o aaaa.txt 
call l10n-tool.bat -p android -f checker -dr strings.xml -cr strings2.xml 
call l10n-tool.bat -p android -f merger -dr ../android/strings.xml -cr ../android/strings2.xml -nr ../android/patch.xml -o merger.xml
call l10n-tool.bat -p android -f history-compare -dr ../android/strings.xml -revision 18 -svnpath  https://10.100.56.178/svn/zoom/trunk/tools/l10n/L10nHistoryChange-Android/src/values/strings.xml  -o his_change.txt