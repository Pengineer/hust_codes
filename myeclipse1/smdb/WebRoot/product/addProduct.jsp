<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>添加</title>
   		<s:include value="/swfupload.jsp" />
		</head>
	<body>
		<div class="link_bar">
			当前位置：个人成果&nbsp;&gt;&nbsp;添加
		</div>
	
		<div class="main">
			<s:include value="/validateError.jsp"/>
			<s:hidden id="productflag" name="productflag"/>
			<s:hidden id="ptypeid" name="ptypeid"/>
			<table width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr>
					<td class="text_red">添加后成果文件、成果名称、成果作者、学科门类、学科代码及所属项目将不能修改，请您认真填写！</td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="table_tr2">
					<td class="table_td2" width="140">成果形式：</td>
					<td class="table_td3"><s:radio cssClass="productType" name="ptype" list="#{'1':'论文','2':'著作','3':'研究咨询报告','4':'电子出版物','5':'专利','6':'其他成果'}"/></td>
					<td class="table_td4"></td>
				</tr>
			</table>
			<div id="product">
				<div id="paper" style="display:none;"><s:include value="/product/paper/add.jsp"/></div>
				<div id="book" style="display:none;"><s:include value="/product/book/add.jsp"/></div>
				<div id="consultation" style="display:none;"><s:include value="/product/consultation/add.jsp"/></div>
				<div id="electronic" style="display:none;"><s:include value="/product/electronic/add.jsp"/></div>
				<div id="patent" style="display:none;"><s:include value="/product/patent/add.jsp"/></div>
				<div id="otherProduct" style="display:none;"><s:include value="/product/otherProduct/add.jsp"/></div>
			</div>
		</div>
   		<script type="text/javascript">
			seajs.use('javascript/product/add_my_product.js', function(add) {
				add.initClick();
				add.init();
			});
		</script>
	</body>
</html>