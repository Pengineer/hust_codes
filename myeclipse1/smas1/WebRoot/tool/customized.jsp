<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="csdc.tool.SpringBean" %>
<%@ page import="csdc.tool.execution.Execution" %>

<%


Execution execution = (Execution)SpringBean.getBean("xxx");
execution.excute();


%>
