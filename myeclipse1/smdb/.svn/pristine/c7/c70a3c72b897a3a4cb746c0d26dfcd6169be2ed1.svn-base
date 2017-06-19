<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>系统选项表</title>
		<s:include value="/innerBase.jsp" />
	</head>

	<body>
		<div class="link_bar">
			当前位置：接口管理&nbsp;&gt;&nbsp;文档管理服务系统&nbsp;&gt;&nbsp;文件上传接口
		</div>
	    <s:form action="configUplodToDmss" namespace="/system/config">
		    <div class="main">
				<div class="main_content">
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">第三方上传接口配置：</span></td>
							<td class="table_td31">
								<table width="100%" border="0" cellspacing="0" cellpadding="2" style="border: 1px solid rgb(170, 170, 170);padding-bottom:5px;">
									<tr>
										<td style="width:15%;">
											<input type="radio" name="thirdUplodState" value="connect"/>
											<span>打开</span>
										</td>
										<td style="width:15%;">
											<input type="radio" name="thirdUplodState" value="close"/>
											<span>关闭</span>
										</td>
										<td style="width:15%;">
											<input type="radio" name="thirdUplodState" value="reconnect"/>
											<span>重新连接</span>
										</td>																			
									</tr>
								</table>
							</td>
						</tr>
						<tr class="table_tr2">
								<td align="center">
									<input id="submit" class="btn1" type="submit" value="确定" />
								</td>
							</tr>
					</table>
				</div>
			</div>
		</s:form>
		<s:hidden id="thirdUplodState" name="thirdUplodState" />
		<script type="text/javascript">
			seajs.use('javascript/system/config/configUploadToDmss.js', function(configUploadToDmss) {
				configUploadToDmss.init();
			});
		</script>
	</body>
</html>
