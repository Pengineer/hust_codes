<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>添加</title>
		<s:include value="/innerBase.jsp" />
		<script type="text/javascript">
			seajs.use(['javascript/statistic/custom.js', 'javascript/statistic/custom/product.js'], function(custom, product) {
				$(function(){
					custom.init();
					product.init();
					product.initClick();
				})
			});
		</script>
	</head>

	<body>
		<div class="link_bar">
			当前位置：定制统计分析&nbsp;&gt;&nbsp;社科成果统计 
		</div>

		<div class="choose_bar">
			<ul>
				<li><input id="listSearch1" class="btn1" type="button" value="编辑+" style="display:none;"/></li>
			</ul>
		</div>
		<div class="choose_bar">
			<ul>
				<li><input id="listSearch2" class="btn1" type="button" value="编辑-" style="display:none;"/></li>
			</ul>
		</div>
		<s:form id="form_stat" action="customStatistic" namespace="/statistic/custom/product" theme="simple" target="sChart">
			<div class="main">
				<div class="main_content">
					<s:include value="/validateError.jsp" />
					<div class="title_statistic">参数配置</div>
					<table width="100%" border="0" cellspacing="2" cellpadding="2">
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">统计指标：</span></td>
							<td class="table_td31">
								<table width="100%" border="0" cellspacing="0" cellpadding="2" style="border: 1px solid rgb(170, 170, 170);padding-bottom:5px;">
									<tr>
										<td style="width:15%;">
											<input type="radio" name="statistic_index" value="[成果维度.成果类型].[成果类型].Members"/>
											<span>成果类型</span>
										</td>
										<td style="width:15%;">
											<input type="radio" name="statistic_index" value="[成果维度.学科门类].[学科门类].Members"/>
											<span>学科门类</span>
										</td>
										<td style="width:15%;">
											<input type="radio" id="statistic_data_university" name="statistic_index" value="[机构维度.高校名称].[高校名称].Members"/>
											<span>所属高校</span>
										</td>
										<td style="width:15%;">
											<input type="radio" name="statistic_index" value="[机构维度.省份名称].[省份名称].Members"/>
											<span>所属省份</span>
										</td>
										<td style="width:15%;">
											<input type="radio" name="statistic_index" value="subject" disabled="disabled"/>
											<span class="disable">成果形态</span>
										</td>
										<td style="width:15%;">
											<input type="radio" name="statistic_index" value="" disabled="disabled"/>
											<span class="disable">成果级别</span>
										</td>
									</tr>
									<tr>
										<td style="width:15%;">
											<input type="radio" name="statistic_index" value="" disabled="disabled"/>
											<span class="disable">成果学科门类</span>
										</td>
										<td style="width:15%;">
											<input type="radio" name="statistic_index" value="" disabled="disabled"/>
											<span class="disable">是否获奖</span>
										</td>
										<td style="width:15%;">
											<input type="radio" name="statistic_index" value="" disabled="disabled"/>
											<span class="disable">获奖等级</span>
										</td>
										<td style="width:15%;">
											<input type="radio" name="statistic_index" value="" disabled="disabled"/>
											<span class="disable">获奖届次</span>
										</td>
										<td style="width:15%;">
											<input type="radio" name="statistic_index" value="" disabled="disabled"/>
											<span class="disable">年度</span>
										</td>
										<td style="width:10%;"></td>
									</tr>
								</table>
							</td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">统计数据：</span></td>
							<td class="table_td3">
								<table width="100%" border="0" cellspacing="0" cellpadding="2" style="border: 1px solid rgb(170, 170, 170);padding-bottom:5px;">
									<tr>
										<td style="width:15%;">
											<input type="checkbox" name="statistic_data" value="[Measures].[成果总数]"/>
											<span>成果数量</span>
										</td>
										
									</tr>
								</table>
							</td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span style="_padding-top:5px;font-weight:bold;">切片指标：</span></td>
							<td class="table_td3">
								<table width="100%" border="0" cellspacing="0" cellpadding="2" style="border: 1px solid rgb(170, 170, 170);padding-bottom:5px;">
									<tr>
										<td style="width:20%;">
											<input onclick="checkUniversity(this.checked);" type="checkbox" id="slecetUniCheck"/>&nbsp;指定高校
										</td>
										<td>
											<div id="universityShow" style="display: none;"><input type="button" class="btn1 select_btn" id="selectUniversity" value="选择" />
											<s:hidden name="statistic_university_name" id="statistic_university_name" />
											<div id="gsjn" class="choose_show" style="display:none" ></div></div>
										</td>
									</tr>
								</table>
							</td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">显示条数：</span></td>
							<td class="table_td3">
								<table width="100%" border="0" cellspacing="0" cellpadding="2">
									<tr>
										<td style="width:15%;">
											<input type="radio" name="statistic_showLineNum" checked="checked" value="10"/>
											<span>前10条</span>
										</td>
										<td style="width:15%;">
											<input type="radio" name="statistic_showLineNum" value="20"/>
											<span>前20条</span>
										</td>
										<td style="width:15%;">
											<input type="radio" name="statistic_showLineNum" value="30"/>
											<span>前30条</span>
										</td>
										<td style="width:15%;">
											<input type="radio" id="self_define" name="statistic_showLineNum" value=""/>
											<span>自定义</span>
											<input type="text" size="5" id="self_define_input" disabled="disabled"/>
										</td>
										<td style="width:15%;">
											<input type="radio" name="statistic_showLineNum" value="-1"/>
											<span>全部</span>
										</td>
										<td style="width:5%;">
										</td>
										<td style="width:20%;">
											<input type="checkbox" name="statistic_showNonZero" checked="checked" value="1" />
											<span>只统计非零行</span>
										</td>
										<td></td>
									</tr>
								</table>
							</td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">指标排序：</span></td>
							<td class="table_td31">
								<table width="100%" border="0" cellspacing="0" cellpadding="2">
									<tr>
										<td style="width:20%;">
											<input type="radio" class="sortLabelByStatisticIndex" name="sortLabelByStatisticIndex" checked="checked" value="0"/>
											<span>默认</span>
										</td>
										<td style="width:20%;">
											<input type="radio" class="sortLabelByStatisticIndex" name="sortLabelByStatisticIndex" value="1"/>
											<span>降序</span>
										</td>
										<td style="width:20%;">
											<input type="radio" class="sortLabelByStatisticIndex" name="sortLabelByStatisticIndex" value="2"/>
											<span>升序</span>
										</td>
										<td></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">统计排序：</span></td>
							<td class="table_td3">
								<table width="100%" border="0" cellspacing="0" cellpadding="2">
									<tr>
										<td style="width:20%;">
											<input type="radio" class="sort_type" name="sort_type" checked="checked" value="1"/>
											<span>默认排序</span>
										</td>
										<td style="width:20%;">
											<input type="radio" class="sort_type" name="sort_type" value="2"/>
											<span>自定义排序</span>
										</td>
										<td style="width:20%;">
											<input type="radio" class="sort_type" name="sort_type" value="3"/>
											<span>不排序</span>
										</td>
										<td></td>
									</tr>
									<tr>
										<td colspan="4">
											<div style="height: 60px; padding: 4px; border: 1px solid rgb(169, 169, 169);display:none;" class="title_bar" id="custom_sort">
												<table width="100%" border="0" cellspacing="0" cellpadding="2">
													<tr>
														<td style="width:85px;text-align:right;padding-top:2px;_padding-top:8px;">排序列：</td>
														<td>
															<table id="sort_column" border="0" cellspacing="2" cellpadding="2"></table>
														</td>
														<td></td>
													</tr>
													<tr>
														<td style="width:85px;text-align:right;">排序方式：</td>
														<td>
															<table border="0" cellspacing="2" cellpadding="2">
																<tr>
																	<td style="width:120px;">
																		<input type="radio" class="sort_type" name="statistic_sortType" checked="checked" value="1"/>
																		<span>降序</span>
																	</td>
																	<td style="width:120px;">
																		<input type="radio" class="sort_type" name="statistic_sortType" value="2"/>
																		<span>升序</span>
																	</td>
																	<td></td>
																</tr>
															</table>
														</td>
														<td></td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">统计图表：</span></td>
							<td class="table_td3">
								<table width="100%" border="0" cellspacing="0" cellpadding="2">
									<tr>
										<td style="width:20%;">
											<input type="radio" name="chartType" checked="checked" value="NORMAL_BAR"/>
											<span>垂直柱图</span>
										</td>
										<td style="width:20%;">
											<input type="radio" name="chartType" value="PIE"/>
											<span>饼状图</span>
										</td>
										<td style="width:20%;">
											<input type="radio" name="chartType" value="DOT_LINE"/>
											<span>折线图</span>
										</td>
										<td style="width:20%;">
											<input type="checkbox" id="showDataLabels" checked="checked" />
											<span>同时显示所有点信息</span>
										</td>
										<td></td>
									</tr>
								</table>
							</td>
							<td class="table_td4"></td>
						</tr>
					</table>
				</div> 
				<div class="btn_bar3">
					<input id="submit" class="btn1" type="submit" value="确定" />
					<input id="reset" class="btn1" type="reset" value="重置"/>
				</div>
			</div>
		</s:form>
		<div class="right2" id="chartIframe">
			<iframe id="sChart" width="100%" height="500px" name="sChart" frameborder="0" marginheight="0" marginwidth="0" scrolling="no"></iframe>
		</div>
	</body>
</html>
