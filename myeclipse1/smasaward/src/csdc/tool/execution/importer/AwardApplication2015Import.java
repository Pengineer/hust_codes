package csdc.tool.execution.importer;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.AwardApplication;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.reader.PoiExcelReader;

/**
 * 附件：《20150612_第七届成果奖申报名单（专家建议名单）_评价中心报社科司_修正导入.xls》
 * @author huangjun
 * 
 */
public class AwardApplication2015Import extends Importer{

	private PoiExcelReader reader;
	
	/**
	 * reader重置
	 * @throws Exception
	 */
	private void resetReader() throws Exception {
		reader.readSheet(0);
	}
	
	@Override
	public void work() throws Exception {
		importData();
	}
	
	private void importData() throws Exception {
		resetReader();
		SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd");
		
		while (next(reader)) {
			if (B.length() == 0 || B == null) {
				break;
		    }
			System.out.println(reader.getCurrentRowIndex() + " / " + reader.getRowNumber());
			AwardApplication awardApplication = new AwardApplication();
			if(B != null && !"".equals(B.trim())){
				awardApplication.setNumber(B.trim());
			}
			if(C != null && !"".equals(C.trim())){
				awardApplication.setShelfNumber(C.trim());
			}
			if(F != null && !"".equals(F.trim())){
				awardApplication.setUnitCode(F.trim());
			}
			if(L != null && !"".equals(L.trim())){
				awardApplication.setGroup(L.trim());
			}
			if(M != null && !"".equals(M.trim())){
				awardApplication.setAwardType(M.trim());
			}
			if(N != null && !"".equals(N.trim())){
				awardApplication.setProductName(N.trim());
			}
			if(O != null && !"".equals(O.trim())){
				awardApplication.setProductType(O.trim());
			}
			if(P != null && !"".equals(P.trim())){
				awardApplication.setBookType(P.trim());
			}
			if(Q != null && !"".equals(Q.trim())){
				awardApplication.setOriginalLanguage(Q.trim());
			}
			if(R != null && !"".equals(R.trim())){
				awardApplication.setProductLanguage(R.trim());
			}
			if(S != null && !"".equals(S.trim())){
				awardApplication.setDisciplinePrimitive(S.trim());
			}
			if(U != null && !"".equals(U.trim())){
				awardApplication.setDiscipline(U.trim());
			}
			
			//TODO
			awardApplication.setDisciplineCode(getDisciplineCode(V, W, X));
			
			if(Y != null && !"".equals(Y.trim())){
				awardApplication.setPublishUnit(Y.trim());
			}
			
			//TODO
			//awardApplication.setPublishDate();
			
			if(AA != null && !"".equals(AA.trim())){
				awardApplication.setJournalNumber(AA);
			}
			
			if(AB != null && !"".equals(AB.trim())){
				awardApplication.setWordNumber( Long.parseLong(AB.trim().replaceAll("千字", "")));
			}
			if(AC != null && !"".equals(AC.trim())){
				awardApplication.setApplicantName(AC.trim());
			}
			if(AD != null && !"".equals(AD.trim())){
				awardApplication.setFirstAuthor(AD.trim());
			}
			if(AE != null && !"".equals(AE.trim())){
				awardApplication.setGender(AE.trim());
			}
			if(AF != null && !"".equals(AF.trim())){
				awardApplication.setBirthday(df.parse(AF.trim()));
			}
			if(AG != null && !"".equals(AG.trim())){
				awardApplication.setPosition(AG.trim());
			}
			if(AH != null && !"".equals(AH.trim())){
				awardApplication.setSpecialistTitle(AH.trim());
			}
			if(AI != null && !"".equals(AI.trim())){
				awardApplication.setIsUnitLeader( AI.equals("是") ? 1 : 0 );
			}
			if(AJ != null && !"".equals(AJ.trim())){
				awardApplication.setIdcardType(AJ.trim());
			}
			if(AK != null && !"".equals(AK.trim())){
				awardApplication.setIdcardNumber(AK.trim());
			}
			if(AL != null && !"".equals(AL.trim())){
				awardApplication.setDivisionName(AL.trim());
			}
			if(AM != null && !"".equals(AM.trim())){
				awardApplication.setNationality(AM.trim());
			}
			if(AN != null && !"".equals(AN.trim())){
				awardApplication.setHumanRelationship(AN.trim());
			}
			if(AO != null && !"".equals(AO.trim())){
				awardApplication.setIsFirstAuthor(AO.trim().equals("是") ? 1 : 0);
			}
			if(AP != null && !"".equals(AP.trim())){
				if(AP.equals("团队")){
					AP = "团体";
				}
				awardApplication.setApplicationType(AP.trim());
			}
			if(AQ.contains("-") || AQ == null || "".equals(AQ.trim())){
				awardApplication.setOfficePhone(AQ + AR);
			}else{
				awardApplication.setOfficePhone(AQ + "-" + AR);
			}
			if(AS != null && !"".equals(AS.trim())){
				awardApplication.setHomePhone(AS.trim());
			}
			if(AT != null && !"".equals(AT.trim())){
				awardApplication.setMobilePhone(AT.trim());
			}
			if(AU != null && !"".equals(AU.trim())){
				awardApplication.setEmail(AU.trim());
			}
			if(AV != null && !"".equals(AV.trim())){
				awardApplication.setAddress(AV.trim());
			}
			if(AW != null && !"".equals(AW.trim())){
				awardApplication.setAuthors(AW.replaceAll(",", "; "));
			}
			if(AX != null && !"".equals(AX.trim())){
				awardApplication.setIsPassed(AX.equals("合格") ? 1 : 0);
			}
			if(AZ != null && !"".equals(AZ.trim())){
				awardApplication.setNote(AZ.trim());
			}
			if(BB != null && !"".equals(BB.trim())){
				awardApplication.setIsTracked(BB.equals("否") ? 0 : 1);
			}
			if(BD != null && !"".equals(BD.trim())){
				awardApplication.setAuditOpinion(BD.trim());
			}
			awardApplication.setCreateMode(2);
			awardApplication.setCreateDate(new Date());
			awardApplication.setIsReviewable(1);
			
			dao.add(awardApplication);
		}
	}
	
	public String getDisciplineCode(String subject1, String subject2, String subject3) {
		//观察数据：subject1有两个的，subject2和subject3都为空；subject2,subject3不为空的都只有一个；
		
		//有两个空
		if((subject2 == null || "".equals(subject2)) && (subject3 ==null || "".equals(subject3))) {
			return subject1;
		} else if((subject1 == null || "".equals(subject1)) && (subject3 ==null || "".equals(subject3))) {
			return subject2;
		} else if((subject2 == null || "".equals(subject2)) && (subject1 ==null || "".equals(subject1))) {
			return subject3;
		}
		
		//有一个空
		if(subject1 == null || "".equals(subject1)) {
			return subject2;     //观察数据所得
		} else if (subject2 == null || "".equals(subject2)) {
			return subject1;     //观察数据所得
		} else if (subject3 ==null || "".equals(subject3)) {
			return subject1;     //观察数据所得
		}
		
		//均不为空
		return subject1;         //观察数据所得
	}
	
	public AwardApplication2015Import(){
	}
	
	public AwardApplication2015Import(String file) {
		reader = new PoiExcelReader(file);
	}
	public static void main(String[] args) {
		String str1 = "123/dsa".substring(0, "123/dsa".indexOf("/"));
		System.out.println(str1);
	}
}
