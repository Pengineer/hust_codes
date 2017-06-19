package org.csdc.service.imp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.csdc.domain.fm.ThirdCheckInForm;
import org.csdc.domain.fm.ThirdUploadForm;

/**
 * DMSS第三方接口类
 * 
 * @author jintf
 * @date 2014-6-16
 */
public class DmssService {
	private int on; // 是否启用云存储
	private boolean status; // 判断云存储是否断线
	private String username; // 用户名
	private String password; // 密码
	private CookieStore cookie; // cookie
	private Integer timeout = 1; // 单位分钟，大于2min
	private String url; // 例如192.168.88.20:8080/dmss

	/**
	 * 建立连接，并保持连接通道
	 */
	public void connect() {
		if (getOn() == 1) {
			auth(); // 必须写在前面
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						if (getStatus()) {
							String response = post("/heartBeat", new HashMap());
							if (response == null) {
								status = false;
							}
						} else {
							auth();
						}
						try {
							Thread.sleep(timeout * 30 * 1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
	}

	/**
	 * 上传文档
	 * 
	 * @param filePath
	 *            文件路径
	 * @param form
	 *            第三方文档上传表单
	 * @return
	 * @throws Exception
	 */
	public String upload(String filePath, ThirdUploadForm form)
			throws Exception {
		HttpParams parms = new BasicHttpParams();
		DefaultHttpClient httpclient = new DefaultHttpClient(parms);
		httpclient.getParams().setParameter("http.socket.timeout", 20000);
		httpclient.getParams().setParameter("http.connection.timeout", 20000);
		if (cookie != null) {
			httpclient.setCookieStore(cookie);
			Cookie cook = cookie.getCookies().get(0);
		}
		File file = new File(filePath);
		FileBody targetFile = new FileBody(file, "application/octet-stream",
				"UTF-8");
		HttpPost httppost = new HttpPost(url + "/fm/document/webUpload");
		httppost.addHeader("charset", HTTP.UTF_8);
		// 对请求的表单域进行填充
		MultipartEntity reqEntity = new MultipartEntity();
		reqEntity.addPart("file", targetFile);
		reqEntity.addPart("fileName", new StringBody(form.getFileName(),
				Charset.forName("UTF-8")));
		reqEntity.addPart("title",
				new StringBody(form.getTitle(), Charset.forName("UTF-8")));
		reqEntity.addPart("sourceAuthor", new StringBody(
				form.getSourceAuthor(), Charset.forName("UTF-8")));
		reqEntity.addPart("tags",
				new StringBody(form.getTags(), Charset.forName("UTF-8")));
		reqEntity.addPart("rating",
				new StringBody(form.getRating(), Charset.forName("UTF-8")));
		reqEntity.addPart("categoryPath", new StringBody(
				form.getCategoryPath(), Charset.forName("UTF-8")));
		// 设置请求
		httppost.setEntity(reqEntity);
		HttpResponse response = httpclient.execute(httppost);
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
			HttpEntity entity = response.getEntity();
			// 显示内容
			if (entity != null) {
				String charset = EntityUtils.getContentCharSet(entity);
				String res = stream2string(entity.getContent(), charset);
				JSONObject jsonObject = JSONObject.fromObject(res);
				return ((JSONObject) jsonObject.get("data")).getString("id");
			}
		}
		return null;
	}

	/**
	 * 检入
	 * 
	 * @param filePath
	 *            文件路径
	 * @param form
	 *            第三方文档上传表单
	 * @return
	 * @throws Exception
	 */
	public String checkIn(String filePath, ThirdCheckInForm form)
			throws Exception {
		HttpParams parms = new BasicHttpParams();
		DefaultHttpClient httpclient = new DefaultHttpClient(parms);
		httpclient.getParams().setParameter("http.socket.timeout", 20000);
		httpclient.getParams().setParameter("http.connection.timeout", 20000);
		if (cookie != null) {
			httpclient.setCookieStore(cookie);
			Cookie cook = cookie.getCookies().get(0);
		}
		File file = new File(filePath);
		FileBody targetFile = new FileBody(file, "application/octet-stream",
				"UTF-8");
		HttpPost httppost = new HttpPost(url + "/fm/document/webCheckIn");
		httppost.addHeader("charset", HTTP.UTF_8);
		// 对请求的表单域进行填充
		MultipartEntity reqEntity = new MultipartEntity();
		reqEntity.addPart("id",
				new StringBody(form.getId(), Charset.forName("UTF-8")));
		reqEntity.addPart("file", targetFile);
		reqEntity.addPart("fileName", new StringBody(form.getFileName(),
				Charset.forName("UTF-8")));
		reqEntity.addPart("title",
				new StringBody(form.getTitle(), Charset.forName("UTF-8")));
		// 设置请求
		httppost.setEntity(reqEntity);
		HttpResponse response = httpclient.execute(httppost);
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
			HttpEntity entity = response.getEntity();
			// 显示内容
			if (entity != null) {
				String charset = EntityUtils.getContentCharSet(entity);
				String res = stream2string(entity.getContent(), charset);
				JSONObject jsonObject = JSONObject.fromObject(res);
				return ((JSONObject) jsonObject.get("data")).getString("id");
			}
		}
		return null;
	}

	/**
	 * 删除一个DMSS的文档（删除后进入回收站）
	 * 
	 * @param dfsId
	 * @return
	 */
	public boolean deleteFile(String dfsId) {
		if (dfsId == null || dfsId.length() < 1)
			return false;
		String path = "/fm/document/delete/" + dfsId;
		String content = post(path, new HashMap());
		JSONObject jsonObject = JSONObject.fromObject(content);
		return jsonObject.getBoolean("success");
	}

	/**
	 * 认证客户端
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public boolean auth() {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url + "/user/login");
		client.getParams().setParameter("http.socket.timeout", 20000);
		client.getParams().setParameter("http.connection.timeout", 20000);
		try {
			Map map = new HashMap();
			map.put("j_username", username);
			map.put("j_password", password);
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("j_username", username));
			pairs.add(new BasicNameValuePair("j_password", password));
			UrlEncodedFormEntity e = new UrlEncodedFormEntity(pairs, "UTF-8");
			post.setEntity(e);
			HttpResponse response = client.execute(post);
			Header[] header = response.getHeaders("Location");
			String redirectUrl = header[0].getValue();
			if (redirectUrl.contains("main")) {
				cookie = client.getCookieStore();
				System.out.println("认证通过，成功连接dmss：" + url);
				setStatus(true);
				return true;
			} else {
				setStatus(false);
				return false;
			}
		} catch (Exception e) {
			System.out.println("DMSS离线");
			setStatus(false);
			return false;
		} finally {
			post.abort();
			client.getConnectionManager().shutdown();
		}
	}

	/**
	 * 
	 * @param path
	 *            (接口路径，比如/sm/selfspace/toModify)
	 * @param params
	 * @return 返回请求数据，或者返回null
	 */
	public String post(String path, Map params) {
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter("http.socket.timeout", 20000);
		client.getParams().setParameter("http.connection.timeout", 20000);
		if (cookie != null) {
			client.setCookieStore(cookie);
		}
		String response = null;
		HttpPost post = new HttpPost(url + path);
		try {
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			Set keys = params.keySet();
			for (Object key : keys) {
				if (null != params.get(key)) {
					pairs.add(new BasicNameValuePair((String) key, params.get(
							key).toString()));
				} else {
					pairs.add(new BasicNameValuePair((String) key, ""));
				}
			}
			UrlEncodedFormEntity e = new UrlEncodedFormEntity(pairs, "UTF-8");
			post.setEntity(e);
			HttpResponse res = client.execute(post);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				String charset = EntityUtils.getContentCharSet(entity);
				response = stream2string(entity.getContent(), charset);
			}
		} catch (Exception e) {
		} finally {
			post.abort();
			client.getConnectionManager().shutdown();
		}
		return response;
	}

