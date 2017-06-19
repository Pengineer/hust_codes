<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec"
uri="http://www.springframework.org/security/tags"%>
<%@ page isELIgnored="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>全站检索</title>
        <s:include value="/innerBase.jsp" />
    </head>
    <body>
        <div class="link_bar">
            当前位置： 全站检索
        </div>
        <s:hidden id="isInit" name="isInit" value="%{isInit}" />
            
        <s:form id="getTabs" action="initList" namespace="/system/search">
        <div class="main">
            <div class="main_content">
                <div class="title_statistic">参数配置</div>
                <table width="100%" border="0" cellspacing="2" cellpadding="0">
                    <tr class="table_tr2">
                        <td class="table_td2" width="110"><span class=""
                            style="_padding-top:5px;font-weight:bold;"><s:text
                            name="检索范围" />：</span>
                        </td>
                        <td class="table_td3"><s:select id="search_range_tabs"
                            cssClass="select" name="searchRange"
                            list="#{'all':'所有', 'person':'人员', 'agency':'机构', 'project':'项目', 'product':'成果', 'award':'奖励', 'info':'消息'}" />
                        </td>
                        <td class="table_td4"></td>
                    </tr>
                    <tr class="table_tr2">
                        <td class="table_td2" width="110"><span
                            style="_padding-top:5px;font-weight:bold;"><s:text
                            name="检索关键字" />：</span>
                        </td>
                        <td class="table_td3"><s:textfield id="search_keyword_tabs"
                            name="searchWord" type="input" cssClass="input_css5" />
                        </td>
                        <td class="table_td4"></td>
                    </tr>
                </table>
            </div>
        </div>
        </s:form>
        <s:form id="search" action="list" namespace="/system/search"
        style="display:none">
        <div class="main">
            <div class="main_content">
                <div class="title_statistic">参数配置</div>
                <table width="100%" border="0" cellspacing="2" cellpadding="0">
                    <tr class="table_tr2">
                        <td class="table_td2" width="110"><span class=""
                            style="_padding-top:5px;font-weight:bold;"><s:text
                            name="检索范围" />：</span>
                        </td>
                        <td class="table_td3"><s:select id="search_range_list"
                            cssClass="select" name="searchRange"
                            list="#{'all':'所有', 'info':'消息', 'person':'人员', 'agency':'机构', 'project':'项目', 'product':'成果', 'award':'奖励'}" />
                        </td>
                        <td class="table_td4"></td>
                    </tr>
                    <tr class="table_tr2">
                        <td class="table_td2" width="110"><span
                            style="_padding-top:5px;font-weight:bold;"><s:text
                            name="检索关键字" />：</span>
                        </td>
                        <td class="table_td3"><s:textfield id="search_keyword_list"
                            name="searchWord" type="input" cssClass="input_css5" />
                        </td>
                        <td class="table_td4"></td>
                    </tr>
                </table>
                <input id="dataType" name="dataType" type="hidden">
            </div>
        </div>
        </s:form>
        <div class="btn_div_view">
            <sec:authorize ifAllGranted="ROLE_SYSTEM_SEARCH_TOTAL">
            <input id="searchAll" type="button" class="btn1" value="检索" />
            </sec:authorize>
            <sec:authorize ifAllGranted="ROLE_SYSTEM_SEARCH_INDEX_UPDATE">
            <input id="update" type="button" class="btn2" value="更新索引" />
            <span id="updating" style="color: red; display: none;">正在更新，请稍候...</span>
            </sec:authorize>
        </div>
		<textarea id="product" name="product" style="display:none;">
        <table id="list_table" width="100%" border="0" cellspacing="0"
            cellpadding="0" class="table_td_padding">
            <thead id="list_head">
                <tr class="table_title_tr">
                    
                    <td width="30">序号</td>
                    <td width="2"><img src="image/table_line.gif" width="2"
                        height="24" />
                    </td>
                    <td>成果名</td>
                    <td width="2"><img src="image/table_line.gif" width="2"
                        height="24" />
                    </td>
                    <td width="100">作者</td>
                    <td width="2"><img src="image/table_line.gif" width="2"
                        height="24" />
                    </td>
                    <td width="200">单位</td>
                    <td width="2"><img src="image/table_line.gif" width="2"
                        height="24" />
                    </td>
                    <td width="100">成果类型</td>
                </tr>
            </thead>
            <tbody>
                {for item in root}
                <tr>
                    
                    <td>${item.num}</td>
                    <td></td>
                    <td class="table_txt_td">${item.laData[0]}</td>
                    <td></td>
                    <td>${item.laData[1]}</td>
                    <td></td>
                    <td class="table_txt_td">${item.laData[2]}</td>
                    <td></td>
                    <td>${item.laData[3]}</td>
                </tr>
                {forelse}
                <tr>
                    <td align="center">暂无符合条件的记录</td>
                </tr>
                {/for}
            </tbody>
        </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0"
            class="table_td_padding">
            <tr class="table_main_tr2">
                <td width="4"></td>
            </tr>
        </table>
        </textarea>
        <textarea id="project" name="project" style="display:none;">
        <table id="list_table" width="100%" border="0" cellspacing="0"
            cellpadding="0" class="table_td_padding">
            <thead id="list_head">
                <tr class="table_title_tr">
                   
                    <td width="30">序号</td>
                    <td width="2"><img src="image/table_line.gif" width="2"
                        height="24" />
                    </td>
                    <td >项目名</td>
                    <td width="2"><img src="image/table_line.gif" width="2"
                        height="24" />
                    </td>
                    <td width="100">申请人</td>
                    <td width="2"><img src="image/table_line.gif" width="2"
                        height="24" />
                    </td>
                    <td width="150">高校</td>
                    <td width="2"><img src="image/table_line.gif" width="2"
                        height="24" />
                    </td>
                    <td width="100">类型</td>
                    <td width="2"><img src="image/table_line.gif" width="2"
                        height="24" />
                    </td>
                    <td width="100">年度</td>
                </tr>
            </thead>
            <tbody>
                {for item in root}
                <tr>
                    
                    <td>${item.num}</td>
                    <td></td>
                    <td class="table_txt_td">${item.laData[0]}</td>
                    <td></td>
                    <td>${item.laData[1]}</td>
                    <td></td>
                    <td>${item.laData[2]}</td>
                    <td></td>
                    <td>${item.laData[3]}</td>
                    <td></td>
                    <td>${item.laData[4]}</td>
                </tr>
                {forelse}
                <tr>
                    <td align="center">暂无符合条件的记录</td>
                </tr>
                {/for}
            </tbody>
        </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0"
            class="table_td_padding">
            <tr class="table_main_tr2">
                <td width="4"></td>
            </tr>
        </table>
        </textarea>
        <textarea id="person" name="person" style="display:none;">
        <table id="list_table" width="100%" border="0" cellspacing="0"
            cellpadding="0" class="table_td_padding">
            <thead id="list_head">
                <tr class="table_title_tr">
                   
                    <td width="30">序号</td>
                    <td width="2"><img src="image/table_line.gif" width="2"
                        height="24" />
                    </td>
                    <td width="100">姓名</td>
                    <td width="2"><img src="image/table_line.gif" width="2"
                        height="24" />
                    </td>
                    <td width="40">性别</td>
                    <td width="2"><img src="image/table_line.gif" width="2"
                        height="24" />
                    </td>
                    <td >所在机构</td>
                    <td width="2"><img src="image/table_line.gif" width="2"
                        height="24" />
                    </td>
                    <td width="200">职务</td>
                </tr>
            </thead>
            <tbody>
                {for item in root}
                <tr>
                   
                    <td>${item.num}</td>
                    <td></td>
                    <td>${item.laData[0]}</td>
                    <td></td>
                    <td>${item.laData[1]}</td>
                    <td></td>
                    <td>${item.laData[2]}</td>
                    <td></td>
                    <td>${item.laData[3]}</td>
                </tr>
                {forelse}
                <tr>
                    <td align="center">暂无符合条件的记录</td>
                </tr>
                {/for}
            </tbody>
        </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0"
            class="table_td_padding">
            <tr class="table_main_tr2">
                <td width="4"></td>
            </tr>
        </table>
        </textarea>
        <textarea id="agency" name="agency" style="display:none;">
        <table id="list_table" width="100%" border="0" cellspacing="0"
            cellpadding="0" class="table_td_padding">
            <thead id="list_head">
                <tr class="table_title_tr">
                    
                    <td width="30">序号</td>
                    <td width="2"><img src="image/table_line.gif" width="2"
                        height="24" />
                    </td>
                    <td width="200">名称</td>
                    <td width="2"><img src="image/table_line.gif" width="2"
                        height="24" />
                    </td>
                    <td width="200">代码</td>
                    <td width="2"><img src="image/table_line.gif" width="2"
                        height="24" />
                    </td>
                    <td width="200">单所属部门</td>
                    <td width="2"><img src="image/table_line.gif" width="2"
                        height="24" />
                    </td>
                    <td width="200">负责人</td>
                </tr>
            </thead>
            <tbody>
                {for item in root}
                <tr>
                   
                    <td>${item.num}</td>
                    <td></td>
                    <td>${item.laData[0]}</td>
                    <td></td>
                    <td>${item.laData[1]}</td>
                    <td></td>
                    <td>${item.laData[2]}</td>
                    <td></td>
                    <td>${item.laData[3]}</td>
                </tr>
                {forelse}
                <tr>
                    <td align="center">暂无符合条件的记录</td>
                </tr>
                {/for}
            </tbody>
        </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0"
            class="table_td_padding">
            <tr class="table_main_tr2">
                <td width="4"></td>
            </tr>
        </table>
        </textarea>
        <textarea id="award" name="award" style="display:none;">
        <table id="list_table" width="100%" border="0" cellspacing="0"
            cellpadding="0" class="table_td_padding">
            <thead id="list_head">
                <tr class="table_title_tr">
                   
                    <td width="30">序号</td>
                    <td width="2"><img src="image/table_line.gif" width="2"
                        height="24" />
                    </td>
                    <td width="200">奖励名</td>
                    <td width="2"><img src="image/table_line.gif" width="2"
                        height="24" />
                    </td>
                    <td width="200">申请人</td>
                    <td width="2"><img src="image/table_line.gif" width="2"
                        height="24" />
                    </td>
                    <td width="200">高校</td>
                    <td width="2"><img src="image/table_line.gif" width="2"
                        height="24" />
                    </td>
                    <td width="200">学科门类</td>
                </tr>
            </thead>
            <tbody>
                {for item in root}
                <tr>
                    
                    <td>${item.num}</td>
                    <td></td>
                    <td class="table_txt_td">${item.laData[0]}</td>
                    <td></td>
                    <td>${item.laData[1]}</td>
                    <td></td>
                    <td>${item.laData[2]}</td>
                    <td></td>
                    <td>${item.laData[3]}</td>
                </tr>
                {forelse}
                <tr>
                    <td align="center">暂无符合条件的记录</td>
                </tr>
                {/for}
            </tbody>
        </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0"
            class="table_td_padding">
            <tr class="table_main_tr2">
                <td width="4"></td>
            </tr>
        </table>
        </textarea>
        <textarea id="info" name="info" style="display:none;">
        <table id="list_table" width="100%" border="0" cellspacing="0"
            cellpadding="0" class="table_td_padding">
            <thead id="list_head">
                <tr class="table_title_tr">
                    <td width="30">序号</td>
                    <td width="2"><img src="image/table_line.gif" width="2"
                        height="24" />
                    </td>
                    <td width="200">标题</td>
                    <td width="2"><img src="image/table_line.gif" width="2"
                        height="24" />
                    </td>
                    <td width="200">时间</td>
                    <td width="2"><img src="image/table_line.gif" width="2"
                        height="24" />
                    </td>
                    <td width="200">发布者</td>
                    <td width="2"><img src="image/table_line.gif" width="2"
                        height="24" />
                    </td>
                    <td width="200">内容</td>
                </tr>
            </thead>
            <tbody>
                {for item in root}
                <tr>
                  
                    <td>${item.num}</td>
                    <td></td>
                    <td>${item.laData[0]}</td>
                    <td></td>
                    <td>${item.laData[1]}</td>
                    <td></td>
                    <td>${item.laData[2]}</td>
                    <td></td>
                    <td>${item.laData[3]}</td>
                </tr>
                {forelse}
                <tr>
                    <td align="center">暂无符合条件的记录</td>
                </tr>
                {/for}
            </tbody>
        </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0"
            class="table_td_padding">
            <tr class="table_main_tr2">
                <td width="4"></td>
            </tr>
        </table>
        </textarea>
        <div id="container_box">
            <div id="tabs" class="p_box_bar">
            <ul></ul>
        </div>
        
        <div id="list_container" style="display:none;"></div>
    </div>
    <script type="text/javascript">
    seajs.use(['javascript/system/search/search.js'], function(search){
        $(function(){
            search.init();
            $(document).keyup(function(event){
                if(event.keyCode ==13){
                    $("#searchAll").trigger("click");
                    return false;
                }
            });
         });        
    });
    </script>
</body>
</html>