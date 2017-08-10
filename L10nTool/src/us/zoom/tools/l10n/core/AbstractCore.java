package us.zoom.tools.l10n.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public abstract class AbstractCore {
	protected BufferedReader reader = null;
	protected OutputStreamWriter osw = null;
	protected PrintStream printStream = null;
	protected PrintStream sysout = System.out;
	protected String header = "";

	protected String path_default = null;
	protected String path_other = null;
	protected String path_output = null;
	protected String svnurl = null;
	protected String svnuser = null;
	protected String svnpass = null;

	protected Map<String, String> tag_defaultMap = new HashMap<String, String>();
	protected Map<String, String> tag_otherMap = new HashMap<String, String>();
	protected Map<String, String> tag_patchMap = new HashMap<String, String>();

	protected Map<String, String> tag_extraMap = new HashMap<String, String>();
	protected Map<String, String> tag_missMap = new HashMap<String, String>();
	protected Map<String, String> tag_changeMap = new HashMap<String, String>();

	protected Stack<String> tagBracketStack = new Stack<String>();
	protected StringBuffer tagBuffer = new StringBuffer();
	protected boolean run = true;
	protected int length;

	protected String path_local = null;
	protected String version = null;
	protected String path_des = null;

	/**
	 * read a char
	 * 
	 * @return null or a Character
	 */
	protected final Character getChar() {
		try {
			int temp = reader.read();
			// System.err.println("(" + (char) temp + ")");
			if (temp == -1) {
				// System.err.println("this is null");
				run = false;
				return null;
			} else {
				// System.err.println(temp);
				// System.err.println(Character.valueOf((char) temp));
				return Character.valueOf((char) temp);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	protected abstract void readHead();

	protected final String readBlank() {
		// System.err.println("readBlank");
		String c = "#$%";
		try {
			// reader.mark(length);

			if ((c = reader.readLine()) != null && run) {
				if (c.trim().equals("")) {
					// System.err.println("blank");
					return "\n";
				} else {
					// this line not a blank line
					// reader.reset();// reset to last line

					return "notblankline";

				}
			}

			return null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	protected abstract String readComment();

	protected abstract String readTag();

	protected abstract void readExt();

	/**
	 * a read process when start reading after read head
	 * 
	 * @return
	 */
	protected String[] read() {
		String result = null;
		String[] returnArray = { "zoom", "android" };
		try {
			if (run) {
				reader.mark(length);
				if ((result = readBlank()) == null) {
					// System.err.println("blank null");
					return null;
				} else {
					if (result.equals("notblankline")) {
						reader.reset();
						reader.mark(length);
						if ((result = readComment()) == null) {
							// System.err.println("Comment null");
							return null;
						} else if (result.equals("notcomment")) {
							reader.reset();
							reader.mark(length);
							if (run) {
								if ((result = readTag()) == null) {
									// System.err.println("Tag null" + "run is "
									// + run);
									return null;
								} else if (result.equals("nottag")) {
									reader.reset();
									returnArray[0] = "nottag";
									returnArray[1] = result;
									// System.err.println("not not a tag");
									return returnArray;

								} else {
									returnArray[0] = "tag";
									returnArray[1] = result;
									getChar();
									getChar();
									return returnArray;
								}
							}
						} else {
							returnArray[0] = "comment";
							returnArray[1] = result;
							getChar();
							getChar();

							return returnArray;
						}
					} else {
						returnArray[0] = "blank";
						returnArray[1] = result;
						return returnArray;
					}

				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	protected abstract Map<String, String> readFileByTags(int type, String fileName);

	protected abstract void printHead();

	protected abstract void printTail();

	protected final boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * print merger result's body
	 * 
	 * @param other_Map
	 * @throws IOException
	 */
	protected abstract void printMergerBody();

	/**
	 * print History Change Body
	 * 
	 * @param tag_reversionMap
	 * @param tag_localMap
	 * @throws IOException
	 */
	protected abstract void printHistoryChangeBody();

	protected abstract String getTagValue(String str);

	protected abstract ArrayList<String> getParamsString(String str);

	protected boolean paramsCheckerBody(Map<String, String> tag_otherMap) {
		tag_changeMap = new HashMap<String, String>();
		ArrayList<String> default_paramsList = new ArrayList<String>();
		ArrayList<String> other_paramsList = new ArrayList<String>();
		// int i = 1;
		for (Map.Entry<String, String> entry : tag_defaultMap.entrySet()) {

			if (tag_otherMap.containsKey(entry.getKey())) {
				// System.err.println(i);
				// i++;
				// System.err.println("get tag value");
				String defaultValue = getTagValue(entry.getValue());
				String otherValue = getTagValue(tag_otherMap.get(entry.getKey()));
				// System.err.println("defaultValue: " + defaultValue);
				// System.err.println("otherValue: " + otherValue);
				// System.err.println();
				if (defaultValue != null && otherValue != null) {
					default_paramsList = getParamsString(defaultValue);
					String type = default_paramsList.get(0);
					if (type.equals("type1")) {
						// compare if equal
						other_paramsList = getParamsString(otherValue);
						if (!other_paramsList.get(0).equals(type)) {
							tag_changeMap.put(entry.getValue(), tag_otherMap.get(entry.getKey()));
						} else {
							if (default_paramsList.size() != other_paramsList.size()) {
								tag_changeMap.put(entry.getValue(), tag_otherMap.get(entry.getKey()));
							} else {
								for (int i = 1; i < default_paramsList.size(); i++) {
									String temp = default_paramsList.get(i);
									if (temp != null && !temp.equals("")) {
										if (!temp.equals(other_paramsList.get(i))) {
											// System.err.println("-------" +
											// type);
											// System.err.println(entry.getValue());
											// System.err.println(tag_otherMap.get(entry.getKey()));
											tag_changeMap.put(entry.getValue(), tag_otherMap.get(entry.getKey()));
											break;
										}
									}

								}
							}
						}
					} else if (type.equals("type2")) {
						// compare if contains
						other_paramsList = getParamsString(otherValue);
						if (!other_paramsList.get(0).equals(type)) {
							tag_changeMap.put(entry.getValue(), tag_otherMap.get(entry.getKey()));
						} else {
							if (default_paramsList.size() != other_paramsList.size()) {
								tag_changeMap.put(entry.getValue(), tag_otherMap.get(entry.getKey()));
							} else {
								for (int i = 1; i < default_paramsList.size(); i++) {
									String temp = default_paramsList.get(i);
									if (temp != null && !temp.equals("")) {

										if (!other_paramsList.contains(default_paramsList.get(i))) {
											// System.err.println("-------" +
											// type);
											// System.err.println(entry.getValue());
											// System.err.println(tag_otherMap.get(entry.getKey()));
											tag_changeMap.put(entry.getValue(), tag_otherMap.get(entry.getKey()));
											break;
										}
									}
								}
							}
						}
					}

				} else {
					System.err.println("default" + entry.getValue());
					System.err.println("other" + tag_otherMap.get(entry.getKey()));
					return false;
				}

			}
		}
		return true;

	}

	protected boolean printParamsCheckerBody(String path_other) {
		System.setOut(sysout);

		if (!tag_changeMap.isEmpty()) {
			// print missingList
			System.out.print("####################################################################" + "\n");
			System.out.print("####" + "\n");
			System.out.print("####        parameters check result for " + path_other + "\n");
			System.out.print("####" + "\n");
			System.out.print("####################################################################" + "\n");
			System.out.print("                         difference  " + tag_changeMap.size() + " item(s)" + "\n");
			System.out.print("\n\n");
			for (Map.Entry<String, String> entry : tag_changeMap.entrySet()) {
				System.out.print(entry.getKey() + "\n");
				System.out.print(entry.getValue() + "\n\n\n");
			}

			return false;
		}
		return true;
	}

	/**
	 * compare other xml file with the default file
	 * 
	 * @param path_en
	 *            the default file path
	 * @param path_zh
	 *            the other file path
	 * @param path_output
	 *            the output file path
	 */
	public final void checkerProcess(Map<String, String> commandMap) {

		try {
			path_default = commandMap.get(Command.DEFAULT_LANGUAGE_RESOURCE_FILE);
			path_other = commandMap.get(Command.COMPARED_LANGUAGE_RESOURCE_FILE);

			if (commandMap.containsKey(Command.OUT_FILE)) {
				path_output = commandMap.get(Command.OUT_FILE);
				FileOutputStream fos;
				fos = new FileOutputStream(path_output);
				printStream = new PrintStream(fos, true, "utf-8");
				System.setOut(printStream);
			}

			// read xml file to arraylist
			tag_defaultMap = readFileByTags(0, path_default);
			tag_otherMap = readFileByTags(0, path_other);

			// System.err.println("------------" + tag_defaultMap.size());
			// System.err.println("------------" + tag_otherMap.size());

			if (tag_defaultMap.size() != 0 && tag_otherMap.size() != 0) {
				// loop through tag_enMap , if tag_zhMap doesn't
				// contains
				// the item ,then add into the misslist
				tag_missMap = new HashMap<String, String>();
				for (Map.Entry<String, String> entry : tag_defaultMap.entrySet()) {

					if (!tag_otherMap.containsKey(entry.getKey())) {
						tag_missMap.put(entry.getKey(), entry.getValue());
					}
				}

				// loop through tag_zhMap , if tag_enMap doesn't
				// contains
				// the item ,then add into the extralist
				tag_extraMap = new HashMap<String, String>();

				for (Map.Entry<String, String> entry : tag_otherMap.entrySet()) {

					if (!tag_defaultMap.containsKey(entry.getKey())) {
						tag_extraMap.put(entry.getKey(), entry.getValue());
					}
				}
				System.out.print(
						"#################################################################################" + "\n");
				System.out.print("###############" + "\n");
				System.out.print("###############         checking result for " + path_other + "\n");
				System.out.print("###############" + "\n");
				System.out.print(
						"#################################################################################" + "\n");

				if (tag_missMap.isEmpty() && tag_extraMap.isEmpty()) {
					System.out.print("                        perfect                      " + "\n");
				}
				if (!tag_missMap.isEmpty()) {
					// print missingList

					System.out.print("                         miss  " + tag_missMap.size() + " item(s)" + "\n");
					System.out.print("\n\n");
					for (Map.Entry<String, String> entry : tag_missMap.entrySet()) {
						System.out.print(entry.getValue() + "\n");
					}

				}
				if (!tag_extraMap.isEmpty()) {
					// print extraList
					System.out.print("\n\n");

					System.out.print("                         extra  " + tag_extraMap.size() + " item(s)" + "\n");
					System.out.print("\n\n");
					for (Map.Entry<String, String> entry : tag_extraMap.entrySet()) {
						System.out.print(entry.getValue() + "\n");
					}
				}
				System.out.print("\n\n");
				System.setOut(sysout);
			} else {

				System.err.println("get XMLinfo failed");
			}
		} catch (Exception e) {
			e.printStackTrace();

			System.err.println("result output failed ");
		} finally {
			System.setOut(sysout);
		}

	}

	@SuppressWarnings("finally")
	public final boolean ParamsCheckerProcess(Map<String, String> tag_defaultMap,
			Map<String, String> tag_otherMap_backup, Map<String, String> tag_patchMap, String path_other,
			String path_patch) {
		System.setOut(sysout);
		boolean flag = false;

		try {

			// System.err.println("------------" + tag_defaultMap.size());
			// System.err.println("------------" + tag_otherMap.size());

			if (tag_defaultMap.size() != 0 && tag_otherMap_backup.size() != 0) {

				if (paramsCheckerBody(tag_otherMap_backup)) {
					if (printParamsCheckerBody(path_other)) {
						flag = true;
					}
					System.out.print("\n\n");
				} else {
					System.err.println("get tag value failed");
				}

			} else {

				System.out.println("get XMLinfo failed");
			}
			if (tag_defaultMap.size() != 0 && tag_patchMap.size() != 0) {

				if (paramsCheckerBody(tag_patchMap)) {
					if (printParamsCheckerBody(path_patch)) {
						flag = true;
					}
					System.out.print("\n\n");
				} else {
					System.err.println("get tag value failed");
				}

			} else {

				System.err.println("get XMLinfo failed");
			}
		} catch (Exception e) {
			e.printStackTrace();

			System.err.println("result output failed ");
		} finally {
			System.setOut(sysout);
			return flag;
		}

	}

	/**
	 * merger a patch file into the other file
	 * 
	 * @param path_en
	 * @param other_Map
	 * @param patch_Map
	 * @param path_output
	 */
	public final void mergerProcess(Map<String, String> commandMap) {

		try {

			path_default = commandMap.get(Command.DEFAULT_LANGUAGE_RESOURCE_FILE);
			path_other = commandMap.get(Command.COMPARED_LANGUAGE_RESOURCE_FILE);
			System.err.println("..........");
			tag_otherMap = readFileByTags(1, path_other);
			System.err.println(".........2");
			tag_patchMap = readFileByTags(1, commandMap.get(Command.NEW_TRANSLATIONS_RESOURCE_FILE));
			System.err.println(".........3");
			if (commandMap.containsKey(Command.OUT_FILE)) {
				path_output = commandMap.get(Command.OUT_FILE);
				FileOutputStream fos;
				fos = new FileOutputStream(path_output);
				printStream = new PrintStream(fos, true, "utf-8");
				System.setOut(printStream);
			}

			File file = new File(path_default);
			length = 8192;
			// length = (int) file.length();

			InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
			reader = new BufferedReader(isr);

			// override other_Map with patch_Map
			tag_otherMap.putAll(tag_patchMap);

			readHead();
			printHead();
			System.err.println(".........4");
			printMergerBody();
			printTail();
			System.setOut(sysout);
			reader.close();
			tag_otherMap = readFileByTags(0, path_other);
			Map<String, String> tag_otherMap_backup = readFileByTags(0, path_other);
			tag_patchMap = readFileByTags(0, commandMap.get(Command.NEW_TRANSLATIONS_RESOURCE_FILE));
			tag_defaultMap = readFileByTags(0, path_default);
			tag_otherMap.putAll(tag_patchMap);
			// ExtraCheckerAfterMerger(tag_defaultMap, tag_otherMap_backup,
			// tag_patchMap, path_other,
			// commandMap.get(Command.NEW_TRANSLATIONS_RESOURCE_FILE));
			// ParamsCheckerProcess(tag_defaultMap, tag_otherMap_backup,
			// tag_patchMap, path_other,
			// commandMap.get(Command.NEW_TRANSLATIONS_RESOURCE_FILE));

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("result output failed ");
		} finally {
			System.setOut(sysout);
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					System.err.println("close BufferedReader error");
				}

			}

		}
	}

	protected final void ExtraCheckerAfterMerger(Map<String, String> tag_defaultMap,
			Map<String, String> tag_otherMap_backup, Map<String, String> tag_patchMap, String path_other,
			String path_patch) {
		System.setOut(sysout);
		tag_extraMap = new HashMap<String, String>();
		Map<String, String> tag_extraMap2 = new HashMap<String, String>();

		for (Map.Entry<String, String> entry : tag_otherMap_backup.entrySet()) {

			if (!tag_defaultMap.containsKey(entry.getKey())) {

				tag_extraMap.put(entry.getKey(), entry.getValue());
			}
		}
		for (Map.Entry<String, String> entry : tag_patchMap.entrySet()) {

			if (!tag_defaultMap.containsKey(entry.getKey())) {

				tag_extraMap2.put(entry.getKey(), entry.getValue());
			}
		}

		if (!tag_extraMap.isEmpty()) {
			System.out.print("####################################################################" + "\n");
			System.out.print("####" + "\n");
			System.out.print("####         extra check result for merger" + path_other + "\n");
			System.out.print("####" + "\n");
			System.out.print("####################################################################" + "\n");
			// print extraList
			System.out.print("\n\n");

			System.out.print("                         extra  " + tag_extraMap.size() + " item(s)" + "\n");
			System.out.print("\n\n");
			for (Map.Entry<String, String> entry : tag_extraMap.entrySet()) {
				System.out.print(entry.getValue() + "\n");
			}
		}
		if (!tag_extraMap2.isEmpty()) {
			System.out.print("####################################################################" + "\n");
			System.out.print("####" + "\n");
			System.out.print("####         extra check result for merger" + path_patch + "\n");
			System.out.print("####" + "\n");
			System.out.print("####################################################################" + "\n");
			// print extraList
			System.out.print("\n\n");

			System.out.print("                         extra  " + tag_extraMap2.size() + " item(s)" + "\n");
			System.out.print("\n\n");
			for (Map.Entry<String, String> entry : tag_extraMap2.entrySet()) {
				System.out.print(entry.getValue() + "\n");
			}
		}
		System.out.print("\n\n");
		System.setOut(sysout);
	}

	/**
	 * compare the local modified strings.xml with the reversion in the svn
	 * server
	 * 
	 */
	public final void historyCompareProcess(Map<String, String> commandMap) {
		try {
			version = commandMap.get(Command.SVN_REVISION_OF_HISTORY_RESOURCE_FILE);
			path_local = commandMap.get(Command.DEFAULT_LANGUAGE_RESOURCE_FILE);
			svnurl = commandMap.get(Command.RESOURCE_SVN_PATH);
			if (commandMap.containsKey(Command.SVN_USER) && commandMap.containsKey(Command.SVN_PASSWORD)) {
				svnuser = commandMap.get(Command.SVN_USER);
				svnpass = commandMap.get(Command.SVN_PASSWORD);
			}
			String fileName = svnurl.substring(svnurl.lastIndexOf("/") + 1, svnurl.length());
			path_des = getPath() + "/" + fileName;

			if (commandMap.containsKey(Command.OUT_FILE)) {
				path_output = commandMap.get(Command.OUT_FILE);
				FileOutputStream fos;
				fos = new FileOutputStream(path_output);
				printStream = new PrintStream(fos, true, "utf-8");

			}
			// export
			Runtime runtime = Runtime.getRuntime();
			Process exportProcess;
			if (svnuser != null && svnpass != null) {

				exportProcess = runtime.exec("cmd /c svn export -r " + version + " " + svnurl + " " + getPath() + "/"
						+ " " + "--username " + svnuser + " --password " + svnpass);
			} else {

				exportProcess = runtime.exec("cmd /c svn export -r " + version + " " + svnurl + " " + getPath() + "/");
			}
			try {

				exportProcess.waitFor();
				// read xml file to arraylist
				// System.err.println(path_des);
				// System.err.println(path_local);

				tag_defaultMap = readFileByTags(0, path_des);

				tag_otherMap = readFileByTags(0, path_local);

				// System.err.println(tag_defaultMap.size());
				// System.err.println(tag_otherMap.size());

				System.setOut(printStream);
				printHistoryChangeBody();
				System.setOut(sysout);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			System.err.println("result output failed ");
		} finally {
			System.setOut(sysout);
			deleteFile(path_des);
		}
	}

	public static final String getPath() {
		URL url = AbstractCore.class.getProtectionDomain().getCodeSource().getLocation();
		String filePath = null;
		try {
			filePath = URLDecoder.decode(url.getPath(), "utf-8");// utf-8
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (filePath.endsWith(".jar")) {//
			//
			filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
		}

		File file = new File(filePath);

		// /If this abstract pathname is already absolute, then the pathname
		// string is simply returned as if by the getPath method. If this
		// abstract pathname is the empty abstract pathname then the pathname
		// string of the current user directory, which is named by the system
		// property user.dir, is returned.
		filePath = file.getAbsolutePath();//
		return filePath;

	}

}
