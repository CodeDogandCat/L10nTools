package us.zoom.tools.l10n.item;

public class SpanishItem extends Item {
	private String English;
	private String Spanish;

	public String getEnglish() {
		return English;
	}

	public void setEnglish(String english) {
		English = english;
	}

	public String getSpanish() {
		return Spanish;
	}

	public void setSpanish(String spanish) {
		Spanish = spanish;
	}

	@Override
	public String getWhich(int a, String param) {
		// TODO Auto-generated method stub
		switch (a) {
		case 0:
			return getEnglish();
		case 1:
			return getSpanish();
		case 2:
			setEnglish(param);
			break;
		case 3:
			setSpanish(param);
			break;

		default:
			break;
		}
		return null;
	}
}
