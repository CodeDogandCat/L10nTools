package us.zoom.tools.l10n.item;

public class HantItem extends Item {
	private String English;
	private String Hant;

	public String getEnglish() {
		return English;
	}

	public void setEnglish(String english) {
		English = english;
	}

	public String getHant() {
		return Hant;
	}

	public void setHant(String french) {
		Hant = french;
	}

	@Override
	public String getWhich(int a, String param) {
		// TODO Auto-generated method stub
		switch (a) {
		case 0:
			return getEnglish();
		case 1:
			return getHant();
		case 2:
			setEnglish(param);
			break;
		case 3:
			setHant(param);
			break;

		default:
			break;
		}
		return null;
	}
}
