package us.zoom.tools.l10n.mac_ios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import us.zoom.tools.l10n.core.AbstractCore;
import us.zoom.tools.l10n.core.Command;

public class TestIos {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// String regEx = "\\\"(.*?)\\\"\\s*=";
		// String tempString = "\"You changed the group name to\\\"%@\\\" \" =";
		// // = "You changed the group name to \"%@\"";
		// Pattern pattern = Pattern.compile(regEx);
		// Matcher matcher = pattern.matcher(tempString);
		// if (matcher.find()) {
		// System.out.println(matcher.group(1) + "\t" + tempString.trim());
		// }

		// System.out.println("//error".charAt(0));
		// System.out.println("//error".charAt(1));
		// System.out.println("//error".charAt(2));

		// File file = new File("test/ios/tmp.strings");
		//
		// try {
		// InputStreamReader isr = new InputStreamReader(new
		// FileInputStream(file), "UTF-8");
		// BufferedReader reader = new BufferedReader(isr);
		// String c = "#$%";
		// c = reader.readLine();
		// System.err.println(c);
		//
		// } catch (Exception e) {
		// // TODO: handle exception
		// e.printStackTrace();
		//
		// }

		AbstractCore tool = new Ios();
		Map<String, String> commandMap = new HashMap<String, String>();
		commandMap.put(Command.PLATFORM, "mac");
		commandMap.put(Command.FUNCTION, "merger");
		commandMap.put(Command.DEFAULT_LANGUAGE_RESOURCE_FILE, "test/mac/mac-en.xml");
		commandMap.put(Command.COMPARED_LANGUAGE_RESOURCE_FILE, "test/mac/mac-fr.xml");
		commandMap.put(Command.NEW_TRANSLATIONS_RESOURCE_FILE, "test/mac/mac_fr_merger.xml");
		tool.mergerProcess(commandMap);

	}
}
