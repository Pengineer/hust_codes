package csdc.service;

import java.util.HashMap;
import java.util.Map;

public interface IMobileProjectService extends IProjectService{
	
	/**
	 * 获取申报初级检索的Hql语句和Map参数
	 * @param hql		最终查询语句
	 * @param parMap	最终参数
	 * @param hqlDiff4Manage	管理人员差异部分
	 * @param hqlDiff4Research	研究人员差异部分
	 * @param session	session
	 * @param keyword	关键字
	 * @return
	 */
	public void getSimpleSearchHqlAndMapOfApp(StringBuffer hql, HashMap parMap, String hqlDiff4Manage, String hqlDiff4Research, Map session, String keyword);
	
	/**
	 * 获取立项初级检索的Hql语句和Map参数
	 * @param hql		最终查询语句
	 * @param parMap	最终参数
	 * @param hqlDiff4Manage	管理人员差异部分
	 * @param hqlDiff4Research	研究人员差异部分
	 * @param session	session
	 * @param keyword	关键字
	 * @return
	 */
	public void getSimpleSearchHqlAndMapOfGra(StringBuffer hql, HashMap parMap, String hqlDiff4Manage, String hqlDiff4Research, Map session, String keyword);

	/**
	 * 获取年检初级检索的Hql语句和Map参数
	 * @param hql		最终查询语句
	 * @param parMap	最终参数
	 * @param hqlDiff4Manage	管理人员差异部分
	 * @param hqlDiff4Research	研究人员差异部分
	 * @param session	session
	 * @param keyword	关键字
	 * @return
	 */
	public void getSimpleSearchHqlAndMapOfAnn(StringBuffer hql, HashMap parMap, String hqlDiff4Manage, String hqlDiff4Research, Map session, String keyword);

	/**
	 * 获取中检初级检索的Hql语句和Map参数
	 * @param hql		最终查询语句
	 * @param parMap	最终参数
	 * @param hqlDiff4Manage	管理人员差异部分
	 * @param hqlDiff4Research	研究人员差异部分
	 * @param session	session
	 * @param keyword	关键字
	 * @return
	 */
	public void getSimpleSearchHqlAndMapOfMid(StringBuffer hql, HashMap parMap, String hqlDiff4Manage, String hqlDiff4Research, Map session, String keyword);
	
	/**
	 * 获取结项初级检索的Hql语句和Map参数
	 * @param hql		最终查询语句
	 * @param parMap	最终参数
	 * @param hqlDiff4Manage	管理人员差异部分
	 * @param hqlDiff4Research	研究人员差异部分
	 * @param session	session
	 * @param keyword	关键字
	 * @return
	 */
	public void getSimpleSearchHqlAndMapOfEnd(StringBuffer hql, HashMap parMap, String hqlDiff4Manage, String hqlDiff4Research, Map session, String keyword);
	
	/**
	 * 获取变更初级检索的Hql语句和Map参数
	 * @param hql		最终查询语句
	 * @param parMap	最终参数
	 * @param hqlSelect	select公共部分
	 * @param hqlFrom	from公共部分
	 * @param hqlDiff4Manage	管理人员差异部分
	 * @param hqlDiff4Research	研究人员差异部分
	 * @param session	session
	 * @param keyword	关键字
	 */
	public void getSimpleSearchHqlAndMapOfVar(StringBuffer hql, HashMap parMap, String hqlSelect, String hqlFrom, String hqlDiff4Manage, String hqlDiff4Research, Map session, String keyword);
	
	/**
	 * 获取申报高级检索的Hql语句和Map参数
	 * @param hql		最终查询语句
	 * @param parMap	最终参数
	 * @param hqlDiff4Manage	管理人员差异部分
	 * @param hqlDiff4Research	研究人员差异部分
	 * @param session	session
	 * @param projName	高级检索项目名称
	 * @param projSubType	高级检索项目子类
	 * @param projDirector	高级检索申请人/负责人
	 * @param projUniversity	高级检索项目所属学校
	 * @return
	 */
	public void getAdvSearchHqlAndMapOfApp(StringBuffer hql, HashMap parMap, String hqlDiff4Manage, String hqlDiff4Research, Map session, String projName, String projSubType, String projDirector, String projUniversity);
	
