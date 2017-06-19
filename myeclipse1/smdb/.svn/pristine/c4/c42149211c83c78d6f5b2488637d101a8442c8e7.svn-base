package csdc.service.imp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Agency;
import csdc.bean.AgencyFunding;
import csdc.bean.EntrustFunding;
import csdc.bean.EntrustGranted;
import csdc.bean.FundingBatch;
import csdc.bean.FundingList;
import csdc.bean.GeneralFunding;
import csdc.bean.GeneralGranted;
import csdc.bean.InstpFunding;
import csdc.bean.InstpGranted;
import csdc.bean.KeyFunding;
import csdc.bean.KeyGranted;
import csdc.bean.Log;
import csdc.bean.Mail;
import csdc.bean.PostFunding;
import csdc.bean.PostGranted;
import csdc.bean.ProjectEndinspection;
import csdc.bean.ProjectFunding;
import csdc.bean.ProjectGranted;
import csdc.bean.WorkFunding;
import csdc.service.IFundingService;
import csdc.tool.ApplicationContainer;
import csdc.tool.HSSFExport;
import csdc.tool.StringTool;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.GlobalInfo;

public class FundingService extends BaseService implements IFundingService {
	@Autowired
	protected MailService mailService;

	public String getFundingBatchId(String fundingBatchId) {
		if (fundingBatchId == null || fundingBatchId.equals("")) {
			fundingBatchId = (String) dao
					.queryUnique("select fb.id from FundingBatch fb where fb.date = (select max(fb2.date) from FundingBatch fb2)");
		}
		return fundingBatchId;
	}

	public List getFundingBatchList() {
		StringBuffer hql = new StringBuffer();
		hql.append("select fb.id, fb.name, fb.date from FundingBatch fb order by fb.date desc");
		List fundingBatchList = dao.query(hql.toString());
		return fundingBatchList;
	}

	public void deleteFunding(List<String> fundingIds, String fundingType) {
		if (fundingType.equals("workFunding")) {
			for (String entityId : fundingIds) {
				dao.delete(WorkFunding.class, entityId);
			}
		} else if (fundingType.equals("projectFunding")) {
			for (String entityId : fundingIds) {
				dao.delete(ProjectFunding.class, entityId);
			}
		}
	}

