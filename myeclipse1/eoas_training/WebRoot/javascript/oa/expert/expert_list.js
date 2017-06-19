$(document).ready(function() {
	// 初始化职位简历列表数据
	var i = 1;
	var _tableList = $('#expertList').dataTable({
		"bProcessing": true, //显示是否加载    
	    "sScrollX": "100%", //表格的宽度 
	    "sScrollXInner": "100%",   //表格的内容宽度
	    "bScrollCollapse": true,  //当显示的数据不足以支撑表格的默认的高度时，依然显示纵向的滚动条。(默认是false) 
	    "bPaginate": true,  //是否显示分页
	    "bLengthChange": true,  //改变每页显示数据数量
	    "bFilter": true, //搜索栏
	    "bSort": true, //是否支持排序功能
	    "bInfo": true, //显示表格信息
	    "bAutoWidth": false,  //自适应宽度
	    "aaSorting": [[2, "asc"]],//给列表排序 ，第一个参数表示数组 (由0开始)。1 表示Browser列。第二个参数为 desc或是asc		    
	    "bStateSave": true, //保存状态到cookie *************** 很重要 ， 当搜索的时候页面一刷新会导致搜索的消失。使用这个属性就可避免了
	    /*"bJQueryUI": true, */   
	    "iDisplayLength": 10,  
	    "sPaginationType": "full_numbers", //分页，一共两种样式，full_numbers和two_button(默认)  
	    "sAjaxSource": "expert/list.action",
		"fnRowCallback" : function(nRow, aData, iDisplayIndex) {
			$('td:eq(0)', nRow).html(i++);
			$('td:eq(1)', nRow).html('<a href="expert/view.action?expertId='
					+ aData[6] + '">' + aData[0] + '</a>');
			$('td:eq(7)', nRow)
					.html('<a title="修改" href="expert/toModify.action?expertId='
							+ aData[6]
							+ '">'
							+ '<span class="glyphicon glyphicon-pencil u-modify"></span>'
							+ '</a>')
					.append(('<a title="删除" id="'
							+ aData[6]
							+ '" class="deleteExpert" href="javascript:void(0)">'
							+ '<span class="glyphicon glyphicon-trash u-delete"></span>' + '</a>'));
			return nRow;
		},
		"fnPreDrawCallback": function(){
			i = 1;
		},
		"sServerMethod" : "POST",
		"fnServerData" : function retrieveData(sSource, aoData, fnCallback) {
			$.ajax({
						"type" : "POST",
						"contentType" : "application/json",
						"url" : sSource,
						"dataType" : "json",
						"data" : JSON.stringify(aoData), // 以json格式传递
						"success" : function(result) {
							fnCallback(result); // 服务器端返回的对象的returnObject部分是要求的格式
						}
					});
		},
		"aoColumns" : [{
					"mData" : "0"
				}, {
					"mData" : "0"
				}, {
					"mData" : "1"
				}, {
					"mData" : "2"
				}, {
					"mData" : "3"
				}, {
					"mData" : "4"
				}, {
					"mData" : "5"
				},{
					"mData" : "6"
				}
				],
		"oLanguage" : {
			"sLengthMenu" : "每页显示 _MENU_ 条记录",
			"sZeroRecords" : "对不起，查询不到任何相关数据",
			"sInfo" : "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
			"sInfoEmtpy" : "找不到相关数据",
			"sInfoFiltered" : "数据表中共为 _MAX_ 条记录)",
			"sProcessing" : "正在加载中...",
			"sSearch" : "搜索：",
			"sUrl" : "", // 多语言配置文件，可将oLanguage的设置放在一个txt文件中，例：Javascript/datatable/dtCH.txt
			"oPaginate" : {
				"sFirst" : "首页",
				"sPrevious" : " 前一页 ",
				"sNext" : " 后一页 ",
				"sLast" : " 尾页 "
			}
		}
	});
	
	
	$("body").on("click", ".deleteExpert", function(){
		var that = this;
		dialog({
		    title: '提示',
		    content: '确定要删除吗?',
		    width:250,
		    okValue: '确定',
		    ok: function () {
		    	var url = "expert/delete.action?expertId=" + $(that).attr("id");
		    	$.get(url, function(data){
		    		if(data.result == "1"){
		    			dialog({
		    			    title: '提示',
		    			    content: '删除成功',
		    			    width:250,
		    			    okValue: '确定',
		    			    ok: function () {
		    			    	window.location.href = "expert/toList.action";
		    			    }
		    			}).showModal();
		    		} else {
		    			dialog({
		    			    title: '提示',
		    			    content: '删除失败!',
		    			    width:250,
		    			    okValue: '确定',
		    			    ok: function () {
		    			    	window.location.href = "expert/toList.action";
		    			    }
		    			}).showModal();
		    		}
		    	})

		    	
		    },
		    cancelValue: '取消',
		    cancel: function () {}
		}).showModal();
	})
})
