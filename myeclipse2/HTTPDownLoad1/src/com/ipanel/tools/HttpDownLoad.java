package com.ipanel.tools;



/**
 * http文件(比如电影等大文件)异步下载类（支持多路同时下载）
 * 要求：子类是单实例。
 */
public interface HttpDownLoad {
	
	/**
	 * 下载文件，下载后存放在savePath目录下fileName文件中。异步方法。
	 * 
	 * @param url
	 * 		格式为"http://xxx/fileName"，下载完后保存。
	 * 
	 * @param savePath
	 * 		下载完保存到的savePath目录下。
	 * 
	 * @param listener
	 * 		监听是否下载成功，通过其他notifyEvent方法回调下载事件。
	 * 
	 * @return 下载开始则返回true;下载开始失败返回false;
	 */
	public boolean load(String url, String savePath, HttpDownLoadListener listener);
	
	/**
	 * 取消下载。
	 * @param url
	 * 		取消正在下载的url；url可以为空，为空时表示取消正在下载的文件。
	 * @return
	 * 		正在下载的文件取消下载成功返回true；否则返回false。
	 */
	public boolean cancel(String url);
}
