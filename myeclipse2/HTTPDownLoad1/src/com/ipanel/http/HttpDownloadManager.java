package com.ipanel.http;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import com.ipanel.tools.HttpDownLoad;
import com.ipanel.tools.HttpDownLoadListener;
import com.ipanel.tools.J2meFile;
import com.ipanel.util.ThreadHashMap;


public class HttpDownloadManager implements HttpDownLoad{
	
	private static HttpDownloadManager httpDownloadManager;
	
	//˽�л����췽��
	private HttpDownloadManager() {}
	
	public static HttpDownloadManager getManager(){          //����ģʽ
		
	if(httpDownloadManager == null){
		httpDownloadManager = new HttpDownloadManager();
	}
	return httpDownloadManager;
	}
	
	public boolean load(String url, String savePath,
			HttpDownLoadListener listener){
		// TODO Auto-generated method stub
	    try{
	    	Thread t = new HttpDownloadThread(url,savePath,listener);           //����һ�������߳�
	    	t.start();
	    	ThreadHashMap.put(url, t);                                         //��¼url���̹߳���
	    }catch(Exception e){
	    	e.printStackTrace();
	    	return false;
	    }
		return true;
	}

	
	public boolean cancel(String url) {
		// TODO Auto-generated method stub
		if(ThreadHashMap.get(url).isAlive())
		{	ThreadHashMap.get(url).stop();
		    System.out.println(ThreadHashMap.get(url).getName()+"ȡ���ɹ���");
		    return true;
		}
		else return false;
	}

}
