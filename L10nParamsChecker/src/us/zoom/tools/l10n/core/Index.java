package us.zoom.tools.l10n.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import us.zoom.tools.l10n.android.Android;
import us.zoom.tools.l10n.mac_ios.Ios;
import us.zoom.tools.l10n.windows.Windows;

public class Index {

	public static boolean check(int type, Map<String, String> commandMap) {
		if (type == 0) {
			if (commandMap.containsKey(Command.DEFAULT_LANGUAGE_RESOURCE_FILE) && commandMap.containsKey(Command.COMPARED_LANGUAGE_RESOURCE_FILE)) {
				return true;
			}
		}

		help();
		return false;

	}

	public static void help() {
		System.err.println("ERROR");
		System.err.println("HELP:");
		System.err.println("L10nParamsChecker.bat ");
		System.err.println("-p <platform> ");
		System.err.println("-dr <default language resource file> ");
		System.err.println("-cr <compared language resource file> ");
		System.err.println("-o <out file>");
	}

	public static void init_android(Map<String, String> commandMap) {
		Android appTool = new Android();
		appTool.checkerProcess(commandMap);

	}

	public static void init_windows(Map<String, String> commandMap) {
		Windows appTool = new Windows();
		appTool.checkerProcess(commandMap);
	}

	public static void init_ios(Map<String, String> commandMap) {
		Ios appTool = new Ios();
		appTool.checkerProcess(commandMap);
	}

	public static void init_mac(Map<String, String> commandMap) {
		Ios appTool = new Ios();
		appTool.checkerProcess(commandMap);
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

			} else if (commandMap.get(Command.PLATFORM).equals("mac")) {
				init_mac(commandMap);

			} else if (commandMap.get(Command.PLATFORM).equals("ios")) {
				init_ios(commandMap);

			} else {
				System.err.println("not defined platform");
			}

		}

	}
}