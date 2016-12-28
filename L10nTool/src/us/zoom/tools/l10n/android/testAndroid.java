package us.zoom.tools.l10n.android;

import java.util.HashMap;
import java.util.Map;

import us.zoom.tools.l10n.core.Command;

public class testAndroid {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Android tool = new Android();
		Map<String, String> commandMap = new HashMap<String, String>();
		commandMap.put(Command.PLATFORM, "android");
		commandMap.put(Command.FUNCTION, "merger");
		commandMap.put(Command.DEFAULT_LANGUAGE_RESOURCE_FILE, "test/android/strings.xml");
		commandMap.put(Command.COMPARED_LANGUAGE_RESOURCE_FILE, "test/android/strings2.xml");
		commandMap.put(Command.NEW_TRANSLATIONS_RESOURCE_FILE, "test/android/patch.xml");
		commandMap.put(Command.OUT_FILE, "test/android/android_merger.xml");
		tool.mergerProcess(commandMap);

		// commandMap.put(Command.PLATFORM, "android");
		// commandMap.put(Command.FUNCTION, "merger");
		// commandMap.put(Command.DEFAULT_LANGUAGE_RESOURCE_FILE,
		// "test/android/strings.xml");
		// commandMap.put(Command.SVN_REVISION_OF_HISTORY_RESOURCE_FILE, "36");
		// commandMap.put(Command.RESOURCE_SVN_PATH,
		// " https://10.100.56.178/svn/zoom/trunk/tools/l10n/L10nTool/test/android/strings.xml");
		// commandMap.put(Command.OUT_FILE,
		// "test/android/android_his_change.xml");
		// tool.historyCompareProcess(commandMap);

	}
}
