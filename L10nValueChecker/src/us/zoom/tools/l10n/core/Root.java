package us.zoom.tools.l10n.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import us.zoom.tools.l10n.android.Android;
import us.zoom.tools.l10n.windows.Windows;

public class Root {

	public static boolean check(int type, Map<String, String> commandMap) {
		if (type == 0) {
			if (commandMap.containsKey(Command.DEFAULT_LANGUAGE_RESOURCE_FILE) && commandMap.containsKey(Command.COMPARED_LANGUAGE_RESOURCE_FILE) && commandMap.containsKey(Command.DATABASE_FILE) && commandMap.containsKey(Command.SHEET_NAME)) {
				return true;
			}
		}

		help();
		return false;

	}

	public static void help() {
		System.err.println("ERROR");
		System.err.println("HELP: parameter can't contain blank");
		System.err.println("l10nValueChecker.bat ");
		System.err.println("-p  <platform> ");
		System.err.println("-dr <default language resource file> ");
		System.err.println("-cr <compared language resource file> ");
		System.err.println("-db <excel database file> ");
		System.err.println("-sh <sheet name> ");
		System.err.println("-o  <out file>");
	}

	public static void init_android(Map<String, String> commandMap) {
		Android appTool = new Android();
		System.err.println("start");
		appTool.rewriterProcess(commandMap);
		System.err.println("end");

	}

	public static void init_windows(Map<String, String> commandMap) {
		Windows appTool = new Windows();
		System.err.println("start");
		appTool.rewriterProcess(commandMap);
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

			if (commandMap.get(Command.PLATFORM).equals("android")) {
				init_android(commandMap);

			} else if (commandMap.get(Command.PLATFORM).equals("windows")) {
				init_windows(commandMap);

			} else {
				System.err.println("not defined platform");
			}

		}

	}
}