<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>重大攻关项目</title>
			<s:include value="/innerBase.jsp" />
		</head>
		
		<body>
			<div style="width:750px;">
				<s:form id="review_endform" theme="simple">
					<s:hidden id="endId" name="endId"/>
					<s:hidden id="accountType" value="%{#session.loginer.currentType}"/>
					<s:hidden id="submitStatus" name="submitStatus"/>
					<div style="overflow-y:scroll;height:500px; *margin-right:20px; _margin-right:20px;">
						<div class="p_box_t">
							<div class="p_box_t_t">专家鉴定信息</div>
							<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
						</div>
						<div>
							<div id="review">
								<s:iterator value="reviews" status="stat">
									<table class="table_valid" width="100%" border="0" cellspacing="2" cellpadding="0">
										<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
											<tr class="table_tr2">
												<td class="table_td8" width="200"><div class="sort_title"><b><span>鉴定专家</span><span title="reviewSpan"><s:property value="#stat.index+1" /></span></b></div>&nbsp;</td>
												<td class="table_td9">
													<input type="button" class="add_review btn1"  value="添加" />&nbsp;
													<input type="button" class="delete_row btn1" value="删除" />&nbsp;
													<input type="button" class="up_row btn1" value="上移" />&nbsp;
													<input type="button" class="down_row btn1" value="下移" />&nbsp;&nbsp;
													<span class="tip">可拖拽空白区域对成员排序</span>
												</td>
												<td class="table_td10" width="90"></td>
											</tr>
											<tr class="table_tr2">
												<td class="table_td8"><span class="table_title11">人员类型：</span></td>
												<td class="table_td9">
													<s:select cssClass="select" name="%{'reviews['+#stat.index+'].reviewerType'}" headerKey='-1' headerValue="--请选择--" list="#{'1':'教师','2':'外部专家'}" />
												</td>
												<td class="table_td10" width="90"></td>
											</tr>
											<tr class="table_tr2">
												<td class="table_td8"><span class="table_title11">姓名：</span></td>
												<td class="table_td9">
													<input name="selectReviewer" type="button" class="btn1 select_btn" value="选择" />
													<div name="reviewerNameDiv" class="choose_show"><s:property value="%{reviews[#stat.index].reviewerName}"/></div>
													<s:hidden name="%{'reviews['+#stat.index+'].reviewer.id'}" />
													<s:hidden name="%{'reviews['+#stat.index+'].reviewerName'}"/>
												</td>
												<td class="table_td10"></td>
											</tr>
											<tr class="table_tr2">
												<td class="table_td8"><span>所在单位与部门：</span></td>
												<td class="table_td9">
													<div name="reviewerUnitDiv" class="unit_show"><s:property value="%{reviews[#stat.index].agencyName}"/>&nbsp;<s:property value="%{reviews[#stat.index].divisionName}"/></div>
												</td>
												<td class="table_td10"></td>
											</tr>
											<tr class="table_tr2">
												<td class="table_td8"><span class="table_title11">创新和突破得分（50分）：</span></td>
												<td class="table_td9"><s:textfield name="%{'reviews['+#stat.index+'].innovationScore'}" cssClass="cal_score input_css" /></td>
												<td class="table_td10"></td>
											</tr>
											<tr class="table_tr2">
												<td class="table_td8"><span class="table_title11">科学性和规范性得分（25分）：</span></td>
												<td class="table_td9"><s:textfield name="%{'reviews['+#stat.index+'].scientificScore'}" cssClass="cal_score input_css" /></td>
												<td class="table_td10"></td>
											</tr>
											<tr class="table_tr2">
												<td class="table_td8"><span class="table_title11">价值和效益得分（25分）：</span></td>
												<td class="table_td9"><s:textfield name="%{'reviews['+#stat.index+'].benefitScore'}" cssClass="cal_score input_css" /></td>
												<td class="table_td10"></td>
											</tr>
											<tr class="table_tr2">
												<td class="table_td8"><span>总分：</span></td>
												<td class="table_td9"><s:textfield name="reviewScore" value="%{reviews[#stat.index].score}"  readonly="true" cssStyle="cursor:pointer;" cssClass="input_css" /></td>
												<td class="table_td10"></td>
											</tr>
											<tr class="table_tr2">
												<td class="table_td8"><span>建议评价等级：</span></td>
												<td class="table_td9"><s:textfield name="reviewGrade" value="%{reviews[#stat.index].grade.name}"  readonly="true"  cssStyle="cursor:pointer;" cssClass="input_css" /></td>
												<td class="table_td10"></td>
											</tr>
											<tr class="table_tr2">
												<td class="table_td5"><span class="table_title7">成果修改定性意见：</span></td>
												<td class="table_td6"><s:select cssClass="select" name="%{'reviews['+#stat.index+'].qualitativeOpinion'}" list="%{baseService.getSystemOptionMapAsName('reviewOpinionQualitative', null)}" headerKey="-1" headerValue="--请选择--" /></td>
												<td class="table_td7"></td>
											</tr>
											<tr class="table_tr2">
												<td class="table_td5"><span>鉴定意见：<br/>（限2000字）</span></td>
												<td class="table_td6"><s:textarea name="%{'reviews['+#stat.index+'].opinion'}" rows="2" cssClass="textarea_css" /></td>
												<td class="table_td7"></td>
											</tr>
										</s:if>
										<s:else>
											<tr class="table_tr2">
												<td class="table_td8" width="200"><div class="sort_title"><b><span>鉴定专家</span><span title="reviewSpan"><s:property value="#stat.index+1" /></span></b></div>&nbsp;</td>
												<td class="table_td17" colspan='4'>
													<input type="button" class="add_review btn1"  value="添加" />&nbsp;
													<input type="button" class="delete_row btn1" value="删除" />&nbsp;
													<input type="button" class="up_row btn1" value="上移" />&nbsp;
													<input type="button" class="down_row btn1" value="下移" />&nbsp;&nbsp;
													<span class="tip">可拖拽空白区域对成员排序</span>
												</td>
												<td class="table_td10" width="90"></td>
											</tr>
											<tr class="table_tr2">
												<td class="table_td8" width="110"><span class="table_title4">证件类型：</span></td>
												<td class="table_td17" width="100">
													<s:select cssClass="select" name="%{'reviews['+#stat.index+'].idcardType'}" headerKey="-1" headerValue="--请选择--" list="#{'身份证':'身份证','军官证':'军官证','护照':'护照'}"/>
												</td>
												<td class="table_td10" width="70"></td>
												<td class="table_td8" width="80"><span class="table_title2">证件号：</span></td>
												<td class="table_td18" width="150">
													<s:textfield name="%{'reviews['+#stat.index+'].idcardNumber'}" value="%{reviews[#stat.index].idcardNumber}" cssClass="input_css2"/></td>
												<td class="table_td10" width="90"></td>
											</tr>
											<tr class="table_tr2">
												<td class="table_td8"><span class="table_title4">人员类型：</span></td>
												<td class="table_td17">
													<s:select cssClass="select" name="%{'reviews['+#stat.index+'].reviewerType'}" headerKey='-1' headerValue="--请选择--" list="#{'1':'教师','2':'外部专家'}" />
												</td>
												<td class="table_td10"></td>
												<td class="table_td8"><span class="table_title2">姓名：</span></td>
												<td class="table_td18">
													<s:textfield name="%{'reviews['+#stat.index+'].reviewerName'}" cssClass="input_css2"/>
												</td>
												<td class="table_td10"></td>
											</tr>
											<tr class="table_tr2">
												<td class="table_td8"><span class="table_title4">性别：</span></td>
												<td class="table_td17">
													<s:select cssClass="select" name="%{'reviews['+#stat.index+'].gender'}" headerKey="-1" headerValue="--请选择--" list="#application.sexList" />
												</td>
												<td class="table_td10"></td>
												<td class="table_td8"><span class="table_title2">所在单位：</span></td>
												<td class="table_td18">
													<div name="inputUniDiv" style="display:none;">
														<s:textfield name="%{'reviews['+#stat.index+'].agencyName'}" value ="%{reviews[#stat.index].agencyName}" cssClass="input_css2"/>
													</div>
													<div name="selectUniDiv">
														<input name="selectUniversity" type="button" class="btn1 select_btn" value="选择" />
														<div name="universityNameDiv" class="choose_show"><s:property value="%{reviews[#stat.index].agencyName}"/></div>
														<s:hidden name="%{'reviews['+#stat.index+'].university.id'}" value="%{reviews[#stat.index].university.id}" />
													</div>
												</td>
												<td class="table_td10"></td>
											</tr>
											<tr class="table_tr2">
												<td class="table_td8"><span class="table_title4">部门类别：</span></td>
												<td class="table_td17">
													<s:select cssClass="select" name="%{'reviews['+#stat.index+'].divisionType'}" headerKey="-1" headerValue="--请选择--" list="#{'1':'研究基地','2':'院系','3':'外部部门'}" />
												</td>
												<td class="table_td10"></td>
												<td class="table_td8"><span class="table_title2">所在部门：</span></td>
												<td class="table_td18">
													<s:textfield name="%{'reviews['+#stat.index+'].divisionName'}" value ="%{reviews[#stat.index].divisionName}" cssClass="input_css2"/>
												</td>
												<td class="table_td10"></td>
											</tr>
											<tr class="table_tr2">
												<td class="table_td8"><span class="table_title11">创新和突破得分（50分）：</span></td>
												<td class="table_td9" colspan='4'><s:textfield name="%{'reviews['+#stat.index+'].innovationScore'}" cssClass="cal_score input_css3" /></td>
												<td class="table_td10"></td>
											</tr>
											<tr class="table_tr2">
												<td class="table_td8"><span class="table_title11">科学性和规范性得分（25分）：</span></td>
												<td class="table_td9" colspan='4'><s:textfield name="%{'reviews['+#stat.index+'].scientificScore'}" cssClass="cal_score input_css3" /></td>
												<td class="table_td10"></td>
											</tr>
											<tr class="table_tr2">
												<td class="table_td8"><span class="table_title11">价值和效益得分（25分）：</span></td>
												<td class="table_td9" colspan='4'><s:textfield name="%{'reviews['+#stat.index+'].benefitScore'}" cssClass="cal_score input_css3" /></td>
												<td class="table_td10"></td>
											</tr>
											<tr class="table_tr2">
												<td class="table_td8"><span>总分：<br></span></td>
												<td class="table_td9" colspan='4'><s:textfield name="reviewScore" value="%{reviews[#stat.index].score}" readonly="true" cssStyle="cursor:pointer;" cssClass="input_css3" /></td>
												<td class="table_td10"></td>
											</tr>
											<tr class="table_tr2">
												<td class="table_td8"><span>建议评价等级：</span></td>
												<td class="table_td9" colspan='4'><s:textfield name="reviewGrade" value="%{reviews[#stat.index].grade.name}" readonly="true"  cssStyle="cursor:pointer;" cssClass="input_css3" /></td>
												<td class="table_td10"></td>
											</tr>
											<tr class="table_tr2">
												<td class="table_td8"><span class="table_title11">成果修改定性意见：</span></td>
												<td class="table_td9" colspan='4'><s:select cssClass="select" name="%{'reviews['+#stat.index+'].qualitativeOpinion'}" list="%{baseService.getSystemOptionMapAsName('reviewOpinionQualitative', null)}" headerKey="-1" headerValue="--请选择--" /></td>
												<td class="table_td10"></td>
											</tr>
											<tr class="table_tr2">
												<td class="table_td8"><span>鉴定意见：<br/>（限2000字）</span></td>
												<td class="table_td9" colspan='4'><s:textarea name="%{'reviews['+#stat.index+'].opinion'}" rows="2" cssClass="textarea_css1" /></td>
												<td class="table_td10"></td>
											</tr>
										</s:else>
									</table>
								</s:iterator>
							</div>
							<div id="last_add_div" style="display:none;">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr class="table_tr2">
										<td class="table_td8" width="230"><div class="sort_title"><b><span>鉴定专家</span><span title="reviewSpan"></span></b></div>&nbsp;</td>
										<td class="table_td9">
											<input type="button" class="add_last_table btn1" value="添加" />
										</td>
										<td class="table_td10" width="90"></td>
										<td>
									</tr>
								</table>
							</div>
						</div>
						<div class="p_box_t">
							<div class="p_box_t_t">小组鉴定信息</div>
							<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
						</div>
						<div>
							<table width="100%" border="0" cellspacing="2" cellpadding="0">
								<tr class="table_tr2">
									<td class="table_td8" width="200"><span class="table_title11">鉴定方式：</span></td>
									<td class="table_td9"><s:radio name="reviewWay" list="#{'1':'通讯鉴定','2':'会议鉴定'}"/></td>
									<td class="table_td10" width="90"></td>
								</tr>
								<tr class="table_tr2">
									<td class="table_td8"><span>总分：<br></span></td>
									<td class="table_td9"><s:textfield name="totalScore" value="%{#request.totalScore}" readonly="true" cssStyle="cursor:pointer;" cssClass="input_css" /></td>
									<td class="table_td10"></td>
								</tr>
								<tr class="table_tr2">
									<td class="table_td8"><span>平均分：<br></span></td>
									<td class="table_td9"><s:textfield name="avgScore" value="%{#request.avgScore}" readonly="true" cssStyle="cursor:pointer;" cssClass="input_css" /></td>
									<td class="table_td10"></td>
								</tr>
								<tr class="table_tr2">
									<td class="table_td8"><span>成果鉴定等级：</span></td>
									<td class="table_td9"><s:textfield name="groupGrade" value="%{#request.groupGrade}" readonly="true"  cssStyle="cursor:pointer;" cssClass="input_css" /></td>
									<td class="table_td10"></td>
								</tr>
								<tr class="table_tr2">
									<td class="table_td8"><span class="table_title11">鉴定结果：</span></td>
									<td class="table_td9"><s:radio name="reviewResult" list="#{'2':'同意','1':'不同意'}"/></td>
									<td class="table_td10"></td>
								</tr>
								<tr class="table_tr2">
									<td class="table_td8"><span class="table_title11">成果修改定性意见：</span></td>
									<td class="table_td9"><s:select cssClass="select" id="reviewOpinionQualitative" name="reviewOpinionQualitative" list="%{baseService.getSystemOptionMapAsName('reviewOpinionQualitative', null)}" headerKey="-1" headerValue="--请选择--" /></td>
									<td class="table_td10"></td>
								</tr>
								<tr class="table_tr2">
									<td class="table_td8"><span>鉴定意见：<br/>（限2000字）</span></td>
									<td class="table_td9"><s:textarea name="reviewOpinion" rows="2" cssClass="textarea_css" /></td>
									<td class="table_td10"></td>
								</tr>
							</table>
						</div>
					</div>
				</s:form>
				<table id="table_review" width="100%" border="0" cellspacing="0" cellpadding="0" style="display:none;">
					<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
						<tr class="table_tr2">
							<td class="table_td8" width="200"><div class="sort_title"><b><span>鉴定专家</span><span title="reviewSpan"><s:property value="#stat.index+1" /></span></b></div>&nbsp;</td>
							<td class="table_td9">
								<input type="button" class="add_review btn1"  value="添加" />&nbsp;
								<input type="button" class="delete_row btn1" value="删除" />&nbsp;
								<input type="button" class="up_row btn1" value="上移" />&nbsp;
								<input type="button" class="down_row btn1" value="下移" />&nbsp;&nbsp;
								<span class="tip">可拖拽空白区域对成员排序</span>
							</td>
							<td class="table_td10" width="90"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td8"><span class="table_title11">人员类型：</span></td>
							<td class="table_td9">
								<s:select cssClass="select" name="reviews[].reviewerType" headerKey='-1' headerValue="--请选择--" list="#{'1':'教师','2':'外部专家'}" />
							</td>
							<td class="table_td10" width="90"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td8"><span class="table_title11">姓名：</span></td>
							<td class="table_td9">
								<input name="selectReviewer" type="button" class="btn1 select_btn" value="选择" />
								<div name="reviewerNameDiv" class="choose_show"><s:property value="%{reviews[#stat.index].reviewerName}"/></div>
								<s:hidden name="reviews[].reviewer.id" />
								<s:hidden name="reviews[].reviewerName"/>
							</td>
							<td class="table_td10"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td8"><span>所在单位与部门：</span></td>
							<td class="table_td9">
								<div name="reviewerUnitDiv" class="unit_show"></div>
							</td>
							<td class="table_td10"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td8"><span class="table_title11">创新和突破得分（50分）：</span></td>
							<td class="table_td9"><s:textfield name="reviews[].innovationScore" cssClass="cal_score input_css" /></td>
							<td class="table_td10"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td8"><span class="table_title11">科学性和规范性得分（25分）：</span></td>
							<td class="table_td9"><s:textfield name="reviews[].scientificScore" cssClass="cal_score input_css" /></td>
							<td class="table_td10"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td8"><span class="table_title11">价值和效益得分（25分）：</span></td>
							<td class="table_td9"><s:textfield name="reviews[].benefitScore" cssClass="cal_score input_css" /></td>
							<td class="table_td10"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td8"><span>总分：<br></span></td>
							<td class="table_td9"><s:textfield name="reviewScore" readonly="true" cssStyle="cursor:pointer;" cssClass="input_css" /></td>
							<td class="table_td10"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td8"><span>建议评价等级：</span></td>
							<td class="table_td9"><s:textfield name="reviewGrade" readonly="true"  cssStyle="cursor:pointer;" cssClass="input_css" /></td>
							<td class="table_td10"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td8"><span class="table_title11">成果修改定性意见：</span></td>
							<td class="table_td9"><s:select cssClass="select" name="reviews[].qualitativeOpinion" list="%{baseService.getSystemOptionMapAsName('reviewOpinionQualitative', null)}" headerKey="-1" headerValue="--请选择--" /></td>
							<td class="table_td10"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td8"><span>鉴定意见：<br/>（限2000字）</span></td>
							<td class="table_td9"><s:textarea name="reviews[].opinion" rows="2" cssClass="textarea_css" /></td>
							<td class="table_td10"></td>
						</tr>
					</s:if>
					<s:else>
						<tr class="table_tr2">
							<td class="table_td8" width="200"><div class="sort_title"><b><span>鉴定专家</span><span title="reviewSpan"><s:property value="#stat.index+1" /></span></b></div>&nbsp;</td>
							<td class="table_td17" colspan='4'>
								<input type="button" class="add_review btn1"  value="添加" />&nbsp;
								<input type="button" class="delete_row btn1" value="删除" />&nbsp;
								<input type="button" class="up_row btn1" value="上移" />&nbsp;
								<input type="button" class="down_row btn1" value="下移" />&nbsp;&nbsp;
								<span class="tip">可拖拽空白区域对成员排序</span>
							</td>
							<td class="table_td10" width="90"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td8" width="110"><span class="table_title4">证件类型：</span></td>
							<td class="table_td17" width="100">
								<s:select cssClass="select" name="reviews[].idcardType" headerKey="-1" headerValue="--请选择--" list="#{'身份证':'身份证','军官证':'军官证','护照':'护照'}"/>
							</td>
							<td class="table_td10" width="70"></td>
							<td class="table_td8" width="80"><span class="table_title2">证件号：</span></td>
							<td class="table_td18" width="150">
								<s:textfield name="reviews[].idcardNumber" cssClass="input_css2"/></td>
							<td class="table_td10" width="90"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td8"><span class="table_title4">人员类型：</span></td>
							<td class="table_td17">
								<s:select cssClass="select" name="reviews[].reviewerType" headerKey='-1' headerValue="--请选择--" list="#{'1':'教师','2':'外部专家'}" />
							</td>
							<td class="table_td10"></td>
							<td class="table_td8"><span class="table_title2">姓名：</span></td>
							<td class="table_td18">
								<s:textfield name="reviews[].reviewerName" cssClass="input_css2"/>
							</td>
							<td class="table_td10"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td8"><span class="table_title4">性别：</span></td>
							<td class="table_td17">
								<s:select cssClass="select" name="reviews[].gender" headerKey="-1" headerValue="--请选择--" list="#application.sexList" />
							</td>
							<td class="table_td10"></td>
							<td class="table_td8"><span class="table_title2">所在单位：</span></td>
							<td class="table_td18">
								<div name="inputUniDiv" style="display:none;">
									<s:textfield name="reviews[].agencyName" cssClass="input_css2"/>
								</div>
								<div name="selectUniDiv">
									<input name="selectUniversity" type="button" class="btn1 select_btn" value="选择" />
									<div name="universityNameDiv" class="choose_show"></div>
									<s:hidden name="reviews[].university.id" />
								</div>
							</td>
							<td class="table_td10"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td8"><span class="table_title4">部门类别：</span></td>
							<td class="table_td17">
								<s:select cssClass="select" name="reviews[].divisionType" headerKey="-1" headerValue="--请选择--" list="#{'1':'研究基地','2':'院系','3':'外部部门'}" />
							</td>
							<td class="table_td10"></td>
							<td class="table_td8"><span class="table_title2">所在部门：</span></td>
							<td class="table_td18">
								<s:textfield name="reviews[].divisionName"  cssClass="input_css2"/>
							</td>
							<td class="table_td10"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td8"><span class="table_title11">创新和突破得分（50分）：</span></td>
							<td class="table_td9" colspan='4'><s:textfield name="reviews[].innovationScore" cssClass="cal_score input_css3" /></td>
							<td class="table_td10"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td8"><span class="table_title11">科学性和规范性得分（25分）：</span></td>
							<td class="table_td9" colspan='4'><s:textfield name="reviews[].scientificScore" cssClass="cal_score input_css3" /></td>
							<td class="table_td10"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td8"><span class="table_title11">价值和效益得分（25分）：</span></td>
							<td class="table_td9" colspan='4'><s:textfield name="reviews[].benefitScore" cssClass="cal_score input_css3" /></td>
							<td class="table_td10"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td8"><span>总分：<br></span></td>
							<td class="table_td9" colspan='4'><s:textfield name="reviewScore" readonly="true" cssStyle="cursor:pointer;" cssClass="input_css3" /></td>
							<td class="table_td10"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td8"><span>建议评价等级：</span></td>
							<td class="table_td9" colspan='4'><s:textfield name="reviewGrade" readonly="true"  cssStyle="cursor:pointer;" cssClass="input_css3" /></td>
							<td class="table_td10"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td8"><span class="table_title11">成果修改定性意见：</span></td>
							<td class="table_td9" colspan='4'><s:select cssClass="select" name="reviews[].qualitativeOpinion" list="%{baseService.getSystemOptionMapAsName('reviewOpinionQualitative', null)}" headerKey="-1" headerValue="--请选择--" /></td>
							<td class="table_td10"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td8"><span>鉴定意见：<br/>（限2000字）</span></td>
							<td class="table_td9" colspan='4'><s:textarea name="reviews[].opinion" rows="2" cssClass="textarea_css1" /></td>
							<td class="table_td10"></td>
						</tr>
					</s:else>
				</table>
			</div>
			<div class="btn_div_view">
				<input id="save" class="btn1 j_submitOrSaveModifyEndReviewResult" data="2" type="button" value="保存" />
				<input id="submit" class="btn1 j_submitOrSaveModifyEndReviewResult" data="3" type="button" value="提交" />
				<input id="cancel" class="btn1" type="button" value="取消" />
			</div>
			<script type="text/javascript">
				<s:if test="null != revResultFlag && revResultFlag == 1">
					(function(){
						var thisPopLayer = top.PopLayer.instances[top.PopLayer.id];
    					thisPopLayer.callBack(thisPopLayer);
    				})();
				</s:if>
				seajs.use(['javascript/project/key/endinspection/review/edit_result.js','javascript/project/project_share/endinspection/review/validate_result.js'], function(editResult, validate) {
					validate.valid();
					editResult.init();
				});
			</script>
		</body>
	
</html>
