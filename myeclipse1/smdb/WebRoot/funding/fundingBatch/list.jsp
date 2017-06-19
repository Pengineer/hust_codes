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
    当前位置：拨款批次管理
</div>
<div class="main">
    <div class="main_content">
        <input id="fundingBatchId" type="hidden" value="<s:property value='fundingBatchId' />"/>
        <s:form id="search" theme="simple" action="list" namespace="/funding/fundingBatch">
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
                        <td width="30">序号</td>
                        <td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
                        <td width=><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按收款机构排序">拨款清单名称</a></td>
                        <td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
                        <td width="150"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按机构代码排序">批次时间</a></td>
                    </tr>
                    </thead>
                    <tbody style="text-align:center">
                    {for item in root}
                    <tr>
                        <td>${item.num}</td>
                        <td></td>
                        <td class="table_txt_td"><a id="${item.laData[0]}" class="link1" href="" title="点击查看详细信息">${item.laData[1]}</a>
                        </td>
                        <td></td>
                        <td>${item.laData[2]}</td>
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
                        <td width="58"><input class="btn1" type="button" id="list_add" value="添加" /></td>
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
        seajs.use('javascript/funding/fundingBatch/list.js', function (list) {
            $(function () {
                list.init();
            })
        });
    </script>
</body>

</html>