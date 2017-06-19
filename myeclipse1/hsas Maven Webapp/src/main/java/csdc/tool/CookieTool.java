package csdc.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieTool {
	/**
	 * 设置cookie
	 * @param response
	 * @param name  cookie名字
	 * @param value cookie值
	 * @param maxAge cookie生命周期  以秒为单位
	 */
	public static void addCookie(HttpServletResponse response,String name, String value,int maxAge,String path){
	    Cookie cookie = new Cookie(name,value);
	    cookie.setPath(path);
	    if(maxAge>=0)  cookie.setMaxAge(maxAge);
	    response.addCookie(cookie);
	}
	
	public static void addCookieForOneDay(HttpServletResponse response,String name, String value,String path) throws ParseException{
	    Date now = new Date();
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    Date after = DatetimeTool.getDayBefore(now, -1);
	    Date after2 = df.parse(DatetimeTool.getDatetimeString(after, "yyyy-MM-dd"));
		long maxAge = (after2.getTime()-now.getTime())/1000;
	    Cookie cookie = new Cookie(name,value);
	    cookie.setPath(path);
	    if(maxAge>=0)  cookie.setMaxAge((int) maxAge);
	    response.addCookie(cookie);
	}
	
	/**
	 * 根据名字获取cookie
	 * @param request
	 * @param name cookie名字
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request,String name){
	    Map<String,Cookie> cookieMap = ReadCookieMap(request);
	    if(cookieMap.containsKey(name)){
	        Cookie cookie = (Cookie)cookieMap.get(name);
	        return cookie;
	    }else{
	        return null;
	    }
	}
	 
	 
	 
	/**
	 * 将cookie封装到Map里面
	 * @param request
	 * @return
	 */
	private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){  
	    Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
	    Cookie[] cookies = request.getCookies();
	    if(null!=cookies){
	        for(Cookie cookie : cookies){
	            cookieMap.put(cookie.getName(), cookie);
	        }
	    }
	    return cookieMap;
	}
	public static void main(String[] args) throws ParseException {
		
	}
}
