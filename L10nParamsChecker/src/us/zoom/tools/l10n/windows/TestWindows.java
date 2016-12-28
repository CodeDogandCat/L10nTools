package us.zoom.tools.l10n.windows;

import java.util.HashMap;
import java.util.Map;

import us.zoom.tools.l10n.core.AbstractCore;
import us.zoom.tools.l10n.core.Command;

public class TestWindows {

	public static void main(String[] args) {
		AbstractCore tool = new Windows();
		Map<String, String> commandMap = new HashMap<String, String>();
		commandMap.put(Command.PLATFORM, "windows");
		commandMap.put(Command.DEFAULT_LANGUAGE_RESOURCE_FILE, "test/windows/language_en.xml");
		commandMap.put(Command.COMPARED_LANGUAGE_RESOURCE_FILE, "test/windows/language_zh_cn.xml");
		commandMap.put(Command.OUT_FILE, "test/windows/windows_checker.txt");
		tool.checkerProcess(commandMap);

	}
}
