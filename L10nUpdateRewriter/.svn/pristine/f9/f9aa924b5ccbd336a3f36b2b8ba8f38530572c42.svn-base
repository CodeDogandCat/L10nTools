package us.zoom.tools.l10n.item;

public class GermanItem extends Item {
	private String English;
	private String German;

	public String getEnglish() {
		return English;
	}

	public void setEnglish(String english) {
		English = english;
	}

	public String getGerman() {
		return German;
	}

	public void setGerman(String german) {
		German = german;
	}

	@Override
	public String getWhich(int a, String param) {
		// TODO Auto-generated method stub
		switch (a) {
		case 0:
			return getEnglish();
		case 1:
			return getGerman();
		case 2:
			setEnglish(param);
			break;
		case 3:
			setGerman(param);
			break;

		default:
			break;
		}
		return null;
	}

}
