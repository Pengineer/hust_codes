<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Project">
		<head>
			<title><s:text name="i18n_EntrustSubProject" /></title>
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
								<input type="button" id="select_university_btn" class="btn1 select_btn" value="<s:text name='i18n_Select'/>"/>
								<div id="univName" class="choose_show"></div>
								<s:hidden name="univId" id="univId"></s:hidden>
							</td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title2">项目子类：</span></td>
							<td class="table_td3" style="width:280px;">
								<s:select cssClass="select" id="subType" name="subType" list="#request.subTypes" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" cssStyle="width:100px;"/>
							</td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title2"><s:text name="i18n_ProjectYear" />：</span></td>
							<td class="table_td3">
								<s:select cssClass="select" name="startYear" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" />
								<s:text name="i18n_To"/>
								<s:select cssClass="select" name="endYear" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" />
							</td>
							<td class="table_td4"></td>
						 </tr>
						 <tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title2"><s:text name="i18n_ProjectStatus" />：</td>
							<td class="table_td3"><s:select cssClass="select"  id="projectStatus" name="projectStatus" list="#{'1':getText('i18n_InStudy'), '2':getText('i18n_Complete'), '3':getText('i18n_Suspend'), '4':getText('i18n_Revoke')}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" /></td>
							<td class="table_td4"></td>
						 </tr>
					</table>
					<div class="btn_div_view">
						<input id="submit" class="btn1" type="button" value="<s:text name='i18n_Ok' />"/>
						<input id="cancel" class="btn1" type="button" value="<s:text name='i18n_Cancel' />"/>
					</div>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/project/project_share/application/granted/popExportOverView.js', function(popExportOverView) {
					popExportOverView.init();
				});
			</script>
		</body>
	</s:i18n>
</html>