	public FundingList addProjectFundingList(String fundingBatchId,
			String listName, String note, Integer year, Double rate,
			String subType, String subSubType, String projectSubtypeId) {
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		int isPrincipal = loginer.getIsPrincipal();
		String attn = "";// 经办人
		if (isPrincipal == 0) {
			attn = loginer.getCurrentPersonName();
		} else if (isPrincipal == 1) {
			attn = loginer.getCurrentBelongUnitName();
		}
		FundingList fundingList = new FundingList();
		fundingList.setAttn(attn);
		fundingList.setCreateDate(new Date());
		FundingBatch fundingBatch = dao.query(FundingBatch.class,
				fundingBatchId);
		fundingList.setFundingBatch(fundingBatch);
		fundingList.setName(listName);
		fundingList.setNote(note);
		fundingList.setRate(rate);
		fundingList.setStatus(0);
		fundingList.setType("ProjectFunding");
		fundingList.setSubType(subType);
		fundingList.setSubSubType(subSubType);
		dao.add(fundingList);
		Date now = new Date();
		if (subType.equals("general")) {
			List<GeneralGranted> projects = getProjectsToFund(
					year, subType, projectSubtypeId, subSubType);
			for (GeneralGranted projectGranted : projects) {
				if (projectGranted.getApproveFee() == null) {
					break;
				}
				GeneralFunding projecFunding = new GeneralFunding();
				projecFunding.setGranted(projectGranted);
				projecFunding.setGrantedId(projectGranted.getId());
				projecFunding.setAttn(attn);
				projecFunding.setDate(now);
				projecFunding.setFundingList(fundingList);
				projecFunding.setNote(note);
				projecFunding.setStatus(0);
				projecFunding.setType(subSubType);
				projecFunding.setPayee(projectGranted.getApplicantName());
				if (projectGranted.getDepartment() != null) {
					projectGranted.setDepartment(projectGranted.getDepartment());
				}
				projecFunding.setAgency(projectGranted.getUniversity());
				AgencyFunding agencyFunding = (AgencyFunding) dao
						.queryUnique(
								"select ag from AgencyFunding agf where agf.agency.id =? and agf.fundingBatch.id=?",
								projectGranted.getUniversity().getId(),
								fundingBatchId);
				projecFunding.setAgencyFunding(agencyFunding);
				projecFunding.setFee(projectGranted.getApproveFee() * rate);
				dao.add(projecFunding);
			}
		} else if (subType.equals("instp")) {
			List<InstpGranted> projects = getProjectsToFund(year,
					subType, projectSubtypeId, subSubType);
			for (InstpGranted projectGranted : projects) {
				if (projectGranted.getApproveFee() == null) {
					break;
				}
				InstpFunding projecFunding = new InstpFunding();
				projecFunding.setGranted(projectGranted);
				projecFunding.setGrantedId(projectGranted.getId());
				projecFunding.setAttn(attn);
				projecFunding.setDate(now);
				projecFunding.setFundingList(fundingList);
				projecFunding.setNote(note);
				projecFunding.setStatus(0);
				projecFunding.setType(subSubType);
				projecFunding.setPayee(projectGranted.getApplicantName());
				// InstpFunding instpFunding = (InstpFunding)
				// dao.queryUnique("select inf from InstpFunding nif where inf.institute.id =? and inf.fundingBatch.id=?",
				// projectGranted.getInstitute().getId(), fundingBatchId);
				// projecFunding.setInstituteFunding(instpFunding);
				projecFunding.setFee(projectGranted.getApproveFee() * rate);
				dao.add(projecFunding);
			}
		} else if (subType.equals("post")) {
			List<PostGranted> projects = getProjectsToFund(year,
					subType, projectSubtypeId, subSubType);
			for (PostGranted projectGranted : projects) {
				if (projectGranted.getApproveFee() == null) {
					break;
				}
				PostFunding projecFunding = new PostFunding();
				projecFunding.setGranted(projectGranted);
				projecFunding.setGrantedId(projectGranted.getId());
				projecFunding.setAttn(attn);
				projecFunding.setDate(now);
				projecFunding.setFundingList(fundingList);
				projecFunding.setNote(note);
				projecFunding.setStatus(0);
				projecFunding.setType(subSubType);
				projecFunding.setPayee(projectGranted.getApplicantName());
				if (projectGranted.getDepartment() != null) {
					projectGranted.setDepartment(projectGranted.getDepartment());
				}
				projecFunding.setAgency(projectGranted.getUniversity());
				AgencyFunding agencyFunding = (AgencyFunding) dao
						.queryUnique(
								"select ag from AgencyFunding agf where agf.agency.id =? and agf.fundingBatch.id=?",
								projectGranted.getUniversity().getId(),
								fundingBatchId);
				projecFunding.setAgencyFunding(agencyFunding);
				projecFunding.setFee(projectGranted.getApproveFee() * rate);
				dao.add(projecFunding);
			}
		} else if (subType.equals("entrust")) {
			List<EntrustGranted> projects = getProjectsToFund(
					year, subType, projectSubtypeId, subSubType);
			for (EntrustGranted projectGranted : projects) {
				if (projectGranted.getApproveFee() == null) {
					break;
				}
				EntrustFunding projecFunding = new EntrustFunding();
				projecFunding.setGranted(projectGranted);
				projecFunding.setGrantedId(projectGranted.getId());
				projecFunding.setAttn(attn);
				projecFunding.setDate(now);
				projecFunding.setFundingList(fundingList);
				projecFunding.setNote(note);
				projecFunding.setStatus(0);
				projecFunding.setType(subSubType);
				projecFunding.setPayee(projectGranted.getApplicantName());
				if (projectGranted.getDepartment() != null) {
					projectGranted.setDepartment(projectGranted.getDepartment());
				}
				projecFunding.setAgency(projectGranted.getUniversity());
				AgencyFunding agencyFunding = (AgencyFunding) dao
						.queryUnique(
								"select ag from AgencyFunding agf where agf.agency.id =? and agf.fundingBatch.id=?",
								projectGranted.getUniversity().getId(),
								fundingBatchId);
				projecFunding.setAgencyFunding(agencyFunding);
				projecFunding.setFee(projectGranted.getApproveFee() * rate);
				dao.add(projecFunding);
			}
		} else if (subType.equals("key")) {
			List<KeyGranted> projects = getProjectsToFund(year,
					subType, projectSubtypeId, subSubType);
			for (KeyGranted projectGranted : projects) {
				if (projectGranted.getApproveFee() == null) {
					break;
				}
				KeyFunding projecFunding = new KeyFunding();
				projecFunding.setGranted(projectGranted);
				projecFunding.setGrantedId(projectGranted.getId());
				projecFunding.setAttn(attn);
				projecFunding.setDate(now);
				projecFunding.setFundingList(fundingList);
				projecFunding.setNote(note);
				projecFunding.setStatus(0);
				projecFunding.setType(subSubType);
				projecFunding.setPayee(projectGranted.getApplicantName());
				if (projectGranted.getDepartment() != null) {
					projectGranted.setDepartment(projectGranted.getDepartment());
				}
				projecFunding.setAgency(projectGranted.getUniversity());
				AgencyFunding agencyFunding = (AgencyFunding) dao
						.queryUnique(
								"select ag from AgencyFunding agf where agf.agency.id =? and agf.fundingBatch.id=?",
								projectGranted.getUniversity().getId(),
								fundingBatchId);
				projecFunding.setAgencyFunding(agencyFunding);
				projecFunding.setFee(projectGranted.getApproveFee() * rate);
				dao.add(projecFunding);
			}
		}

		return fundingList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getProjectsToFund(Integer year, String projectType,
			String projectSubtypeId, String subSubType) {
		Map parMap = new HashMap();
		parMap.put("projectYear", year);
		StringBuffer hql = new StringBuffer();
		if (projectType.equals("general")) {
			if (subSubType.equals("granted")) {
				hql.append("select pf.id from GeneralGranted gra left outer join gra.application app " +
					"left outer join gra.subtype so where app.finalAuditStatus=3 and app.finalAuditResult=2 ");
			} else if (subSubType.equals("mid")) {
				hql.append("select pf.id from GeneralMidinspection midi left join midi.granted gra " +
						"left outer join gra.application app left outer join gra.subtype so " +
						"where midi.finalAuditStatus=3 and midi.finalAuditResult=2 ");
			} else if (subSubType.equals("end")) {
				hql.append("select pf.id from GeneralEndinspection endi left join endi.granted gra " +
						"left outer join gra.application app left outer join gra.subtype so " +
						"where endi.finalAuditStatus=3 and endi.finalAuditResultEnd=2 ");
			}
		} else if (projectType.equals("entrust")) {
			if (subSubType.equals("granted")) {
				hql.append("select pf.id from EntrustGranted gra left outer join gra.application app "
						+ "left outer join gra.subtype so where app.finalAuditStatus=3 and app.finalAuditResult=2 ");
			} else if (subSubType.equals("end")) {
				hql.append("select pf.id from EntrustEndinspection endi left join endi.granted gra left outer join gra.application app "
						+ "left outer join gra.subtype so where endi.finalAuditStatus=3 and endi.finalAuditResultEnd=2 ");
			}
		} else if (projectType.equals("instp")) {
			if (subSubType.equals("granted")) {
				hql.append("select pf.id from InstpGranted gra left outer join gra.application app "
						+ "left outer join gra.subtype so where and app.finalAuditStatus=3 and app.finalAuditResult=2 ");
			} else if (subSubType.equals("mid")) {
				hql.append("select pf.id from InstpMidinspection midi left join midi.granted gra left outer join gra.application app "
						+ "left outer join gra.subtype so where midi.finalAuditStatus=3 and midi.finalAuditResult=2 ");
			} else if (subSubType.equals("end")) {
				hql.append("select pf.id from InstpEndinspection endi left join endi.granted gra left outer join gra.application app "
						+ "left outer join gra.subtype so where endi.finalAuditStatus=3 and endi.finalAuditResultEnd=2 ");
			}
		} else if (projectType.equals("key")) {
			if (subSubType.equals("granted")) {
				hql.append("select pf.id from KeyGranted gra left outer join gra.application app "
						+ "left outer join gra.subtype so app.finalAuditStatus=3 and app.finalAuditResult=2 ");
			} else if (subSubType.equals("mid")) {
				hql.append("select pf.id from KeyMidinspection midi left join midi.granted gra left outer join gra.application app "
						+ "left outer join gra.subtype so where midi.finalAuditStatus=3 and midi.finalAuditResult=2 ");
			} else if (subSubType.equals("end")) {
				hql.append("select pf.id from KeyEndinspection endi left join endi.granted gra left outer join gra.application app "
						+ "left outer join gra.subtype so where endi.finalAuditStatus=3 and endi.finalAuditResultEnd=2 ");
			}
		} else if (projectType.equals("post")) {
			if (subSubType.equals("granted")) {
				hql.append("select pf.id from PostGranted gra left outer join gra.application app "
						+ "left outer join gra.subtype so where app.finalAuditStatus=3 and app.finalAuditResult=2 ");
			} else if (subSubType.equals("end")) {
				hql.append("select pf.id from PostEndinspection endi left join endi.granted gra left outer join gra.application app "
						+ "left outer join gra.subtype so where endi.finalAuditStatus=3 and endi.finalAuditResultEnd=2 ");
			}
		}
		hql.append("and app.year =:projectYear ");
		if (projectSubtypeId != null && !projectSubtypeId.equals("")
				&& !projectSubtypeId.equals("-1")) {
			parMap.put("projectSubtype", projectSubtypeId);
			hql.append(" and so.id =:projectSubtypeId ");
		}
		hql.append("and gra.id not in (select pf.grantedId from ProjectFunding pf where pf.projectType =:projectType and pf.type =:subSubType)");
		List projects = dao.query(hql.toString(), parMap);
		return projects;
	}
	
	public FundingList addWorkFundingList(String fundingBatchId,
			String listName, String note, Integer year, String subType,
			String subSubType) {
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		int isPrincipal = loginer.getIsPrincipal();
		String attn = "";// 经办人
		if (isPrincipal == 0) {
			attn = loginer.getCurrentPersonName();
		} else if (isPrincipal == 1) {
			attn = loginer.getCurrentBelongUnitName();
		}
		FundingList fundingList = new FundingList();
		fundingList.setAttn(attn);
		fundingList.setCreateDate(new Date());
		FundingBatch fundingBatch = dao.query(FundingBatch.class,
				fundingBatchId);
		fundingList.setFundingBatch(fundingBatch);
		fundingList.setName(listName);
		fundingList.setNote(note);
		fundingList.setStatus(0);
		fundingList.setType("WorkFunding");
		fundingList.setSubType(subType);
		fundingList.setSubSubType(subSubType);
		dao.add(fundingList);
		return fundingList;
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

	public void updateAgencyFundingFee(String fundingBatchId) {
		List<Agency> agencyList = null;
		agencyList = dao
				.query("select ag from AgencyFunding agf left join agf.agency ag left join agf.fundingBatch fb where fb.id = ? order by ag.code asc",
						fundingBatchId);
		System.out.println("共" + agencyList.size() + "所收款机构");
		String hql4work = "select sum(wf.fee) from WorkFunding wf left join wf.agency ag left join wf.agencyFunding agf where ag.id =:agencyId and agf.fundingBatch.id = :fundingBatchId";
		String hql4project = "select sum(pf.fee) from ProjectFunding pf left join pf.agency ag left join pf.agencyFunding agf where ag.id =:agencyId and agf.fundingBatch.id = :fundingBatchId";
		String hql4workCount = "select wf.fee from WorkFunding wf left join wf.agency ag left join wf.agencyFunding agf where ag.id =:agencyId and agf.fundingBatch.id = :fundingBatchId";
		String hql4projectCount = "select pf.fee from ProjectFunding pf left join pf.agency ag left join pf.agencyFunding agf where ag.id =:agencyId and agf.fundingBatch.id = :fundingBatchId";
		String hql4agencyFunding = "select agf from AgencyFunding agf left join agf.fundingBatch fb left join agf.agency ag where ag.id =:agencyId and fb.id = :fundingBatchId";
		Map map = new HashMap();
		map.put("fundingBatchId", fundingBatchId);
		for (Agency agency : agencyList) {
			double agencyFee = 0;
			map.put("agencyId", agency.getId());
			List pObject = dao.query(hql4project.toString(), map);
			List wObject = dao.query(hql4work.toString(), map);
			long pInt = dao.count(hql4projectCount.toString(), map);
			long wInt = dao.count(hql4workCount.toString(), map);
			if (pObject != null && !pObject.toString().contains("null")) {
				agencyFee += new Double(pObject.get(0).toString());
			}
			if (wObject != null && !wObject.toString().contains("null")) {
				agencyFee += new Double(wObject.get(0).toString());
			}
			try {
				List asdasd = dao.query(hql4agencyFunding.toString(), map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			AgencyFunding agencyFunding = (AgencyFunding) dao.query(
					hql4agencyFunding.toString(), map).get(0);
			agencyFunding.setFee(agencyFee);
			agencyFunding.setCount((int) (pInt + wInt));
			dao.modify(agencyFunding);
			// System.out.println(agency.getName()+agency.getCode()+":"+agencyFee);
			map.remove("agencyId");
		}
	}

	public double getAgencyFee4FundingListByListName(String fundingListId,
			String fundingBatchId, String agencyId) {
		Map temp = new HashMap();
		temp.put("fundingBatchId", fundingBatchId);
		double tempFundingListFee = 0;
		Map map = new HashMap();
		map.put("fundingListId", fundingListId);
		FundingList fundingList = dao.query(FundingList.class, fundingListId);
		String fundingListType = fundingList.getType();
		if (agencyId != null && !agencyId.equals("")) {
			map.put("agencyId", agencyId);
			if (fundingListType.contains("work")) {
				Object wfFee = dao
						.query("select sum(wf.fee) from WorkFunding wf left outer join wf.agency ag left outer join wf.fundingList fl where ag.id =:agencyId and fl.id =:fundingListId ",
								map).get(0);
				if (wfFee != null) {
					tempFundingListFee = (Double) wfFee;
					// tempFundingListCount =(int)
					// dao.count("select wf from WorkFunding wf left outer join wf.agency ag left outer join wf.fundingList fl where ag.id =:agencyId and fl.id =:fundingListId",
					// map);
				}
			} else if (fundingListType.contains("project")) {
				Object pfFee = dao
						.query("select sum(pf.fee) from ProjectFunding pf left join pf.agency ag left join pf.fundingList fl where ag.id =:agencyId and fl.id =:fundingListId ",
								map).get(0);
				if (pfFee != null) {
					tempFundingListFee = (Double) pfFee;
					// tempFundingListCount = (int)
					// dao.count("select pf from ProjectFunding pf left join pf.agency ag left join pf.fundingList fl where ag.id =:agencyId and fl.id =:fundingListId ",
					// map);
				}
			}
		}
		return tempFundingListFee;
	}

	@Override
	public double getAgencyFee4FundingListByListId(String fundingListId,
			String agencyId) {
		double tempFundingListFee = 0;
		Map map = new HashMap();
		map.put("fundingListId", fundingListId);
		FundingList fundingList = dao.query(FundingList.class, fundingListId);
		String fundingListType = fundingList.getType();
		if (agencyId != null && !agencyId.equals("")) {
			map.put("agencyId", agencyId);
			if (fundingListType.contains("work")) {
				Object wfFee = dao
						.query("select sum(wf.fee) from WorkFunding wf left outer join wf.agency ag left outer join wf.fundingList fl where ag.id =:agencyId and fl.id =:fundingListId ",
								map).get(0);
				if (wfFee != null) {
					tempFundingListFee = (Double) wfFee;
				}
			} else if (fundingListType.contains("project")) {
				Object pfFee = dao
						.query("select sum(pf.fee) from ProjectFunding pf left join pf.agency ag left join pf.fundingList fl where ag.id =:agencyId and fl.id =:fundingListId ",
								map).get(0);
				if (pfFee != null) {
					tempFundingListFee = (Double) pfFee;
				}
			}
		} else {
			if (fundingListType.contains("work")) {
				Object wfFee = dao
						.query("select sum(wf.fee) from WorkFunding wf left outer join wf.fundingList fl where fl.id =:fundingListId ",
								map).get(0);
				if (wfFee != null) {
					tempFundingListFee = (Double) wfFee;
				}
			} else if (fundingListType.contains("project")) {
				Object pfFee = dao
						.query("select sum(pf.fee) from ProjectFunding pf left join pf.fundingList fl where fl.id =:fundingListId ",
								map).get(0);
				if (pfFee != null) {
					tempFundingListFee = (Double) pfFee;
				}
			}
		}
		return tempFundingListFee;
	}

	@SuppressWarnings("deprecation")
	public ByteArrayInputStream commonExportExcel(String fundingBatchId) {
		FundingBatch fundingBatch = dao.query(FundingBatch.class,
				fundingBatchId);
		List<Object[]> items = dao
				.query("select fl.type, fl.subType, fl.subSubType, fl.id from FundingList fl left join fl.fundingBatch fb where fb.id =? order by fl.type asc, fl.subType asc, fl.subSubType asc",
						fundingBatchId);

		// 新建excel
		HSSFWorkbook wb = new HSSFWorkbook();
		// 新建sheet
		HSSFSheet sheet1 = wb.createSheet();

		String header = fundingBatch.getName();// 表头
		header = (null == header) ? "" : header;
		wb.setSheetName(0, ("".equals(header)) ? "Sheet1" : header);
		sheet1.setDefaultRowHeightInPoints(22);
		sheet1.setDefaultColumnWidth((short) 22);
		// 设置页脚
		HSSFFooter footer = sheet1.getFooter();
		footer.setRight("Page " + HSSFFooter.page() + " of "
				+ HSSFFooter.numPages());
		// 设置样式 表头
		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font1 = wb.createFont();
		font1.setFontHeightInPoints((short) 20);
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style1.setFont(font1);
		style1.setBorderTop(CellStyle.BORDER_THIN);
		style1.setBorderRight(CellStyle.BORDER_THIN);
		style1.setBorderBottom(CellStyle.BORDER_THIN);
		style1.setBorderLeft(CellStyle.BORDER_THIN);
		// 合并标题行
		sheet1.addMergedRegion(new Region(0, (short) 0, (short) 0, (short) 18));
		// 设置样式 标题
		HSSFCellStyle style2 = wb.createCellStyle();
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font2 = wb.createFont();
		font2.setFontHeightInPoints((short) 13);
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style2.setFont(font2);
		style2.setBorderTop(CellStyle.BORDER_THIN);
		style2.setBorderRight(CellStyle.BORDER_THIN);
		style2.setBorderBottom(CellStyle.BORDER_THIN);
		style2.setBorderLeft(CellStyle.BORDER_THIN);
		// 设置样式 正文
		HSSFCellStyle style4 = wb.createCellStyle();
		style4.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style4.setWrapText(true);
		style4.setBorderTop(CellStyle.BORDER_THIN);
		style4.setBorderRight(CellStyle.BORDER_THIN);
		style4.setBorderBottom(CellStyle.BORDER_THIN);
		style4.setBorderLeft(CellStyle.BORDER_THIN);

		// 第一行表头
		HSSFRow row0 = sheet1.createRow(0);
		row0.setHeightInPoints(40);
		HSSFCell cell = null;
		cell = row0.createCell((short) 0);
		cell.setCellValue(header);
		cell.setCellStyle(style1);

		// 第二行（项目类型或者工作经费）
		HSSFRow row1 = sheet1.createRow(1);
		row1.setHeightInPoints(22);

		cell = row1.createCell(0);
		cell.setCellValue("序号");
		cell.setCellStyle(style2);
		cell = row1.createCell(1);
		cell.setCellValue("单位");
		cell.setCellStyle(style2);
		cell = row1.createCell(2);
		cell.setCellValue("金额");
		cell.setCellStyle(style2);

		// 第三行
		HSSFRow row2 = sheet1.createRow(2);
		row2.setHeightInPoints(22);
		cell = row2.createCell(0);
		cell.setCellValue("序号");
		cell.setCellStyle(style2);
		cell = row2.createCell(1);
		cell.setCellValue("单位");
		cell.setCellStyle(style2);
		cell = row2.createCell(2);
		cell.setCellValue("金额");
		cell.setCellStyle(style2);

		// 第四行
		HSSFRow row3 = sheet1.createRow(3);
		row3.setHeightInPoints(20);
		cell = row3.createCell(0);
		cell.setCellValue("序号");
		cell.setCellStyle(style2);
		cell = row3.createCell(1);
		cell.setCellValue("单位");
		cell.setCellStyle(style2);
		cell = row3.createCell(2);
		cell.setCellValue("金额");
		cell.setCellStyle(style2);

		sheet1.addMergedRegion(new Region((short) 1, (short) 0, (short) 4,
				(short) 0));
		sheet1.addMergedRegion(new Region((short) 1, (short) 1, (short) 3,
				(short) 1));
		sheet1.addMergedRegion(new Region((short) 1, (short) 2, (short) 3,
				(short) 2));

		int lenp = 0;
		int lenw = 0;
		for (Object[] item : items) {
			if (item[0].equals("project")) {
				lenp++;
			} else if (item[0].equals("work")) {
				lenw++;
			}
		}
		String[] pcolumesAbbr = new String[lenp];
		String[] pcolumes = new String[lenp];
		for (int i = 0; i < lenp; i++) {
			pcolumesAbbr[i] = this.convertProjectType4excel(
					items.get(i)[1].toString(), true);
			pcolumes[i] = convertProjectType4excel(items.get(i)[1].toString(),
					false);
		}
		// 第二行标题

		for (int i = 0; i < lenp; i++) {
			cell = row1.createCell((short) i + 3);
			cell.setCellValue(pcolumesAbbr[i]);
			cell.setCellStyle(style2);
			cell = row2.createCell((short) i + 3);
			cell.setCellValue(pcolumes[i]);
			cell.setCellStyle(style2);
			cell = row3.createCell((short) i + 3);
			cell.setCellValue(convertFundingListSubSubType4project(items.get(i)[2]
					.toString()));
			cell.setCellStyle(style2);
		}
		int repeatLen1 = 0;
		for (int i = 1; i < lenp; i++) {
			if (!pcolumesAbbr[i - 1].equals(pcolumesAbbr[i])) {
				if (pcolumesAbbr[i - 1].equals("专项项目")) {
					sheet1.addMergedRegion(new Region(1,
							(short) (i + 2 - repeatLen1), (short) 1,
							(short) (i + 2)));
				} else {
					sheet1.addMergedRegion(new Region(1,
							(short) (i + 2 - repeatLen1), (short) 2,
							(short) (i + 2)));
				}
				repeatLen1 = 0;
			} else {
				repeatLen1++;
			}
			if (i == (lenp - 1)) {
				if (pcolumesAbbr[lenp - 1].equals("专项项目")) {
					sheet1.addMergedRegion(new Region(1,
							(short) (lenp + 2 - repeatLen1), (short) 1,
							(short) (lenp + 2)));
				} else {
					sheet1.addMergedRegion(new Region(1,
							(short) (lenp + 2 - repeatLen1), (short) 2,
							(short) (lenp + 2)));
				}
			}
		}

		for (int j = lenp, len = lenp + lenw; j < len; j++) {
			cell = row1.createCell((short) j + 3);
			cell.setCellValue(convertFundingListType(items.get(j)[0].toString()));
			cell.setCellStyle(style2);
			cell = row2.createCell((short) j + 3);
			cell.setCellValue(convertFundingListType(items.get(j)[0].toString()));
			cell.setCellStyle(style2);
			cell = row3.createCell((short) j + 3);
			cell.setCellValue(convertFundingListSubType4work(items.get(j)[1]
					.toString()));
			cell.setCellStyle(style2);
		}
		// new Region(rowFrom, colFrom, rowTo, colTo)
		// 合并工作经费
		sheet1.addMergedRegion(new Region(1, (short) (lenp + 3), (short) 2,
				(short) (lenp + lenw + 2)));
		int index = 0;
		List dataList = new ArrayList();
		double[] eachListFee = new double[18];// 每个清单的总额

		List<AgencyFunding> agencyFundings = dao
				.query("select agf from AgencyFunding agf left join agf.fundingBatch fb where fb.id =? order by agf.agencyName asc",
						fundingBatchId);
		for (AgencyFunding agencyFunding : agencyFundings) {
			// System.out.println(index);
			index++;
			Object[] item = new Object[20];
			item[0] = index;
			item[1] = agencyFunding.getAgencyName();
			item[2] = agencyFunding.getFee();
			eachListFee[0] += agencyFunding.getFee();
			for (int i = 0; i < items.size(); i++) {
				double tempFee = getAgencyFee4FundingListByListName(
						items.get(i)[3].toString(), fundingBatchId,
						agencyFunding.getAgency().getId());
				item[i + 3] = tempFee;
				eachListFee[i + 1] += tempFee;
			}
			dataList.add(item);
		}
		Object[] totalItem = new Object[20];
		totalItem[0] = "";
		totalItem[1] = "合计";
		for (int i = 0; i < totalItem.length - 2; i++) {
			totalItem[i + 2] = eachListFee[i];
		}
		dataList.add(0, totalItem);
		// 第三行正文
		for (int i = 0, size = dataList.size(); i < size; i++) {
			Object[] o = (Object[]) dataList.get(i);
			HSSFRow row4 = sheet1.createRow((short) (i + 4)); // 第三行开始填充数据
			// row3.setHeight((short)500);
			for (int j = 0, len = o.length; j < len; j++) {
				cell = row4.createCell((short) j);
				// 如果是数字类型的，则设置单元格类型位数字,可选择求和
				if (o[j] instanceof Integer) {
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					Integer intValue = (Integer) o[j];
					cell.setCellValue(intValue);
				} else if (o[j] instanceof Long) {
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					long longValue = (Long) o[j];
					cell.setCellValue(longValue);
				} else if (o[j] instanceof Float) {
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					float floatValue = (Float) o[j];
					cell.setCellValue(floatValue);
				} else if (o[j] instanceof Double) {
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					double doubleValue = (Double) o[j];
					cell.setCellValue(doubleValue);
				} else {
					// cell.setCellValue(o[j] == null ? "" : o[j].toString());
					cell.setCellValue(new HSSFRichTextString(o[j] == null ? ""
							: o[j].toString()));// 强制换行，用于历次拨款（万元）的分行显示

				}
				cell.setCellStyle(style4);

			}
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			wb.write(bos);
			bos.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		byte[] content = bos.toByteArray();
		ByteArrayInputStream bis = null;
		try {
			bis = new ByteArrayInputStream(content);
			bis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bis;
	}

	public String convertProjectType4excel(String type, boolean isAbbr) {
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
		} else {
			if (type.startsWith("special")) {
				if (isAbbr) {
					return "专项项目";
				} else {
					if (type.equals("special:incorrupt")) {
						return "教育廉政";
					} else if (type.equals("special:ideologyTeacher")) {
						return "择优资助";
					} else if (type.equals("special:faith")) {
						return "学风建设";
					} else if (type.equals("special:ideologyCourse")) {
						return "思政课";
					} else if (type.equals("special:ideologyKey")) {
						return "重点难点";
					} else if (type.equals("special:ideologyWork")) {
						return "思政工作";
					} else if (type.equals("special:socialism")) {
						return "中特";
					} else if (type.equals("special:engTalent")) {
						return "工程人才";
					} else if (type.equals("special:marx")) {
						return "马三化";
					} else if (type.equals("special:comunication")) {
						return "中外交流";
					}
				}

			}
		}
		return type;
	}

	public Map getCountAndFee4FundingList(String fundingListId, String agencyId) {
		Map data = new HashMap();
		double tempFundingListFee = 0;
		int tempFundingListCount = 0;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("fundingListId", fundingListId);
		FundingList fundingList = dao.query(FundingList.class, fundingListId);
		String fundingListType = fundingList.getType();
		if (agencyId != null && !agencyId.equals("")) {
			map.put("agencyId", agencyId);
			if (fundingListType.contains("work")) {
				Object wfFee = dao
						.query("select sum(wf.fee) from WorkFunding wf left outer join wf.agency ag left outer join wf.fundingList fl where ag.id =:agencyId and fl.id =:fundingListId ",
								map).get(0);
				if (wfFee != null) {
					tempFundingListFee = (Double) wfFee;
					tempFundingListCount = (int) dao
							.count("select wf from WorkFunding wf left outer join wf.agency ag left outer join wf.fundingList fl where ag.id =:agencyId and fl.id =:fundingListId",
									map);
				}
			} else if (fundingListType.contains("project")) {
				Object pfFee = dao
						.query("select sum(pf.fee) from ProjectFunding pf left join pf.agency ag left join pf.fundingList fl where ag.id =:agencyId and fl.id =:fundingListId ",
								map).get(0);
				if (pfFee != null) {
					tempFundingListFee = (Double) pfFee;
					tempFundingListCount = (int) dao
							.count("select pf from ProjectFunding pf left join pf.agency ag left join pf.fundingList fl where ag.id =:agencyId and fl.id =:fundingListId ",
									map);
				}
			}
		} else {
			if (fundingListType.contains("work")) {
				Object wfFee = dao
						.query("select sum(wf.fee) from WorkFunding wf left outer join wf.fundingList fl where fl.id =:fundingListId ",
								map).get(0);
				if (wfFee != null) {
					tempFundingListFee = (Double) wfFee;
					tempFundingListCount = (int) dao
							.count("select wf from WorkFunding wf left outer join wf.fundingList fl where fl.id =:fundingListId",
									map);
				}
			} else if (fundingListType.contains("project")) {
				Object pfFee = dao
						.query("select sum(pf.fee) from ProjectFunding pf left join pf.fundingList fl where fl.id =:fundingListId ",
								map).get(0);
				if (pfFee != null) {
					tempFundingListFee = (Double) pfFee;
					tempFundingListCount = (int) dao
							.count("select pf from ProjectFunding pf left join pf.fundingList fl where fl.id =:fundingListId ",
									map);
				}
			}
		}
		data.put("count", tempFundingListCount);
		data.put("fee", tempFundingListFee);
		return data;
	}

	public void deleteProjectFundingList(String id) {
		FundingList fundList = dao.query(FundingList.class, id);
		String hql = null;
		Map parMap = new HashMap();
		parMap.put("id", id);
		if (fundList.getStatus() == 0) {
			hql = "from ProjectFunding pf where pf.fundingList.id = :id ";
			List<ProjectFunding> projectFundings = dao.query(hql, parMap);
			for (ProjectFunding projectFunding : projectFundings) {
				dao.delete(projectFunding);
			}
			dao.delete(fundList);
		}
	}
	public void deleteWorkFundingList(String id) {
		FundingList fundList = dao.query(FundingList.class, id);
		String hql = null;
		Map parMap = new HashMap();
		parMap.put("id", id);
		if (fundList.getStatus() == 0) {
			hql = "from WorkFunding pf where pf.fundingList.id = :id ";
			List<WorkFunding> workFundings = dao.query(hql, parMap);
			for (WorkFunding workFunding : workFundings) {
				dao.delete(workFunding);
			}
			dao.delete(fundList);
		}
	}

	public FundingList auditProjectFundingList(String fundingListId, String attn) {
		FundingList fundList = dao.query(FundingList.class, fundingListId);
		String hql = null;
		Map parMap = new HashMap();
		parMap.put("id", fundList.getId());
		if (fundList.getStatus() == 0) {
			hql = "from ProjectFunding pf where pf.fundingList.id = :id ";
			List<ProjectFunding> projectFundings = dao.query(hql, parMap);
			for (ProjectFunding projectFunding : projectFundings) {
				projectFunding.setStatus(1);
				projectFunding.setDate(new Date());
				projectFunding.setAttn(attn);
				dao.modify(projectFunding);
			}
			fundList.setStatus(1);
			dao.modify(fundList);
		}
		return fundList;
	}

	public void addEmailToUnit(String id, LoginInfo loginer) {
		Map session = ActionContext.getContext().getSession();
		FundingList fundList = dao.query(FundingList.class, id);
		if (session.containsKey("univName2Excel")) {
			Map<String, List<Object[]>> univName2Excel = (Map<String, List<Object[]>>) session
					.get("univName2Excel");
			for (Entry<String, List<Object[]>> entry : univName2Excel
					.entrySet()) {
				String univName = entry.getKey();
				if (univName.contains("[")) {// 去除可能存在的中括号
					univName = univName.replace("[", "").trim();
				}
				if (univName.contains("]")) {
					univName = univName.replace("]", "").trim();
				}
				// univName = univName.replaceAll("[", "").trim();
				// univName = univName.replaceAll("]", "").trim();
				List<Object[]> univProjects = entry.getValue();
				List<Object[]> univProjects4xls = new ArrayList<Object[]>();// 用于存放学校所对应的项目
				int index = 0;
				String sendTo = null;
				for (Object[] objs : univProjects) {
					index += 1;
					sendTo = objs[8].toString();
					Object[] univProject = new Object[9];
					univProject[0] = index;
					for (int i = 0; i < 8; i++) {
						univProject[i + 1] = objs[i];
					}
					univProjects4xls.add(univProject);
				}

				Mail mail = new Mail();
				mail.setSendTo(sendTo);
				// mail.setSendTo("xn1893@qq.com");
				StringBuffer subject = new StringBuffer();
				subject.append(fundList.getYear() + "年度");
				if (fundList.getSubSubType().equals("general")) {
					subject.append("一般项目");
				} else if (fundList.getSubSubType().equals("instp")) {
					subject.append("基地项目");
				} else if (fundList.getSubSubType().equals("post")) {
					subject.append("后期资助项目");
				} else if (fundList.getSubSubType().equals("entrust")) {
					subject.append("委托应急课题");
				} else if (fundList.getSubSubType().equals("key")) {
					subject.append("重大攻关项目");
				}
				if (fundList.getSubSubType().equals("granted")) {
					subject.append("立项拨款");
				} else if (fundList.getSubSubType().equals("mid")) {
					subject.append("中检拨款");
				} else if (fundList.getSubSubType().equals("end")) {
					subject.append("结项拨款");
				}
				mail.setSubject("[SMDB] " + "关于下发" + univName
						+ subject.toString() + "的通知");
				mail.setReplyTo("serv@csdc.info");// 认证地址
				mail.setBody("123");
				mail.setIsHtml(1);
				mail.setCreateDate(new Date());
				mail.setFinishDate(null);
				mail.setSendTimes(0);
				mail.setStatus(0);
				// 设置邮件发送账号及发送者属性
				String accountBelong = "";// 账号所属名称
				if (loginer != null) {// 当前处于登录状态，则从登录对象中获取账号及账号所属者信息
					mail.setAccount(loginer.getAccount());// 设置发送账号

					// 从loginer中获取发送者名称信息
					if (loginer.getCurrentBelongUnitName() != null) {// 所属机构信息存在，则读取机构名称
						accountBelong = loginer.getCurrentBelongUnitName();
					}

					if (loginer.getCurrentPersonName() != null) {// 所属人员信息存在，则读取人员名称
						accountBelong = loginer.getCurrentPersonName();
					}
					mail.setAccountBelong(accountBelong);// 设置发送者名称
				}

				dao.add(mail);
				String header = univName + subject.toString() + "明细";
				String[] title = { "序号", "项目名称", "批准号", "负责人", "学校名称", "项目子类",
						"项目年度", "批准经费(万元)", "拨款金额(万元)" };
				String realPath = ApplicationContainer.sc.getRealPath("");
				String filepath = (String) ApplicationContainer.sc
						.getAttribute("MailFileUploadPath");
				realPath = realPath.replace('\\', '/');
				HSSFExport.createExcel(univProjects4xls, header, title,
						realPath + filepath + "/" + univName + ".xls");

				File xlsFile = new File(realPath + filepath + "/" + univName
						+ ".xls");
				String xlsPath = mailService.renameFile(mail.getId(), xlsFile);

				List<String> attachments = new ArrayList<String>();
				List<String> attachmentNames = new ArrayList<String>();
				attachments.add(xlsPath);
				attachmentNames.add(univName + subject.toString() + "清单.xls");
				mail.setAttachment(StringTool.joinString(
						attachments.toArray(new String[0]), "; "));
				mail.setAttachmentName(StringTool.joinString(
						attachmentNames.toArray(new String[0]), "; "));
				dao.modify(mail);
			}
		}
	}

}