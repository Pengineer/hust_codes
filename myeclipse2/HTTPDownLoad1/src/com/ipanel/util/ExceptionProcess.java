package com.ipanel.util;

import com.ipanel.http.HttpDownLoadListenerImpl;
import com.ipanel.http.HttpDownloadManager;
import com.ipanel.tools.HttpDownLoadEvent;
import com.ipanel.tools.HttpDownLoadListener;

public class ExceptionProcess {

	//δ�ҵ��ļ�������
	public static void FileNotFoundExceptionProcess(HttpDownLoadListener listener){
		HttpDownLoadEvent event = new HttpDownLoadEvent(HttpDownloadManager.getManager(),
				HttpDownLoadEvent.EVENT_TYPE_FAILURE,HttpDownLoadEvent.FAILURE_REASON_FILE_NOTFOUND);
		listener.notifyEvent(event);
	}
	
	//�ļ����������������
	public static void IOExceptionProcess(HttpDownLoadListener listener){
		HttpDownLoadEvent event = new HttpDownLoadEvent(HttpDownloadManager.getManager(),
				HttpDownLoadEvent.EVENT_TYPE_FAILURE,HttpDownLoadEvent.FAILURE_REASON_SAVE_EXCEPTION);
		listener.notifyEvent(event);
	}
	
	//�������Ӵ�������
	public static void ConnectErrorProcess(HttpDownLoadListener listener){
		HttpDownLoadEvent event = new HttpDownLoadEvent(HttpDownloadManager.getManager(),
				HttpDownLoadEvent.EVENT_TYPE_FAILURE,HttpDownLoadEvent.FAILURE_REASON_CONNECT_ERROR);
		listener.notifyEvent(event);
	}

	//����ԭ������
	public static void OtherProcess(HttpDownLoadListener listener) {
		// TODO Auto-generated method stub
		HttpDownLoadEvent event = new HttpDownLoadEvent(HttpDownloadManager.getManager(),
				HttpDownLoadEvent.EVENT_TYPE_FAILURE,HttpDownLoadEvent.FAILURE_REASON_OTHER);
		listener.notifyEvent(event);
	}
}
