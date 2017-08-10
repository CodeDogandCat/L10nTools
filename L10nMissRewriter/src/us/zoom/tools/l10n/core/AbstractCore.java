package us.zoom.tools.l10n.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	protected String path_db = null;
	protected String sheetName = null;

	protected Map<String, String> tag_defaultMap = new HashMap<String, String>();
	protected Map<String, String> tag_otherMap = new HashMap<String, String>();
	// <name ,value>
	protected Map<String, String> tag_missMap = new HashMap<String, String>();
	// <default_value, other_value>
	protected Map<String, String> tag_default_other = new HashMap<String, String>();
	// list<string> :<string name="">(other value found in the database
	// file)</string>
	protected List<String> rewriteList = null;
	protected List<String> rewrite_miss_List = null;

	protected Stack<String> tagBracketStack = new Stack<String>();
	protected StringBuffer tagBuffer = new StringBuffer();
	protected boolean run = true;
	protected int length;
	protected ExcelManage em;

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

	protected boolean readValueFromExcel(String fileDir, String sheetName) {
		ExcelManage em = new ExcelManage();
		tag_default_other = new HashMap<String, String>();

		if (em.fileExist(fileDir)) {

			if (em.sheetExist(fileDir, sheetName)) {

				Item item = new Item();
				List list = em.readFromExcel(fileDir, sheetName, item);
				for (int i = 0; i < list.size(); i++) {
					item = (Item) list.get(i);
					tag_default_other.put(item.getEnglish(), item.getOther());

				}
				return true;
			}
		}

		return false;
	}

	protected abstract String getTagValue(String str);

	protected abstract String setTagValue(String str, String otherValue);

	protected abstract void printHead();

	protected abstract void printTail();

	protected boolean CheckerBody() {
		tag_missMap = new HashMap<String, String>();
		rewrite_miss_List = new ArrayList<String>();
		rewriteList = new ArrayList<String>();
		String rewriteString = null;
		if (readValueFromExcel(path_db, sheetName)) {
			// System.err.println("##############################");
			// for (Map.Entry<String, String> entry :
			// tag_default_other.entrySet()) {
			// System.err.println(entry.getKey());
			// System.err.println(entry.getValue());
			// System.err.println();
			// }
			// System.err.println("##############################");

			for (Map.Entry<String, String> entry : tag_defaultMap.entrySet()) {
				// System.err.println("en key: " + entry.getKey() + ", en str :"
				// + entry.getValue());
				if (!tag_otherMap.containsKey(entry.getKey())) {

					tag_missMap.put(entry.getKey(), entry.getValue());

					String defaultValue = getTagValue(entry.getValue());
					// System.err.println("de not contain the key, get value : "
					// + defaultValue);
					// boolean flag = false;
					// if (defaultValue.equals("Click ${joinMeetingUrl} to start
					// or join a scheduled Zoom meeting.")) {
					// System.err.println("get en str");
					// flag = true;
					// }
					String otherValue = "";

					if (tag_default_other.containsKey(defaultValue)) {

						// System.err.println(defaultValue);

						otherValue = tag_default_other.get(defaultValue);
						// System.err.println("get en str, and find de str:" +
						// otherValue);
						// System.err.println(otherValue);

						// rewrite to string xml
						if ((rewriteString = setTagValue(entry.getValue(), otherValue)) != null) {
							rewriteList.add(rewriteString);
							// System.err.println("rewritestring: " +
							// rewriteString);
						} else {
							rewrite_miss_List.add(entry.getValue());
						}

					} else {
						rewrite_miss_List.add(entry.getValue());
					}

				}
			}
			return true;
		} else {
			return false;
		}

	}

	protected void printCheckerBody() {
		System.out.print("#################################################################################" + "\n");
		System.out.print("###############" + "\n");
		System.out.print("###############        parameters checking result for " + path_other + "\n");
		System.out.print("###############" + "\n");
		System.out.print("#################################################################################" + "\n");
		if (tag_missMap.isEmpty()) {
			System.out.print("                        same                      " + "\n");
		}
		if (!tag_missMap.isEmpty()) {
			// print missingList
			if (rewriteList.size() != 0) {
				System.out.print("                        rewrite  " + rewriteList.size() + " item(s)" + "\n");
				System.out.print("\n\n");
				for (String string : rewriteList) {
					System.out.println(string);
				}
			}
			if (rewrite_miss_List.size() != 0) {
				System.out
						.print("                        rewrite  miss " + rewrite_miss_List.size() + " item(s)" + "\n");
				System.out.print("\n\n");
				for (String string : rewrite_miss_List) {
					System.out.println(string);
				}
			}

		}
	}

	public final void rewriterProcess(Map<String, String> commandMap) {

		try {
			path_default = commandMap.get(Command.DEFAULT_LANGUAGE_RESOURCE_FILE);
			path_other = commandMap.get(Command.COMPARED_LANGUAGE_RESOURCE_FILE);
			path_db = commandMap.get(Command.DATABASE_FILE);

			if (commandMap.containsKey(Command.OUT_FILE)) {
				path_output = commandMap.get(Command.OUT_FILE);
				FileOutputStream fos;
				fos = new FileOutputStream(path_output);
				printStream = new PrintStream(fos, true, "utf-8");
				System.setOut(printStream);
			}
			if (commandMap.containsKey(Command.SHEET_NAME)) {
				sheetName = commandMap.get(Command.SHEET_NAME);
			} else {
				sheetName = "Sheet1";
			}

			// read xml file to arraylist
			tag_defaultMap = readFileByTags(0, path_default);
			tag_otherMap = readFileByTags(0, path_other);
			//
			// System.err.println("------------" + tag_defaultMap.size());
			// System.err.println("------------" + tag_otherMap.size());

			if (tag_defaultMap.size() != 0 && tag_otherMap.size() != 0) {
				if (CheckerBody()) {
					printCheckerBody();
					System.out.print("\n\n");
				} else {
					System.err.println(
							"get tag value failed,check you excel title, it must be \"English\" and \"Other\"");
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
