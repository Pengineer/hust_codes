package com.ipanel.http;

import com.ipanel.tools.HttpDownLoadEvent;
import com.ipanel.tools.HttpDownLoadListener;

public class HttpDownLoadListenerImpl implements HttpDownLoadListener{
 
	
	@Override
	public void notifyEvent(HttpDownLoadEvent e) {
		// TODO Auto-generated method stub
	   if(e.getType() == HttpDownLoadEvent.EVENT_TYPE_TYPESUCCESS)
		   System.out.print("下载成功！");
	   
	   if(e.getType() == HttpDownLoadEvent.EVENT_TYPE_FAILURE)
		   System.out.print("下载失败！");
	   
	   if(e.getReason() == HttpDownLoadEvent.FAILURE_REASON_CONNECT_ERROR)
		   System.out.print("失败原因：连接错误！");
	   
	   if(e.getReason() == HttpDownLoadEvent.FAILURE_REASON_FILE_NOTFOUND)
		   System.out.print("失败原因：未找到文件！");
	   
	   if(e.getReason() == HttpDownLoadEvent.FAILURE_REASON_OTHER)
		   System.out.print("失败原因：其他原因！");
	   
	   if(e.getReason() == HttpDownLoadEvent.FAILURE_REASON_SAVE_EXCEPTION)
		   System.out.print("失败原因：保存异常！");
	   
	   
	}

}
