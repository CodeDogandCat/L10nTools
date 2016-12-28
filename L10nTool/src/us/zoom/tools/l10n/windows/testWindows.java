package us.zoom.tools.l10n.windows;

import java.util.HashMap;
import java.util.Map;

import us.zoom.tools.l10n.core.AbstractCore;
import us.zoom.tools.l10n.core.Command;

public class testWindows {

	public static void main(String[] args) {
		AbstractCore tool = new Windows();
		Map<String, String> commandMap = new HashMap<String, String>();
		// commandMap.put(Command.PLATFORM, "windows");
		// commandMap.put(Command.FUNCTION, "history-compare");
		// commandMap.put(Command.DEFAULT_LANGUAGE_RESOURCE_FILE,
		// "test/windows/language_en.xml");
		// commandMap.put(Command.SVN_REVISION_OF_HISTORY_RESOURCE_FILE, "36");
		// commandMap.put(Command.RESOURCE_SVN_PATH,
		// "https://10.100.56.178/svn/zoom/trunk/tools/l10n/L10nTool/test/windows/language_en2.xml");
		// commandMap.put(Command.OUT_FILE,
		// "test/windows/windows_his_change.txt");
		commandMap.put(Command.PLATFORM, "windows");
		commandMap.put(Command.FUNCTION, "merger");
		commandMap.put(Command.DEFAULT_LANGUAGE_RESOURCE_FILE, "test/windows/language_en.xml");
		commandMap.put(Command.COMPARED_LANGUAGE_RESOURCE_FILE, "test/windows/language_zh_cn.xml");
		commandMap.put(Command.NEW_TRANSLATIONS_RESOURCE_FILE, "test/windows/language_zh_patch.xml");
		commandMap.put(Command.OUT_FILE, "test/windows/windows_merger.xml");
		tool.mergerProcess(commandMap);

	}
}
