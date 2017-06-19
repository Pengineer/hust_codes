package csdc.tool.webService;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import csdc.bean.ProjectFee;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.StringTool;

public class BaseRecordResolver {
	protected List badItemsList; //无法解析的id集合
	protected IHibernateBaseDao dao;
	/**
	 * “项目名称”和“项目负责人” ProjectApplication，查处是否在smas中存在此项目，并返回smas中的项目申请id
	 * @param argsMap:projectName,director
	 * @return
	 */
	protected String checkProjectExists(Map<String, String> argsMap) {
		String hql = "select proApp.id from ProjectApplication proApp where proApp.projectName = :projectName and proApp.director = :director";
		List idList = (List) dao.query(hql, argsMap);
		if (!idList.isEmpty()) {
			return (String) idList.get(0);
		} else {
			return "";
		}
	}
	/**
	 * 获取elementRoot中的子节点信息
	 * @param elementRoot
	 * @param tag
	 * @return
	 */
	protected String getArgumentByTag(Element elementRoot, String tag) {
		 String context = null;
		 context = elementRoot.elementText(tag);
		 if (context != null) {
			return context;
		}
		return "null";
//		List elemtList = elementRoot.selectNodes("//" + tag);
//		if(elemtList.isEmpty()) {
//			return "";
//		} else if(((Element) elemtList.get(0)).getTextTrim().equals("")) {
//			return "";
//		}
//		return ((Element) elemtList.get(0)).getText();
	}
	/**
	 * 根据xml中的element 元素，从feeElementRoot抽取出ProjectFee对象，
	 * @param feeElementRoot
	 * @return 
	 */
	protected ProjectFee getProjectFeeObject(Element feeElementRoot, String projectType) {
		ProjectFee pojFee = null;
		if (!getArgumentByTag(feeElementRoot, "projectFeeID").equals("") &&
				!getArgumentByTag(feeElementRoot, "projectFeeID").equals("null")) {
			//存在projectFree
			String bookFee = getArgumentByTag(feeElementRoot, "bookFee");
			String bookNote = getArgumentByTag(feeElementRoot, "bookNote");
			String dataFee = getArgumentByTag(feeElementRoot, "dataFee");
			String dataNote = getArgumentByTag(feeElementRoot, "dataNote");
			String travelFee = getArgumentByTag(feeElementRoot, "travelFee");
			String travelNote = getArgumentByTag(feeElementRoot, "travelNote");
			String deviceFee = getArgumentByTag(feeElementRoot, "deviceFee");
			String deviceNote = getArgumentByTag(feeElementRoot, "deviceNote");
			String conferenceFee = getArgumentByTag(feeElementRoot, "conferenceFee");
			String conferenceNote = getArgumentByTag(feeElementRoot, "conferenceNote");
			String consultationFee = getArgumentByTag(feeElementRoot, "consultationFee");
			String consultationNote = getArgumentByTag(feeElementRoot, "consultationNote");
			String laborFee = getArgumentByTag(feeElementRoot, "laborFee");
			String laborNote = getArgumentByTag(feeElementRoot, "laborNote");
			String internationalFee = getArgumentByTag(feeElementRoot, "internationalFee");
			String internationalNote = getArgumentByTag(feeElementRoot, "internationalNote");
			String indirectFee = getArgumentByTag(feeElementRoot, "indirectFee");
			String indirectNote = getArgumentByTag(feeElementRoot, "indirectNote");
			String pojOtherFee = getArgumentByTag(feeElementRoot, "otherFee");
			String pojOtherNote = getArgumentByTag(feeElementRoot, "otherNote");
			String totalFee = getArgumentByTag(feeElementRoot, "totalFee");
			String pojType = getArgumentByTag(feeElementRoot, "type");
			String feeNote = getArgumentByTag(feeElementRoot, "feeNote");
			String fundedFee = getArgumentByTag(feeElementRoot, "fundedFee");
			
			pojFee = new ProjectFee();
			pojFee.setType(projectType);//项目类型
			
			if (!bookFee.equals("") && !bookFee.equals("null")) {
				pojFee.setBookFee(Double.parseDouble(bookFee));
			}
			if (!bookNote.equals("") && !bookNote.equals("null")) {
				pojFee.setBookNote(bookNote);
			}
			if (!dataFee.equals("") && !dataFee.equals("null")) {
				pojFee.setDataFee(Double.parseDouble(dataFee));
			}
			if (!dataNote.equals("") && !dataNote.equals("null")) {
				pojFee.setDataNote(dataNote);
			}
			if (!travelFee.equals("") && !travelFee.equals("null")) {
				pojFee.setTravelFee(Double.parseDouble(travelFee));
			}
			if (!travelNote.equals("") && !travelNote.equals("null")) {
				pojFee.setTravelNote(travelNote);
			}
			if (!deviceFee.equals("") && !deviceFee.equals("null")) {
				pojFee.setDeviceFee(Double.parseDouble(deviceFee));
			}
			if (!deviceNote.equals("") && !deviceNote.equals("null")) {
				pojFee.setDeviceNote(deviceNote);
			}
			if (!conferenceFee.equals("") && !conferenceFee.equals("null")) {
				pojFee.setConferenceFee(Double.parseDouble(conferenceFee));
			}
			if (!conferenceNote.equals("") && !conferenceNote.equals("null")) {
				pojFee.setConferenceNote(conferenceNote);
			}
			if (!consultationFee.equals("") && !consultationFee.equals("null")) {
				pojFee.setConsultationFee(Double.parseDouble(consultationFee));
			}
			if (!consultationNote.equals("") && !consultationNote.equals("null")) {
				pojFee.setConsultationNote(consultationNote);
			}
			if (!laborFee.equals("") && !laborFee.equals("null")) {
				pojFee.setLaborFee(Double.parseDouble(laborFee));
			}
			if (!laborNote.equals("") && !laborNote.equals("null")) {
				pojFee.setLaborNote(laborNote);
			}
			if (!internationalFee.equals("") && !internationalFee.equals("null")) {
				pojFee.setInternationalFee(Double.parseDouble(internationalFee));
			}
			if (!internationalNote.equals("") && !internationalNote.equals("null")) {
				pojFee.setInternationalNote(internationalNote);
			}
			if (!indirectFee.equals("") && !indirectFee.equals("null")) {
				pojFee.setIndirectFee(Double.parseDouble(indirectFee));
			}
			if (!indirectNote.equals("") && !indirectNote.equals("null")) {
				pojFee.setIndirectNote(indirectNote);
			}
			if (!pojOtherFee.equals("") && !pojOtherFee.equals("null")) {
				pojFee.setOtherFee(Double.parseDouble(pojOtherFee));
			}
			if (!pojOtherNote.equals("") && !pojOtherNote.equals("null")) {
				pojFee.setOtherNote(pojOtherNote);
			}
			if (!totalFee.equals("") && !totalFee.equals("null")) {
				pojFee.setTotalFee(Double.parseDouble(totalFee));
			}
			if (!pojType.equals("null")) {
				pojFee.setBookNote(pojType);	
			}
			if (!feeNote.equals("null")) {
				pojFee.setFeeNote(feeNote);
			}
			if (!fundedFee.equals("") && !fundedFee.equals("null")) {
				pojFee.setFundedFee(Double.parseDouble(fundedFee));
			}
		}
		return pojFee;
	}
	
