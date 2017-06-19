package csdc.tool;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class Http {
	
	public static String http(String url, Map<String, String> params) {
		URL u = null;
		HttpURLConnection con = null;
		// 构建请求参数
		StringBuffer sb = new StringBuffer();
		String postStr = null;
		if (params != null) {
			for (Entry<String, String> e : params.entrySet()) {
				sb.append(e.getKey());
				sb.append("=");
				sb.append(e.getValue());
				sb.append("&");
			}
			postStr = sb.substring(0, sb.length() - 1);
		}
		System.out.println("send_url:" + url);
		System.out.println("send_data:" + postStr);
		// 尝试发送请求
		try {
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
			osw.write(postStr);
			osw.flush();
			osw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}

		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
				buffer.append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return buffer.toString();
	}
	
	private static void httpClientPost() throws Exception {
		String url="http://192.168.88.26:8080/amss/toIndex.action";
		//POST的URL
		HttpPost httppost=new HttpPost(url);
		//建立HttpPost对象
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		//建立一个NameValuePair数组，用于存储欲传送的参数
		params.add(new BasicNameValuePair("j_username","test"));
		params.add(new BasicNameValuePair("j_password","123465"));
		//添加参数
		httppost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
		//设置编码
		HttpResponse response=new DefaultHttpClient().execute(httppost);
		//发送Post,并返回一个HttpResponse对象
        //Header header = response.getFirstHeader("Content-Length");
		//String Length=header.getValue();
        // 上面两行可以得到指定的Header
		if(response.getStatusLine().getStatusCode()==200){//如果状态码为200,就是正常返回
			String result=EntityUtils.toString(response.getEntity());
			//得到返回的字符串
			System.out.println("httpclient:"+ result);
			//打印输出
            //如果是下载文件,可以用response.getEntity().getContent()返回InputStream
		}
	}
}
