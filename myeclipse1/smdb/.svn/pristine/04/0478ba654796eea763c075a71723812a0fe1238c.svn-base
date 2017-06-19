package csdc.action.system.config;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.csdc.service.imp.DmssService;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.SystemConfig;
import csdc.dao.HibernateBaseDao;
import csdc.service.IBaseService;
import org.csdc.service.imp.DmssService;

/**
 * 管理SystemConfig
 * @author zhouzj
 */
@SuppressWarnings("unchecked")
public class SystemConfigAction extends ActionSupport{
	
	private static final long serialVersionUID = -8706230865273774538L;
	private SystemConfig sc;
	private String id;
	private String tip;
	private String teacherRegister;
	private String allowedIp;
	private String refusedIp;
	private String maxSession;
	private String rows;
	private String queryRows;
	private String numReview;
	private String awardSession;
	private String specialAwardScore;
	private String firstAwardScore;
	private String secondAwardScore;
	private String thirdAwardScore;
	private String popularAwardScore;
	private String youthFee;//青年基金项目费用
	private String planFee;//规划基金项目费用
	private String hugeFee;//重大项目费用
	private String keyFee;//重点项目费用
	private String generalFee;//一般项目费用
	private String tempUploadPath;
	private String UserPictureUploadPath;
	private String NewsFileUploadPath;
	private String NoticeFileUploadPath;
	private String MailFileUploadPath;
	private String xmlConfig;
	private String thirdUplodState;//第三方上传接口状态
	
	private IBaseService baseService;
	@Autowired
	private DmssService dmssService;
	@Autowired
	private HibernateBaseDao dao;

	/**
	 * 进入系统配置页面
	 * @return
	 */
	public String toConfig() {
		return SUCCESS;
	}
	
	public String toConfigRegister() {
		return SUCCESS;
	}
	
	public String configRegister() {
		modifySystemConfig("teacherRegister", teacherRegister);
		return SUCCESS;
	}
	
	public String toConfigLogin() {
		return SUCCESS;
	}
	
	public String configLogin() {
		modifySystemConfig("allowedIp", allowedIp);
		modifySystemConfig("refusedIp", refusedIp);
		modifySystemConfig("maxSession", maxSession);
		return SUCCESS;
	}
	
	public String toConfigSearchCondition() {
		return SUCCESS;
	}
	
	public String configSearchCondition() {
		modifySystemConfig("rows", rows);
		modifySystemConfig("queryRows", queryRows);
		return SUCCESS;
	}
	
	public String toConfigAward() {
		return SUCCESS;
	}
	
	public String toConfigFee() {
		return SUCCESS;
	}
	
	public String configAward() {
		modifySystemConfig("youthFee", youthFee);
		modifySystemConfig("planFee", planFee);
		modifySystemConfig("hugeFee", hugeFee);
		modifySystemConfig("keyFee", keyFee);
		modifySystemConfig("generalFee", generalFee);
		return SUCCESS;
	}
	
	public String configFee() {
		modifySystemConfig("youthFee", youthFee);
		modifySystemConfig("planFee", planFee);
		modifySystemConfig("hugeFee", hugeFee);
		modifySystemConfig("keyFee", keyFee);
		modifySystemConfig("generalFee", generalFee);
		return SUCCESS;
	}
	
	public String toConfigReview(){
		return SUCCESS;
	}
	
	public String configReview(){
		modifySystemConfig("numReview", numReview);
		return SUCCESS;
	}
	
	public String toConfigPath() {
		return SUCCESS;
	}
	
	public String configPath() {
		modifySystemConfig("tempUploadPath", tempUploadPath);
		modifySystemConfig("UserPictureUploadPath", UserPictureUploadPath);
		modifySystemConfig("NewsFileUploadPath", NewsFileUploadPath);
		modifySystemConfig("NoticeFileUploadPath", NoticeFileUploadPath);
		modifySystemConfig("MailFileUploadPath", MailFileUploadPath);
		return SUCCESS;
	}
	
