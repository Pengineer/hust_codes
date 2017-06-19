<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <title>查看任务配置状况</title>
  	<s:include value="/innerBase.jsp" />
</head>

<body>
    <div class="link_bar">
        当前位置：事务配置模块&nbsp;&gt;&nbsp; 查看事务配置状况
    </div>
    <div class="main" style = "margin-top:0px">
    <div class="main_content" style = "margin-top:0px">
            <textarea id="taskConfigInfos_template" style="display:none;">
                <table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding" style = "padding-top: 10px;">
                    <thead id="list_head">
                        <tr class="table_title_tr">
                            <td width="30">序号</td>
                            <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
                            <td width=>事务名称</td>
                            <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
                            <td width=>事务类型</td>
                            <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
                            <td width=>事务状态</td>
                       </tr>
                    </thead>
                    <tbody style="text-align:center">
                        {for item in taskConfigInfos}
                        <tr>
                            <td>${item[4]}</td>
                            <td></td>
                            <td class = "taskName"><a id = "${item[0]}" href = "javascript:void(0)" class = "link1" >${item[1]}</a></td>
                            <td></td>
                            <td class = "taskType">${item[2]}</td>
                            <td></td>
                            <td class = "taskStatus">${item[3]}</td>
                          {forelse}
                            <tr>
                                <td align="center">暂无符合条件的记录</td>
                            </tr>
                            {/for}
                    </tbody>
                </table>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
                    <tr class="table_main_tr2">
                        <td width="58" align="left">
                          
                        </td>
                    </tr>
                </table>
            </textarea>
            <div id = "taskConfigInfos" style = "display:none; margin-top:10px"></div>
        </div>
    </div>
    <script type="text/javascript">
    seajs.use('javascript/dataProcessing/taskConfig/taskConfigList.js', function(taskConfigList) {
        $(function() {
        	taskConfigList.init();
        })
    });
    </script>
</body>

</html>

