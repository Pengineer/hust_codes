package csdc.tool.execution.importer;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 《20150612_第七届成果奖申请名单（专家建议名单）_评价中心报社科司_修正导入.xls》
 * @author pengliang
 * 检验学科门类和学科代码
 */

public class CheckUniversityCodeToName extends Importer {

	ExcelReader reader;
	
	@Autowired
	Tool tool;
	
	@Override
	protected void work() throws Throwable {
		//CheckCode();
//		panduan2();
//		subjectCodeCheck();  //学科代码校验
		subjectNameCheck();  //学科门类校验
		
	}
	
	public void subjectNameCheck() throws Exception {
		tool.initDiscNameCodeMap();
		reader.readSheet(0);
		
		int total=reader.getRowNumber();
		int current=0;
		Set<String> msg = new LinkedHashSet<String>();
		while(next(reader)) {
			if(B == null || "".equals(B)){
				break;
			}
			System.out.println((++current) + "/" + total);
			if(tool.discNameCodeMap.get(U) == null) {
				msg.add(current + "--" + T + "::" + U);
			}
		}
		if(msg.size() > 0) {
			System.out.println(msg.toString().replaceAll(",\\s+", "\n"));
		}
	}
	
	public void subjectCodeCheck() throws Exception {
		tool.initDiscNameCodeMap();
		reader.readSheet(0);
		
		int total=reader.getRowNumber();
		int current=0;
		Set<String> msg = new LinkedHashSet<String>();
		while(next(reader)) {
			if(B == null || "".equals(B)){
				break;
			}
			System.out.println((++current) + "/" + total);
			if(!doDisciplineCode(V,W,X)){
				msg.add(current + "--" + V + "::" + W + "::" + X);
			}
		}
		if(msg.size() > 0) {
			System.out.println(msg.toString().replaceAll(",\\s+", "\n"));
		}
	}
	
	public boolean doDisciplineCode(String subject1, String subject2, String subject3) {
		//观察数据：subject1有两个的，subject2和subject3都为空；subject2,subject3不为空的都只有一个；
		
		//有两个空
		if((subject2 == null || "".equals(subject2)) && (subject3 ==null || "".equals(subject3))) {
			return checkcodeName(subject1);     //观察数据所得
		} else if((subject1 == null || "".equals(subject1)) && (subject3 ==null || "".equals(subject3))) {
			return checkcodeName(subject2);     //观察数据所得
		} else if((subject2 == null || "".equals(subject2)) && (subject1 ==null || "".equals(subject1))) {
			return checkcodeName(subject3);     //观察数据所得
		}
		
		//有一个空
		if(subject1 == null || "".equals(subject1)) {
			return checkcodeName(subject2);     //观察数据所得
		} else if (subject2 == null || "".equals(subject2)) {
			return checkcodeName(subject1);     //观察数据所得
		} else if (subject3 ==null || "".equals(subject3)) {
			return checkcodeName(subject1);     //观察数据所得
		}
		
		//均不为空
		return checkcodeName(subject1);     //观察数据所得
	}
	
	public boolean checkcodeName(String subject){
		if(subject.contains("; ")) {
			String[] subs = subject.split("; ");
			for(String sub : subs) {
				String code = sub.substring(0, sub.indexOf("/"));
				String name = sub.substring(sub.indexOf("/") + 1, sub.length());
				if(tool.discCodeNameMap.get(code) == null || !tool.discCodeNameMap.get(code).equals(name)){
					return false;
				}
			}
		}else{
			String code = subject.substring(0, subject.indexOf("/"));
			String name = subject.substring(subject.indexOf("/") + 1, subject.length());
			if(tool.discCodeNameMap.get(code) == null || !tool.discCodeNameMap.get(code).equals(name)){
				return false;
			}
		}
		return true;
	}
	
	public void panduan() throws Exception{
		reader.readSheet(0);
		
		int total=reader.getRowNumber();
		int current=0;
		
		Set<String> msg = new LinkedHashSet<String>();
		
		while(next(reader)) {
			if(B == null || "".equals(B)){
				break;
			}
			System.out.println((++current) + "/" + total);
			if(X !=null && !"".equals(X)){
				continue;
			}else if( (V!=null && !"".equals(V)) && (W!=null && !"".equals(W)) ){
				
				if (!V.trim().equals(W.trim())) {
					msg.add(current+"--"+ V + "::"+W);
				}
			}
			
		}
		
		if(msg.size() > 0) {
			System.out.println(msg.toString().replaceAll(",\\s+", "\n"));
		}
		
	}
	
	public void panduan2() throws Exception{
		reader.readSheet(0);
		
		int total=reader.getRowNumber();
		int current=0;
		
		Set<String> msg = new LinkedHashSet<String>();
		
		while(next(reader)) {
			if(B == null || "".equals(B)){
				break;
			}
			System.out.println((++current) + "/" + total);
			if(current ==51 || W.equals("法律史学")){
				continue;
			}
			if(X !=null && !"".equals(X)){
				if( (V!=null && !"".equals(V)) && (W!=null && !"".equals(W)) ){
					if (!(X.trim().equals(V.trim()) && X.trim().equals(W.trim()))) {
						String str1 = V.substring(0,V.indexOf("/"));
						String str2 = W.substring(0,W.indexOf("/"));
						String str3 = X.substring(0,X.indexOf("/"));
						if(str1.equals(str2) && (str1.contains(str3))){
							continue;
						}
						if(str1.equals(str3) && str1.contains(str2)){
							continue;
						}
						
						msg.add(current+"--"+ V +"::"+ W + "::"+X);
					}
					
				}
			}
			
		}
		
		if(msg.size() > 0) {
			System.out.println(msg.toString().replaceAll(",\\s+", "\n"));
		}
		
	}
	
	public void CheckCode() throws Exception{
		reader.readSheet(0);
		
		int total=reader.getRowNumber();
		int current=0;
		
		Set<String> msg = new LinkedHashSet<String>();
		
		while(next(reader)) {
			if(B == null || "".equals(B)){
				break;
			}
			System.out.println((++current) + "/" + total);
			
			String name = (String)dao.queryUnique("select agency.name from Agency agency where agency.code = ?",F.trim());
			
			if(name == null || !name.trim().equals(G.trim())) {
				msg.add(current + "——" + F + ":" + G);
			}
		}
		
		if(msg.size() > 0) {
			System.out.println(msg.toString().replaceAll(",\\s+", "\n"));
		}
	}
	
	CheckUniversityCodeToName(){}
	
	CheckUniversityCodeToName(String filename) {
		reader = new ExcelReader(filename);
	}

}
