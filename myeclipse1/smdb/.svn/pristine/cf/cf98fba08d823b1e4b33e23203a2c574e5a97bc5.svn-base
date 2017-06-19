package csdc.service.imp;

import java.util.HashMap;
import java.util.Map;

import csdc.bean.FundingList;
import csdc.service.IMobileFundingService;

public class MobileFundingService extends BaseService implements IMobileFundingService{
	public String getFundingBatchId(String fundingBatchId) {
		if (fundingBatchId == null || fundingBatchId.equals("")) {
			fundingBatchId = (String) dao.queryUnique("select fb.id from FundingBatch fb where fb.date = (select max(fb2.date) from FundingBatch fb2)");
		}
		return fundingBatchId;
	}
	public Map getCountAndFee4FundingList(String fundingListId, String agencyId) {
		Map data = new HashMap();
		double tempFundingListFee = 0;
		int tempFundingListCount = 0;
		Map map = new HashMap();
		map.put("fundingListId", fundingListId);
		FundingList fundingList = dao.query(FundingList.class,fundingListId);
		String fundingListType = fundingList.getType();
		if (agencyId!=null&&!agencyId.equals("")) {
			map.put("agencyId", agencyId);
			if (fundingListType.contains("work")) {
				Object wfFee=dao.query("select sum(wf.fee) from WorkFunding wf left outer join wf.agency ag left outer join wf.fundingList fl where ag.id =:agencyId and fl.id =:fundingListId ", map).get(0);
				if (wfFee!=null) {
					tempFundingListFee = (Double) wfFee;
					tempFundingListCount =(int) dao.count("select wf from WorkFunding wf left outer join wf.agency ag left outer join wf.fundingList fl where ag.id =:agencyId and fl.id =:fundingListId", map);
				}			
			} else if (fundingListType.contains("project")) {
				Object pfFee=dao.query("select sum(pf.fee) from ProjectFunding pf left join pf.agency ag left join pf.fundingList fl where ag.id =:agencyId and fl.id =:fundingListId ", map).get(0);
				if (pfFee!=null) {
					tempFundingListFee = (Double) pfFee;
					tempFundingListCount = (int) dao.count("select pf from ProjectFunding pf left join pf.agency ag left join pf.fundingList fl where ag.id =:agencyId and fl.id =:fundingListId ", map);
				}
			}
		}else {
			if (fundingListType.contains("work")) {
				Object wfFee=dao.query("select sum(wf.fee) from WorkFunding wf left outer join wf.fundingList fl where fl.id =:fundingListId ", map).get(0);
				if (wfFee!=null) {
					tempFundingListFee = (Double) wfFee;
					tempFundingListCount =(int) dao.count("select wf from WorkFunding wf left outer join wf.fundingList fl where fl.id =:fundingListId", map);
				}			
			} else if (fundingListType.contains("project")) {
				Object pfFee=dao.query("select sum(pf.fee) from ProjectFunding pf left join pf.fundingList fl where fl.id =:fundingListId ", map).get(0);
				if (pfFee!=null) {
					tempFundingListFee = (Double) pfFee;
					tempFundingListCount = (int) dao.count("select pf from ProjectFunding pf left join pf.fundingList fl where fl.id =:fundingListId ", map);
				}
			}
		}
		data.put("count", tempFundingListCount);
		data.put("fee", tempFundingListFee);
		return data;
	}
	public String convertFundingListType(String type) {
		if (type.equals("project")) {
			return "项目经费";
		} else if (type.equals("work")) {
			return "工作经费";
		}
		return type;
	}

	public String convertFundingListSubType4work(String type) {
		if (type.equals("common")) {
			return "日常经费";
		} else if (type.equals("review")) {
			return "评审经费";
		} else if (type.equals("award")) {
			return "奖励经费";
		} else if (type.equals("publish")) {
			return "出版经费";
		} else if (type.equals("communication")) {
			return "交流经费";
		} else if (type.equals("training")) {
			return "培训经费";
		} else if (type.equals("other")) {
			return "其他经费";
		}
		return type;
	}

	public String convertProjectType(String type) {
		if (type.equals("general")) {
			return "一般项目";
		} else if (type.equals("instp")) {
			return "基地项目";
		} else if (type.equals("post")) {
			return "后期资助项目";
		} else if (type.equals("devrpt")) {
			return "发展报告项目";
		} else if (type.equals("key")) {
			return "重大攻关";
		}

		if (type.startsWith("special")) {
			if (type.equals("special:incorrupt")) {
				return "专项项目（教育廉政）";
			} else if (type.equals("special:ideologyTeacher")) {
				return "专项项目（择优资助）";
			} else if (type.equals("special:faith")) {
				return "专项项目（学风建设）";
			} else if (type.equals("special:ideologyCourse")) {
				return "专项项目（思政课）";
			} else if (type.equals("special:ideologyKey")) {
				return "专项项目（重点难点）";
			} else if (type.equals("special:ideologyWork")) {
				return "专项项目（思政工作）";
			} else if (type.equals("special:socialism")) {
				return "专项项目（中特）";
			} else if (type.equals("special:engTalent")) {
				return "专项项目（工程人才）";
			} else if (type.equals("special:marx")) {
				return "专项项目（马三化）";
			} else if (type.equals("special:comunication")) {
				return "专项项目（中外交流）";
			}
		}
		return type;
	}

	public String convertFundingListSubSubType4project(String type) {
		if (type.equals("granted")) {
			return "立项经费";
		} else if (type.equals("mid")) {
			return "中检经费";
		} else if (type.equals("end")) {
			return "结项经费";
		} else if (type.equals("first")) {
			return "一期经费";
		} else if (type.equals("second")) {
			return "二期经费";
		}
		return type;
	}
}