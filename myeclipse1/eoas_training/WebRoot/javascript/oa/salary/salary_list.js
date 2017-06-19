$(document).ready(function(){
	$('#salaryList').dataTable({  
	    "bProcessing": true, //显示是否加载    
	    "sScrollX": "100%", //表格的宽度 
	    "sScrollXInner": "110%",   //表格的内容宽度
	    "bScrollCollapse": true,  //当显示的数据不足以支撑表格的默认的高度时，依然显示纵向的滚动条。(默认是false) 
	    "sScrollY": "400px",
	    "bPaginate": true,  //是否显示分页
	    "bLengthChange": true,  //改变每页显示数据数量
	    "bFilter": true, //搜索栏
	    "bSort": true, //是否支持排序功能
	    "bInfo": true, //显示表格信息
	    "bAutoWidth": false,  //自适应宽度
	    "aaSorting": [[1, "asc"]],  //给列表排序 ，第一个参数表示数组 (由0开始)。1 表示Browser列。第二个参数为 desc或是asc		    
	    "bStateSave": true, //保存状态到cookie *************** 很重要 ， 当搜索的时候页面一刷新会导致搜索的消失。使用这个属性就可避免了
	    "bJQueryUI": true,    
	    "iDisplayLength": 10,  
	    "sPaginationType": "full_numbers", //分页，一共两种样式，full_numbers和two_button(默认)  
	    "sAjaxSource": "salary/list.action",
	    "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
            $('td:eq(0)', nRow).html('<a href="salary/view.action?roleId=' + aData[2] + '">' +
                aData[0] + '</a>');
            $('td:eq(2)', nRow).html('<a href="salary/delete.action?roleId=' + aData[2] + '">' +
                    '删除' + '</a>').append(('<a href="role/toModify.action?roleId=' + aData[2] + '">' +
                            '修改' + '</a>'));
            return nRow;
        },
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
		              {"mData": "4"},
		              {"mData": "5"},
		              {"mData": "6"},
		              {"mData": "7"},
		              {"mData": "8"},
		              {"mData": "9"}
		              ],   	
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
	
/*	function selectObjects(){
		$.ajax({
			  url: "person/loadPerson.action",
			  dataType: json,
			  success: funtion(result)({
				  return (result);
			  }) 
		});
	};*/
})
