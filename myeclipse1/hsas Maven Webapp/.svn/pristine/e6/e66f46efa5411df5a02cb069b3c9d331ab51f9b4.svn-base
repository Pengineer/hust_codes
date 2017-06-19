package csdc.tool;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.net.URLCodec;

public class SendRandTool {
	public static Map send(String phone,String random) {
		Map map = new HashMap();
		HttpURLConnection httpURLConnection = null;
		String result = null;
		try {
			//发送POST请求
			StringBuffer urlBuffer = new StringBuffer();
			urlBuffer.append("http://118.145.30.35:9999/sms.aspx");
			//action=send&userid=12&account=账号&password=密码&mobile=15023239810,13527576163&content=内容&sendTime=&extno=
			//action=overage&userid=300&account=袁&password=123456
//			String str = new String("?action=overage&userid=300&account="+'\"'+"袁"+'\"'+"&password=123456".getBytes(),"UTF-8");
//			urlBuffer.append("?action=overage&userid=300&account=");
			urlBuffer.append("?action=send&userid=300&account=");
			String userName = URLEncoder.encode("袁", "UTF-8");
			urlBuffer.append(userName);
			urlBuffer.append("&password=123456&mobile=");
/*			StringBuffer phoneBuffer = new StringBuffer();
			for (int i = 0; i < phone.length; i++) {
				if (i == 0) {
					phoneBuffer.append(phone[i]);
				}else {
					phoneBuffer.append(",");
					phoneBuffer.append(phone[i]);
				}
			}*/
			urlBuffer.append(phone);
			urlBuffer.append("&content=您正在使用“车管家”软件，您的验证码为：");
			urlBuffer.append(random);
			urlBuffer.append("。请妥善保管好您的验证码，半小时后失效。");	
			String temp = URLEncoder.encode("【车管家】", "UTF-8");
			urlBuffer.append(temp);
			urlBuffer.append("&sendTime=&extno=");
			System.out.println(urlBuffer);
			URL url = new URL(urlBuffer.toString());
			httpURLConnection = (HttpURLConnection) url.openConnection();
			InputStream inputStream = httpURLConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8"));
			StringBuffer sb = new StringBuffer();
			int ch;
			while ((ch = bufferedReader.read()) > -1) {
				sb.append((char) ch);
			}
			String returnstatus = XMLTool.getElementText(sb.toString(), "returnstatus");
			String message = XMLTool.getElementText(sb.toString(), "message");
			System.out.println(sb);
			System.out.print(returnstatus);
			map.put("message", message);
			map.put("returnstatus", returnstatus);
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if (httpURLConnection !=null) {
				httpURLConnection.disconnect();
				httpURLConnection = null;
			}
		}
		return map;
	}
	
	public static void main(String[] args) throws Exception {
		String strings="15926307076";
		RandomNumUtil rdnu = null;
		rdnu = RandomNumUtil.Instance();
		System.out.println(SendRandTool.send(strings, rdnu.getString()));;
//		send(strings, "1516");
	}
}
