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
									getChar();
									getChar();
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

	protected abstract String getTagValue(String str);

	protected abstract ArrayList<String> getParamsString(String str);

	protected abstract void printHead();

	protected abstract void printTail();

	protected boolean paramsCheckerBody() {
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
					return false;
				}

			}
		}
		return true;

	}

	protected void printParamsCheckerBody() {
		System.out.print("#################################################################################" + "\n");
		System.out.print("###############" + "\n");
		System.out.print("###############        parameters checking result for " + path_other + "\n");
		System.out.print("###############" + "\n");
		System.out.print("#################################################################################" + "\n");
		if (tag_changeMap.isEmpty()) {
			System.out.print("                        same                      " + "\n");
		}
		if (!tag_changeMap.isEmpty()) {
			// print missingList

			System.out.print("                         difference  " + tag_changeMap.size() + " item(s)" + "\n");
			System.out.print("\n\n");
			for (Map.Entry<String, String> entry : tag_changeMap.entrySet()) {
				System.out.print(entry.getKey() + "\n");
				System.out.print(entry.getValue() + "\n\n\n");
			}

		}
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
				if (paramsCheckerBody()) {
					printParamsCheckerBody();
					System.out.print("\n\n");
				} else {
					System.err.println("get tag value failed");
				}

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
