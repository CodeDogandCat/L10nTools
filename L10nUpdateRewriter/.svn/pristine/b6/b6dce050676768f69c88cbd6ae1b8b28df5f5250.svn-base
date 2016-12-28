package us.zoom.tools.l10n.item;

public class JapaneseItem extends Item {

	private String English;
	private String Japanese;

	public String getEnglish() {
		return English;
	}

	public void setEnglish(String english) {
		English = english;
	}

	public String getJapanese() {
		return Japanese;
	}

	public void setJapanese(String japanese) {
		Japanese = japanese;
	}

	@Override
	public String getWhich(int a, String param) {
		// TODO Auto-generated method stub
		switch (a) {
		case 0:
			return getEnglish();
		case 1:
			return getJapanese();
		case 2:
			setEnglish(param);
			break;
		case 3:
			setJapanese(param);
			break;

		default:
			break;
		}
		return null;
	}

}