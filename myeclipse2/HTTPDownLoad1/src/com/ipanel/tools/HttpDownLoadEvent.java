package com.ipanel.tools;

/**
 * HttpDownLoad�����¼�
 */

public class HttpDownLoadEvent {
	public static final int EVENT_TYPE_TYPESUCCESS = 1;
	public static final int EVENT_TYPE_FAILURE     = 2;
	
	
	public static final int FAILURE_REASON_CONNECT_ERROR   = 0x1001;
	public static final int FAILURE_REASON_FILE_NOTFOUND   = 0x1002;
	public static final int FAILURE_REASON_SAVE_EXCEPTION  = 0x1003;
	public static final int FAILURE_REASON_OTHER           = 0x100f;
	
	private HttpDownLoad downLoad;
	
	private int type;//������Ϣ�¼����ɹ���ʧ��
	private int reason;//ʧ��ԭ��
	
	public HttpDownLoadEvent(HttpDownLoad downLoad, int type, int reason){
		this.downLoad = downLoad;
		this.type = type;
		this.reason = reason;
	}
	
	public HttpDownLoad getHttpDownLoad(){
		return downLoad;
	}
	
	public int getType(){
		return type;
	}
	
	public int getReason(){
		return reason;
	}
}
