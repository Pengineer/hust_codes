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
    当前位置：研究项目经费&nbsp;&gt;&nbsp;项目经费清单

</div>
<div class="main">
    <div class="main_content">
        <div id="name_gender" class="title_bar">
                    <table width="100%" border="0" cellspacing="2" cellpadding="0">
                <tr>
                    <td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9"/>
                    </td>
                    <td class="title_bar_td" width="60" align="right">历次拨款：</td>
                    <td class="title_bar_td" colspan="7">
                        <select name="fundingBatchNames" id="fundingBatchNames" class="select">
                            <s:iterator value="fundingBatchList" status="stat">
                                <option value="<s:property value='fundingBatchList[#stat.index][0]' /> ">
                                    <s:property value='fundingBatchList[#stat.index][1]'/>
                                </option>
                            </s:iterator>
                        </select>
                    </td>
                </tr>
            </table>
        </div>
        <input id="fundingBatchId" type="hidden" value="<s:property value='fundingBatchId' />"/>
        <s:form id="search" theme="simple" action="list" namespace="/funding/fundingList/project">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
                <tr class="table_main_tr">
                    <td align="right"><span class="choose_bar">
								<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--不限--"
                                          value="-1"
                                          list="#{
										'0':'拨款清单名称',
										'1':'拨款年度',	
										'2':'清单类型',		
										'3':'清单子类型'
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
				<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
                    <thead id="list_head">
                    <tr class="table_title_tr">
                    	<td width="10">
                            <input id="check" name="check" type="checkbox" title="点击全选/不选本页所有项目" />
                        </td>
                        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
                        <td width="30">序号</td>
                        <td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
                        <td width=><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按收款机构排序">拨款清单名称</a></td>
                        <td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
                        <td width="60"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按机构代码排序">拨款年度</a></td>
                        <td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
                        <td width="60"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按拨款金额排序">清单类型</a></td>
                        <td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
                        <td width="100"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按拨款条目数排序">清单子类型</a></td>
                        <td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
                        <td width="60"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按银行户名排序">拨款条数</a></td>
                        <td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
                        <td width="60"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按银行账户排序">拨款金额</a></td>
                    </tr>
                    </thead>
                    <tbody style="text-align:center">
                    {for item in root}
                    <tr>
                     	<td>
                            <input type="checkbox" name="entityIds" value="${item.laData[0]}" />
                        </td>
                        <td></td>
                        <td>${item.num}</td>
                        <td></td>
                        <td class="table_txt_td"><a id="${item.laData[0]}" class="link1" href="" title="点击查看详细信息">${item.laData[1]}</a>
                        </td>
                        <td></td>
                        <td>${item.laData[2]}</td>
                        <td></td>
                        <td>${item.laData[3]}</td>
                        <td></td>
                        <td class="table_txt_td">${item.laData[4]}</td>
                        <td></td>
                        <td class="table_txt_td">${item.laData[5]}</td>
                        <td></td>
                        <td class="table_txt_td">${item.laData[6]}</td>
                    </tr>
                    {forelse}
                    <tr>
                        <td align="center">暂无符合条件的记录</td>
                    </tr>
                    {/for}
                    </tbody>
                </table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
                    <tr class="table_main_tr2">
                        <td width="4"></td>
                        <td>
                        	<input type = "button" class = "btn1" id = "addFundList" value = "添加"  />
                        	<input type = "button" class = "btn1" id = "del" value = "删除"  />
                        </td>
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
        seajs.use('javascript/funding/researchProjectFee/projectFundingList/list.js', function (list) {
            $(function () {
                list.init();
            })
        });
    </script>
</body>

</html>