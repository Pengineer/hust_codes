package csdc.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;

public class CheckFormat {
	public static boolean checkTelephone(String phone) {
        Pattern pattern = Pattern.compile("^(13|15|18)\\d{9}$");
        Matcher matcher = pattern.matcher(phone);
        if (matcher.matches()) {
            return true;
        }
        return false;
	}
	
	
	
	
	public static void main(String[] args) {
		System.out.println(checkTelephone("13545260178"));
		System.out.println(checkTelephone("123456789"));
		long i = (long) 3;
		double d = 0.9;
		Double ii = (double) i;
		Double asdDouble= d/i;
		Double asdDouble2=d/ii;
	}

}
