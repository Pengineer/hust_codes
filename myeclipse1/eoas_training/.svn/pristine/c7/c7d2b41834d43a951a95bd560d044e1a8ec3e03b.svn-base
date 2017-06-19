package csdc.action.oa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.Expert;
import csdc.bean.SystemOption;
import csdc.service.IBaseService;
import csdc.service.IExpertService;
import csdc.tool.ApplicationContainer;
import csdc.tool.FileTool;

public class ExpertAction extends ActionSupport {
	private IBaseService baseService;
	private IExpertService expertService;
	private Expert expert;
	private String expertId;
	private String hiddenId;
	private String discpId; //学科编号
	private Map jsonMap = new HashedMap();
	
	public String toAdd() {
		return SUCCESS;
	}
	
	public String add() {
		expert.setCreateDate(new Date());
		baseService.add(expert);
		return SUCCESS;
	}
	
	public String toModify() {
		expert = (Expert) baseService.load(Expert.class, expertId);
		return SUCCESS;
	}
	
	public String modify() {
		Date update = new Date();
		expert.setUpdateDate(update);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			sdf.format(expert.getUpdateDate()).toString();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		System.out.println(sdf.format(expert.getUpdateDate()).toString());
		baseService.modify(expert);
		return SUCCESS;
	}
	
	public String delete() {
		baseService.delete(Expert.class, expertId);
		Expert expert = (Expert) baseService.load(Expert.class, expertId);
		if(null != expert) {
			jsonMap.put("result", 0);
		} else {
			jsonMap.put("result", 1);
		}
		return SUCCESS;
	}
	
	public String view() {
		expert = (Expert) baseService.load(Expert.class, expertId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(expert.getCreateDate()).toString());
		return SUCCESS;
	}
	
			
	public String toList() {
		return SUCCESS;
	}
	
	public String list() {
		ArrayList<Expert> expertList = new ArrayList<Expert>();
		List<Object[]> eList = new ArrayList<Object[]>();
			expertList = (ArrayList<Expert>) baseService.list(Expert.class, null);
		String[] item;
		for (Expert e : expertList) {
			item = new String[7];
			item[0] = e.getName();
			item[1] = e.getGender();
			item[2] = e.getCompany();
			item[3] = e.getDepartment();
			item[4] = e.getJob();
			item[5] = e.getSpecialityTitle();
			item[6] = e.getId();
			eList.add(item);
		}
		jsonMap.put("aaData", eList);
		return SUCCESS;
	}
	
	public String toSelectDiscipline() {
		return SUCCESS;
	}
	
	/**
	 * 生成学科树
	 * @return
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public String createDisciplineTree() throws DocumentException {
		//将Document生成xml文件
		XMLWriter writer = null;
		SAXReader reader = new SAXReader();
		Map map = new HashedMap();
		map.put("systemOptionId", discpId);
		//由该学科获取它的下级学科代码
		List list = baseService.list(SystemOption.class.getName() + ".listCodeByParentId", map);
		String names = "";
		if(list !=null && list.size()>0){
			StringBuffer name = new StringBuffer();
			for(int j=0;j<list.size();j++){
				name.append(list.get(j).toString());
				name.append(";");
			}
			names = name.toString();
			names = names.substring(0,names.length()-1);
		}
		StringBuffer codes = new StringBuffer();
		String discpCode = names;

		//建立学科树文件的存放目录（有则不会重复建）
		String filePath=ApplicationContainer.sc.getRealPath("");
		FileTool.mkdir_p(filePath+"//data//discipline//");
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");
		//判断指定学科xml文件是否存在，不存在则建立
		File file = new File(filePath+"//data//discipline//"+discpCode+".xml");
		if(file.exists()){ //xml文件已经存在
		}else{ //临时建立xml
			Document doc = baseService.createDisciplineXML(discpId);
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				writer = new XMLWriter(fos, format);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			try {
				writer.write(doc);
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Document document = null;
		try {
			document = reader.read(file);
		} catch (Exception e1) {
			e1.printStackTrace();
		}// 读取XML文件
		//直接输出Document
		String content = document.asXML();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml;charset=UTF-8"); // 使用utf-8
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print(content); // 通过Io流写到页面上去了
		//必须返回空
		return null;
	}
	
	public IBaseService getBaseService() {
		return baseService;
	}
	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public Expert getExpert() {
		return expert;
	}

	public void setExpert(Expert expert) {
		this.expert = expert;
	}

	public String getExpertId() {
		return expertId;
	}

	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}

	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}

	public String getDiscpId() {
		return discpId;
	}

	public void setDiscpId(String discpId) {
		this.discpId = discpId;
	}

	public String getHiddenId() {
		return hiddenId;
	}

	public void setHiddenId(String hiddenId) {
		this.hiddenId = hiddenId;
	}

	public IExpertService getExpertService() {
		return expertService;
	}

	public void setExpertService(IExpertService expertService) {
		this.expertService = expertService;
	}
	
}