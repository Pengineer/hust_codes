<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>项目分类预测</title>
		<s:include value="/innerBase.jsp" />
	</head>

	<body>
		<div class="link_bar">
			当前位置：分类预测挖掘&nbsp;&gt;&nbsp;项目预测配置
		</div>
		
		<div class="main">
			<div class="main_content">
				<s:form id="form_dm" action="toPredict" namespace="/dataMining/classification" theme="simple" >
					<table width="100%" border="0" cellspacing="2" cellpadding="2">
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">预测类型：</span></td>
							<td class="table_td3"><s:select cssClass="select" id="predictType" name="predictType" headerKey="-1" headerValue="--请选择--" list="#{'general_mid':'一般项目中检预测', 'general_end':'一般项目结项预测', 'key_mid':'重大攻关项目中检预测', 'key_end':'重大攻关项目结项预测',
								 'instp_mid':'基地项目中检预测', 'instp_end':'基地项目结项预测', 'post_end':'后期资助项目结项预测', 'entrust_end':'应急委托课题结项预测'}"/></td>
							<td class="table_td4"></td>
						</tr>
						
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">预测年度：</span></td>
							<td class="table_td3"><input type="text" name="predictYear" value="2013"/></td>
							<td class="table_td4"></td>
						</tr>
						
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">预测模型：</span></td>
							<td class="table_td3"><s:textfield id="modelName" cssClass="input_css6" readonly="true"></s:textfield> </td>
							<td class="table_td4"><input id="open_update" class="btn2" type="button" value="更新模型" /></td>
						</tr>
						
						<tr id="isToDataBase" class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">结果入库：</span></td>
							<td class="table_td3"><s:radio name="toDataBase" value="0" list="#{'1':'入库', '0':'不入库'}" /></td>
							<td class="table_td4"></td>
						</tr>
						
						<tr class="table_tr2" id="predictorVariable" style="display:none">
							<td class="table_td2" width="110">
								<span class="table_title3" style="_padding-top:5px;font-weight:bold;">预测因子：</span>
								<span style="font-size:11.5px;_font-size:0.65em;color:#6c6c96;padding-right:10px;">
									全选&nbsp;<input class="select_all" type="checkbox" checked="true"/>
								</span>
							</td>
							<td class="table_td3">
								<table width="100%" border="0" cellspacing="0" cellpadding="2" style="border:0px; padding-bottom:5px;">
									<tr>
										<td style="width:10%;">
											<input type="checkbox" class="selected" name="predictorVariables" value="SUBTYPE" checked="true"/>
											<span>项目子类</span>
										</td>
										<td style="width:10%;">
											<input type="checkbox" class="selected" name="predictorVariables" value="DISCIPLINETYPE" checked="true"/>
											<span>学科门类</span>
										</td>
										<td style="width:10%;">
											<input type="checkbox" class="selected" name="predictorVariables" value="PASSMID" checked="true"/>
											<span>中检状态</span>
										</td>
									</tr>
									<tr>	
										<td style="width:10%;">
											<input type="checkbox" class="selected" name="predictorVariables" value="YEARS" checked="true"/>
											<span>进展年数</span>
										</td>
										<td style="width:10%;">
											<input type="checkbox" class="selected" name="predictorVariables" value="UNIVTYPE" checked="true"/>
											<span>高校类型</span>
										</td>
										<td style="width:10%;">
											<input type="checkbox"  class="selected" name="predictorVariables" value="LASTDEGREE" checked="true"/>
											<span>负责人学历</span>
										</td>
									</tr>
									<tr>
										<td style="width:10%;">
											<input type="checkbox" class="selected" name="predictorVariables" value="TITLE" checked="true"/>
											<span>负责人职称</span>
										</td>
										<td style="width:10%;">
											<input type="checkbox" class="selected" name="predictorVariables" value="GENDER" checked="true"/>
											<span>负责人性别</span>
										</td>
										<td style="width:10%;">
											<input type="checkbox" class="selected" name="predictorVariables" value="AGEGROUP" checked="true"/>
											<span>负责人年龄分布</span>
										</td>
										<td style="width:10%;"></td>
									</tr>
								</table>
							</td>
							<td id="cb_error" class="table_td4"></td>
						</tr>
					</table>
					
					<div class="btn_bar3">
						<input id="predict" class="btn2" type="submit" value="预测" />
						<sec:authorize ifAnyGranted="ROLE_DATAMINING_CLASSIFICATION_TRAINMODEL">
							<input id="train" class="btn2" type="submit" value="训练" style="display:none"/>
						</sec:authorize>
						<input id="reset" class="btn2" type="reset" value="重置" />
						<span id="updating" style="color: red; display: none;">正在训练，请稍候...</span>
					</div>
				</s:form>
			</div>
		</div>
		
		<script type="text/javascript">
			seajs.use(['javascript/dataMining/classification/validate.js', 'javascript/dataMining/classification/toPredict.js'], function(validate, toPredict) {
				$(function(){
					validate.valid();
					toPredict.init();
				});
			});
		</script>
	</body>
</html>