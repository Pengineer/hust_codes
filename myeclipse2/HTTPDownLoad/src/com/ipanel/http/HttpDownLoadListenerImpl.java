package com.ipanel.http;

import com.ipanel.tools.HttpDownLoadEvent;
import com.ipanel.tools.HttpDownLoadListener;

public class HttpDownLoadListenerImpl implements HttpDownLoadListener{
 
	
	@Override
	public void notifyEvent(HttpDownLoadEvent e) {
		// TODO Auto-generated method stub
	   if(e.getType() == HttpDownLoadEvent.EVENT_TYPE_TYPESUCCESS)
		   com.ipanel.http.Test.jta.append("���سɹ���"+"\r\n");
	   
	   if(e.getType() == HttpDownLoadEvent.EVENT_TYPE_FAILURE)
		   com.ipanel.http.Test.jta.append("����ʧ�ܣ�"+"\r\n");
	   
	   if(e.getReason() == HttpDownLoadEvent.FAILURE_REASON_CONNECT_ERROR)
		   com.ipanel.http.Test.jta.append("ʧ��ԭ�����Ӵ���"+"\r\n");
	   
	   if(e.getReason() == HttpDownLoadEvent.FAILURE_REASON_FILE_NOTFOUND)
		   com.ipanel.http.Test.jta.append("ʧ��ԭ��δ�ҵ��ļ���"+"\r\n");
	   
	   if(e.getReason() == HttpDownLoadEvent.FAILURE_REASON_OTHER)
		   com.ipanel.http.Test.jta.append("ʧ��ԭ������ԭ��"+"\r\n");
	   
	   if(e.getReason() == HttpDownLoadEvent.FAILURE_REASON_SAVE_EXCEPTION)
		   com.ipanel.http.Test.jta.append("ʧ��ԭ�򣺱����쳣��"+"\r\n");
	   
	   
	}

}
