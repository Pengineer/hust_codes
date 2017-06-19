<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>添加</title>
		<s:include value="/innerBase.jsp" />
		<script type="text/javascript">
			seajs.use(['javascript/statistic/custom.js', 'javascript/statistic/custom/project.js'], function(custom, project) {
				$(function(){
					custom.init();
					project.init();
					project.initClick();
				})
			});
		</script>
	</head>

	<body>
		<div class="link_bar">
			当前位置：定制统计分析&nbsp;&gt;&nbsp;社科项目统计 
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
		
		<s:form id="form_stat" action="customStatistic" namespace="/statistic/custom/project" theme="simple" target="sChart">
			<div class="main stat">
				<div class="main_content">
					<s:include value="/validateError.jsp"/>
					<div class="title_statistic">参数配置</div>
					<table width="100%" border="0" cellspacing="2" cellpadding="2">
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">统计指标：</span></td>
							<td class="table_td31">
								<table width="100%" border="0" cellspacing="0" cellpadding="2" style="border: 1px solid rgb(170, 170, 170);padding-bottom:5px;">
									<tr>
										<td style="width:10%;">
											<input class="statistic_index" type="radio" id="statistic_index_university" name="statistic_index" value="[机构维度.高校名称].[高校名称].Members"/>
											<span>项目依托高校</span>
										</td>
										<td style="width:10%;">
											<input class="statistic_index" type="radio" id="statistic_index_discipline" name="statistic_index" value="[项目维度.学科门类].[学科门类].Members"/>
											<span>项目学科门类</span>
										</td>
										<td style="width:10%;">
											<input class="statistic_index" type="radio" id="statistic_index_productType" name="statistic_index" value="[项目维度.成果形式].[成果形式].Members"/>
											<span>项目成果形式</span>
										</td>
										<td style="width:10%;">
											<input class="statistic_index" type="radio" id="statistic_index_projectType" name="statistic_index" value="[项目维度.项目类型].[项目类型].Members"/>
											<span>项目类型</span>
										</td>
										<td style="width:10%;">
											<input class="statistic_index" type="radio" id="statistic_index_subType" name="statistic_index" value="[项目维度.项目子类].[项目子类].Members"/>
											<span>项目子类</span>
										</td>
										<td style="width:10%;">
											<input class="statistic_index" type="radio" id="statistic_index_projectArea" name="statistic_index" value="[项目维度.项目区域].[项目区域].Members"/>
											<span>项目区域</span>
										</td>
										<td style="width:10%;">
											<input class="statistic_index" type="radio" id="statistic_index_projectYear" name="statistic_index" value="[项目维度.项目年度].[项目年度].Members"/>
											<span>项目年度</span>
										</td>
									</tr>
									<tr>
										<td style="width:10%;">
											<input class="statistic_index" type="radio" name="statistic_index" value="[人员维度.职称].[职称].Members"/>
											<span>负责人职称</span>
										</td>
										<td style="width:10%;">
											<input class="statistic_index" type="radio" name="statistic_index" value="[项目维度.负责人年龄段].[负责人年龄段].Members"/>
											<span>负责人年龄段</span>
										</td>
										<td style="width:10%;">
											<input class="statistic_index" type="radio" name="statistic_index" value="[项目维度.负责人性别].[负责人性别].Members"/>
											<span>负责人性别</span>
										</td>
										<td style="width:10%;">
											<input class="statistic_index" type="radio" name="statistic_index" value="[项目维度.负责人学位].[负责人学位].Members"/>
											<span>负责人学位</span>
										</td>
										<td style="width:10%;">
											<input class="statistic_index" type="radio" id="statistic_index_province" name="statistic_index" value="[机构维度.省份名称].[省份名称].Members" />
											<span>高校所在省份</span>
										</td>
										<td style="width:10%;">
											<input class="statistic_index" type="radio" name="statistic_index" value="[项目维度.中检次数].[中检次数].Members"/>
											<span>项目中检次数</span>
										</td>
										<td style="width:10%;">
											<input class="statistic_index" type="radio" id="statistic_index_evaluType" name="statistic_index" value="[项目维度.评审类型].[评审类型].Members"/>
											<span>项目评审类型</span>
										</td>
									</tr>
									<tr>
										<td style="width:10%;">
											<input class="statistic_index" type="radio" id="statistic_index_universityType" name="statistic_index" value="[机构维度.高校类别].[高校类别].Members"/>
											<span>高校类别</span>
										</td>
										<td style="width:10%;">
											<input class="statistic_index" type="radio" id="statistic_index_applyExcelle" name="statistic_index" value="[项目维度.申请优秀成果].[申请优秀成果].Members"/>
											<span>申请优秀成果</span>
										</td>
										<td style="width:10%;">
											<input class="statistic_index" type="radio" id="statistic_index_endYears" name="statistic_index" value="[项目维度.结项年限].[结项年限].Members"/>
											<span>结项年限</span>
										</td>
										<td style="width:10%;">
											<input class="statistic_index" type="radio" id="statistic_index_researchType" name="statistic_index" value="[项目维度.研究类型].[研究类型].Members"/>
											<span>研究类型</span>
										</td>
									</tr>
								</table>
							</td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2" width="110">
								<span class="table_title3" style="_padding-top:5px;font-weight:bold;">统计数据：</span>
								<span style="font-size:11.5px;_font-size:0.65em;color:#6c6c96;padding-right:10px;">
									全选&nbsp;<input class="vertical-align:middle; margin-bottom:2px;" type="checkbox" name="select_all" value="0"/>
								</span>
							</td>
							<td class="table_td31">
								<table width="100%" border="0" cellspacing="0" cellpadding="2" style="border: 1px solid rgb(170, 170, 170);padding-bottom:5px;">
									<tr>
										<td style="width:10%;">
											<input type="checkbox" class="selected" name="statistic_data" value="[Measures].[申请数]"/>
											<span>申请数</span>
										</td>
										<td style="width:10%;">
											<input type="checkbox" class="selected" name="statistic_data" value="[Measures].[立项数]"/>
											<span>立项数</span>
										</td>
										<td style="width:10%;">
											<input type="checkbox" class="selected" name="statistic_data" value="[Measures].[立项率]"/>
											<span>立项率</span>
										</td>
										<td style="width:10%;">
											<input type="checkbox" class="selected" name="statistic_data" value="[Measures].[中检数]" />
											<span>中检数</span>
										</td>
										<td style="width:10%;">
											<input type="checkbox" class="selected" name="statistic_data" value="[Measures].[中检率]" />
											<span>中检率</span>
										</td>
										<td style="width:10%;">
											<input type="checkbox"  class="selected" name="statistic_data" value="[Measures].[结项数]" />
											<span>结项数</span>
										</td>
									</tr>
									<tr>
										<td style="width:10%;">
											<input type="checkbox" class="selected" name="statistic_data" value="[Measures].[结项率]" />
											<span>结项率</span>
										</td>
										<td style="width:10%;">
											<input type="checkbox" class="selected" name="statistic_data" value="[Measures].[批准经费]" />
											<span>批准经费（万元）</span>
										</td>
										<td style="width:10%;">
											<input type="checkbox" name="statistic_data" value="" disabled="disabled"/>
											<span class="disable">支出经费</span>
										</td>
										<td style="width:10%;">
											<input type="checkbox" name="statistic_data" value="" disabled="disabled"/>
											<span class="disable">人均支出经费</span>
										</td>
										<td style="width:10%;">
											<input type="checkbox" name="statistic_data" value="" disabled="disabled"/>
											<span class="disable">支出经费同比增加</span>
										</td>
										<td style="width:10%;"></td>
									</tr>
								</table>
							</td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span style="_padding-top:5px;font-weight:bold;">切片指标：</span></td>
							<td class="table_td31">
								<table width="100%" border="0" cellspacing="0" cellpadding="2" style="border: 1px solid rgb(170, 170, 170);padding-bottom:5px;">
									<tr>
										<td style="width:20%;">
											<input onclick="checkUniversity(this.checked);" type="checkbox" id="selectUniversityCheck"/>&nbsp;指定依托高校
										</td>
										<td>
											<div id="universityShow" style="display: none;"><input type="button" class="btn1 select_btn" id="selectUniversity" value="选择" />
											<s:hidden name="statistic_university" id="statistic_university" />
											<div id="universityDiv" class="choose_show" style="display:none" ></div></div>
										</td>
									</tr>
									<tr>
										<td style="width:20%;">
											<input onclick="checkDiscipline(this.checked);" type="checkbox" id="selectDisciplineCheck"/>&nbsp;指定学科门类
										</td>
										<td>
											<div id="disciplineShow" style="display: none;"><input type="button" class="btn1 select_btn" id="selectDiscipline" value="选择" />
											<s:hidden name="statistic_discipline" id="statistic_discipline" />
											<div id="disciplineDiv" class="choose_show" style="display:none" ></div></div>
										</td>
									</tr>
									<tr>
										<td style="width:20%;">
											<input onclick="checkProjectType(this.checked);" type="checkbox" id="selectProjectTypeCheck"/>&nbsp;指定项目类型
										</td>
										<td>
											<div id="projectTypeShow" style="display: none;"><s:select cssClass="select" id="statistic_projectType" name="statistic_projectType" list="#{'':'--请选择--', '一般项目':'一般项目', 
											'重大攻关项目':'重大攻关项目', '基地项目':'基地项目', '后期资助项目':'后期资助项目', '应急委托课题':'应急委托课题', '专项任务项目':'专项任务项目'}"/></div>
										</td>
									</tr>
									<tr>
										<td style="width:20%;">
											<input onclick="checkSubType(this.checked);" type="checkbox" id="selectSubTypeCheck"/>&nbsp;指定项目子类
										</td>
										<td>
											<div id="subTypeShow" style="display: none;"><s:select cssClass="select" id="statistic_subType" name="statistic_subType" list="#{'':'--请选择--', '青年基金项目':'青年基金项目', '规划基金项目':'规划基金项目', '自筹经费项目':'自筹经费项目'}"/></div>
										</td>
									</tr>
									<tr>
										<td style="width:20%;">
											<input onclick="checkProjectArea(this.checked);" type="checkbox" id="selectAreaCheck"/>&nbsp;指定项目区域
										</td>
										<td>
											<div id="projectAreaShow" style="display: none;"><s:select id="statistic_projectArea" name="statistic_projectArea" list="#{'':'--请选择--', '西部项目':'西部项目', '新疆项目':'新疆项目', '西藏项目':'西藏项目', '一般项目':'一般项目'}"/></div>
										</td>
									</tr>
									<tr>
										<td style="width:20%;">
											<input onclick="checkProductType(this.checked);" type="checkbox" id="selecetProductTypeCheck"/>&nbsp;指定成果形式
										</td>
										<td>
											<div id="productTypeshow" style="display: none;">
												<table style="border: 1px solid rgb(150, 150, 150);">
													<tr>
														<td>
															&nbsp;<input id="论文" onclick="generateProductType();" name="productType" type="checkbox"/>论文&nbsp;&nbsp;<input id="著作" onclick="generateProductType();" name="productType" type="checkbox"/>著作&nbsp;&nbsp;<input id="研究咨询报告" onclick="generateProductType();" name="productType" type="checkbox"/>研究咨询报告&nbsp;&nbsp;<input id="专利" onclick="generateProductType();" name="productType" type="checkbox"/>专利&nbsp;&nbsp;<input id="电子出版物" onclick="generateProductType();" name="productType" type="checkbox"/>电子出版物&nbsp;
														</td>
													</tr>
												</table>
											</div>
											<s:hidden name="statistic_productType" id="statistic_productType"/>
										</td>
									</tr>
									<tr>
										<td style="width:20%;">
											<input onclick="checkProvince(this.checked);" type="checkbox" id="selectProvinceCheck"/>&nbsp;指定省份名称
										</td>
										<td>
											<div id="provinceShow" style="display: none;"><s:select id="statistic_province" name="statistic_province" list="#{'':'--请选择--', '北京市':'北京市', '天津市':'天津市', '河北省':'河北省', '山西省':'山西省', '内蒙古自治区':'内蒙古自治区', '辽宁省':'辽宁省', '吉林省':'吉林省', '黑龙江省':'黑龙江省', '上海市':'上海市', '江苏省':'江苏省', '浙江省':'浙江省', '安徽省':'安徽省', '福建省':'福建省', '江西省':'江西省', '山东省':'山东省', '河南省':'河南省', '湖北省':'湖北省', '湖南省':'湖南省', '广东省':'广东省', '广西壮族自治区':'广西壮族自治区', '海南省':'海南省', '重庆市':'重庆市', '四川省':'四川省', '贵州省':'贵州省', '云南省':'云南省', '西藏自治区':'西藏自治区', '陕西省':'陕西省', '甘肃省':'甘肃省', '青海省':'青海省', '宁夏回族自治区':'宁夏回族自治区', '新疆维吾尔自治区':'新疆维吾尔自治区'}"/></div>
										</td>
									</tr>
									<tr>
										<td style="width:20%;">
											<input onclick="checkEvaluType(this.checked);" type="checkbox" id="selectEvaluTypeCheck"/>&nbsp;指定评审类型
										</td>
										<td>
											<div id="evaluTypeShow" style="display: none;"><s:select id="statistic_evaluType" name="statistic_evaluType" list="#{'':'--请选择--', '免鉴定':'免鉴定', '通讯评审':'通讯评审', '会议评审':'会议评审', '默认':'默认'}"/></div>
										</td>
									</tr>
									<tr>
										<td style="width:20%;">
											<input onclick="checkUniversityType(this.checked);" type="checkbox" id="selectUniversityTypeCheck"/>&nbsp;指定高校类别
										</td>
										<td>
											<div id="universityTypeShow" style="display: none;"><s:select id="statistic_universityType" name="statistic_universityType" list="#{'':'--请选择--', '地方高校':'地方高校', '部属高校':'部属高校'}"/></div>
										</td>
									</tr>
									<tr>
										<td style="width:20%;">
											<input onclick="checkUniversityOrganizer(this.checked);" type="checkbox" id="selectUniversityOrganizerCheck"/>&nbsp;指定高校举办者
										</td>
										<td>
											<div id="universityOrganizerShow" style="display: none;"><s:select id="statistic_universityOrganizer" name="statistic_universityOrganizer" headerKey="" headerValue="--请选择--" list="%{statisticService.getUniversityOrganizer()}" /></div>
										</td>
									</tr>
									<tr>
										<td style="width:20%;">
											<input onclick="checkEndStatus(this.checked);" type="checkbox" id="selectEndStatusCheck"/>&nbsp;指定结项状态
										</td>
										<td>
											<div id="endStatusShow" style="display: none;"><s:select id="statistic_endStatus" name="statistic_endStatus" list="#{'':'--请选择--', '已结项':'已结项', '未结项':'未结项'}" /></div>
										</td>
									</tr>
								</table>
							</td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">显示条数：</span></td>
							<td class="table_td31">
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
						<tr id="tr_projectYear" class="table_tr2">
							<td class="table_td2" ><span class="table_title3" style="_padding-top:5px;font-weight:bold;">项目年度：</span></td>
							<td class="table_td31">
								<table width="100%" border="0" cellspacing="0" cellpadding="2">
									<tr>
										<td>
											<s:select cssClass="select" onchange="checkYear();" id="statistic_startYear" name="statistic_startYear" list="%{statisticService.getProjectYear()}" />
											&nbsp;至&nbsp;
											<s:select cssClass="select" id="statistic_endYear" onchange="checkYear();" name="statistic_endYear" list="%{statisticService.getProjectYear()}" />
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
							<td class="table_td31">
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
							<td class="table_td31">
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