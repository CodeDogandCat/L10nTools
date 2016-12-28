package us.zoom.tools.l10n.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Run {

	public static boolean check(int type, Map<String, String> commandMap) {
		if (type == 0) {
			if (commandMap.containsKey(Command.DEFAULT_LANGUAGE_RESOURCE_FILE)) {
				return true;
			}
		}

		help();
		return false;

	}

	public static void help() {
		System.err.println("ERROR");
		System.err.println("HELP: parameter can't contain blank");
		System.err.println("l10nUpdateRewriter.bat ");
		System.err.println("-dr <the reviewed excel file> ");
		System.err.println("-fr <French's excel  file> ");
	}

	public static void init(Map<String, String> commandMap) {
		System.err.println("start");
		AbstractCore temp = new AbstractCore();
		temp.rewriterProcess(commandMap);
		System.err.println("end");

	}

	public static void main(String[] args) {
		String[] temp = args[0].trim().split("#");
		ArrayList<String> commandList = new ArrayList<String>();
		for (String string : temp) {
			if (!string.equals("")) {

				commandList.add(string);
			}
		}

		if (commandList.size() % 2 != 0) {
			help();
		} else {
			Map<String, String> commandMap = new HashMap<String, String>();
			for (int i = 0; true;) {
				commandMap.put(commandList.get(i), commandList.get(i + 1));
				i += 2;
				if (i >= commandList.size()) {
					break;
				}
			}
			init(commandMap);

		}

	}
}
