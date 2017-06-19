package org.cdsc.service;

import java.io.File;

import javax.persistence.criteria.From;

import org.csdc.domain.fm.ThirdCheckInForm;
import org.csdc.domain.fm.ThirdUploadForm;
import org.csdc.service.imp.DmssService;
import org.junit.Test;

public class DmssServiceTest {
	public void testUpload() throws Exception{
		DmssService service = new DmssService();
		service.setOn(1);
		service.setTimeout(30);		
		service.setUrl("http://localhost/dmss");
		service.setUsername("smdb");
		service.setPassword("123456");
		service.connect();
		File file = new File("E:\\调查报告－默认报告.doc");
		ThirdUploadForm form = new ThirdUploadForm();	
		form.setTitle("上传第一版");
		form.setFileName(file.getName());
		form.setSourceAuthor("金天凡");
		form.setRating("3.1");
		form.setTags("aaa,ccc");
		form.setCategoryPath("/CMIS/test");
		System.out.println(service.upload(file.getAbsolutePath(), form));
	}
	
	
	public void testCheckIn() throws Exception{
		DmssService service = new DmssService();
		service.setOn(1);
		service.setTimeout(30);		
		service.setUrl("http://localhost/dmss");
		service.setUsername("smdb");
		service.setPassword("123456");
		service.connect();
		File file = new File("D:\\demo.txt");
		ThirdCheckInForm form = new ThirdCheckInForm();
		form.setComment("更新了");
		form.setFileName(file.getName());
		form.setTitle("第二版罗");
		form.setId("4028d89446b3d6cb0146b3dd7f320000");
		System.out.println(service.checkIn(file.getAbsolutePath(), form));
	}
	
	@Test
	public void testDelete() throws Exception{
		DmssService service = new DmssService();
		service.setOn(1);
		service.setTimeout(30);		
		service.setUrl("http://localhost/dmss");
		service.setUsername("smdb");
		service.setPassword("123456");
		service.connect();
		service.deleteFile("4028d89446b3d6cb0146b3dd7f320000");
	}
}
