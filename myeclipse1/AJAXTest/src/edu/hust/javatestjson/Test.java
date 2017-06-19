package edu.hust.javatestjson;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import edu.whut.json.Province;

public class Test {

	public static void main(String[] args) {
		//将集合转换成json格式
		Province p1 = new Province("01","wuhan");
		Province p2 = new Province("02","beijing");
		Province p3 = new Province("03","shenzhen");
		Province p4 = new Province("04","shanghai");
		
		List<Province> list = new ArrayList<Province>();
		list.add(p1); 
		list.add(p2);
		list.add(p3);
		list.add(p4);
		
	//	test1(list);
	//	test2(list);
		
		//将单个对象转换成json格式
		test3();
		
		
	}

	public static void test1(List<Province> list){
		JSONArray jsArray = new JSONArray().fromObject(list);
		/* 测试直接得到json格式的数据
		[{"pid":"01","pname":"wuhan"},{"pid":"02","pname":"beijing"},{"pid":"03","pname":"shenzhen"},{"pid":"04","pname":"shanghai"}]*/
		System.out.println(jsArray.toString());
	}
	

	//创建过滤器，将json中的某些属性滤掉，比如说将所有的pid属性滤掉
	public static void test2(List<Province> list){
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[]{"pid"});
		
		JSONArray jsArray = new JSONArray().fromObject(list,config);
		System.out.println(jsArray.toString());
	}
	
	private static void test3() {
		Province p = new Province("01","wuhan");
		//方法一：[{"pid":"01","pname":"wuhan"}]  得到的是个集合对象
		JSONArray jsArray = new JSONArray().fromObject(p);
		
		//方法二：{"pid":"01","pname":"wuhan"}    得到的是单个对象
		JSONObject jsonObject = JSONObject.fromObject(p);
		System.out.println(jsArray.toString());
		System.out.println(jsonObject.toString());
	}

}
