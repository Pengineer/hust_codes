package edu.whut.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

public class JsonServlet2 extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		
		Province p1 = new Province("01","wuhan");
		Province p2 = new Province("02","beijing");
		Province p3 = new Province("03","shenzhen");
		Province p4 = new Province("04","shanghai");
		
		List<Province> list = new ArrayList<Province>();
		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);
		
		JSONArray jsarr = JSONArray.fromObject(list);
		out.print(jsarr);
	}

}
