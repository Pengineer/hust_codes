<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:form id="list" theme="simple" action="delete">
	<s:hidden id="pagenumber" name="pageNumber" />
	<s:hidden id="type" name="type" value="1" />
	<s:hidden id="datepick" name="validity" />
	<s:hidden id="roleIds" name="roleIds" />
	<div id="list_container" style="display:none;"></div>
</s:form>