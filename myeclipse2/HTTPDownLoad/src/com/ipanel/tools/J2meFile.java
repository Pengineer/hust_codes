package com.ipanel.tools;


import java.io.IOException;

/**
 * ͨ��native���������ļ� 
 */

public class J2meFile {
	public static final int J2ME_FILE_OPEN_MODE_WRITE = 1;
	public static final int J2ME_FILE_OPEN_MODE_READ  = 2;
	public static final int J2ME_FILE_OPEN_MODE_RW    = 3;
	
	private int peer = 0;
	
	/**
	 * ����J2meFile
	 * 
	 * @param path
	 * 		�ļ�·��
	 * 
	 * @param mode
	 * 		�ļ��򿪷�ʽ
	 */
	public J2meFile(String path, int mode){
		peer = open(path, mode);		
	}
	
	/**
	 * �ر��ļ�
	 * @return 
	 * 		�ɹ�����true��ʧ���򷵻�false��
	 * 
	 * @throws IOException
	 */
	public boolean close() throws IOException {
		return close(peer);
	}
	
	/**
	 * ��buf������д���ļ�
	 * @param buf �ַ�����
	 * @param offset ��ʼλ��
	 * @param len д�����ݵĳ���
	 * @return ���سɹ�д��ĳ���
	 * @throws IOException
	 */
	public int write(byte buf[], int offset, int len) 
		throws IOException
	{
		return write(peer, buf, offset, len);
	}
	
	/**
	 * ���ļ���ȡ���ݴ���buf
	 * @param buf �ַ�����
	 * @param offset ��ʼλ��
	 * @param len �����������ݵĳ���
	 * @return ���سɹ���������ݵĳ���
	 * @throws IOException
	 */
	public int read(byte buf[], int offset, int len) 
		throws IOException
	{
		return read(peer, buf, offset, len);
	}
	
	/**
	 * ɾ���ļ�
	 * @return �ɹ��򷵻�true��ʧ�ܷ���false��
	 */
	public boolean delete(){
		return delete(peer);
	}
	
	/**
	 * ��ȡ�ļ���ص����ԣ����ļ����ȣ�����޸�ʱ��
	 * @param key
	 *   �������ƣ���"length","last-monitime"
	 * @return ���ض�Ӧ������ֵ�����û����Ӧ�������򷵻ؿա�
	 */
	public String getProperty(String key){
		return getProperty(peer, key);
	}
	
	
	/*
	 * ��������Ҫʵ�ֵ�native����,���쳣һ��Ҫ���쳣��
	 */	
	private native int open(String path, int mode);
	private native boolean close(int peer);
	private native int write(int peer, byte buf[], int offset, int len);
	private native int read(int peer, byte buf[], int offset, int len);
	private native boolean delete(int peer);
	private native String getProperty(int peer, String key);
	
	/**
	 * ��jar���ļ���ÿһ���ַ�ת����16����ת�����ַ�0x##�������á�,���ֿ���
	 * ÿ��10����������ַ������ļ���
	 * 
	 * @param jarPath
	 * 		 jar��Դ�ļ�·��
	 * 
	 * @param resPath
	 * 		 ת�����res�ļ�·��
	 * 
	 * @return
	 * 		�ɹ���Ż�true,ʧ�ܷ���false��
	 */
	public static native boolean jar2res(String jarPath, String resPath);
}
