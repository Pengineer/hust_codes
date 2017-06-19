package csdc.tool;

import java.io.UnsupportedEncodingException;

public class StringTranscodeTool {
	public static String transcode(String string, String decode, String encode) {
		byte[] a;
		String b = null;
		try {
			a = string.getBytes(decode);
			b = new String(a, encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
        return b;
	}
}
