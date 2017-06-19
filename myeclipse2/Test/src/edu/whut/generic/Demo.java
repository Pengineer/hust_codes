package edu.whut.generic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class Demo {
	@Test
	public void test1(){
		List<String> list = new ArrayList<String>();
		list.add("aa");
		list.add("bb");
		
		Iterator<String> it = list.iterator();
		while(it.hasNext())
			System.out.println(it.next());
		
		for(String str : list)
			System.out.println(str);
	}
}
