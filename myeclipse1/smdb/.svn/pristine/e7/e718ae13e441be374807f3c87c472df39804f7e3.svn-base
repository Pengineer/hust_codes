<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>审核意见</title>
        <s:include value="/innerBase.jsp" />
    </head>
    <body>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" >
            <tr>
                <span>请选择审核意见：</span>
            </tr>
            <tr style = "height:36px">
                <td width = "160">
                    <label><input type="radio" name="verifyResult" id="rejected" value = "1"> 审核通过</label>
                </td>
                <td >
                    <label><input type="radio" name="verifyResult" id="approved" value = "2"> 审核不通过</label>
                </td>
            </tr>
        </table>
        <div class="btn_div_view">
            <input id="confirmed" class="btn1 j_modifySave" type="button" value="确定" style = "margin-right:100px"/>
            <input id="cancel" class="btn1" type="button" value="取消" />
        </div>
    </body>
    <script>
    seajs.use("javascript/management/recruit/applicant/verify.js")
    </script>
</html>