<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="btn_bar2">
	<input id="submit" class="btn1" type="submit" value="<s:text name='i18n_Ok' />" />
	<input id="cancel" class="btn1" type="button" value="<s:text name='i18n_Cancel' />" onclick="history.back();" />
</div>