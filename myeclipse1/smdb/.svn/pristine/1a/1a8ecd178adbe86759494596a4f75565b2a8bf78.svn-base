<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <title>清单查看</title>
    <s:include value="/innerBase.jsp" />
</head>

<body>
    <div class="link_bar">
        当前位置：研究项目经费&nbsp;&gt;&nbsp;项目经费清单&nbsp;&gt;&nbsp;查看
    </div>
    <s:hidden id="entityId" name="entityId" value="%{entityId}"/>
    <div class="main">
        <textarea id="view_choose_bar_template" style="display:none;">
			 <div class="choose_bar">
				<ul>
					<li id="view_back"><input class="btn1" type="button" value="返回" /></li>
					<li id="view_next"><input class="btn1" type="button" value="下条" /></li>
					<li id="view_prev"><input class="btn1" type="button" value="上条" /></li>
					<li id="view_mod_pop"><input class="btn1" type="button" value="修改" /></li>
					<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
				</ul>
			</div>
        </textarea>
        <div id="view_choose_bar" style="clear:both;"></div>
        <div class="main_content">
            <textarea id="view_base_template" style="display:none;">
                    <div class="title_bar">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
                                <td class="title_bar_td" width="64" align="right">清单名称：</td>
                                <td class="title_bar_td">${fundingList.name}</td>
                                <td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
                                <td class="title_bar_td" width="64" align="right">经办人：</td>
                                <td class="title_bar_td">${fundingList.attn}</td>
                            </tr>
                            <tr>
                                <td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
                                <td class="title_bar_td" width="64" align="right">清单类型：</td>
                                <td id="projectNum" class="title_bar_td" width="120">${fundingList.type}</td>
                                <td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
                                <td class="title_bar_td" width="64" align="right">清单子类型：</td>
                                <td class="title_bar_td" width="120">${fundingList.subType}</td>
                            </tr>
                            <tr>
                                <td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
                                <td class="title_bar_td" width="64" align="right">生成时间：</td>
                                <td class="title_bar_td" width="120">${fundingList.createDate}</td>
                                <td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
                                <td class="title_bar_td" width="64" align="right">拨款比率：</td>
                                <td class="title_bar_td" width="120">${fundingList.rate}%</td>
                            </tr>
                            <tr>
                                <td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
                                <td class="title_bar_td" width="64" align="right">清单状态：</td>
                                <td class="title_bar_td" width="120">
                                    {if fundingList.status ==0}未拨款
                                    <span>
							<input id="view_audit" type="button" class="btn1" value="拨款" />
						</span> {elseif fundingList.status == 1}已拨款 {/if}
                                    <td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
                                    <td class="title_bar_td" width="64" align="right">清单备注：</td>
                                    <td class="title_bar_td">${fundingList.note}</td>
                            </tr>
                            
                        </table>
                    </div>
            </textarea>
            <div id="view_base" style="clear:both;"></div>
            <div class="main_content">
                <div id="tabs" class="p_box_bar">
                    <ul>
                        <li><a href="#forProject">按项目查看</a></li>
                    </ul>
                </div>
                <div class="p_box">
                    <div id="forProject">
                         <s:form id="search" theme="simple" action="list" namespace="/funding/funding/project">
				            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
				                <tr class="table_main_tr">
				                    <td align="right"><span class="choose_bar">
												<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--不限--"
				                                          value="-1"
				                                          list="#{
														'0':'项目名称',
														'1':'负责人',
														'2':'所属高校',
														'3':'拨款金额',
														'4':'项目类型',
														'5':'经费类型',
														'6':'备注'
															
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
                                        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
                                        <td><a id="sortcolumn0" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目名称排序">项目名称</a></td>
                                        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
                                        <td width="60"><a id="sortcolumn1" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按负责人排序">负责人</a></td>
                                        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
                                        <td width="100"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按所属高校排序">所属高校</a></td>
                                        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
                                        <td width="60"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按拨款金额排序">拨款金额</a></td>
                                        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
                                        <td width="60"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目类型排序">项目类型</a></td>
                                        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
                                        <td width="60"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按经费类型排序">经费类型</a></td>
                                        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
                                        <td width="60"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按备注排序">备注</a></td>
                                    </tr>
                                </thead>
                                <tbody>
                                    {for item in root}
                                    <tr>
                                        <td>
                                            <input type="checkbox" name="entityIds" value="${item.laData[0]}" />
                                        </td>
                                        <td></td>
                                        <td>${item.num}</td>
                                        <td></td>
                                        <td class="table_txt_td">${item.laData[1]}</td>
                                        <td></td>
                                        <td>${item.laData[2]}</td>
                                        <td></td>
                                        <td>${item.laData[3]}</td>
                                        <td></td>
                                        <td><a href = "javascript:void(0)" class = "modifyFee" id = ${item.laData[0]}>${item.laData[4]}</a></td>
                                        <td></td>
                                        <td>${item.laData[6]}</td>
                                        <td></td>
                                        <td>${item.laData[7]}</td>
                                        <td></td>
                                        <td>${item.laData[5]}</td>
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
                                    <td width="58">
                                        <input id="list_modify" type="button" class="btn1" value="删除" />
                                    </td>
                                        <td width="58">
                                            <input id="list_export_fund" type="button" class="btn1" value="导出清单" />
                                        </td>
                                </tr>
                            </table>
                        </textarea>
                        <s:form id="list" theme="simple" action="fundingListDelete" namespace="/funding/funding/project">
                            <s:hidden id="fundingList_entityId" name="entityId" value="%{entityId}" />
                            <s:hidden id="pagenumber" name="pageNumber" />
                            <s:hidden id="type" name="type" value="1" />
                            <div id="list_container" style="display:none;"></div>
                        </s:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        seajs.use('javascript/funding/researchProjectFee/projectFundingList/view.js', function (view) {
           $(function(){
        	   var nameSpace = "funding/fundingList/project";
               view.init(nameSpace);
           });
        });
    </script>
</body>

</html>
