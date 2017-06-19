$(document).ready(function(){
	document.getElementById("resumeList").style.display = 'none';	
    $('#resumeList').modal({	
        backdrop:true,
        keyboard:true,
        show:false
        });

	//初始化简历列表数据    
	oTable = $('#myResumeList').dataTable({  
	    "bPaginate": true,  //是否显示分页
	    "bLengthChange": true,  //改变每页显示数据数量
	    "bFilter": true, //搜索栏
	    "bSort": true, //是否支持排序功能
	    "bInfo": true, //显示表格信息
	    "bAutoWidth": true,  //自适应宽度
	    "aaSorting": [[1, "asc"]],  //给列表排序 ，第一个参数表示数组 (由0开始)。1 表示Browser列。第二个参数为 desc或是asc		    
	    "bStateSave": true, //保存状态到cookie *************** 很重要 ， 当搜索的时候页面一刷新会导致搜索的消失。使用这个属性就可避免了
	    "bJQueryUI": true,    
	    "iDisplayLength": 10,  
	    "sPaginationType": "full_numbers", //分页，一共两种样式，full_numbers和two_button(默认)  
	    "sAjaxSource": "resume/listMyResume.action",
	    "bServerSide": false, 
	    "sServerMethod": "POST",
	    "fnServerData":function retrieveData( sSource, aoData, fnCallback ) {
	    	$.ajax({
	    	"type": "POST",
	    	"contentType": "application/json",
	    	"url": sSource,
	    	"dataType": "json",
	    	"data": JSON.stringify(aoData), //以json格式传递
	    	"success": function(result) {
	    			fnCallback(result); //服务器端返回的对象的returnObject部分是要求的格式
	    			}
	    		});
	    	},
		"aoColumns": [{"mData": "0"},
		              {"mData": "1"},
		              {"mData": "2"},
		              {"mData": "3"},
		              {"mRender": function (data, type, full) {return '<input type="radio" name="resumeids" value="' + data + '">'}}],   	
		"oLanguage": {
			"sLengthMenu": "每页显示 _MENU_ 条记录",
			      "sZeroRecords": "对不起，查询不到任何相关数据",
			      "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
			      "sInfoEmtpy": "找不到相关数据",
			      "sInfoFiltered": "数据表中共为 _MAX_ 条记录)",
			      "sProcessing": "正在加载中...",
			      "sSearch": "搜索",
			      "sUrl": "", //多语言配置文件，可将oLanguage的设置放在一个txt文件中，例：Javascript/datatable/dtCH.txt
			      "oPaginate": {
			          "sFirst":    "首页",
			          "sPrevious": " 前一页 ",
			          "sNext":     " 后一页 ",
			          "sLast":     " 尾页 "
			      }
			}
	});
	oTable.css("width","1385px");
})

function applyPosition(){
	$.ajax({
		type: "POST",
		dataType:"json",
		url:"position/apply.action?positionId=" + $("#positionId").val() + "&resumeId=" + $("input[name=resumeids]:checked").val(),		
		success:function(jsonMap){
				if(jsonMap.message == 1) {
					alert("对不起，您已经应聘过该职位，不能重复投递！");
					$('#resumeList').modal('hide');
				} else {
					alert("申请成功!!!");
					$('#resumeList').modal('hide');
				}
		}
	});
};


function collectPosition(){
	$.ajax({
		type: "POST",
		url:"position/collect.action?positionId=" + $("#positionId").val() + "&resumeId=" + $("input[name=resumeids]:checked").val(),
		success:function(jsonMap){
			if(jsonMap.message == 1) {
				alert("对不起，您已经收藏过该职位，不能重复收藏！");
				$('#resumeList').modal('hide');
			} else {
				alert("收藏成功!!!");
				$('#resumeList').modal('hide');
			}
		}
	})
}