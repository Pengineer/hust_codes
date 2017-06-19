package csdc.service.webService.server;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Account;
import csdc.bean.Passport;
import csdc.bean.SyncStatus;
import csdc.dao.HibernateBaseDao;
import csdc.service.IAccountService;
import csdc.service.IInterfaceService;
import csdc.tool.MD5;
import csdc.tool.webService.VisitorInfoBean;
import csdc.tool.webService.WSServerSecurityTool;

@WebService
@SOAPBinding(style = Style.DOCUMENT,use = Use.LITERAL)
public class WebServices{

	protected ISinossWebService sinossWebService;
    @Resource  
    private WebServiceContext wsc;
    private IAccountService accountService;
    private IInterfaceService interfaceService;
    private ISmasWebService smasWebService;
    private Document document;

	@Autowired
    protected HibernateBaseDao dao;
	
	@WebMethod
	public String operate(@WebParam(name = "arg0", targetNamespace = "http://csdc.info/") String contentHex){
		//首先进行判断，若有session中有错误信息，直接返回错误信息
		MessageContext ctx = wsc.getMessageContext();  
        HttpServletRequest request = (HttpServletRequest)ctx.get(SOAPMessageContext.SERVLET_REQUEST);
        String userMarkString = (String) request.getAttribute("userMark");
        if(userMarkString == null){
        	////异常情况:此时用户标识也没有，则创建临时标识，并创建临时对象，soap报文返回使用
        	request.setAttribute("userMark", "errorTempMark");
        	userMarkString = "errorTempMark";
        	VisitorInfoBean tempErroeBean = new VisitorInfoBean(userMarkString,3);
        	WSServerSecurityTool.visitorBeansMap.put(userMarkString, tempErroeBean);
        	return sinossWebService.responseContent("error", "用户标志信息userMark空！");
        }
        Map soapInfo = (Map) request.getSession().getAttribute("soapInfo");
        if(soapInfo.get("error") != null) {
        	//异常情况：对客户端验证异常。即session中的异常信息
        	if(!WSServerSecurityTool.getVisitorBeansMap().containsKey(userMarkString)){//若用户对象没有建立
        		VisitorInfoBean tempErroeBean = new VisitorInfoBean(userMarkString,3);
            	WSServerSecurityTool.visitorBeansMap.put(userMarkString, tempErroeBean);
        	}
        	WSServerSecurityTool.visitorBeansMap.get(userMarkString).setVisitorTag(3);//soap返回， 错误提示专用通道
			return sinossWebService.responseContent("error", (String) soapInfo.get("error"));
		}
        //其他情况：用户已经通过验证，并且已经获取用户标识和对象
		VisitorInfoBean visitorBean = null;
		String contentString = null;
		try {
			contentString = new String(WSServerSecurityTool.hexStr2ByteArray(contentHex), "utf-8");
			document = DocumentHelper.parseText(contentString);
			visitorBean = WSServerSecurityTool.visitorBeansMap.get(userMarkString);
		} catch (DocumentException e) {
			//异常情况：参数解析异常 ，当前userMarkd对象修改为裸奔通道标志
			WSServerSecurityTool.visitorBeansMap.get(userMarkString).setVisitorTag(3);
			return sinossWebService.responseContent("error", "组装的xml参数可能有误，参数解析错误");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//正常请求解析，并且以上异常都经过通道三返回
		String endResultString = null;
		int tag = visitorBean.getVisitorTag();
		switch (tag) {
		case 0:
			endResultString = doServicesResults(contentString);
			break;
		case 1:
			endResultString = doSecurityResults(contentString,visitorBean.getVisitorMark());
			break;
		case 2:
			endResultString = sinossWebService.responseContent("data", "用户安全连接已关闭！");
			//返回服务数据，实际清理工作在soap出server中完成。
			break;
		default:
			break;
		}
		return endResultString;
	}

	@WebMethod(exclude = true)
	public String doSecurityResults(String contentString,String userMark){
	   String endResultString = null;
	   
		MessageContext ctx = wsc.getMessageContext();  
        HttpServletRequest request = (HttpServletRequest)ctx.get(SOAPMessageContext.SERVLET_REQUEST);
		if (!getArgumentByTag("error").equals("")) {
			endResultString = sinossWebService.responseContent("error", getArgumentByTag("error"));
		}else if (getArgumentByTag("service").equals("ControlService") && getArgumentByTag("method").equals("requestHandsShake")) {
		    // 握手接口
			endResultString = sinossWebService.requestHandShake(getArgumentByTag("shakeMaterial"),userMark);
		}else {
			WSServerSecurityTool.visitorBeansMap.get(userMark).setVisitorTag(3);//soap返回， 错误提示专用通道
			endResultString = sinossWebService.responseContent("error", "安全请求不能正常处理，(服务方)握手动作失败！");
		}
		return endResultString; 
	}
	
	@WebMethod(exclude = true)
	public String doServicesResults(String content){
		MessageContext ctx = wsc.getMessageContext();  
        HttpServletRequest request = (HttpServletRequest)ctx.get(SOAPMessageContext.SERVLET_REQUEST);
        //先查看是否需要返回错误信息，有则反馈，无则校验后，进行服务应答
        Map soapInfo = (Map) request.getSession().getAttribute("soapInfo");
        String result = "";
		if(soapInfo.get("error") != null) {
			return sinossWebService.responseContent("error", (String) soapInfo.get("error"));
		} 
		Passport passport = accountService.getPassportByName((String) soapInfo.get("username"));
		if (null == passport) {
			return sinossWebService.responseContent("error", "此户名非本系统用户(passport null)，拒绝提供服务！");
		}
		String md5Password = MD5.getMD5((String) soapInfo.get("password"));
		if (passport.getStatus() == 0) {// 该用户已停用，则阻止登录
			return sinossWebService.responseContent("error","此账号已停用！");
		} else {// 该用户未停用，则判断该账号是否有效
			Date currenttime = new Date();
			if (currenttime.after(passport.getExpireDate())) {// 如果超过有效期，则阻止登录，并更新账号状态为停用
				passport.setStatus(0);
				dao.modify(passport);
				return sinossWebService.responseContent("error", "此账号已过期！");
			}
		}
		Account account = accountService.getAccountListByName((String) soapInfo.get("username")).get(0);
		String[] belongIdName = accountService.getAccountBelong(account);// 获取帐号所属的名字
		String accountBelong = belongIdName[1];
		if (!md5Password.equals(passport.getPassword())) {
			return sinossWebService.responseContent("error","密码错误，匹配失败！");
		}
		System.out.println("验证通过");
		List<String> userRight = null;
		userRight = accountService.getRightByAccountName((String)soapInfo.get("username"));
		try {
			if(!getArgumentByTag("error").equals("")) {
				return result = sinossWebService.responseContent("error", getArgumentByTag("error"));
			}
			if(getArgumentByTag("service").equals("SinossService")) {
				if(getArgumentByTag("method").equals("requestProjectApplicationResult")) {
					if(interfaceService.getIsPublished("SinossWebService", "requestProjectApplicationResult") != 1) {
						return result = sinossWebService.responseContent("error", "此服务已关闭");
					} else if(userRight.contains("ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_APP_RESULT_ACCESS")) {
						return result = sinossWebService.requestProjectApplicationResult(getArgumentByTag("projectType"), Integer.parseInt(getArgumentByTag("length").trim()), account, accountBelong);
					} else {
						return result = sinossWebService.responseContent("error", "此服务未对您开放");
					}
				}
				if(getArgumentByTag("method").equals("requestProjectMidinspectionRequired")) {
					if(interfaceService.getIsPublished("SinossWebService", "requestProjectMidinspectionRequired") != 1) {
						return result = sinossWebService.responseContent("error", "此服务已关闭");
					} else if(userRight.contains("ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_MID_REQUIRED_ACCESS")) {
						return result = sinossWebService.requestProjectMidinspectionRequired(getArgumentByTag("projectType"), Integer.parseInt(getArgumentByTag("length").trim()), account, accountBelong);
					} else {
						return result = sinossWebService.responseContent("error", "此服务未对您开放");
					}
				}
				if(getArgumentByTag("method").equals("requestProjectMidinspectionResult")) {
					if(interfaceService.getIsPublished("SinossWebService", "requestProjectMidinspectionResult") != 1) {
						return result = sinossWebService.responseContent("error", "此服务已关闭");
					} else if(userRight.contains("ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_MID_RESULT_ACCESS")) {
						return result = sinossWebService.requestProjectMidinspectionResult(getArgumentByTag("projectType"), Integer.parseInt(getArgumentByTag("length").trim()), account, accountBelong);
					} else {
						return result = sinossWebService.responseContent("error", "此服务未对您开放");
					}
				}
				if(getArgumentByTag("method").equals("requestProjectVariationResult")) {
					if(interfaceService.getIsPublished("SinossWebService", "requestProjectVariationResult") != 1) {
						return result = sinossWebService.responseContent("error", "此服务已关闭");
					} else if(userRight.contains("ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_VAR_RESULT_ACCESS")) {
						return result = sinossWebService.requestProjectVariationResult(getArgumentByTag("projectType"), Integer.parseInt(getArgumentByTag("length").trim()), account, accountBelong);
					} else {
						return result = sinossWebService.responseContent("error", "此服务未对您开放");
					}
				}
				if(getArgumentByTag("method").equals("requestProjectEndinspectionResult")) {
					if(interfaceService.getIsPublished("SinossWebService", "requestProjectEndinspectionResult") != 1) {
						return result = sinossWebService.responseContent("error", "此服务已关闭");
					} else if(userRight.contains("ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_END_RESULT_ACCESS")) {
						return result = sinossWebService.requestProjectEndinspectionResult(getArgumentByTag("projectType"), Integer.parseInt(getArgumentByTag("length").trim()), account, accountBelong);
					} else {
						return result = sinossWebService.responseContent("error", "此服务未对您开放");
					}
				} else {
					return sinossWebService.responseContent("error", "调用的服务不在服务范围");
				}
			} else if (getArgumentByTag("service").equals("ControlService")) {
				if(getArgumentByTag("method").equals("replySyncStatus")) {
					return result = replySyncStatus(getArgumentByTag("content"),account,accountBelong);
				} else {
					return sinossWebService.responseContent("error", "调用的服务不在服务范围");
				}
			}/*else if (serviceMap.get("service").equals("SmasService")) {
				if(serviceMap.get("interface").equals("addKeyPerson")) {
					return result = smasWebService.addKeyPerson(account, accountBelong);
				} else {
					return smasWebService.responseContent("error", "调用的服务不在服务范围");
				}
			}*/					
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return result;
}
	@WebMethod(exclude = true)
	private String replySyncStatus(String content,Account account,String replyAccountBelong) throws DocumentException{
		Date replyDate = new Date();
		StringBuffer unsynchronizedItem = new StringBuffer();
		Document doc = DocumentHelper.parseText(content);
		List list = doc.selectNodes("//interface");
		Element interfaceElement = (Element) list.get(0);
		Map map = new HashMap();
//		Calendar c = Calendar.getInstance();
//		String year = String.valueOf(c.get(Calendar.YEAR));
//		map.put("year", year);
		map.put("interface", interfaceElement.getText());
		map.put("peer",sinossWebService.getPeerByAccount(account));//同一机构，只能应答同一机构访问的接口
		List<SyncStatus> syncStatusList = new ArrayList<SyncStatus>();
		syncStatusList = dao.query("from SyncStatus s where s.status = 0 and s.peer = :peer and s.interfaceName = :interface",map);
		if(syncStatusList.isEmpty()) {
			return sinossWebService.responseContent("error", "您尚未获取同步数据");
		}
		int idType;
		if(interfaceElement.getText().equals("requestProjectApplicationResult") || interfaceElement.getText().equals("requestProjectMidinspectionRequired")) {
			idType = 1;
		} else if(interfaceElement.getText().equals("requestProjectMidinspectionResult")) {
			idType = 2;
		} else if(interfaceElement.getText().equals("requestProjectVariationResult")) {
			idType = 3;
		} else if(interfaceElement.getText().equals("requestProjectEndinspectionResult")) {
			idType = 4;
		} else {
			return sinossWebService.responseContent("error", "同步接口名不存在，请检查是否有误");
		}
		Map<String, SyncStatus> syncStatusMap = new HashMap<String, SyncStatus>();
		for (int i = 0; i < syncStatusList.size(); i++) {
			switch (idType) {
			case 1:
				syncStatusMap.put(syncStatusList.get(i).getProjectId(), syncStatusList.get(i));
				break;
			case 2:
				syncStatusMap.put(syncStatusList.get(i).getMidinspectionId(), syncStatusList.get(i));
				break;
			case 3:
				syncStatusMap.put(syncStatusList.get(i).getVariationId(), syncStatusList.get(i));
				break;
			case 4:
				syncStatusMap.put(syncStatusList.get(i).getEndinspectionId(), syncStatusList.get(i));
				break;
			}
		}
		list = doc.selectNodes("//item" );
		Iterator iter = list.iterator();
  outer:while (iter.hasNext()) {
			Element element = (Element) iter.next();
			Iterator<Element> elementItor = element.elementIterator();
			SyncStatus syncStatus = new SyncStatus();
			while(elementItor.hasNext()) {
				String itemId = null;
				Integer status = null;
				String note = null;
				Element itemElement = elementItor.next();
				if(itemElement.getName().equals("itemId")) {
					itemId = itemElement.getText();
					if(syncStatusMap.get(itemId) != null) {
						syncStatus = syncStatusMap.get(itemId);
						syncStatusMap.remove(itemId);
					} else continue outer;
				}
				if(itemElement.getName().equals("status")) {
					status = Integer.parseInt(itemElement.getText());
					syncStatus.setStatus(status);
				}
				if(itemElement.getName().equals("note")) {
					note = itemElement.getText();
					syncStatus.setNote(note);
				}
				syncStatus.setReplyDate(replyDate);
				syncStatus.setResponseAccount(account);
				syncStatus.setResponseAccountBelong(replyAccountBelong);
			} 
			dao.modify(syncStatus);
		}
		if(!syncStatusMap.isEmpty()) {
			unsynchronizedItem.append(syncStatusMap.keySet().toString()+"等item尚未应答");
			return sinossWebService.responseContent("notice", unsynchronizedItem.toString());
		}	
		return sinossWebService.responseContent("notice", "同步成功");
	}
	
	@WebMethod(exclude = true)	
	private String getArgumentByTag(String tag) {
		List elemtList = document.selectNodes("//" + tag);
		if(elemtList.isEmpty()) {
			return "";
		} else if(((Element) elemtList.get(0)).getTextTrim().equals("")) {
			return "";
		}
		return ((Element) elemtList.get(0)).getText();
	}
	@WebMethod(exclude = true)
	public ISinossWebService getSinossWebService() {
		return sinossWebService;
	}
	@WebMethod(exclude = true)
	public void setSinossWebService(ISinossWebService sinossWebService) {
		this.sinossWebService = sinossWebService;
	}
	@WebMethod(exclude = true)
	public IAccountService getAccountService() {
		return accountService;
	}
	@WebMethod(exclude = true)
	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}
	@WebMethod(exclude = true)
	public IInterfaceService getInterfaceService() {
		return interfaceService;
	}
	@WebMethod(exclude = true)
	public void setInterfaceService(IInterfaceService interfaceService) {
		this.interfaceService = interfaceService;
	}
	@WebMethod(exclude = true)
	public ISmasWebService getSmasWebService() {
		return smasWebService;
	}
	@WebMethod(exclude = true)
	public void setSmasWebService(ISmasWebService smasWebService) {
		this.smasWebService = smasWebService;
	}
}
