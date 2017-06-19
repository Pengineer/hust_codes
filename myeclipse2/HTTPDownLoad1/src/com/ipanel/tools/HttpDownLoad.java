package com.ipanel.tools;



/**
 * http�ļ�(�����Ӱ�ȴ��ļ�)�첽�����֧ࣨ�ֶ�·ͬʱ���أ�
 * Ҫ�������ǵ�ʵ����
 */
public interface HttpDownLoad {
	
	/**
	 * �����ļ������غ�����savePathĿ¼��fileName�ļ��С��첽������
	 * 
	 * @param url
	 * 		��ʽΪ"http://xxx/fileName"��������󱣴档
	 * 
	 * @param savePath
	 * 		�����걣�浽��savePathĿ¼�¡�
	 * 
	 * @param listener
	 * 		�����Ƿ����سɹ���ͨ������notifyEvent�����ص������¼���
	 * 
	 * @return ���ؿ�ʼ�򷵻�true;���ؿ�ʼʧ�ܷ���false;
	 */
	public boolean load(String url, String savePath, HttpDownLoadListener listener);
	
	/**
	 * ȡ�����ء�
	 * @param url
	 * 		ȡ���������ص�url��url����Ϊ�գ�Ϊ��ʱ��ʾȡ���������ص��ļ���
	 * @return
	 * 		�������ص��ļ�ȡ�����سɹ�����true�����򷵻�false��
	 */
	public boolean cancel(String url);
}
