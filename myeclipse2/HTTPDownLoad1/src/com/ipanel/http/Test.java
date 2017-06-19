package com.ipanel.http;

import com.ipanel.tools.HttpDownLoadListener;
import com.ipanel.tools.J2meFile;


public class Test {
	
public static void main(String[] args){
/*		// TODO Auto-generated method stub
	try{
		System.loadLibrary("J2meFileDll");//加载动态库
	}catch(Exception e){
		e.printStackTrace();
	}
	*/
	boolean isSuccess;
	//String savePath1 = "C:/Users/liangjian/Desktop/coco.apk";
	//String savePath2 = "C:/Users/liangjian/Desktop/365rili.exe";
	//String url = "http://d2.365rili.com/coco.apk";
	//String url2 = "http://d2.365rili.com/pc/update/365rili.exe";
	String savePath1 = "C:/Users/liangjian/Desktop/1.mp3";
	String savePath2 = "C:/Users/liangjian/Desktop/2.mp3";
	String url = "http://stream11.qqmusic.qq.com/35144031.mp3";	
	String url2 = "http://stream11.qqmusic.qq.com/35144031.mp3";
	
	HttpDownloadManager httpDownLoadManager = HttpDownloadManager.getManager();
	
	//下载文件
	isSuccess = httpDownLoadManager.load(url,savePath1 , new HttpDownLoadListenerImpl());
	httpDownLoadManager.load(url2,savePath2 , new HttpDownLoadListenerImpl());
	System.out.println(isSuccess);
	
	//取消下载功能测试
//	for(long n=0;n<1000000000;n++){}
	
//	HttpDownloadManager.getManager().cancel(url);
	
	}
}