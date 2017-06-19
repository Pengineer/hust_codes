<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored ="true"%> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>研究咨询报告</title>
		<s:include value="/innerBase.jsp"/>
	</head>
 
		<body>
			<div class="link_bar">
			当前位置：社科成果数据&nbsp;&gt;&nbsp;研究咨询报告
		</div>
		
		<div class="main">
			<div class="main_content">
				<div class="table_main_tr_adv">
					<s:include value="/product/consultation/search.jsp"/>
				</div>
				
				<textarea id="list_template" style="display:none;">
					<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<thead id="list_head">
							<tr class="table_title_tr">
								<sec:authorize ifAnyGranted="ROLE_PRODUCT_DELETE,ROLE_PRODUCT_AUDIT_ADD">
									<td width="20"><input id="check" name="check" type="checkbox"  title="点击全选/不选本页所有成果"/></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
								</sec:authorize>
								
								<td width="30">序号</td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
								
								<td><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按报告名称排序">报告名称</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
								
								<!-- 研究人员 -->
								<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)">
									<td width="85"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按所属单位排序">所属单位</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
								</s:if>
								<!-- 校级管理人员 -->
								<s:elseif test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@LOCAL_UNIVERSITY)">
									<td width="64"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按第一作者排序">第一作者</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
									<td width="100"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按所属部门排序">所属部门</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
								</s:elseif>
								<!-- 其它人员 -->
								<s:else>
									<td width="65"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按第一作者排序">第一作者</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
									<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@PROVINCE)">
										<td style="width:85px;"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按所属单位排序">所属单位</a></td>
									</s:if>
									<s:else>
										<td style="width:64px;"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按所属单位排序">所属单位</a></td>
									</s:else>
									<td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
								</s:else>
								
								<td width="90"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按采纳单位排序">采纳单位</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
							
								<!-- 研究人员 -->
								<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)">
									<td width="64"><a id="sortcolumn7" href="" class="{if sortColumn == 7}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按提交状态排序">提交状态</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
									<td width="64"><a id="sortcolumn7" href="" class="{if sortColumn == 7}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按审核状态排序">审核状态</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
								</s:if>
								<!-- 其它人员 -->
								<s:else>
									<td width="64"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按审核状态排序">审核状态</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
									<td width="64"><a id="sortcolumn8" href="" class="{if sortColumn == 8}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按审核时间排序">审核时间</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
								</s:else>
							
								<td width="55">成果文件</td>
							</tr>
						</thead>
						
						<tbody>
							{for item in root}
								<tr>
									<sec:authorize ifAnyGranted="ROLE_PRODUCT_DELETE, ROLE_PRODUCT_AUDIT_ADD">
										<td><input type="checkbox" name="entityIds" value="${item.laData[0]}"/></td><td></td>
									</sec:authorize>
									
									<td>${item.num}</td><td></td>
									
									<td class="table_txt_td"><a id="${item.laData[0]}" class="link1" href="" title="点击查看详细信息">${item.laData[1]}</a></td><td></td>
									
									<!-- 研究人员 -->
									<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)">
										<td><a id="${item.laData[7]}" class="view_university" href="">${item.laData[3]}</a></td><td></td>
									</s:if>
									<!-- 校级管理人员 -->
									<s:elseif test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@LOCAL_UNIVERSITY)">
										<td>
											{if item.laData[6]!=null && item.laData[6]!=''}
												<a id="${item.laData[6]}" class="view_author" href="">${item.laData[2]}</a>
											{else}
												${item.laData[2]}
											{/if}
										</td><td></td>
										<td>
											{if item.laData[9]!=''}
												<div class="author_dept">
													<a id="${item.laData[10]}" class="view_author_dept" href="">${item.laData[8]}</a>
												</div>
											{/if}
											{if item.laData[10]!=''}
												<div class="author_inst">
													<a id="${item.laData[11]}" class="view_author_inst" href="">${item.laData[8]}</a>
												</div>
											{/if}
										</td><td></td>
									</s:elseif>
									<!-- 其它人员 -->
									<s:else>
										<td>
											{if item.laData[6]!=null && item.laData[6]!=''}
												<a id="${item.laData[6]}" class="view_author" href="">${item.laData[2]}</a>
											{else}
												${item.laData[2]}
											{/if}
										</td><td></td>
										<td>
											 {if item.laData[7]!=null && item.laData[7]!=''}<a id="${item.laData[7]}" class="view_university" href="">${item.laData[3]}</a>
											 {else}${item.laData[3]}
											 {/if}
										</td><td></td>
									</s:else>
									
									<td class="_publication">${item.laData[4]}</td><td></td>
									
									<!-- 研究人员 -->
									<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)">
										<td>
			                  				{if item.laData[11] == 2}保存
			                  				{elseif item.laData[11] == 3}已提交
			                  				{/if}
				                  			</td><td></td>
										<td>
											{if item.laData[13] == 0}待审
											{elseif item.laData[13] == 3 && item.laData[12] == 1}不同意
											{elseif item.laData[13] == 3 && item.laData[12] == 2}同意
											{/if}
										</td><td></td>
									</s:if>
									<!-- 其它人员 -->
									<s:else>
										<td>
											{if item.laData[13] == 0}待审
											{elseif item.laData[13] == 3 && item.laData[12] == 1}不同意
											{elseif item.laData[13] == 3 && item.laData[12] == 2}同意
											{/if}
										</td><td></td>
										<td>${item.laData[14]}</td><td></td>
									</s:else>
									
									<td>
										{if item.laData[5]!=""}
											<a href=""  class="downlaod_product" name="${item.laData[5]}" id="${item.laData[0]}"><img src="image/ico03.gif" width="11" height="12" title="下载成果文件"/></a>
										{/if}
									</td>
								</tr>
							{forelse}
								<tr>
									<td align="center">>暂无符合条件的记录</td>
								</tr>
							{/for}
						</tbody>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<tr class="table_main_tr2">
							<td width="4"></td>
							<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY) || #session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)">
								<sec:authorize ifAnyGranted="ROLE_PRODUCT_ADD">
									<td width="58"><input id="list_add" class="btn1" type="button" value="添加"/></td>
								</sec:authorize>
							</s:if>
							<sec:authorize ifAnyGranted="ROLE_PRODUCT_DELETE">
								<td width="58"><input id="list_delete" type="button" class="btn1" value="删除" /></td>
							</sec:authorize>
							<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@EXPERT)<0 || #session.loginer.currentType.equals(@csdc.tool.bean.AccountType@STUDENT)">
								<sec:authorize ifAnyGranted="ROLE_PRODUCT_AUDIT_ADD">
									<td width="58"><input id="list_audit" type="button" class="btn1" value="审核" /></td>
								</sec:authorize>
							</s:if>
						</tr>
					</table>
				</textarea>
				<s:form id="list" theme="simple" action="delete" namespace="/product/consultation">
					<s:hidden id="pagenumber" name="pageNumber"/>
					<s:hidden id="type" name="type" value="1"/>
					<div id="list_container" style="display:none;"></div>
				</s:form>
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/product/consultation/list.js', function(list) {
				list.init();
			});
		</script>
	</body>
</html>