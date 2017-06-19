package csdc.action.statistic.review;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.action.BaseAction;
import csdc.dao.SqlBaseDao;
import csdc.tool.ApplicationContainer;
import csdc.tool.DisciplineMatchLevel;
import csdc.tool.bean.Level;

/**
 * 学科匹配统计
 * @author jtf
 *
 */
public class DisciplineStatisticAction extends BaseAction {
	private static final String HQLGeneral = "select p.c_discipline as c1, e.c_discipline  as c2 from t_Expert e,t_project_application p,T_Project_Application_Review r where p.c_type = 'general' and r.c_type = 'general' and e.c_id=r.c_reviewer_id and p.c_id=r.c_project_id and p.c_year=:defaultYear ";
	private static final String HQLINSTITUTE = "select p.c_discipline as c1, e.c_discipline  as c2 from t_Expert e,t_project_application p,T_Project_Application_Review r where p.c_type = 'instp' and r.c_type = 'instp'and e.c_id=r.c_reviewer_id and p.c_id=r.c_project_id and p.c_year=:defaultYear";
	private static final int dim = 6;
	@Autowired
	private SqlBaseDao sqlDao;		
	
	/**
	 * 构造6行6列的矩阵
	 *  matrix[0,0]存放学科3退3，专家3退3的结果
	 *  
	 *  matrix[1,1]存放学科2退2，专家2退2的结果
	 *  matrix[3,3]存放学科3退2，专家3退2的结果
	 *  matrix[3,1]存放学科3退2，专家2退2的结果
	 *  matrix[1,3]存放学科2退2，专家3退2的结果
	 *  
	 *  matrix[5,5]存放学科3退1，专家3退1的结果
	 *  matrix[5,4]存放学科3退1，专家2退1的结果
	 *  matrix[5,2]存放学科3退1，专家1退1的结果
	 *  matrix[4,5]存放学科2退1，专家3退1的结果
	 *  matrix[4,4]存放学科2退1，专家2退1的结果
	 *  matrix[4,2]存放学科2退1，专家1退1的结果
	 *  matrix[2,5]存放学科1退1，专家3退1的结果
	 *  matrix[2,4]存放学科1退1，专家2退1的结果
	 *  matrix[2,2]存放学科1退1，专家1退1的结果
	 *  
	 *  matrix[0,1] 失配的记录
	 *  matrix[0,2] 总记录数
	 */
	private int [][] matrix = new int[dim][dim];//构造6行6列的矩阵
	private Map matrixMap = new HashMap(); //matrix对应的数据存到map中方便计算
	private Integer listType;//1表示一般项目，2表示基地项目
	private Map analyzeMap = new HashMap(); //数据分析结果
	/**
	 * 学科匹配度统计结果
	 * @return
	 */
	public String disciplineMatrix(){
		String sql = null;
		int lost = 0 ;//不在匹配矩阵中的情况
		if(listType.equals(1)){
			sql=HQLGeneral;
		}else {
			sql=HQLINSTITUTE;
		}
		String  key = String.valueOf(session.get("defaultYear"))+"_"+String.valueOf(listType);
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				matrix[i][j]=0; 
			}
		}
		Map paraMap = new HashMap();
		paraMap.put("defaultYear", session.get("defaultYear"));
		List<Object[]> result = sqlDao.query(sql, paraMap);
		for (int i = 0; i < result.size(); i++) {
			Level level=new Level(100, 100);
			try{
				level=DisciplineMatchLevel.projectWithdrawLevel(((result.get(i))[0]).toString(), ((result.get(i))[1]).toString());
			}catch (Exception e) {
				e.printStackTrace();
				level=new Level(100, 100);
			}
			if(level.pro<100&&level.exp<100){
				(matrix[level.pro][level.exp])++;
			}else{
				//适配是值，专家和项目的学科代码没有共同部分
				System.out.println("Lost:"+((result.get(i))[0]).toString()+"---------;"+((result.get(i))[1]).toString());
				lost++;
			}
		}
		matrix[0][1]=lost;
		matrix[0][2]=result.size();
		putArrayIntoMap();
		computeAnalyzeMap();
		return SUCCESS;
	}
	
	private void putArrayIntoMap(){
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				matrixMap.put(String.valueOf(i)+String.valueOf(j), matrix[i][j]);
			}
		}
	}
	
	private void computeAnalyzeMap(){
		NumberFormat nf = NumberFormat.getPercentInstance();
		int total = 0,complete = 0,withdraw=0;
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				total += (Integer) matrixMap.get(String.valueOf(i)+String.valueOf(j));
			}
		}
		total = total -(Integer)matrixMap.get("01")- (Integer)matrixMap.get("02");
		//总数
		complete = (Integer)matrixMap.get("00")+(Integer)matrixMap.get("11") + (Integer)matrixMap.get("22");
		withdraw = total -complete;
		analyzeMap.put("total", total); //总记录
		analyzeMap.put("complete",complete);//匹配个数
		analyzeMap.put("withdraw",withdraw);//退避个数
		if(total>0){
			analyzeMap.put("complete_per",nf.format(complete/(1.0*total))); //匹配度
			analyzeMap.put("withdraw_per",nf.format(withdraw/(1.0*total))); //退避度
		}else {
			analyzeMap.put("complete_per",nf.format(0.0));
			analyzeMap.put("withdraw_per",nf.format(0.0));
		}
	}
	@Override
	public String pageName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] column() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String dateFormat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] simpleSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] advSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String pageBufferId() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map getMatrixMap() {
		return matrixMap;
	}

	public void setMatrixMap(Map matrixMap) {
		this.matrixMap = matrixMap;
	}

	public Integer getListType() {
		return listType;
	}

	public void setListType(Integer listType) {
		this.listType = listType;
	}

	public Map getAnalyzeMap() {
		return analyzeMap;
	}

	public void setAnalyzeMap(Map analyzeMap) {
		this.analyzeMap = analyzeMap;
	}
	
}