	/**
	 * 获取立项高级检索的Hql语句和Map参数
	 * @param hql		最终查询语句
	 * @param parMap	最终参数
	 * @param hqlDiff4Manage	管理人员差异部分
	 * @param hqlDiff4Research	研究人员差异部分
	 * @param session	session
	 * @param projName	高级检索项目名称
	 * @param projSubType	高级检索项目子类
	 * @param projDirector	高级检索申请人/负责人
	 * @param projUniversity	高级检索项目所属学校
	 * @return
	 */
	public void getAdvSearchHqlAndMapOfGra(StringBuffer hql, HashMap parMap, String hqlDiff4Manage, String hqlDiff4Research, Map session, String projName, String projSubType, String projDirector, String projUniversity);
	
	/**
	 * 获取年检高级检索的Hql语句和Map参数
	 * @param hql		最终查询语句
	 * @param parMap	最终参数
	 * @param hqlDiff4Manage	管理人员差异部分
	 * @param hqlDiff4Research	研究人员差异部分
	 * @param session	session
	 * @param projName	高级检索项目名称
	 * @param projSubType	高级检索项目子类
	 * @param projDirector	高级检索申请人/负责人
	 * @param projUniversity	高级检索项目所属学校
	 * @return
	 */
	public void getAdvSearchHqlAndMapOfAnn(StringBuffer hql, HashMap parMap, String hqlDiff4Manage, String hqlDiff4Research, Map session, String projName, String projSubType, String projDirector, String projUniversity);

	/**
	 * 获取中检高级检索的Hql语句和Map参数
	 * @param hql		最终查询语句
	 * @param parMap	最终参数
	 * @param hqlDiff4Manage	管理人员差异部分
	 * @param hqlDiff4Research	研究人员差异部分
	 * @param session	session
	 * @param projName	高级检索项目名称
	 * @param projSubType	高级检索项目子类
	 * @param projDirector	高级检索申请人/负责人
	 * @param projUniversity	高级检索项目所属学校
	 * @return
	 */
	public void getAdvSearchHqlAndMapOfMid(StringBuffer hql, HashMap parMap, String hqlDiff4Manage, String hqlDiff4Research, Map session, String projName, String projSubType, String projDirector, String projUniversity);
	
	/**
	 * 获取结项高级检索的Hql语句和Map参数
	 * @param hql		最终查询语句
	 * @param parMap	最终参数
	 * @param hqlDiff4Manage	管理人员差异部分
	 * @param hqlDiff4Research	研究人员差异部分
	 * @param session	session
	 * @param projName	高级检索项目名称
	 * @param projSubType	高级检索项目子类
	 * @param projDirector	高级检索申请人/负责人
	 * @param projUniversity	高级检索项目所属学校
	 * @return
	 */
	public void getAdvSearchHqlAndMapOfEnd(StringBuffer hql, HashMap parMap, String hqlDiff4Manage, String hqlDiff4Research, Map session, String projName, String projSubType, String projDirector, String projUniversity);
	
	/**
	 * 获取变更高级检索的Hql语句和Map参数
	 * @param hql		最终查询语句
	 * @param parMap	最终参数
	 * @param hqlSelect	select公共部分
	 * @param hqlFrom	from公共部分
	 * @param hqlDiff4Manage	管理人员差异部分
	 * @param hqlDiff4Research	研究人员差异部分
	 * @param session	session
	 * @param projName	高级检索项目名称
	 * @param projSubType	高级检索项目子类
	 * @param projDirector	高级检索申请人/负责人
	 * @param projUniversity	高级检索项目所属学校
	 */
	public void getAdvSearchHqlAndMapOfVar(StringBuffer hql, HashMap parMap, String hqlSelect, String hqlFrom, String hqlDiff4Manage, String hqlDiff4Research, Map session, String projName, String projSubType, String projDirector, String projUniversity);
}
