package csdc.tool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Property {	
	public static Properties getPro(String url){
		InputStream in=Property.class.getClassLoader().getResourceAsStream(url);
		Properties p =new Properties();
		try {
			if(p != null)
				p.load(in);
			in.close();
			System.out.println(p);		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}
}
