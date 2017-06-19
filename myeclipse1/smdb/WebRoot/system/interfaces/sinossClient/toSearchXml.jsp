<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>接口管理</title>
		<s:include value="/innerBase.jsp" />
	</head>
	<body>
		<div class="link_bar">
			当前位置：接口管理&nbsp;&gt;&nbsp;社科网客户端&nbsp;&gt;&nbsp;XML备份下载
		</div>
	    <s:form id="form_search">
			<div class="main">
				<div class="main_content">
					<div class="title_statistic">参数配置</div>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="" style="_padding-top:5px;font-weight:bold;">数据类型：</span></td>
							<td class="table_td3"><s:select id="projectStatus" cssClass="select" name="projectStatus" list="#{'app':'申请数据',
							 'var':'变更数据','mid':'中检数据', 'end':'结项数据'}"/></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="" style="_padding-top:5px;font-weight:bold;">年度：</span></td>
							<td class="table_td3"><select id="sourceYear" cssClass="select" name="sourceYear" ></select></td>
							<td class="table_td4"></td>
						</tr>						
					</table>
					
				</div>
			</div>
		</s:form>
		<div class="btn_div_view" style = "text-align:right">
			<input id="submit" class="btn2" type="button" value="检索XML" />
		</div>
		<div id="showInfo" style = "max-height:400px;overflow-y:scroll;margin-top:10px"></div>
		<textarea id="viewXml_template" style="display:none;">
                <table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding" style = "padding-top: 10px;">
                    <thead id="list_head">
                        <tr class="table_title_tr">
                            <td width="30">序号</td>
                            <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
                            <td width=>文件名</td>
                       </tr>
                    </thead>
                    <tbody style="text-align:center">
                        {for item in fileNameList}
                        <tr>
                            <td>${parseInt(item_index) + 1} </td>
                            <td></td>
                            <td align = "left"><a id = "${item}" class = "downloadXml" href = "javascript:void(0)" title = "点击下载Xml文件" class = "link1" >${item}</a></td>
                          {forelse}
                            <tr>
                                <td align="center" colspan = "9">暂无符合条件的记录</td>
                            </tr>
                            {/for}
                    </tbody>
                </table>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
                    <tr class="table_main_tr2">
                        <td width="58" align="left">
                          
                        </td>
                    </tr>
                </table>
            </textarea>
		<script type="text/javascript">
			seajs.use('javascript/system/interfaces/sinossClient/searchXml.js', function(xml) {
				xml.init();
			});
		</script>
	</body>
</html>