	public String toConfigUplodToDmss() {
		int on = dmssService.getOn();
		if(on == 1) {
			thirdUplodState = "connect";
		} else {
			thirdUplodState = "close";
		}
		return SUCCESS;
	}
	/**
	 * 第三方上传接口配置包括：关闭、打开、重连	
	 * @return
	 */
	public String configUplodToDmss() {
		if(thirdUplodState.trim().equals("connect")) {
			dmssService.setOn(1);
		} else if(thirdUplodState.trim().equals("close")) {
			dmssService.setOn(0);
		} else if(thirdUplodState.trim().equals("reconnect")) {
			dmssService.setStatus(false);
		}
		return SUCCESS;
	}
//	public String toConfigMailer() {
//		return SUCCESS;
//	}
//	
//	public String configMailer() {
//		modifySystemConfig("MailerAccount", MailerAccount);
//		return SUCCESS;
//	}
	
	private String modifySystemConfig(String key, String value) {
		Map parMap = new HashMap();
		parMap.put("key", key);
		List list = dao.query("select s from SystemConfig s where s.key =:key", parMap);
		if(list.size() != 0){
			sc = (SystemConfig) list.get(0);
			sc.setValue(value);
			dao.modify(sc);
		}
		ActionContext.getContext().getApplication().put(key, value);
		return SUCCESS;
	}
	
	public String toClear2ndCache() {
		return SUCCESS;
	}
	
	public String clear2ndCache() {
		dao.clear2ndCache();
		return SUCCESS;
	}
	
	public String toDownloadXML() {
		return SUCCESS;
	}
	
	public String downloadXML() {
		System.out.println(xmlConfig);
		Document root = DocumentHelper.createDocument();
		Element rootDoc = root.addElement("DocumentData");
		rootDoc.addAttribute("version", "1.0");
		if(xmlConfig.contains("university")) {
			List list = dao.query("select ag.code, ag.name from Agency ag where (ag.type=3 or ag.type=4) and (ag.style like '11%' or ag.style like '12%' or ag.style like '13%' or ag.style like '14%' or ag.style like '22%') order by ag.code asc ");
			Element uv = rootDoc.addElement("universityCodes");
			Element school = null;
			school = uv.addElement("SchoolCode");
			for(int i = 0; i < list.size(); i++) {
				Object[] o = (Object[]) list.get(i);
				Element uni = school.addElement("university");
				uni.addAttribute("code", o[0].toString());
				uni.addAttribute("name", o[1].toString());
			}
		}
		if(xmlConfig.contains("discipline")) {
			List list = dao.query("select so.code, so.name from SystemOption so where standard = 'GBT13745-2009' and so.code is not null order by so.code asc ");
			Element di = rootDoc.addElement("subjectData");
			Element subjectA = null, subjectB = null, subjectC = null;
			for(int i = 0; i < list.size(); i++) {
				Object[] o = (Object[]) list.get(i);
				if(o[0].toString().length() == 3) {
					subjectA = di.addElement("SubjectA");
					subjectA.addAttribute("code", o[0].toString());
					subjectA.addAttribute("name", o[1].toString());
				} else if(o[0].toString().length() == 5) {
					subjectB = subjectA.addElement("SubjectB");
					subjectB.addAttribute("code", o[0].toString());
					subjectB.addAttribute("name", o[1].toString());
				} else {
					subjectC = subjectB.addElement("SubjectB");
					subjectC.addAttribute("code", o[0].toString());
					subjectC.addAttribute("name", o[1].toString());
				}
			}
		}
		if(xmlConfig.contains("university")) {
			List list = dao.query("select so.code, so.name from SystemOption so where so.standard = 'GBT8561-2001' and so.code is not null order by so.code asc ");
			Element sp = rootDoc.addElement("specialityTitle");
			Element titleA = null, titleB = null;
			for(int i = 0; i < list.size(); i++) {
				Object[] o = (Object[]) list.get(i);
				if(o[0].toString().endsWith("0")) {
					titleA = sp.addElement("titleA");
					titleA.addAttribute("name", o[1].toString());
				} else {
					titleB = titleA.addElement("titleB");
					titleB.addAttribute("name", o[1].toString());
				}
			}
		}
		
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/xml");
			OutputStream outs;
			outs = response.getOutputStream();
			BufferedOutputStream bouts=new BufferedOutputStream(outs);
			response.setHeader("Charset","UTF-8");
			response.setHeader("Content-disposition","attachment;filename=const.dat");//设置头部信息
			int bytesRead = 0;
            byte[] buffer = new byte[8192];
            //开始向网络传输文件流
            InputStream ins = new ByteArrayInputStream(root.asXML().getBytes("UTF-8"));
            BufferedInputStream bins=new BufferedInputStream(ins);
            while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
                bouts.write(buffer, 0, bytesRead);
            }
            bouts.flush();//这里一定要调用flush()方法
            ins.close();
            bins.close();
            outs.close();
            bouts.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public SystemConfig getSc() {
		return sc;
	}

