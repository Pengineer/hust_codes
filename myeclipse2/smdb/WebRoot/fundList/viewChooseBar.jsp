<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class="choose_bar">
	<ul>
		<li id="view_back"><input class="btn1" type="button" value="<s:text name='i18n_Return' />" /></li>
		<li id="view_next"><input class="btn1" type="button" value="<s:text name='i18n_NextRecord' />" /></li>
		<li id="view_prev"><input class="btn1" type="button" value="<s:text name='i18n_PrevRecord' />" /></li>
		{if fundList.status ==0}
			<li id="view_mod_pop"><input class="btn1" type="button" value="<s:text name='i18n_Modify' />" /></li>
		{/if}
		{if fundList.status ==0}
			<li id="view_del"><input class="btn1" type="button" value="<s:text name='i18n_Delete' />" /></li>
		{/if}
	</ul>
</div>