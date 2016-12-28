package us.zoom.tools.l10n.windows;

import java.util.HashMap;
import java.util.Map;

import us.zoom.tools.l10n.android.Android;
import us.zoom.tools.l10n.core.AbstractCore;
import us.zoom.tools.l10n.core.Command;

public class TestWindows {

	public static void main(String[] args) {
		AbstractCore tool = new Windows();
		Map<String, String> commandMap = new HashMap<String, String>();
		commandMap.put(Command.PLATFORM, "windows");
		commandMap.put(Command.DEFAULT_LANGUAGE_RESOURCE_FILE, "test/windows/win-En.xml");
		commandMap.put(Command.COMPARED_LANGUAGE_RESOURCE_FILE, "test/windows/win-Fr.xml");
		commandMap.put(Command.DATABASE_FILE, "test/windows/Already-translated-Fr-all.xlsx");
		commandMap.put(Command.SHEET_NAME, "Sheet1");
		commandMap.put(Command.OUT_FILE, "test/windows/temp.xml");
		tool.rewriterProcess(commandMap);

	}
}
