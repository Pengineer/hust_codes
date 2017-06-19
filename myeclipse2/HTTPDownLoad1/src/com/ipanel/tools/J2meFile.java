package com.ipanel.tools;


import java.io.IOException;

/**
 * 通过native方法操作文件 
 */

public class J2meFile {
	public static final int J2ME_FILE_OPEN_MODE_WRITE = 1;
	public static final int J2ME_FILE_OPEN_MODE_READ  = 2;
	public static final int J2ME_FILE_OPEN_MODE_RW    = 3;
	
	private int peer = 0;
	
	/**
	 * 构建J2meFile
	 * 
	 * @param path
	 * 		文件路径
	 * 
	 * @param mode
	 * 		文件打开方式
	 */
	public J2meFile(String path, int mode){
		peer = open(path, mode);		
	}
	
	/**
	 * 关闭文件
	 * @return 
	 * 		成功返回true，失败则返回false。
	 * 
	 * @throws IOException
	 */
	public boolean close() throws IOException {
		return close(peer);
	}
	
	/**
	 * 把buf的数据写入文件
	 * @param buf 字符数组
	 * @param offset 起始位置
	 * @param len 写入数据的长度
	 * @return 返回成功写入的长度
	 * @throws IOException
	 */
	public int write(byte buf[], int offset, int len) 
		throws IOException
	{
		return write(peer, buf, offset, len);
	}
	
	/**
	 * 从文件读取数据存入buf
	 * @param buf 字符数组
	 * @param offset 起始位置
	 * @param len 期望读入数据的长度
	 * @return 返回成功读入的数据的长度
	 * @throws IOException
	 */
	public int read(byte buf[], int offset, int len) 
		throws IOException
	{
		return read(peer, buf, offset, len);
	}
	
	/**
	 * 删除文件
	 * @return 成功则返回true；失败返回false。
	 */
	public boolean delete(){
		return delete(peer);
	}
	
	/**
	 * 获取文件相关的属性，如文件长度，最近修改时间
	 * @param key
	 *   属性名称，如"length","last-monitime"
	 * @return 返回对应的属性值，如果没有相应的属性则返回空。
	 */
	public String getProperty(String key){
		return getProperty(peer, key);
	}
	
	
	/*
	 * 以下是需要实现的native方法,有异常一定要抛异常。
	 */	
	private native int open(String path, int mode);
	private native boolean close(int peer);
	private native int write(int peer, byte buf[], int offset, int len);
	private native int read(int peer, byte buf[], int offset, int len);
	private native boolean delete(int peer);
	private native String getProperty(int peer, String key);
	
	/**
	 * 把jar包文件流每一个字符转换成16进制转换成字符0x##，并且用”,“分开，
	 * 每行10个，存放在字符数组文件中
	 * 
	 * @param jarPath
	 * 		 jar包源文件路径
	 * 
	 * @param resPath
	 * 		 转存后存放res文件路径
	 * 
	 * @return
	 * 		成功则放回true,失败返回false。
	 */
	public static native boolean jar2res(String jarPath, String resPath);
}
