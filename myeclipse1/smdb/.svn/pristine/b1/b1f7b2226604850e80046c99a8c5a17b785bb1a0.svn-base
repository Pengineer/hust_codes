<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <title>经费管理系统</title>
    <s:include value="/innerBase.jsp"/>
</head>
<body>
<div class="link_bar">
    当前位置：项目研究经费&nbsp;&gt;&nbsp;项目经费概况

</div>
<div class="main">
    <div class="main_content">
        <s:form id="search" theme="simple" action="list" namespace="/funding/projectFund">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
                <tr class="table_main_tr">
                    <td align="right"><span class="choose_bar">
								<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--不限--"
                                          value="-1"
                                          list="#{
										'0':'项目名称',
										'1':'项目批准号',
										'2':'负责人',
										'3':'机构名称',
										'4':'项目年度'		
									}"
                                        />
							</span>
                        <s:textfield id="keyword" name="keyword" cssClass="keyword" size="10"/>
                        <s:hidden id="list_pagenumber" name="pageNumber"/>
                        <s:hidden id="list_sortcolumn" name="sortColumn"/>
                        <s:hidden id="list_pagesize" name="pageSize"/>
                    </td>
                    <td width="66"><input id="list_button_query" type="button" value="检索" class="btn1"/></td>
                </tr>

            </table>
        </s:form>
				<textarea id="list_template" style="display:none;">
				<div>
				<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
                    <thead id="list_head">
                    <tr class="table_title_tr">
                        <td width="30">序号</td>
                        <td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
                        <td width=""><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按收款机构排序">项目名称</a></td>
                        <td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
                        <td width="60"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按机构代码排序">项目批准号</a></td>
                        <td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
                        <td width="50"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按拨款金额排序">负责人</a></td>
                        <td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
                        <td width="50"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按拨款条目数排序">机构名称</a></td>
                        <td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
                        <td width="50"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按银行户名排序">项目子类</a></td>
                        <td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
                        <td width="50"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按银行账户排序">项目年度</a></td>
                        <td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
                        <td width="50"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按开户银行排序">项目批准经费额</a></td>
                        <td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
                        <td width="50"><a id="sortcolumn6" href="" class="{if sortColumn == 7}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按开户银行排序">立项拨款金额</a></td>
                        <td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
                        <td width="50"><a id="sortcolumn6" href="" class="{if sortColumn == 8}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按开户银行排序">中检拨款金额</a></td>
                        <td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
                        <td width="50"><a id="sortcolumn6" href="" class="{if sortColumn == 9}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按开户银行排序">结项拨款金额</a></td>
                    </tr>
                    </thead>
                    <tbody style="text-align:center">
                    {for item in root}
                    <tr>
                        <td>${item.num}</td>
                        <td></td>
                        <td class="table_txt_td">${item.laData[2]}
                        </td>
                        <td></td>
                        <td>${item.laData[3]}</td>
                        <td></td>
                        <td>${item.laData[5]}</td>
                        <td></td>
                        <td class="table_txt_td">${item.laData[7]}</td>
                        <td></td>
                        <td class="table_txt_td">${item.laData[8]}</td>
                        <td></td>
                        <td class="table_txt_td">${item.laData[9]}</td>
                        <td></td>
                        <td class="table_txt_td">${item.laData[10]}</td>
                        <td></td>
                        <td class="table_txt_td">${item.laData[12]}</td>
                        <td></td>
                        <td class="table_txt_td">${item.laData[14]}</td>
                        <td></td>
                        <td class="table_txt_td">${item.laData[16]}</td>
                    </tr>
                    {forelse}
                    <tr>
                        <td align="center">暂无符合条件的记录</td>
                    </tr>
                    {/for}
                    </tbody>
                </table>
                </div>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
                    <tr class="table_main_tr2">
                        <td width="4"></td>
                    </tr>
                </table>
				</textarea>
        <s:form id="list" theme="simple" action="delete" namespace="/verify">
            <s:hidden id="pagenumber" name="pageNumber"/>
            <s:hidden id="checkedIds" name="checkedIds"/>
            <div id="list_container" style="display:none;"></div>
        </s:form>
    </div>
    <script type="text/javascript">
        seajs.use('javascript/funding/researchProjectFee/projectFund/list.js', function (list) {
            $(function () {
                list.init();
            })
        });
    </script>
</body>

</html>