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
			当前位置：系统配置&nbsp;&gt;&nbsp;配置第三方上传接口
		</div>
	    <s:form action="configUplodToDmss" namespace="/system/config">
		    <div class="main">
				<div class="main_content">
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td2" width="130"><span style="_padding-top:5px;font-weight:bold;">第三方上传接口配置：</span></td>
							<td  width="70">
								<input id="submit" class="btn1" type="submit" value="重连" />
							</td>
							<td>
								<input id="cancel" class="btn1" type="button" value="返回" onclick="history.back();" />
							</td>
						</tr>
					</table>
				</div>
			</div>
		</s:form>
	</body>
</html>
