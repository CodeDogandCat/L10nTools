package us.zoom.tools.l10n.item;

public class ModifiedItem extends Item {

	private String All;
	private String Modified;

	public String getAll() {
		return All;
	}

	public void setAll(String all) {
		All = all;
	}

	public String getModified() {
		return Modified;
	}

	public void setModified(String modified) {
		Modified = modified;
	}

	@Override
	public String getWhich(int a, String param) {
		// TODO Auto-generated method stub
		switch (a) {
		case 0:
			return getAll();
		case 1:
			return getModified();
		case 2:
			setAll(param);
			break;
		case 3:
			setModified(param);
			break;

		default:
			break;
		}
		return null;
	}

}