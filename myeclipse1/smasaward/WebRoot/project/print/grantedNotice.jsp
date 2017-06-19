<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
   		<title>立项通知</title>
		<s:include value="/innerBase.jsp" />
		<link rel="stylesheet" type="text/css" href="css/print/print.css" />
  	</head>
  	
  	<body>
    	<div id="printNotice" style="margin-left: auto;margin-right: auto;border:0px;">
			<s:iterator value="#request.dataList" status="stat">
				<s:if test="#request.dataList.size() != 0">
					<div class="content" style="page-break-after:always;border:0px solid #000; margin:0px; padding:0px; text-align:center;">
						
						<s:if test="#request.noticeType == 0">
						<%--  给单位立项通知书模板	--%>
						<div style="width: 555px;margin-left: auto;margin-right: auto; text-align:left;">
							<p style="height: 17pt;">&nbsp;</p>
							<p style="height: 17pt;">&nbsp;</p>
							<p style="height: 17pt;">&nbsp;</p>
							<p style="height: 17pt;">&nbsp;</p>
							<p style="height: 17pt;">&nbsp;</p>
							<p align="right" style="font-size: 14pt;">教社科司函［<s:property value="#request.grantYear"/>］280号</p>
							<p style="height: 17pt;">&nbsp;</p>
							<p align="center" style="font-size: 18pt;font-weight: bold;">关于<s:property value="#request.grantYear"/>年度教育部人文社会科学研究<br/>一般项目立项及一期拨款的通知</p>
							<p>&nbsp;</p>
							<p style="font-size: 14pt;">各省、自治区、直辖市教育厅（教委），新疆生产建设兵团教育局，有关部门（单位）教育司（局），有关高等学校：</p>
							<p style="font-size: 14pt;">　　<s:property value="#request.grantYear"/>年度教育部人文社会科学研究一般项目立项工作已经结束。现将有关情况通知如下：</p>
							<p style="font-size: 14pt;">　　各单位申报的<s:property value="#request.grantYear"/>年度教育部人文社会科学研究一般项目经我部组织专家评审并经公示，现予以正式批准立项。在继续设立西部和边疆地区项目的同时，为进一步支持新疆和西藏地区高校人文社会科学研究的繁荣发展，在今年的一般项目中增设新疆、西藏项目。各单位立项及拨款情况详见附件1。立项项目第一期经费按批准经费的80％拨款。</p>
							<p style="font-size: 14pt;">　　上述项目的研究工作应于2013年12月31日前完成。我部将于2012年进行项目中期检查，第二次拨款待中期检查通过后拨付，剩余经费待鉴定结项后拨付。</p>
							<p style="font-size: 14pt;">　　项目负责人应按照《教育部人文社会科学研究项目管理办法》的要求和申报的《教育部人文社会科学研究项目申请评审书》中设计的研究内容及研究计划开展项目研究，确保项目按期保质保量完成。按照规定，所有出版或发表的项目研究成果，须在显著位置按照不同的项目类别分别标注：&ldquo;教育部人文社会科学研究规划基金/青年基金/自筹经费项目&rdquo;、&ldquo;教育部人文社会科学研究西部和边疆地区项目&rdquo;、&ldquo;教育部人文社会科学研究新疆项目&rdquo;、&ldquo;教育部人文社会科学研究西藏项目&rdquo;等字样，以及相应的项目批准号，否则项目中期检查及结项验收不予通过。</p>
							<p style="font-size: 14pt;">　　上述拨款将由我部财务司直接拨至各高校财务计划内账户。教育部人文社会科学研究项目经费系中央财政专项资金。各高校要加强对中央财政专项资金的管理，经费开支范围和标准要严格按照教育部、财政部《面向21世纪教育振兴行动计划专项资金管理办法（修订）》（财教[2001]214号）和国家现行财务规章制度的要求执行，确保专款专用，不得以任何形式挪用、挤占、转移资金。</p>
							<p>&nbsp;</p>
							<p style="font-size: 14pt;">　　附件：<s:property value="#request.grantYear"/> 年度教育部人文社会科学研究一般项目立项及拨款情况一览表</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p align="center" style="font-size: 14pt;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 教育部社会科学司</p>
							<p align="center" style="font-size: 14pt;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span class="toChinese"><s:property value="#request.list[0]"/></span>年<span class="toChinese"><s:property value="#request.list[1]"/></span>月<span class="toChinese"><s:property value="#request.list[2]"/></span>日</p>
							<p>&nbsp;</p>
						</div>
						</s:if>
						
						<s:elseif test="#request.noticeType == 1">
						<%--  西部项目立项通知书模板	--%>
						<div style="width: 555px; margin-left: auto;margin-right: auto;text-align:left;">
							<p style="height: 17pt;">&nbsp;</p>
							<p style="height: 17pt;">&nbsp;</p>
							<p style="height: 17pt;">&nbsp;</p>
							<p style="height: 17pt;">&nbsp;</p>
							<p style="height: 17pt;">&nbsp;</p>
							<p align="right" style="font-size: 14pt;">教社科司函［<s:property value="#request.grantYear"/>］<s:property value="#request.dataList[#stat.index][1]"/>号</p>
							<p style="height: 17pt;">&nbsp;</p>
							<p align="center" style="font-size: 18pt;font-weight: bold;"><s:property value="#request.grantYear"/>年度教育部人文社会科学研究<br/>西部和边疆地区项目立项通知书</p>
							<p>&nbsp;</p>
							<p style="font-size: 14pt;"><s:property value="#request.dataList[#stat.index][2]"/> <s:property value="#request.dataList[#stat.index][3]"/> 同志：</p>
							<p style="font-size: 14pt;">　　您申报的《<s:property value="#request.dataList[#stat.index][4]"/>》课题，经我部组织专家评审并经公示，现正式批准为<s:property value="#request.dataList[#stat.index][1]"/>年度教育部人文社会科学研究西部和边疆地区<s:property value="#request.dataList[#stat.index][10]"/>。</p>
							<p style="font-size: 14pt;">　　项目批准号：<s:property value="#request.dataList[#stat.index][9]"/></p>
							<p style="font-size: 14pt;">　　批准经费： <s:property value="#request.dataList[#stat.index][5]"/> 万元。其中第一期拨款 <s:property value="#request.dataList[#stat.index][6]"/> 万元。该笔经费将由我部财务司于近期拨至你单位计划内财务账号，请查收。</p>
							<p style="font-size: 14pt;">　　项目完成时间：<s:property value="#request.dataList[#stat.index][7]"/>年12月31日前。我部将于<s:property value="#request.dataList[#stat.index][11]"/>年进行项目中期检查，第二次拨款待中期检查通过后拨付，剩余经费待鉴定结项后拨付。</p>
							<p style="font-size: 14pt;">　　请按照《教育部人文社会科学研究项目管理办法》的要求和您申报的《教育部人文社会科学研究项目申请评审书》中设计的研究内容及研究计划开展项目研究，确保项目按期保质保量完成。所有出版或发表的项目研究成果，须在显著位置标明&ldquo;教育部人文社会科学研究西部和边疆地区项目&rdquo;字样和项目批准号，否则项目中期检查及鉴定结项不予通过。</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p align="center" style="font-size: 14pt;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 教育部社会科学司</p>
							<p align="center" style="font-size: 14pt;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span class="toChinese"><s:property value="#request.list[0]"/></span>年<span class="toChinese"><s:property value="#request.list[1]"/></span>月<span class="toChinese"><s:property value="#request.list[2]"/></span>日</p>
							<p>&nbsp;</p>
						</div>
						</s:elseif>
						
						<s:elseif test="#request.noticeType == 2">
						<%--  西藏项目立项通知书模板	--%>
						<div style="width: 555px; margin-left: auto;margin-right: auto;text-align:left;">
							<p style="height: 17pt;">&nbsp;</p>
							<p style="height: 17pt;">&nbsp;</p>
							<p style="height: 17pt;">&nbsp;</p>
							<p style="height: 17pt;">&nbsp;</p>
							<p style="height: 17pt;">&nbsp;</p>
							<p align="right" style="font-size: 14pt;">教社科司函［<s:property value="#request.grantYear"/>］<s:property value="#request.dataList[#stat.index][1]"/>号</p>
							<p style="height: 17pt;">&nbsp;</p>
							<p align="center" style="font-size: 18pt;font-weight: bold;"><s:property value="#request.grantYear"/>年度教育部人文社会科学研究<br/>西藏项目立项通知书</p>
							<p>&nbsp;</p>
							<p style="font-size: 14pt;"><s:property value="#request.dataList[#stat.index][2]"/> <s:property value="#request.dataList[#stat.index][3]"/> 同志：</p>
							<p style="font-size: 14pt;">　　您申报的《<s:property value="#request.dataList[#stat.index][4]"/>》课题，经我部组织专家评审并经公示，现正式批准为<s:property value="#request.dataList[#stat.index][1]"/>年度教育部人文社会科学研究西藏项目<s:property value="#request.dataList[#stat.index][10]"/>。</p>
							<p style="font-size: 14pt;">　　项目批准号：<s:property value="#request.dataList[#stat.index][9]"/></p>
							<p style="font-size: 14pt;">　　批准经费： <s:property value="#request.dataList[#stat.index][5]"/> 万元。其中第一期拨款 <s:property value="#request.dataList[#stat.index][6]"/> 万元。该笔经费将由我部财务司于近期拨至你单位计划内财务账号，请查收。</p>
							<p style="font-size: 14pt;">　　项目完成时间：<s:property value="#request.dataList[#stat.index][7]"/>年12月31日前。我部将于<s:property value="#request.dataList[#stat.index][11]"/>年进行项目中期检查，第二次拨款待中期检查通过后拨付，剩余经费待鉴定结项后拨付。</p>
							<p style="font-size: 14pt;">　　请按照《教育部人文社会科学研究项目管理办法》的要求和您申报的《教育部人文社会科学研究项目申请评审书》中设计的研究内容及研究计划开展项目研究，确保项目按期保质保量完成。所有出版或发表的项目研究成果，须在显著位置标明&ldquo;教育部人文社会科学研究西藏项目&rdquo;字样和项目批准号，否则项目中期检查及鉴定结项不予通过。</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p align="center" style="font-size: 14pt;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 教育部社会科学司</p>
							<p align="center" style="font-size: 14pt;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span class="toChinese"><s:property value="#request.list[0]"/></span>年<span class="toChinese"><s:property value="#request.list[1]"/></span>月<span class="toChinese"><s:property value="#request.list[2]"/></span>日</p>
							<p>&nbsp;</p>
						</div>
						</s:elseif>
						
						<s:elseif test="#request.noticeType == 3">
						<%--  新疆项目立项通知书模板	--%>
						<div style="width: 555px; margin-left: auto;margin-right: auto;text-align:left;">
							<p style="height: 17pt;">&nbsp;</p>
							<p style="height: 17pt;">&nbsp;</p>
							<p style="height: 17pt;">&nbsp;</p>
							<p style="height: 17pt;">&nbsp;</p>
							<p style="height: 17pt;">&nbsp;</p>
							<p align="right" style="font-size: 14pt;">教社科司函［<s:property value="#request.grantYear"/>］<s:property value="#request.dataList[#stat.index][1]"/>号</p>
							<p style="height: 17pt;">&nbsp;</p>
							<p align="center" style="font-size: 18pt;font-weight: bold;"><s:property value="#request.grantYear"/>年度教育部人文社会科学研究<br/>新疆项目立项通知书</p>
							<p>&nbsp;</p>
							<p style="font-size: 14pt;"><s:property value="#request.dataList[#stat.index][2]"/> <s:property value="#request.dataList[#stat.index][3]"/> 同志：</p>
							<p style="font-size: 14pt;">　　您申报的《<s:property value="#request.dataList[#stat.index][4]"/>》课题，经我部组织专家评审并经公示，现正式批准为<s:property value="#request.dataList[#stat.index][1]"/>年度教育部人文社会科学研究新疆项目<s:property value="#request.dataList[#stat.index][10]"/>。</p>
							<p style="font-size: 14pt;">　　项目批准号：<s:property value="#request.dataList[#stat.index][9]"/></p>
							<p style="font-size: 14pt;">　　批准经费： <s:property value="#request.dataList[#stat.index][5]"/> 万元。其中第一期拨款 <s:property value="#request.dataList[#stat.index][6]"/> 万元。该笔经费将由我部财务司于近期拨至你单位计划内财务账号，请查收。</p>
							<p style="font-size: 14pt;">　　项目完成时间：<s:property value="#request.dataList[#stat.index][7]"/>年12月31日前。我部将于<s:property value="#request.dataList[#stat.index][11]"/>年进行项目中期检查，第二次拨款待中期检查通过后拨付，剩余经费待鉴定结项后拨付。</p>
							<p style="font-size: 14pt;">　　请按照《教育部人文社会科学研究项目管理办法》的要求和您申报的《教育部人文社会科学研究项目申请评审书》中设计的研究内容及研究计划开展项目研究，确保项目按期保质保量完成。所有出版或发表的项目研究成果，须在显著位置标明&ldquo;教育部人文社会科学研究新疆项目&rdquo;字样和项目批准号，否则项目中期检查及鉴定结项不予通过。</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p align="center" style="font-size: 14pt;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 教育部社会科学司</p>
							<p align="center" style="font-size: 14pt;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span class="toChinese"><s:property value="#request.list[0]"/></span>年<span class="toChinese"><s:property value="#request.list[1]"/></span>月<span class="toChinese"><s:property value="#request.list[2]"/></span>日</p>
							<p>&nbsp;</p>
						</div>
						</s:elseif>
						
						<s:elseif test="#request.noticeType == 4">
						<%--  一般项目立项通知书模板	--%>
						<div style="width: 555px; margin-left: auto;margin-right: auto;text-align:left;">
							<p style="height: 17pt;">&nbsp;</p>
							<p style="height: 17pt;">&nbsp;</p>
							<p style="height: 17pt;">&nbsp;</p>
							<p style="height: 17pt;">&nbsp;</p>
							<p style="height: 17pt;">&nbsp;</p>
							<p align="right" style="font-size: 14pt;">教社科司函［<s:property value="#request.grantYear"/>］<s:property value="#request.dataList[#stat.index][1]"/>号</p>
							<p style="height: 17pt;">&nbsp;</p>
							<p align="center" style="font-size: 18pt;font-weight: bold;"><s:property value="#request.grantYear"/>年度教育部人文社会科学研究<br/>一般项目立项通知书</p>
							<p>&nbsp;</p>
							<p style="font-size: 14pt;"><s:property value="#request.dataList[#stat.index][2]"/> <s:property value="#request.dataList[#stat.index][3]"/> 同志：</p>
							<p style="font-size: 14pt;">　　您申报的《<s:property value="#request.dataList[#stat.index][4]"/>》课题，经我部组织专家评审并经公示，现正式批准为<s:property value="#request.dataList[#stat.index][1]"/>年度教育部人文社会科学研究<s:property value="#request.dataList[#stat.index][10]"/>。</p>
							<p style="font-size: 14pt;">　　项目批准号：<s:property value="#request.dataList[#stat.index][9]"/></p>
							<p style="font-size: 14pt;">　　批准经费： <s:property value="#request.dataList[#stat.index][5]"/> 万元。其中第一期拨款 <s:property value="#request.dataList[#stat.index][6]"/> 万元。该笔经费将由我部财务司于近期拨至你单位计划内财务账号，请查收。</p>
							<p style="font-size: 14pt;">　　项目完成时间：<s:property value="#request.dataList[#stat.index][7]"/>年12月31日前。我部将于<s:property value="#request.dataList[#stat.index][11]"/>年进行项目中期检查，第二次拨款待中期检查通过后拨付，剩余经费待鉴定结项后拨付。</p>
							<p style="font-size: 14pt;">　　请按照《教育部人文社会科学研究项目管理办法》的要求和您申报的《教育部人文社会科学研究项目申请评审书》中设计的研究内容及研究计划开展项目研究，确保项目按期保质保量完成。所有出版或发表的项目研究成果，须在显著位置标明&ldquo;教育部人文社会科学研究规划基金/青年基金/自筹经费项目&rdquo;字样和项目批准号，否则项目中期检查及鉴定结项不予通过。</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p align="center" style="font-size: 14pt;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 教育部社会科学司</p>
							<p align="center" style="font-size: 14pt;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span class="toChinese"><s:property value="#request.list[0]"/></span>年<span class="toChinese"><s:property value="#request.list[1]"/></span>月<span class="toChinese"><s:property value="#request.list[2]"/></span>日</p>
							<p>&nbsp;</p>
						</div>
						</s:elseif>
					</div>
				</s:if>
			</s:iterator>
		</div>
		<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/project/print/print.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	</body>
</html>