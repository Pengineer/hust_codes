<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>委托应急课题</title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div style="width:400px;">
				<div style="*margin-right:20px; _margin-right:20px;">
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title2">高校范围：</span></td>
							<td class="table_td3" style="width:280px;">
								<s:radio name="univ" list="#{'0': '所有高校', '1' : '指定高校'}" cssClass="input_css_radio" value="0"/>
							</td>	
						</tr>
						<tr class="table_tr2" style="display:none;">
							<td class="table_td2" width="110"><span class="table_title2">所属高校：</span></td>
							<td class="table_td3" style="width:280px;">
								<input type="button" id="select_university_btn" class="btn1 select_btn" value="选择"/>
								<div id="univName" class="choose_show"></div>
								<s:hidden name="univId" id="univId"></s:hidden>
							</td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title2">项目子类：</span></td>
							<td class="table_td3" style="width:280px;">
								<s:select cssClass="select" id="subType" name="subType" list="#request.subTypes" headerKey="-1" headerValue="--不限--" cssStyle="width:100px;"/>
							</td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title2">项目年度：</span></td>
							<td class="table_td3">
								<s:select cssClass="select" name="startYear" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--不限--" />
								至
								<s:select cssClass="select" name="endYear" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--不限--" />
							</td>
							<td class="table_td4"></td>
						 </tr>
						 <tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title2">项目状态：</td>
							<td class="table_td3"><s:select cssClass="select"  id="projectStatus" name="projectStatus" list="#{'1':'在研', '2':'已结项', '3':'已中止', '4':'已撤项'}" headerKey="-1" headerValue="--不限--" /></td>
							<td class="table_td4"></td>
						 </tr>
						 <tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title2">变更时间：</span></td>
							<td class="table_td3" style="width:280px;">
								<s:textfield id="startDate" name="startDate" cssClass="FloraDatepick" readonly="true">
									<s:param name="value">
										<s:date name="startDate" format="yyyy-MM-dd"/>
									</s:param>
								</s:textfield>
								至
								<s:textfield id="endDate" name="endDate" cssClass="FloraDatepick" readonly="true">
									<s:param name="value">
										<s:date name="endDate" format="yyyy-MM-dd"/>
									</s:param>
								</s:textfield>
							</td>
						</tr>
					</table>
					<div class="btn_div_view">
						<input id="submit" class="btn1" type="button" value="确定"/>
						<input id="cancel" class="btn1" type="button" value="取消"/>
					</div>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/project/project_share/variation/popExportOverView.js', function(popExportOverView) {
					popExportOverView.init();
				});
			</script>
		</body>
	
</html>