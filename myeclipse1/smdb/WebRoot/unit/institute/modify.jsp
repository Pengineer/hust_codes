<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>修改</title>
		<s:include value="/innerBase.jsp" />
	</head>
	
	<body>
		<s:if test="viewFlag == 1">
		<div class="link_bar">
			当前位置：我的资料&nbsp;&gt;&nbsp;修改
		</div>
		<s:hidden id="namespace" value="unit/selfspace" />
		</s:if>
		<s:else>
		<div class="link_bar">
			当前位置：社科机构数据&nbsp;&gt;&nbsp;社科研究机构&nbsp;&gt;&nbsp;修改
		</div>
		<s:hidden id="namespace" value="unit/institute" />
		</s:else>
		<s:form id="form_institute" action="modify" theme="simple">
		<div id="info" class="main" style="display: none">
			<div class="main_content">
				<s:hidden name="viewFlag" id="viewFlag"/>
				<div class="step_css">
					<ul>
						<li class="proc" name="basicInfo"><span class="left_step"></span><span class="right_step">基本信息</span></li>
						<li class="proc" name="academicInfo"><span class="left_step"></span><span class="right_step">学术信息</span></li>
						<li class="proc" name="resourceInfo"><span class="left_step"></span><span class="right_step">资源信息</span></li>
						<li class="proc" name="contactInfo"><span class="left_step"></span><span class="right_step">联系信息</span></li>
						<li class="proc step_oo"><span class="left_step"></span><span class="right_step">完成</span></li>
					</ul>
				</div>
			</div>
			<div class="main_content">
				<s:include value="/validateError.jsp" />
				<div id="basicInfo">
					<s:hidden name="institute.id" id="entityId" />
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td2" width="130"><span class="table_title5">研究基地名称：</span></td>
							<td class="table_td3"><s:textfield id="name" name="institute.name" cssClass="input_css" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">英文名称：</td>
							<td class="table_td3"><s:textfield name="institute.englishName" cssClass="input_css" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">研究基地代码：</td>
							<td class="table_td3"><s:textfield name="institute.code" cssClass="input_css" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">名称缩写：</td>
							<td class="table_td3"><s:textfield name="institute.abbr" cssClass="input_css" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">研究基地类型：</td>
							<td class="table_td3"><s:select cssClass="select" name="institute.type.id" value="%{institute.type.id}" headerKey="-1" headerValue="--请选择--" list="%{baseService.getSystemOptionMap('researchAgencyType', null)}" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2"><span class="table_title5">上级管理机构：</span></td>
							<td class="table_td3">
								<input type="button" id="select_subjection_btn" class="btn1 select_btn" value="选择"/>
								<div id="subjectionName" class="choose_show"><s:property value="subjectionName" /></div>
								<s:hidden name="institute.subjection.id" id="subjectionId" />
							</td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">单位负责人：</td>
							<td class="table_td3">
								<input type="button" id="select_director_btn" class="btn1 select_btn" value="选择"/>
								<div id="directorName" class="choose_show"><s:property value="institute.director.name"/></div>
								<s:hidden name="directorId" id="directorId" />
							</td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">是否是独立机构：</td>
							<td class="table_td3"><s:radio name="institute.isIndependent" list="#{'1':'是','0':'否'}" cssClass="input_css_radio" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">组成方式：</td>
							<td class="table_td3"><s:textfield name="institute.form" cssClass="input_css" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">批准批次：</td>
							<td class="table_td3"><s:textfield name="institute.approveSession" cssClass="input_css" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">批准时间：</td>
							<td class="table_td3"><input name="institute.approveDate" class="input_css" value="<s:date name='%{institute.approveDate}' format='yyyy-MM-dd' />" id="datepick" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">简介：</td>
							<td class="table_td3"><s:textarea name="institute.introduction" rows="6" cssClass="textarea_css" /></td>
							<td class="table_td4"></td>
						</tr>
					</table>
				</div>
				<div id="academicInfo" style="display:none">
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td2" width="110">研究活动类型：</td>
							<td class="table_td3"><s:select cssClass="select" name="institute.researchActivityType.id" headerKey="-1" headerValue="--请选择--" list="%{baseService.getSystemOptionMap('researchActivityType', null)}" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">所属研究片：</td>
							<td class="table_td3"><s:textfield name="institute.researchArea" cssClass="input_css" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">所属学科门类：</td>
							<td class="table_td3">
								<input type="button" id="selectDisciplineType" class="btn1 select_btn" value="选择" />
								<s:hidden name="institute.disciplineType" id="discipline" />
								<div id="disptr" class="choose_show"><s:property value="institute.disciplineType"/></div>
							</td>
							<td class="table_td4"></td>
						</tr>
						<%--								<tr class="table_tr2">--%>
							<%--									<td class="table_td2">依托重点学科：</td>--%>
							<%--									<td class="table_td3">--%>
								<%--										<input type="button" id="selectRelyDiscipline" class="btn1 select_btn" value="选择" />--%>
								<%--										<s:hidden name="institute.relyDisciplineId" id="relyDisciplineId" />--%>
								<%--										<div id="rdsp" class="choose_show" style="display:none" ></div>--%>
							<%--									</td>--%>
							<%--									<td class="table_td4"></td>--%>
						<%--								</tr>--%>
						<%--								<tr class="table_tr2">--%>
							<%--									<td class="table_td2">依托博士点：</td>--%>
							<%--									<td class="table_td3"><s:textfield name="institute.relyDoctoral"  cssClass="input_css" /></td>--%>
							<%--									<td class="table_td4"></td>--%>
						<%--								</tr>--%>
					</table>
				</div>
				<div id="resourceInfo" style="display:none">
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td2" width="110">办公用房面积：</td>
							<td class="table_td3"><s:textfield name="institute.officeArea" cssClass="input_css" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">资料室面积：</td>
							<td class="table_td3"><s:textfield name="institute.dataroomArea" cssClass="input_css" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">中文藏书数量：</td>
							<td class="table_td3"><s:textfield name="institute.chineseBookAmount" cssClass="input_css" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">中文报刊数量：</td>
							<td class="table_td3"><s:textfield name="institute.chinesePaperAmount" cssClass="input_css" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">外文藏书数量：</td>
							<td class="table_td3"><s:textfield name="institute.foreignBookAmount" cssClass="input_css" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">外文报刊数量：</td>
							<td class="table_td3"><s:textfield name="institute.foreignPaperAmount" cssClass="input_css" /></td>
							<td class="table_td4"></td>
						</tr>
					</table>
				</div>
				<div id="contactInfo" style="display:none">
				<s:textfield type = "hidden" name="institute.addressIds" cssClass="input_css" />
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td2" width="100">部门地址：</td>
							<td class="table_td3" colspan="2">
								<table id="agency-caddr-table" >
									<tbody>
										<tr>
											<td colspan="5"><input id="add-agency-caddr" class="btn1" type="button" value="添加"></td>
											<td></td>
										</tr>
										<s:iterator value="commonAddress" status="stat">
										<tr>
											<td width="40">地址：</td>
											<td>
												<s:textfield name="%{'commonAddress['+#stat.index+'].address'}" value="%{commonAddress[#stat.index].address}" size = "21"  />
												<s:textfield type = "hidden" name="%{'commonAddress['+#stat.index+'].id'}" value="%{commonAddress[#stat.index].id}"  />
											</td>
											<td width="40">邮编：</td>
											<td>
												<s:textfield name="%{'commonAddress['+#stat.index+'].postCode'}" value="%{commonAddress[#stat.index].postCode}" size="6" maxlength="6"  />
											</td>
											<td width = "80">
												<label>是否默认：<input type = "checkbox" name = "" value = ""></label>
												<s:textfield type = "hidden" name="%{'commonAddress['+#stat.index+'].isDefault'}" filedValue="%{commonAddress[#stat.index].isDefault}"  />
											</td>
											<td><input class="delete_row btn1" type="button" value="删除" name=""></td><td class="comb-error"></td>
										</tr>
										</s:iterator>
										<tr id="tr_common_addr" class = "address" style="display:none;"><td width="40">地址：</td>
										<td>
											<input name="commonAddress[].address" type="text" size = "21" />
											<input type = "hidden" name="commonAddress[].id" type="text" />
										</td>
										<td width="40">邮编：</td><td><input 	name="commonAddress[].postCode" type="text" size="6" maxlength="6" /></td>
										<td width = "80">
											<label>是否默认：<input type="checkbox" name="commonAddress[].isDefault" value = "0"/></label>
											<input type="hidden" name="commonAddress[].isDefault" class="" value = "0"/>
										</td>
										<td><input class="delete_row btn1" type="button" value="删除" name=""></td>
										<td class="comb-error"></td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">电话：</td>
						<td class="table_td3"><s:textfield name="institute.phone" cssClass="input_css" /><br/><span class="tip">电话格式：区号-电话号-分机号</span></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">传真：</td>
						<td class="table_td3"><s:textfield name="institute.fax" cssClass="input_css" /><br/><span class="tip">传真格式：区号-电话号-分机号</span></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">邮箱：</td>
						<td class="table_td3"><s:textfield name="institute.email" cssClass="input_css" /></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">主页：</td>
						<td class="table_td3"><s:textfield name="institute.homepage" cssClass="input_css" /></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">联系人：</td>
						<td class="table_td3">
							<input type="button" id="select_linkman_btn" class="btn1 select_btn" value="选择"/>
							<div id="linkmanName" class="choose_show"><s:property value="institute.linkman.name"/></div>
							<s:hidden name="linkmanId" id="linkmanId" />
						</td>
						<td class="table_td4"></td>
					</tr>
				</table>
			</div>
		</div>
		<div id="optr" class="btn_bar2">
			<input id="prev" class="btn2" type="button" style="display: none" value="上一步" />
			<input id="next" class="btn2" type="button" style="display: none" value="下一步" />
			<input id="save" class="btn1" type="button" style="display: none" value="保存"/>
			<input id="cancel" class="btn1" type="button" style="display: none" value="取消" />
		</div>
	</div>
	</s:form>
	<script type="text/javascript">
		seajs.use('javascript/unit/institute/modify.js', function(modify) {
			$(function(){
				modify.init();
			})
		});
	</script>
</body>
</html>