	public void setSc(SystemConfig sc) {
		this.sc = sc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getTeacherRegister() {
		return teacherRegister;
	}

	public void setTeacherRegister(String teacherRegister) {
		this.teacherRegister = teacherRegister;
	}

	public String getAllowedIp() {
		return allowedIp;
	}

	public void setAllowedIp(String allowedIp) {
		this.allowedIp = allowedIp;
	}

	public String getRefusedIp() {
		return refusedIp;
	}

	public void setRefusedIp(String refusedIp) {
		this.refusedIp = refusedIp;
	}

	public String getMaxSession() {
		return maxSession;
	}

	public void setMaxSession(String maxSession) {
		this.maxSession = maxSession;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getQueryRows() {
		return queryRows;
	}

	public void setQueryRows(String queryRows) {
		this.queryRows = queryRows;
	}

	public String getAwardSession() {
		return awardSession;
	}

	public void setAwardSession(String awardSession) {
		this.awardSession = awardSession;
	}

	public String getSpecialAwardScore() {
		return specialAwardScore;
	}

	public void setSpecialAwardScore(String specialAwardScore) {
		this.specialAwardScore = specialAwardScore;
	}

	public String getFirstAwardScore() {
		return firstAwardScore;
	}

	public void setFirstAwardScore(String firstAwardScore) {
		this.firstAwardScore = firstAwardScore;
	}

	public String getSecondAwardScore() {
		return secondAwardScore;
	}

	public void setSecondAwardScore(String secondAwardScore) {
		this.secondAwardScore = secondAwardScore;
	}

	public String getThirdAwardScore() {
		return thirdAwardScore;
	}

	public void setThirdAwardScore(String thirdAwardScore) {
		this.thirdAwardScore = thirdAwardScore;
	}

	public String getYouthFee() {
		return youthFee;
	}

	public String getPlanFee() {
		return planFee;
	}

	public void setYouthFee(String youthFee) {
		this.youthFee = youthFee;
	}

	public void setPlanFee(String planFee) {
		this.planFee = planFee;
	}

	public String getHugeFee() {
		return hugeFee;
	}

	public void setHugeFee(String hugeFee) {
		this.hugeFee = hugeFee;
	}

	public String getKeyFee() {
		return keyFee;
	}

	public void setKeyFee(String keyFee) {
		this.keyFee = keyFee;
	}

	public String getGeneralFee() {
		return generalFee;
	}

	public void setGeneralFee(String generalFee) {
		this.generalFee = generalFee;
	}

	public String getPopularAwardScore() {
		return popularAwardScore;
	}

	public void setPopularAwardScore(String popularAwardScore) {
		this.popularAwardScore = popularAwardScore;
	}

	public String getTempUploadPath() {
		return tempUploadPath;
	}

	public void setTempUploadPath(String tempUploadPath) {
		this.tempUploadPath = tempUploadPath;
	}

	public String getUserPictureUploadPath() {
		return UserPictureUploadPath;
	}

	public void setUserPictureUploadPath(String userPictureUploadPath) {
		UserPictureUploadPath = userPictureUploadPath;
	}

	public String getNewsFileUploadPath() {
		return NewsFileUploadPath;
	}

	public void setNewsFileUploadPath(String newsFileUploadPath) {
		NewsFileUploadPath = newsFileUploadPath;
	}

	public String getNoticeFileUploadPath() {
		return NoticeFileUploadPath;
	}

	public void setNoticeFileUploadPath(String noticeFileUploadPath) {
		NoticeFileUploadPath = noticeFileUploadPath;
	}

	public String getMailFileUploadPath() {
		return MailFileUploadPath;
	}

	public void setMailFileUploadPath(String mailFileUploadPath) {
		MailFileUploadPath = mailFileUploadPath;
	}

	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public String getXmlConfig() {
		return xmlConfig;
	}

	public void setXmlConfig(String xmlConfig) {
		this.xmlConfig = xmlConfig;
	}

	public String getNumReview() {
		return numReview;
	}

	public void setNumReview(String numReview) {
		this.numReview = numReview;
	}

	public String getThirdUplodState() {
		return thirdUplodState;
	}

	public void setThirdUplodState(String thirdUplodState) {
		this.thirdUplodState = thirdUplodState;
	}

}