	/**
	 * 根据乱七八糟的日期字符串分析出正确日期
	 * @param rawDate
	 * @return
	 */
	public Date _date(String rawDate){
		if (rawDate == null) {
			return null;
		}
		rawDate = StringTool.toDBC(rawDate).trim();
		if (rawDate.isEmpty()) {
			return null;
		}
		if (rawDate.matches("(19|20)\\d{4}") || rawDate.matches("20\\d{4}")) {
			Integer year = Integer.parseInt(rawDate) / 100;
			Integer month = Integer.parseInt(rawDate) % 100;
			if (month >= 1 && month <= 12) {
				return new Date(year - 1900, month - 1, 1);
			}
		}
		if (rawDate.matches("-?\\d{5,6}")) {
			//Excel中的日期在常规格式下的值(1900-1-1过去的天数 + 2)
			return new Date(Long.parseLong(rawDate) * 86400000L - 2209161600000L);
		} else if (rawDate.matches("-?\\d{7,}")) {
			//1970-1-1 00:00:00过去的毫秒数
			return new Date(Long.parseLong(rawDate));
		}
		String tmp[] = rawDate.replaceAll("\\D+", " ").trim().split("\\s+");
		if (tmp.length == 0 || tmp[0].isEmpty()) {
			return null;
		}
		Integer mid;
		Integer year = Integer.parseInt(tmp[0]);
		Integer month = tmp.length > 1 ? Integer.parseInt(tmp[1]) : 1;
		Integer date = tmp.length > 2 ? Integer.parseInt(tmp[2]) : 1;
		if (month > 31) {
			mid = month; month = year; year = mid;
		} else if (date > 31) {
			mid = date; date = year; year = mid;
		}
		if (month > 12) {
			mid = date;	date = month; month = mid;
		}
		if (year < 10) {
			year += 2000;
		} else if (year < 100) {
			year += 1900;
		}
		return new Date(year - 1900, month - 1, date);
	}
	
	
}
