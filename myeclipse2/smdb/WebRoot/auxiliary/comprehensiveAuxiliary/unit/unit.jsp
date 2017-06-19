<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Statistic">
		<head>
			<title><s:text name="i18n_Add" /></title>
			<s:include value="/innerBase.jsp" />
			<script type="text/javascript">
				seajs.use(['javascript/comprehensiveAuxiliary/unit/edit.js','javascript/comprehensiveAuxiliary/validate.js'], function(person, validate) {
					$(function(){
						person.init();
						validate.valid();
					})
				});
			</script>
		</head>

		<body>
			<div class="link_bar">
				<s:text name="i18n_CurrentPosition" />：综合辅助信息&nbsp;&gt;&nbsp;社科机构查询
			</div>

			<div class="choose_bar">
				<ul>
					<li><input id="listSearch1" class="btn1" type="button" value="<s:text name='编辑+'/>" style="display:none;"/></li>
				</ul>
			</div>
			<div class="choose_bar">
				<ul>
					<li><input id="listSearch2" class="btn1" type="button" value="<s:text name='编辑-'/>" style="display:none;"/></li>
				</ul>
			</div>
			<div id="config" style="display:">
				<s:form id="form_stat" action="unitQuery" namespace="/auxiliary/unit" theme="simple">
					<s:include value="config.jsp" />
				</s:form>
				<div class="btn_bar3" style="padding：0 0 10px 0;">
					<input id="submit" class="btn1" type="button" value="<s:text name='i18n_Ok'/>" />
				</div>
			</div>
			<br/>
			<div class="right2" id="chartIframe" style="display:none;">
				<div style="margin-bottom：10px; background: none repeat scroll 0 0 #F4F1F8;">
					<table id="list_parm" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_show_padding">
						<br/>
						<tr style ="color: #6C6C96;font-size: 18px;">
							<td width="130" style="paddin-top:15px;"><span>省级机构：</span>
							<td style="paddin-top:15px;">
								<div id="provinceName" ></div> 
							</td>
						</tr>
						<tr style ="color: #6C6C96;font-size: 18px;">
							<td width="130"><span>高校总数：</span>
							<td>
								<div id="totlaUniversity" ></div> 
							</td>
						</tr>
						<tr style ="color: #6C6C96;font-size: 18px;">
							<td width="130"><span>部署高校总数：</span>
							<td>
								<div id="totlaMoeUniversity" ></div> 
							</td>
						</tr>
						<tr style ="color: #6C6C96;font-size: 18px;">
							<td width="130"><span>地方高校总数：</span>
							<td>
								<div id="totlaLocUniversity" ></div> 
							</td>
						</tr>
					</table>
				</div>
				<div class="p_box_t_p">
					<div class="p_box_t_t_p"><s:text name='各高校排名' /></div>
					<div class="p_box_t_b_p"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
				</div>
				<div>
					<s:include value="viewScore.jsp" />
					<div id="container"></div>
				</div>
				<div id="tabs" class="p_box_bar" style="margin-top: 10px">
					<ul>
						<li class="j_person" ><a href="#person"><s:text name="人员分布" /></a></li>
						<li  class="j_project" ><a href="#project"><s:text name="项目分布" /></a></li>
						<li  class="j_product" ><a href="#product"><s:text name="成果分布" /></a></li>
						<li class="j_award""><a href="#award"><s:text name="奖励分布" /></a></li>
					</ul>
				</div>
	
				<div class="p_box">
					<div id="person">
						<s:include value="viewPerson.jsp" />
					</div>
					<div id="project">
						<s:include value="viewProject.jsp" />
					</div>
					<div id="product">
						<s:include value="viewProduct.jsp" />
					</div>
					<div id="award">
						<s:include value="viewAward.jsp" />
					</div>
				</div>
			</div>
		</body>
	</s:i18n>
</html>