package org.csdc.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * Http工具类
 * @author jintf
 * @date 2014-6-16
 */
public class HttpTool {
	
	/**
	 * 发送post请求，不带参数
	 * @param url
	 * @return
	 */
	public static String post(String url){
		return post(url,new HashMap());
	}
	
	/**
	 * 发送Post请求，参数用map封装
	 * @param url
	 * @param params
	 * @return
	 */
	public static String post(String url,Map params){
		HttpClient client = new DefaultHttpClient();
		String response = null;
		try {
			//int pos = url.lastIndexOf('?') + 1;
			//URI uri = new URI(url.substring(0, pos) + URLEncoder.encode(url.substring(pos), "utf-8"));
			HttpPost post =  new HttpPost( url);
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();  
			Set keys = params.keySet();
			for(Object key:keys){
				pairs.add(new BasicNameValuePair((String)key,params.get(key).toString())); 
			}	     
		    UrlEncodedFormEntity e = new UrlEncodedFormEntity(pairs,"UTF-8");  
		    post.setEntity(e);  
			HttpResponse res = client.execute(post);
			System.out.println(res.getStatusLine().getStatusCode());
			if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity entity = res.getEntity();
				String charset = EntityUtils.getContentCharSet(entity);
				response = stream2string(entity.getContent(),charset);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return response;
	}
	
	/**
	 * 发送post请求，返回JSON
	 * @param url
	 * @param params
	 * @return
	 */
	public static JSONObject getJson(String url,Map params){
		return JSONObject.fromObject(post(url,params));
	}
	
	/**
	 * 发送post请求，返回JSON
	 * @param url
	 * @return
	 */
	public static JSONObject getJson(String url){
		return JSONObject.fromObject(post(url));
	}
	
	/**
	 * 把二进制流转为字符串
	 * @param is
	 * @param encode
	 * @return
	 */
	private static String stream2string(InputStream is, String encode) {
        if (is != null) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, encode));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                return sb.toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
	

}
