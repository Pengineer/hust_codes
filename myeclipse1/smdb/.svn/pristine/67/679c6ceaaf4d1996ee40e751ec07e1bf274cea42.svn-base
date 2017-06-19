<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
      <s:form id="search" theme="simple" action="list" namespace="/funding/fundingList">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
                <tr class="table_main_tr">
                    <td align="right"><span class="choose_bar">
								<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--不限--"
                                          value="-1"
                                          list="#{
										'0':'拨款清单',
										'1':'拨款年度'
											
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
                        <td><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按拨款清单排序">拨款清单</a></td>
                        <td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
                        <td width="70"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按清单类型排序">清单类型</a></td>
                        <td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
                        <td width="100"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按清单子类型排序">清单子类型</a></td>
                        <td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
                        <td width="80"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按拨款年度排序">拨款年度</a></td>
                        <td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
                        <td width="80">拨款条数</td>
                        <td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
                        <td width="120">拨款金额 （万元）</td>
                    </tr>
                    </thead>
                    <tbody style="text-align:center">
                    {for item in root}
                    <tr>
                        <td>${item.num}</td>
                        <td></td>
                        <td class="table_txt_td"><a id="${item.laData[0]}" class="showdetail" href="javascript:void(0)" title="点击查看详细信息">${item.laData[1]}</a>
                        </td>
                        <td></td>
                        <td>${item.laData[3]}</td>
                        <td></td>
                        <td>${item.laData[4]}</td>
                        <td></td>
                        <td>${item.laData[2]}</td>
                        <td></td>
                        <td>${item.laData[5]}</td>
                        <td></td>
                        <td>${item.laData[6]}</td>
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
                    </tr>
                </table>
                </textarea>
         <s:form id="list" theme="simple" action="delete" namespace="/verify">
            <s:hidden id="pagenumber" name="pageNumber"/>
            <s:hidden id="checkedIds" name="checkedIds"/>
            <div id="list_container" style="display:none;"></div>
        </s:form>               
    </div>
