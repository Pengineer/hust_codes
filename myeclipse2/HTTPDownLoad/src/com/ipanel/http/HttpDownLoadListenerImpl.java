package com.ipanel.http;

import com.ipanel.tools.HttpDownLoadEvent;
import com.ipanel.tools.HttpDownLoadListener;

public class HttpDownLoadListenerImpl implements HttpDownLoadListener{
 
	
	@Override
	public void notifyEvent(HttpDownLoadEvent e) {
		// TODO Auto-generated method stub
	   if(e.getType() == HttpDownLoadEvent.EVENT_TYPE_TYPESUCCESS)
		   com.ipanel.http.Test.jta.append("下载成功！"+"\r\n");
	   
	   if(e.getType() == HttpDownLoadEvent.EVENT_TYPE_FAILURE)
		   com.ipanel.http.Test.jta.append("下载失败！"+"\r\n");
	   
	   if(e.getReason() == HttpDownLoadEvent.FAILURE_REASON_CONNECT_ERROR)
		   com.ipanel.http.Test.jta.append("失败原因：连接错误！"+"\r\n");
	   
	   if(e.getReason() == HttpDownLoadEvent.FAILURE_REASON_FILE_NOTFOUND)
		   com.ipanel.http.Test.jta.append("失败原因：未找到文件！"+"\r\n");
	   
	   if(e.getReason() == HttpDownLoadEvent.FAILURE_REASON_OTHER)
		   com.ipanel.http.Test.jta.append("失败原因：其他原因！"+"\r\n");
	   
	   if(e.getReason() == HttpDownLoadEvent.FAILURE_REASON_SAVE_EXCEPTION)
		   com.ipanel.http.Test.jta.append("失败原因：保存异常！"+"\r\n");
	   
	   
	}

}
