<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <title>申请数据入库</title>
    <s:include value="/innerBase.jsp"/>
</head>
<body style = "width:779px;">
<div>
    <div>
        <div id="name_gender" class="title_bar">
            <table width="100%" border="0" cellspacing="2" cellpadding="0">
                <tr>
                    <td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9"/>
                    </td>
                    <td class="title_bar_td" width="60" align="right">历次拨款：</td>
                    <td class="title_bar_td" colspan="7" id = "fundingBatchName">
                    </td>
                </tr>

                <tr>
                    <td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9"/>
                    </td>
                    <td class="title_bar_td" width="60" align="right">收款单位：</td>
                    <s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@ADMINISTRATOR, @csdc.tool.bean.AccountType@MINISTRY)">
                        <!-- 系统管理员或部级 -->
                        <td class="title_bar_td" width="" id = "agency">
                           
                        </td>
                    </s:if>
                    <s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@LOCAL_UNIVERSITY)">
                        <!-- 地方高校或部署高校 -->
                        <td class="title_bar_td" width="60" id="agencyName"></td>
                    </s:if>
                    </td>
                </tr>
                <tr>
                    <td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9"/>
                    </td>
                    <td class="title_bar_td" width="60" align="right">拨款总额：</td>
                    <td class="title_bar_td" id="totalFee" colspan="7"></td>
                </tr>
                <tr>
                    <td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9"/>
                    </td>
                    <td class="title_bar_td" width="60" align="right">拨款时间：</td>
                    <td class="title_bar_td" id='fundingBatchDate' colspan="7"></td>
                </tr>

            </table>
        </div>
 <%@ include file="/fund/fund_share/listFundingList.jsp"%>
     <script type="text/javascript">
        seajs.use('javascript/fund/popListFundingListByType.js', function (list) {
            $(function () {
                list.init();
            })
        });
    </script>
</body>
</html>