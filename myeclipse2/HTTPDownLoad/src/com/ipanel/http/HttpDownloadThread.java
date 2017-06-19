package com.ipanel.http;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.xml.ws.http.HTTPException;

import com.ipanel.tools.HttpDownLoadEvent;
import com.ipanel.tools.HttpDownLoadListener;
import com.ipanel.tools.J2meFile;
import com.ipanel.util.ExceptionProcess;

public class HttpDownloadThread extends Thread{
	private int i=0;
    private	String url;
	private String savePath;
	private HttpDownLoadListener listener;
	
	public HttpDownloadThread(String url, String savePath,
			HttpDownLoadListener listener){
		this.url = url;
		this.savePath = savePath;
		this.listener = listener;
	}

	//ʵ��run����
	public void run(){
	//	J2meFile jf = new J2meFile(savePath,J2meFile.J2ME_FILE_OPEN_MODE_WRITE);
		HttpURLConnection httpConnection = null;
		InputStream input;
		FileOutputStream output;
		URL u;
		byte[] buf = new byte[1024];
		int n;
		
	    try {
	    	//����URL����,�ļ�д�����
			u = new URL(url);
			RandomAccessFile saveFile = new RandomAccessFile(savePath,"rw");
			//�����ӣ�����HTTPͷ
			httpConnection = (HttpURLConnection)u.openConnection();
			httpConnection.setRequestProperty("User-Agent", "Internet Explorer");
			//��ȡ�����ļ�д��ָ���ļ�
			input = httpConnection.getInputStream();
//			InputStreamReader inreader = new InputStreamReader(input,"UTF-8");
			com.ipanel.http.Test.jta.append("��ʼ���ء���"+"\r\n");
			
			while((n = input.read(buf))!=-1){
			//	i = jf.write(buf, 1, n);
			//	System.out.println(i+" "+n);
				saveFile.write(buf);
				com.ipanel.http.Test.jta.append(new Integer(++i).toString()+"."+Thread.currentThread()+"�������ء���"+"\r\n");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			ExceptionProcess.FileNotFoundExceptionProcess(listener);
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(ConnectException e){
			ExceptionProcess.OtherProcess(listener);
			e.printStackTrace();
		} catch(UnknownHostException e){
			ExceptionProcess.ConnectErrorProcess(listener);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			ExceptionProcess.IOExceptionProcess(listener);
			e.printStackTrace();
		} finally{
			httpConnection.disconnect();   //�ر�����
		}
		com.ipanel.http.Test.jta.append(Thread.currentThread()+"������ϣ�"+"\r\n");
		i=0;
	}
}
