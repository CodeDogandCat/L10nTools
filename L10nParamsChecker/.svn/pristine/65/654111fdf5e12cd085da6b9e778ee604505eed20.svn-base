package us.zoom.tools.l10n.android;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import us.zoom.tools.l10n.core.AbstractCore;

public class Android extends AbstractCore {

	@Override
	protected void readHead() {
		try {
			Character c = null;
			reader.mark(length);
			header = "";
			while ((c = getChar()) != null && run) {
				header += c;
				if (c == '<') {

					if ((c = getChar()) != null && run) {
						header += c;
						if (c == 's') {
							if ((c = getChar()) != null && run) {
								header += c;
								if (c == 't') {
									if ((c = getChar()) != null && run) {
										header += c;
										if (c == 'r') {
											if ((c = getChar()) != null && run) {
												header += c;
												if (c == 'i') {
													if ((c = getChar()) != null && run) {
														header += c;
														if (c == 'n') {
															if ((c = getChar()) != null && run) {
																header += c;
																if (c == 'g') {
																	if ((c = getChar()) != null && run) {
																		header += c;
																		if (c == ' ') {
																			header = header.substring(0, header.lastIndexOf('<'));
																			reader.reset();
																			break;
																		}
																	}

																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				} else {
					reader.mark(length);
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected String readComment() {
		Character c;
		// <string name="zm_msg_conffail_internal_only_sign">Please sign in
		// with your company account to join.</string>
		// while (true) {
		// remark , be used to reset
		// reader.mark(length);
		while ((c = getChar()) != null && run) {
			if (c == '<') {
				break;
			}
		}
		// System.out.println("jump--" + c);
		if (c == null) {
			return null;
		}
		// <!--
		StringBuffer commentbBuffer = new StringBuffer();
		// go until be sure it isn't a comment
		c = getChar();

		if (c == '!') {

			c = getChar();
			if (c == '-') {

				c = getChar();

				if (c == '-') {
					// System.out.println("------it is a comment");
					commentbBuffer.append("<!--");
					while (true) {// just go until the --> then
									// return

						// -->
						if ((c = getChar()) != null && run) {
							commentbBuffer.append(c);
							if (c == '-') {
								if ((c = getChar()) != null && run) {
									commentbBuffer.append(c);
									if (c == '-') {
										if ((c = getChar()) != null && run) {
											commentbBuffer.append(c);
											if (c == '>') {
												String commentContent = commentbBuffer.toString();
												// System.out.println("-----------readComment()"
												// + commentContent);
												commentbBuffer.setLength(0);// clean
																			// the
																			// buffer
												return commentContent;
											}
										}
									}
								}
							}

						}

					}
				}
			}
		} else {// reset,because be sure this is not a comment
			// System.out.println("notcomment");
			// reader.reset();
			return "notcomment";
		}
		return null;
	}

	@Override
	protected String readTag() {
		boolean flag = false;
		boolean readNextChar = true;
		Character c;
		// </ and />
		// reader.mark(length);
		c = getChar();
		while (c != null && run) {
			if (c == '<') {
				// System.err.println("it is <");
				if ((c = getChar()) != '!') {// it is a tag
					tagBuffer.append("<" + c);
					if (c != '/') {

						tagBracketStack.push("<");

					} else {// it's </,but the tag isn't completed

						if (!tagBracketStack.isEmpty()) {
							tagBracketStack.pop();
							// System.err.println("it should pop");
						} else {
							// System.err.println("--------------null-----1");
							return null;
						}
						flag = true;
						// read until the end of the tag
						while ((c = getChar()) != '>' && run) {
							tagBuffer.append(c);
						}
						tagBuffer.append(c);// add >

					}
				} else {
					// maybe it is also a tag
					// but
					// <string
					// name="zm_lbl_forget_password_link"><![CDATA[<a
					// href="%s">Forgot password?</a>]]></string>

					// ## alter##
					// <plurals name="zm_msg_invitations_sent">
					// <!-- <item quantity="one">1个邀请已发出</item> -->
					// <item quantity="other">%d个邀请已经发出</item>
					// </plurals>
					tagBuffer.append("<" + c);
					if (tagBracketStack.isEmpty()) {

						if ((c = getChar()) != null && run) {
							tagBuffer.append(c);
							if (c == '-') {
								if ((c = getChar()) != null && run) {
									tagBuffer.append(c);
									if (c == '-') {
										// be sure it is not a tag, it is a
										// comment
										// reader.reset();
										return "nottag";
									}
								}
							}
						}
					}

				}

			} else if (c == '/') {// maybe />
				tagBuffer.append(c);
				// System.err.println("it is /");
				if ((c = getChar()) == '>') {
					// it's /> ,and the tag is completed
					tagBuffer.append(c);

					if (!tagBracketStack.isEmpty()) {
						tagBracketStack.pop();
						flag = true;
					} else {
						// System.err.println("--------------null-----2");
						return null;
					}

				} else {
					if (c == '<') {
						readNextChar = false;
					}
					tagBuffer.append(c);
				}
			} else {// it isn't start or end mark

				tagBuffer.append(c);
			}

			if (tagBracketStack.isEmpty() && flag) {
				String tagContent = tagBuffer.toString();

				// System.out.println(tagContent);
				tagBuffer.setLength(0);
				return tagContent;
			} else {
				if (readNextChar) {

					c = getChar();// read next char
				}
				readNextChar = true;
			}

		}

		// System.err.println("--------------null-----3");
		// System.err.println("--------------" + c);
		return null;
	}

	@Override
	protected void readExt() {
		// TODO Auto-generated method stub

	}

	@Override
	protected Map<String, String> readFileByTags(int type, String fileName) {
		tagBuffer.setLength(0);
		// System.err.println(fileName + "***************");
		File file = new File(fileName);
		// length = (int) file.length();
		length = 8192;
		// BufferedReader reader = null;
		Map<String, String> tag_Map = new HashMap<String, String>();
		try {

			InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
			reader = new BufferedReader(isr);
			// jump <?xml <resources>
			readHead();

			String tempString = null;
			String[] returnArray = { "", "" };
			int i = 1;

			while ((returnArray = read()) != null && run) {
				// System.err.println(returnArray[0] + "**" + returnArray[1]);

				if (returnArray[0].equals("tag")) {
					// if (!returnArray[1].equals("nottag")) {
					tempString = returnArray[1];
					// System.out.println(tempString);
					// eg
					// <string name="zm_msg_no_zoom_rooms">No Rooms
					// yet</string>
					// operation
					String regEx = "<string.*?name=\\\"(.*?)\\\">.*?</string>";
					Pattern pattern = Pattern.compile(regEx);
					Matcher matcher = pattern.matcher(tempString);
					if (matcher.find()) {
						tag_Map.put(matcher.group(1), "\t" + tempString.trim());
					} else {
						regEx = "<string.*?name='(.*?)'>.*?</string>";
						pattern = Pattern.compile(regEx);
						matcher = pattern.matcher(tempString);
						if (matcher.find()) {
							tag_Map.put(matcher.group(1), "\t" + tempString.trim());
						} else {
							// eg
							// <plurals name="zm_lbl_folder_items">
							// <item quantity="one">1 Item</item>
							// <item quantity="other">%d Items</item>
							// </plurals>
							// operation
							regEx = "<plurals.*?name=\\\"(.*?)\\\">";
							pattern = Pattern.compile(regEx);
							matcher = pattern.matcher(tempString);
							if (matcher.find()) {

								tag_Map.put(matcher.group(1), "\t" + tempString.trim());
							} else {
								regEx = "<plurals.*?name='(.*?)'>";
								pattern = Pattern.compile(regEx);
								matcher = pattern.matcher(tempString);
								if (matcher.find()) {
									tag_Map.put(matcher.group(1), "\t" + tempString.trim());
								}
							}
						}

					}
					// }

				} else if (returnArray[0].equals("blank")) {
					// System.out.println("get a blank line");
					// tempString = returnArray[1];
				} else {
					if (type == 0) {

					} else if (type == 1) {
						if (returnArray[0].equals("comment")) {
							tempString = returnArray[1];
							tag_Map.put(tempString.trim(), "");
							// System.out.println("get a comment");

						}
					}
				}

			}
			// System.err.println(returnArray + "run is " + run);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("read xml file error , maybe path error");
		} finally {
			// reset
			run = true;
			tagBracketStack.clear();
			tagBuffer.setLength(0);
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					System.err.println("close BufferedReader error");
				}

			}
			return tag_Map;
		}
	}

	@Override
	protected ArrayList<String> getParamsString(String str) {
		ArrayList<String> paramsList = new ArrayList<String>();
		paramsList.add("type1");
		int length = str.length();

		ArrayList<Character> twotonine = new ArrayList<Character>();

		twotonine.add('2');
		twotonine.add('3');
		twotonine.add('4');
		twotonine.add('5');
		twotonine.add('6');
		twotonine.add('7');
		twotonine.add('8');
		twotonine.add('9');

		ArrayList<Character> onetonine = new ArrayList<Character>();
		onetonine.add('1');
		onetonine.add('2');
		onetonine.add('3');
		onetonine.add('4');
		onetonine.add('5');
		onetonine.add('6');
		onetonine.add('7');
		onetonine.add('8');
		onetonine.add('9');
		boolean flag = true;
		char tmp;
		for (int i = 0; i < length; i++) {
			// %
			tmp = str.charAt(i);
			if (tmp == '%') {
				if (i + 1 < length) {
					tmp = str.charAt(++i);
					if (flag) {
						if (tmp == '%') {
							// %% just to print a %
						} else if (tmp == 's') {
							// %s parameter
							paramsList.add("%s");
						} else if (tmp == 'd') {
							// %d parameter
							paramsList.add("%d");
						} else if (tmp == 'f') {
							// %f parameter
							paramsList.add("%f");
						} else if (tmp == '.') {
							if (i + 1 < length) {
								tmp = str.charAt(++i);
								if (twotonine.contains(tmp)) {
									char num = tmp;
									if (i + 1 < length) {
										tmp = str.charAt(++i);
										if (tmp == 'f') {
											// %.2f parameter
											paramsList.add("%." + num + "f");
										}
									}
								}
							}
						} else if (onetonine.contains(tmp)) {
							char number = tmp;
							if (i + 1 < length) {

								tmp = str.charAt(++i);

								if (tmp == '$') {

									// %2$xxxxxx
									if (i + 1 < length) {
										tmp = str.charAt(++i);
										if (tmp == 's') {
											// %s parameter
											paramsList.clear();
											paramsList.add("type2");
											paramsList.add("%" + number + "$" + "s");
											flag = false;
										} else if (tmp == 'd') {
											// %d parameter
											paramsList.clear();
											paramsList.add("type2");
											paramsList.add("%" + number + "$" + "d");
											flag = false;
										} else if (tmp == 'f') {
											// %f parameter
											paramsList.clear();
											paramsList.add("type2");
											paramsList.add("%" + number + "$" + "f");
											flag = false;
										} else if (tmp == '.') {
											if (i + 1 < length) {
												tmp = str.charAt(++i);
												if (twotonine.contains(tmp)) {
													char num = tmp;
													if (i + 1 < length) {
														tmp = str.charAt(++i);
														if (tmp == 'f') {
															// %.2f parameter
															// remove type1
															// params
															paramsList.clear();
															paramsList.add("type2");
															paramsList.add("%" + number + "$" + "." + num + "f");
															flag = false;
														}
													}
												}
											}
										}
									}

								}
							}
						}
					} else {
						if (onetonine.contains(tmp)) {
							char number = tmp;
							if (i + 1 < length) {

								tmp = str.charAt(++i);

								if (tmp == '$') {

									// %2$xxxxxx
									if (i + 1 < length) {
										tmp = str.charAt(++i);
										if (tmp == 's') {
											// %s parameter
											paramsList.add("%" + number + "$" + "s");
										} else if (tmp == 'd') {
											// %d parameter
											paramsList.add("%" + number + "$" + "d");
										} else if (tmp == 'f') {
											// %f parameter
											paramsList.add("%" + number + "$" + "f");
										} else if (tmp == '.') {
											if (i + 1 < length) {
												tmp = str.charAt(++i);
												if (twotonine.contains(tmp)) {
													char num = tmp;
													if (i + 1 < length) {
														tmp = str.charAt(++i);
														if (tmp == 'f') {
															// %.2f parameter
															paramsList.add("%" + number + "$" + "." + num + "f");

														}
													}
												}
											}
										}
									}

								}
							}
						}
					}

				}

			} else if (tmp == '$') {
				// maybe ${time}
				if (i + 1 < length) {
					tmp = str.charAt(++i);

					if (tmp == '{') {
						String replacement = "";
						for (int j = i; j < length; j++) {
							replacement += str.charAt(j);
							if (str.charAt(j) == '}') {
								paramsList.add("$" + replacement);
								break;
							}
						}
					}
				}
			}
		}

		return paramsList;

	}

	@Override
	protected String getTagValue(String str) {
		// System.err.println(str);
		String regEx = "<string.*?>(.*?)</string>";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			return matcher.group(1);
		} else if (str.contains("plurals")) {
			int start = str.indexOf('>');
			int end = str.lastIndexOf('<');
			return str.substring(start + 1, end);
			// regEx = "<plurals.*?>(.*?)</plurals>";
			// pattern = Pattern.compile(regEx);
			// matcher = pattern.matcher(str.trim());
			// if (matcher.find()) {

			// return matcher.group(1);
			// }
		}
		return null;
	}

	@Override
	protected void printHead() {
		System.out.println(header);

	}

	@Override
	protected void printTail() {
		System.out.println("</resources>");

	}

}
