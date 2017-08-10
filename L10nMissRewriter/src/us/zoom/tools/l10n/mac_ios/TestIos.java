package us.zoom.tools.l10n.mac_ios;

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
		// Map<String, String> commandMap = new HashMap<String, String>();
		// commandMap.put(Command.PLATFORM, "ios");
		// commandMap.put(Command.DEFAULT_LANGUAGE_RESOURCE_FILE,
		// "test/ios/IOS-En.strings");
		// commandMap.put(Command.COMPARED_LANGUAGE_RESOURCE_FILE,
		// "test/ios/IOS-Fr.strings");
		// commandMap.put(Command.DATABASE_FILE,
		// "test/ios/Already-translated-Fr-all.xlsx");
		// commandMap.put(Command.SHEET_NAME, "Sheet1");
		// commandMap.put(Command.OUT_FILE, "test/ios/ios_rewriter.xml");
		// tool.rewriterProcess(commandMap);

		Map<String, String> commandMap = new HashMap<String, String>();
		commandMap.put(Command.PLATFORM, "mac");
		commandMap.put(Command.DEFAULT_LANGUAGE_RESOURCE_FILE, "test/mac/en.strings");
		commandMap.put(Command.COMPARED_LANGUAGE_RESOURCE_FILE, "test/mac/pt.strings");
		commandMap.put(Command.DATABASE_FILE, "test/mac/Portuguese.xls");
		commandMap.put(Command.SHEET_NAME, "Sheet1");
		commandMap.put(Command.OUT_FILE, "test/mac/dadada.xml");
		tool.rewriterProcess(commandMap);

	}
}
