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
	
	//私有化构造方法
	private HttpDownloadManager() {}
	
	public static HttpDownloadManager getManager(){          //单例模式
		
	if(httpDownloadManager == null){
		httpDownloadManager = new HttpDownloadManager();
	}
	return httpDownloadManager;
	}
	
	public boolean load(String url, String savePath,
			HttpDownLoadListener listener){
		// TODO Auto-generated method stub
	    try{
	    	Thread t = new HttpDownloadThread(url,savePath,listener);           //创建一个下载线程
	    	t.start();
	    	ThreadHashMap.put(url, t);                                         //记录url与线程关联
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
		    System.out.println(ThreadHashMap.get(url).getName()+"取消成功！");
		    return true;
		}
		else return false;
	}

}
