package us.zoom.tools.l10n.item;

public class HansItem extends Item {
	private String English;
	private String Hans;

	public String getEnglish() {
		return English;
	}

	public void setEnglish(String english) {
		English = english;
	}

	public String getHans() {
		return Hans;
	}

	public void setHans(String hans) {
		Hans = hans;
	}

	@Override
	public String getWhich(int a, String param) {
		// TODO Auto-generated method stub
		switch (a) {
		case 0:
			return getEnglish();
		case 1:
			return getHans();
		case 2:
			setEnglish(param);
			break;
		case 3:
			setHans(param);
			break;

		default:
			break;
		}
		return null;
	}

}
