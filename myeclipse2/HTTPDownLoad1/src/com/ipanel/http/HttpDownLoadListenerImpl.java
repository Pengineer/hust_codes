package com.ipanel.http;

import com.ipanel.tools.HttpDownLoadEvent;
import com.ipanel.tools.HttpDownLoadListener;

public class HttpDownLoadListenerImpl implements HttpDownLoadListener{
 
	
	@Override
	public void notifyEvent(HttpDownLoadEvent e) {
		// TODO Auto-generated method stub
	   if(e.getType() == HttpDownLoadEvent.EVENT_TYPE_TYPESUCCESS)
		   System.out.print("���سɹ���");
	   
	   if(e.getType() == HttpDownLoadEvent.EVENT_TYPE_FAILURE)
		   System.out.print("����ʧ�ܣ�");
	   
	   if(e.getReason() == HttpDownLoadEvent.FAILURE_REASON_CONNECT_ERROR)
		   System.out.print("ʧ��ԭ�����Ӵ���");
	   
	   if(e.getReason() == HttpDownLoadEvent.FAILURE_REASON_FILE_NOTFOUND)
		   System.out.print("ʧ��ԭ��δ�ҵ��ļ���");
	   
	   if(e.getReason() == HttpDownLoadEvent.FAILURE_REASON_OTHER)
		   System.out.print("ʧ��ԭ������ԭ��");
	   
	   if(e.getReason() == HttpDownLoadEvent.FAILURE_REASON_SAVE_EXCEPTION)
		   System.out.print("ʧ��ԭ�򣺱����쳣��");
	   
	   
	}

}
