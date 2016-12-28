package us.zoom.tools.l10n.item;

public class FrenchItem extends Item {

	private String English;
	private String French;

	public String getEnglish() {
		return English;
	}

	public void setEnglish(String english) {
		English = english;
	}

	public String getFrench() {
		return French;
	}

	public void setFrench(String french) {
		French = french;
	}

	@Override
	public String getWhich(int a, String param) {
		// TODO Auto-generated method stub
		switch (a) {
		case 0:
			return getEnglish();
		case 1:
			return getFrench();
		case 2:
			setEnglish(param);
			break;
		case 3:
			setFrench(param);
			break;

		default:
			break;
		}
		return null;
	}

}