	/**
	 * 获取文档大小
	 * 
	 * @param id
	 *            文档ID
	 */
	public long accquireFileSize(String id) {
		String path = "/fm/document/accquireFileSize/" + id;
		String content = post(path, new HashMap());
		JSONObject jsonObject = JSONObject.fromObject(content);
		return ((JSONObject) jsonObject.get("data")).getInt("fileSize");
	}

	/**
	 * 文档下载
	 * 
	 * @param id
	 *            文档ID
	 * @param filePath
	 *            保存路径
	 */
	public InputStream download(String id) {
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter("http.socket.timeout", 20000);
		client.getParams().setParameter("http.connection.timeout", 20000);
		HttpGet get = new HttpGet(url + "/fm/document/download/" + id);
		if (cookie != null) {
			client.setCookieStore(cookie);
		}
		try {
			HttpResponse res = client.execute(get);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				InputStream downloadStream = entity.getContent();
				return downloadStream;
			}
		} catch (Exception e) {
			get.abort();
			client.getConnectionManager().shutdown();
			throw new RuntimeException(e);
		}
		return null;
	}

	/**
	 * 流转字符串
	 * 
	 * @param is
	 *            输入流
	 * @param encode
	 *            编码
	 * @return
	 */
	private String stream2string(InputStream is, String encode) {
		if (is != null) {
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, encode));
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public int getOn() {
		return on;
	}

	public void setOn(int on) {
		this.on = on;
	}

	public boolean getStatus() {
		return status;
	}

	/**
	 * DMSS是否可用（DMSS已起用且认证成功）
	 * 
	 * @param status
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	public static void main(String[] args) throws Exception {
		DmssService service = new DmssService();
		service.setOn(1);
		service.setUrl("http://localhost/dmss");
		service.setUsername("smdb");
		service.setPassword("123456");
		service.connect();
		/*
		 * service.connect();
		 * //service.download("4028d894448c15c601448c1f37aa07cd",
		 * "D://demo.txt");
		 */
		/*
		 * File file = new File("E:\\调查报告－默认报告.doc"); ThirdUploadForm form = new
		 * ThirdUploadForm(); form.setTitle("上传第一版");
		 * form.setFileName(file.getName()); form.setSourceAuthor("金天凡");
		 * form.setRating("3.1"); form.setTags("aaa,ccc");
		 * form.setCategoryPath("/CMIS/test");
		 * System.out.println(service.upload(file.getAbsolutePath(), form));
		 */
	}

}
