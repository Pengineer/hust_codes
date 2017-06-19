<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<title>选择评审专家</title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="css/project/project.css" />
			<link rel="stylesheet" href="tool/ztree/css/zTreeStyle.css" type="text/css" />
		</head>
		
		<body onload="init();">
			<div id="container" style="margin-left: 10px;margin-right: 10px;">
			
				<div id="base_info" class="base_info" onclick="toggle_view('base_info')">
					<div class="img_undis"></div>
					<div class="title_dis">检索评审专家</div>
				</div>
				<div id="disAll" style="display:none;width:100%">
					<table class="table_pview" cellspacing="0" cellpadding="0">
						<tr style="background-color:#E6E6E6;"><td colspan="8" align="left" style="border: solid #999;border-width: 0 1px 1px 1px;">&nbsp;学科代码及名称</td></tr>
					</table>
					<table cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<td style="border: solid #999;border-width: 0 1px 0 1px;">
								<table>	
									<s:iterator value="disList" status="stat">
										<s:if test="(#stat.index)%4==0"><tr></s:if>
										<td><input type="checkbox" id="<s:property value="disList[#stat.index].code" />" name="disids" onclick="selectDis(this);" value="false" /></td>
										<td align="left"><s:property value="disList[#stat.index].code" /><s:property value="disList[#stat.index].name" /></td>
										<s:if test="(#stat.index+1)%4==0"></tr></s:if>
									</s:iterator>
								</table>	
							</td>
						</tr>
					</table>
					<table class="table_pview" cellspacing="0" cellpadding="0">
						<s:hidden name="hidid"/>
						<s:form theme="simple" id="expertTree">
							<s:hidden id="selectedExpertIds" name="selectExp"/>
							<s:hidden name="discipline"/>
							<s:hidden id="projectId" name="projectId"/>
							<s:hidden name="isReviewable" value="1"/>
							<tr><td align="center" style="background-color:#E6E6E6;" width="80px">高校名称</td><td align="left">&nbsp;&nbsp;<s:textfield cssStyle="width:666px" theme="simple" name="uname"/></td></tr>
							<tr><td align="center" style="background-color:#E6E6E6;" width="80px">专家姓名</td><td align="left">&nbsp;&nbsp;<s:textfield cssStyle="width:666px" theme="simple" name="ename"/></td></tr>
						</s:form>
					</table>
					<table width="100%" height="30px">
						<tr><td align="center"><input class="btn" type="button" value="检索并生成专家树" onclick="create();"/></td></tr>
					</table>
				</div>
				
				<div id="exp_info" class="base_info" onclick="toggle_view('exp_info')">
					<div class="img_dis"></div>
					<div class="title_dis">分配评审专家</div>	
				</div>
				<div id="loading"><center><img src="image/project/loading.gif"/>正在加载专家树，请稍后...</center></div>
				<div id="expall" style="width:100%;display:none;">
					<table cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<td style="width:50%; border: solid #999;border-width: 0 0 0 0;">
								<table class="table_eview" cellspacing="0" cellpadding="0">
									<tr><th class="thhead" style="border: solid #999;border-width: 0 0 1px 1px;">◇&nbsp;专家树</th></tr>
								</table>
								<div id="treeId"><ul id="expert_tree" class="ztree"></ul></div>
							</td>
							
							<td style="width:50%; border: solid #999;border-width: 0 0 0 0;">
								<table class="table_eview" cellspacing="0" cellpadding="0">
									<tr><th class="thhead" style="border: solid #999;border-width: 0 1px 1px 1px;">◇&nbsp;已选专家</th></tr>
								</table>
								<div id="expList"></div>
							</td>
						</tr>
					</table>
				</div>
				<s:hidden name="selectExpBk"/>
				<s:form id="expertTreeBk"></s:form>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="tool/ztree/js/jquery.ztree.core-3.3.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="tool/ztree/js/jquery.ztree.excheck-3.3.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="dwr/interface/instpService.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type='text/javascript' src='dwr/engine.js'></script>
			<script type='text/javascript' src='dwr/util.js'></script>
			<script type="text/javascript" src="javascript/project/instp/expertTree.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/popLayer.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/popLayer.self.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>