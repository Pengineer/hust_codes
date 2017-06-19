<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>专项任务项目</title>
			<s:include value="/innerBase.jsp" />
		</head>
		
		<body>
			<div style="width:650px;">
				<s:form id="review_endform" theme="simple">
					<s:hidden id="entityId" name="entityId"/>
					<s:hidden id="accountType" value="%{#session.loginer.currentType}"/>
					<div style="overflow-y:scroll;height:500px; *margin-right:20px; _margin-right:20px;">
						<div class="p_box_t">
							<div class="p_box_t_t">添加鉴定专家</div>
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
										</s:if>
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
									</tr>
								</table>
							</div>
						</div>
						<div style="display:none;">
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
					</s:if>
				</table>
			</div>
			<div class="btn_div_view">
				<input id="submit" class="btn1 j_addReviewExpert" type="button" value="提交" />
				<input id="cancel" class="btn1" type="button" value="取消" />
			</div>
			<script type="text/javascript">
				<s:if test="null != revResultFlag && revResultFlag == 1">
					(function(){
						var thisPopLayer = top.PopLayer.instances[top.PopLayer.id];
						thisPopLayer.callBack(thisPopLayer);
					})();
				</s:if>
				seajs.use(['javascript/project/special/application/review/edit_expert.js'], function(editResult) {
					editResult.init();
				});
			</script>
		</body>
	
</html>
