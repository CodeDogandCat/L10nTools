package us.zoom.tools.l10n.android;

import java.util.HashMap;
import java.util.Map;

import us.zoom.tools.l10n.core.Command;

public class testAndroid {

	public static void main(String[] args) {
		Android tool = new Android();
		Map<String, String> commandMap = new HashMap<String, String>();
		commandMap.put(Command.PLATFORM, "android");
		commandMap.put(Command.DEFAULT_LANGUAGE_RESOURCE_FILE, "test/android/en.xml");
		commandMap.put(Command.COMPARED_LANGUAGE_RESOURCE_FILE, "test/android/pt.xml");
		commandMap.put(Command.DATABASE_FILE, "test/android/Portuguese.xls");
		commandMap.put(Command.SHEET_NAME, "Sheet1");
		commandMap.put(Command.OUT_FILE, "test/android/ggg.xml");
		tool.rewriterProcess(commandMap);
		// String str = "\n\n\nCont\nacts\n";
		// System.out.println(str.replaceAll("\n", "\\\\n"));
		// Runtime runtime = Runtime.getRuntime();
		// try {
		// runtime.exec("cmd /c svn export -r 17
		// https://10.100.56.178/svn/zoom/trunk/tools/l10n/L10nHistoryChange-Android/src/values/strings.xml
		// ");
		// } catch (Exception e) {
		// System.out.println("Error!");
		// }

		// String s = "ABC\r\nD";
		// // 封装ByteArrayInputStream-->InputStreamReader-->BufferedReader
		// BufferedReader br = new BufferedReader(new InputStreamReader(new
		// ByteArrayInputStream(s.getBytes(Charset.forName("utf8"))),
		// Charset.forName("utf8")));
		// int line;
		// try {
		// while ((line = br.read()) != -1) {
		// System.out.println((char) line);
		// }
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// System.err.println(str);
		// String tempString = "<string name=\"zm_msg_sms_invite_in_meeting\"
		// formatted=\"false\">Click ${joinMeetingUrl} to start or join a
		// scheduled Zoom meeting.</string>";
		// String regEx = "<string.*?name=\\\"(.*?)\\\".*?>.*?</string>";
		// Pattern pattern = Pattern.compile(regEx);
		// Matcher matcher = pattern.matcher(tempString);
		// if (matcher.find()) {
		// System.out.println("find it" + matcher.group(1));
		// } else {
		// System.out.println("not find it");
		// }

	}
}
