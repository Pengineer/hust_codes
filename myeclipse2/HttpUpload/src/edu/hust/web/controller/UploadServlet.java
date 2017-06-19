package edu.hust.web.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UploadServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//�ô�ͳ��ʽ��ȡ����ֵ����multipart/form-data���ǲ����Ե�
		String name = request.getParameter("username");
		System.out.println(name);
		
		//���ϴ������������ļ����ݴ�ӡ�����۲�����ʽ
		InputStream in = request.getInputStream();
		byte[] buf = new byte[1024];
		int len = 0 ;
		while((len = in.read(buf))!=-1){
			System.out.println(new String(buf,0,len));
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doGet(request, response);
	}

}

