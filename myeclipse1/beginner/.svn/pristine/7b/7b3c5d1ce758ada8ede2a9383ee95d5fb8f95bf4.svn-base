package csdc.tool;

public class SignID {
	public static SignID getInstance() {
		if (si == null) {
			si = new SignID();
		}
		return si;
	}

	synchronized public String getSignID() {
		String signID = null;
		signID = getRandomString() + System.currentTimeMillis();
		signID = signID.substring(0, 3);
		return signID;
	}

	private String getRandomString() {
		double d = Math.random();
		String s = Double.toString(d).substring(2);
		return s;
	}

	private static SignID si;

}
