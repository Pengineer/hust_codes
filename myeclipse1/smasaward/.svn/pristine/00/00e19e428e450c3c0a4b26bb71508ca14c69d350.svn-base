<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<title>打印</title>
			<title>一般项目</title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="css/project/project.css" />
		</head>

		<body>
			<div style="width:450px;">
				<div style="height:300px; overflow-y:scroll; overflow-x:hidden;">
					<div class="expand base_info" style="border:0;cursor:pointer;margin-top:0;" title="点击展开">
						<div class="title_dis">选择立项通知打印类型</div>
					</div>
					<div class="hide" style="display:none">
						<table width="100%" border="0" cellspacing="2" cellpadding="0">
							<tr class="table_tr2">
								<td style="width:110px;text-align:right;">通知类型：</td>
								<td>
									<s:select theme="simple" name="noticeType" headerKey="-1" headerValue="--%{'请选择'}--" id="noticeType" list="#{'0': '给单位', '1' : '西部项目', '2' : '西藏项目', '3' : '新疆项目', '4': '一般项目'}"/>
								</td>	
								<td>
									年份：<s:select id="noticeYear" theme="simple" name="grantYear" list="%{#application.allYears}" value="%{#session.defaultYear}" />
								</td>	
							</tr>
						</table>
						<div class="btn_div">
							<input id="noticePrint" type="button" class="btn" value="确定" />
							<input type="button" class="cancel btn" value="取消" />
						</div>
					</div>
				
					<div class="expand base_info" style="border:0;cursor:pointer;" title="点击展开">
						<div class="title_dis">选择立项清单打印类型</div>
					</div>
					<div class="hide" style="display:none">
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td style="width:110px;text-align:right;">清单类型：</td>
							<td>
								<s:select id="listType" theme="simple" name="listType" headerKey="-1" headerValue="--%{'请选择'}--" list="#{'1': '给高校', '2' : '给省厅', '3' : '给部委'}" />
							</td>
							<td>
								年份：<s:select id="listYear" theme="simple" name="grantYear" list="%{#application.allYears}" value="%{#session.defaultYear}" />
							</td>	
						</tr>
						<%-- 1、给高校 --%>
						<tr id="selectUniv" class="selectBoxIsDisabled" style="display:none;">
							<td style="width:110px;text-align:right;">高校所在地：</td>
							<td>
								<s:select id="univProvName" cssClass="provName" theme="simple" name="provinceName" headerKey="-1" headerValue="--所有高校--" list="#{}"/>
							</td>
							<td></td>
						</tr>
						<%-- 2、给省厅 --%>
						<tr id="selectProv" class="selectBoxIsDisabled" style="display:none;">
							<td style="width:110px;text-align:right;">选择省厅：</td>
							<td>
								<s:select id="provName" cssClass="provName" theme="simple" name="provName" headerKey="-1" headerValue="--所有省属高校--" list="#{}"/>
							</td>
							<td></td>
						</tr>
						<%-- 3、给部位 --%>
						<tr id="selectMin" class="selectBoxIsDisabled" style="display:none;">
							<td style="width:110px;text-align:right;">选择部委：</td>
							<td>
								<s:select id="minName" theme="simple" name="minName" headerKey="-1" headerValue="--所有部委高校--" list="#{'教育部' : '教育部直属高校', '其他部委': '其他部委所属高校'}"/>
							</td>
<%--							<td>--%>
<%--								<s:select id="minCode" theme="simple" name="minName" headerKey="-1" headerValue="--请选择--" list="#{'201' : '中央办公厅', '301': '外交部', '308' : '国家民族事务委员会', --%>
<%--								'312' : '公安部', '315' : '司法部', '339' : '工业和信息化部', '348' : '交通运输部', '361' : '卫生部', '415' : '海关总署', '419' : '中国地震局', '432' : '国家林业局', '435' : '国务院侨务办公室', --%>
<%--								'450' : '国家安全生产监督管理总局', '451' : '国家体育总局', '491' : '中国科学院', '711' : '中华全国总工会', '712' : '中国共产主义青年团中央委员会', '713' : '中华全国妇女联合会', '790' : '总装备部'}"/>--%>
<%--							</td>--%>
						</tr>
					</table>
					<table>
						<tr id="selectBox" style="display:none;">
							<td style="width:110px;text-align:right;">指定高校：</td>
							<td>
								<s:select id="univName" theme="simple" name="univName" headerKey="-1" headerValue="--所有--" list="#{}" disabled="true"/>
							</td>
							<td></td>
						</tr>
					</table>
					
					<div class="btn_div">
						<input id="listPrint" type="button" class="confirm btn" value="确定" />
						<input type="button" class="cancel btn" value="取消" />
					</div>
				</div>
			</div>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/pop/pop_init.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/project/print/print.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>