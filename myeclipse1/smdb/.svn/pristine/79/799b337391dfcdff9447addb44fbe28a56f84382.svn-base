<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <title>清单添加</title>
    <s:include value="/innerBase.jsp" />
</head>

<body style = "width:480px">
    <div>
		<s:include value="/validateError.jsp" />
		<s:form id="addProjectFundingList" action = "funding/fundingList/project/add.action">
			<table width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="table_tr2">
					<td class="table_td11" width="100"><span class="table_title6">批次选择：</span></td>
					<td class="table_td12">
						<select name="fundingBatchId" id="fundingBatchId" class="select">
					        <s:iterator value="fundingBatchList" status="stat">
					            <option value="<s:property value='fundingBatchList[#stat.index][0]' /> ">
					                <s:property value='fundingBatchList[#stat.index][1]'/>
					            </option>
					        </s:iterator>
					    </select>
				    </td>
					<td class="table_td13" width="80"></td>
				</tr>
				
				<tr class="table_tr2">
					<td class="table_td11" width="100"><span class="table_title6">清单名称：</span></td>
					<td class="table_td12"><input name = "listName" id = "listName" class = "input_css"/></td>
					<td class="table_td13" width="80"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td11" width="100"><span class="table_title6">清单子类型：</span></td>
					<td class="table_td12">
						<s:select cssClass="select" name="subType" id = "subType" headerKey="-1" headerValue="--不限--"
				         value="-1" list="#{
							'general':'一般项目',
							'instp':'基地项目',
							'key':'重大攻关项目',
							'post':'后期资助项目',
							'entrust':'委托应急课题',
							'special':'专项任务项目'
						}"/>
					</td>
					<td class="table_td13" width="80"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td11" width="100"><span class="table_title6">清单子子类型：</span></td>
					<td class="table_td12">
						<s:select cssClass="select" name="subSubType" id = "subSubType" headerKey="-1" headerValue="--不限--"
					         value="-1" list="#{
								'granted':'立项经费',
								'mid':'中检经费',
								'end':'结项经费',
								'first':'一期经费',
								'second':'二期经费'
							}"/>
					</td>
					<td class="table_td13" width="80"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td11" width="100"><span class="table_title6">项目子类：</span></td>
					<td class="table_td12">
						<s:select cssClass="select" name="projectSubtypeId" id = "projectSubtypeId" value="%{searchQuery.projectSubtype}" 
							list="%{baseService.getSystemOptionMap('projectType', '01')}" headerKey="-1" headerValue="--不限--" />
					</td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td11" width="100"><span class="table_title6">年度：</span></td>
					<td class="table_td12"><input name = "year" id = "year" class = "input_css"/></td>
					<td class="table_td13" width="80"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td11" width="100"><span class="table_title6">拨款比率：</span></td>
					<td class="table_td12"><input name = "rate" id = "rate" class = "input_css"/></td>
					<td class="table_td13" width="80"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td11" width="100"><span class="table_title6">清单备注：</span></td>
					<td class="table_td12"><textarea name = "note" id = "note" class = "textarea_css"></textarea></td>
					<td class="table_td13" width="80"></td>
				</tr>
			</table>
			<div class="btn_div_view">
			<input id="submit" class="btn1 addSubmit" type="submit" value="提交"/>
			<input id="cancel" class="btn1" type="button" value="取消" />
			</div>
			</s:form>
		
	</div>
    <div class = "main">
    	
    </div>
    <script type="text/javascript">
    seajs.use('javascript/funding/researchProjectFee/projectFundingList/add.js', function (add) {
        $(function(){
     	   add.init()
        });
     });
    </script>
</body>

</html>
