package com.ipanel.util;

import java.util.HashMap;

public class ThreadHashMap {

	public static HashMap<String,Thread> hashMap = new HashMap<String, Thread>();
	
	public static void put(String url,Thread t){    //���url-Thread��ֵ��
		
		hashMap.put(url, t);
		
	}
	
	public static Thread get(String url){
		
		return hashMap.get(url);
		
	}
}
