$(document).ready(function(){
	$('#taskList').dataTable({  
	    "bProcessing": true, //显示是否加载    
	    "bScrollCollapse": true,  //当显示的数据不足以支撑表格的默认的高度时，依然显示纵向的滚动条。(默认是false) 
	    "sScrollX": "100%", 
	    "sScrollXInner": "100%",
	    "bAutoWidth":true,
	    "bPaginate": true,  //是否显示分页
	    "bLengthChange": true,  //改变每页显示数据数量
	    "bFilter": true, //搜索栏
	    "bSort": true, //是否支持排序功能
	    "bInfo": true, //显示表格信息    
	    "aaSorting": [[1, "asc"]],  //给列表排序 ，第一个参数表示数组 (由0开始)。1 表示Browser列。第二个参数为 desc或是asc		    
	    "bStateSave": true, //保存状态到cookie *************** 很重要 ， 当搜索的时候页面一刷新会导致搜索的消失。使用这个属性就可避免了
	    "bJQueryUI": true,    
	    "iDisplayLength": 10,  
	    "sPaginationType": "full_numbers", //分页，一共两种样式，full_numbers和two_button(默认)  
	    "sAjaxSource": "project/taskList.action",  
	    "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
            $('td:eq(6)', nRow).html(	
				function(){
					if(aData[5] == ""){
						return "";
					}else{
						var disIcon,iconClass,title;
						if(eval( "(" + aData[6] + ")" ).assignee == 'null'){
							title="签收",
							disIcon = "claimTask",
							iconClass = "glyphicon glyphicon-pencil u-modify";
						}else{
							title="办理",
							disIcon = "handleTask",
							iconClass = "glyphicon glyphicon-trash u-delete";
						}
						return '<a title="' + title + '" class="' + disIcon + '" id="'+eval( "(" + aData[6] + ")" ).id+ 
							 '" tkey="' + eval( "(" + aData[6] + ")" ).taskDefinitionKey +
							 '" tname="' + eval( "(" + aData[6] + ")" ).name +
							'" href="javascript:void(0)">'
								+ '<span class="' + iconClass + '">' + eval( "(" + aData[6] + ")" ).id + '</span>'
								+ '</a>';
					}
				}	
            );
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
    				var count = document.getElementById("taskList").getElementsByTagName("tbody")[0].getElementsByTagName("tr").length;
    				for (var i = 0; i < count; i++) {
    					document.getElementById("taskList").getElementsByTagName("tbody")[0].getElementsByTagName("tr")[i].id = 
    						document.getElementById("taskList").getElementsByTagName("tbody")[0].getElementsByTagName("tr")[i].getElementsByTagName("td")[0].firstChild.data;
    					document.getElementById("taskList").getElementsByTagName("tbody")[0].getElementsByTagName("tr")[i]
    					.setAttribute("tid",document.getElementById("taskList").getElementsByTagName("tbody")[0].getElementsByTagName("tr")[i].getElementsByTagName("td")[6].getElementsByTagName("a")[0].id);
    					}
	    			}
	    		});
	    	},
		"aoColumns": [{"mData": "0"},
                      {"mData": "1"},
                      {"mData": "2"},
                      {"mData": "3"},
                      {"mData": "4"},
                      {"mData": "5"},
                      {"mData": "6"}
		              ],   	
		"oLanguage": {
			"infoEmpty":      "显示 0 to 0 of 0 entries",  
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

	$("body").on("click", ".handle", function(){
		$( "#handleDialog" ).dialog("open");
	});

	//签收
	$("body").on("click", ".claimTask", function(){
		var that = this;
		dialog({
		    title: '提示',
		    content: '确定要签收吗?',
		    width:250,
		    okValue: '确定',
		    ok: function () {
		    	var url = "http://localhost:8080/eoas/project/claim.action?taskId=" + $('.claimTask').attr("id");
		    	$.get(url, function(data){
		    		
		    		if(data.result == "1"){
		    			dialog({
		    			    title: '提示',
		    			    content: '签收成功',
		    			    width:250,
		    			    okValue: '确定',
		    			    ok: function () {
		    			    	window.location.href = "project/toTaskList.action";
		    			    }
		    			}).showModal();
		    		} else {
		    			dialog({
		    			    title: '提示',
		    			    content: '签收失败!',
		    			    width:250,
		    			    okValue: '确定',
		    			    ok: function () {
		    			    	window.location.href = "project/toTaskList.action";
		    			    }
		    			}).showModal();
		    		}
		    	})
		    },
		    cancelValue: '取消',
		    cancel: function () {}
		}).showModal();
	});
	
	// 用于保存加载的详细信息
	var detail = {};
	
	/**
	 * 加载详细信息
	 * @param {Object} id
	 */
	function loadDetail(id, withVars, callback) {
	    var dialog = this;
	    $.ajax({
	    	  url: "project/load.action",
	    	 
	    	  dataType: "json",
	    	  success: function(result) {

	    		  $.each(result, function(k, v) {


	  				$('.view-info td[name="accountId"]', dialog).text(result.name);

	  				
	  	        });
	    	  }     	  
	    	});
	};
	
	//办理
	$("body").on("click", ".handleTask", function(){
		// 当前节点的英文名称
		var tkey = $(this).attr('tkey');
		// 当前节点的中文名称
		var tname = $(this).attr('tname');
		// 项目记录ID
		var rowId = $(this).parents('tr').attr('id');
		// 任务ID
		var taskId = $(this).parents('tr').attr('tid');
		
		var that = this;
		alert(tkey);
		dialog({
		    title: '流程办理[' + tname + ']',
		    url: "project/load.action?projectId=" + rowId+"&taskId="+taskId,
		    width:250, 
		    button:handleOpts[tkey].btns
		}).showModal();
	});

	/**
	 * 完成任务
	 * @param {Object} taskId
	 */
	function complete(taskId, variables) {
	    var dialog = this;
		// 转换JSON为字符串
	    var keys = "", values = "", types = "";
		if (variables) {
			$.each(variables, function() {
				if (keys != "") {
					keys += ",";
					values += ",";
					types += ",";
				}
				keys += this.key;
				values += this.value;
				types += this.type;
			});
		}
		
	    $.post("project/complete.action?taskId=" + taskId, {
	        keys: keys,
	        values: values,
	        types: types
	    } , function(resp) {
			/*$.unblockUI();*/
	        if (resp.result == '1') {
	            alert('任务完成');
	            location.reload();
	        } else {
	            alert('操作失败!');
	        }
	    });
	}
	 
	var handleOpts = {
			expertAudit: {
				width: 300,
				height: 300,
				open: function(id) {
					// 打开对话框的时候读取项目内容
					loadDetail.call(this, id);
				},
				btns: [{
					value: '同意',
					callback: function() {
						//alert(document.getElementsByTagName("frame")[2].contentWindow.document.body.getElementsByTagName('iframe')[0].contentWindow.document.getElementById('popTaskId').getAttribute("value"));
						var taskId = this.iframeNode.contentWindow.document.getElementById('popTaskId').getAttribute("value");
						
						// 设置流程变量
						complete(taskId, [{
							key: 'expertPass',
							value: true,
							type: 'B'
						}]);
					}
				}, {
					value: '驳回',
					callback: function() {
						var taskId = this.iframeNode.contentWindow.document.getElementById('popTaskId').getAttribute("value");	
						dialog({
						    title: '填写驳回理由',					    
						    width:250, 
						    content: "<textarea id='expertBackReason' style='width: 250px; height: 60px;'></textarea>",
						    button:[{
								value: '驳回',
								callback: function() {
									var expertBackReason = $('#expertBackReason').val();
									if (expertBackReason == '') {
										alert('请输入驳回理由！');
										return;
									}	
									// 设置流程变量
									complete(taskId, [{
										key: 'expertPass',
										value: false,
										type: 'B'
									}, {
										key: 'expertBackReason',
										value: expertBackReason,
										type: 'S'
									}]);
								}
							}, {
								value: '取消',
								cancel: function () {}
							}]
						}).showModal();
					}
				}, {
					value: '取消',
					cancel: function () {}
				}]
			},


			
			adminAudit: {
				width: 300,
				height: 300,
				open: function(id) {
					// 打开对话框的时候读取项目内容
					loadDetail.call(this, id);
				},
				btns: [{
					value: '同意',
					callback: function() {
						//alert(document.getElementsByTagName("frame")[2].contentWindow.document.body.getElementsByTagName('iframe')[0].contentWindow.document.getElementById('popTaskId').getAttribute("value"));
						var taskId = this.iframeNode.contentWindow.document.getElementById('popTaskId').getAttribute("value");
						
						// 设置流程变量
						complete(taskId, [{
							key: 'adminPass',
							value: true,
							type: 'B'
						}]);
					}
				}, {
					value: '驳回',
					callback: function() {
						var taskId = this.iframeNode.contentWindow.document.getElementById('popTaskId').getAttribute("value");	
						dialog({
						    title: '填写驳回理由',					    
						    width:250, 
						    content: "<textarea id='adminBackReason' style='width: 250px; height: 60px;'></textarea>",
						    button:[{
								value: '驳回',
								callback: function() {
									var adminBackReason = $('#adminBackReason').val();
									if (adminBackReason == '') {
										alert('请输入驳回理由！');
										return;
									}	
									// 设置流程变量
									complete(taskId, [{
										key: 'adminPass',
										value: false,
										type: 'B'
									}, {
										key: 'adminReason',
										value: adminBackReason,
										type: 'S'
									}]);
								}
							}, {
								value: '取消',
								cancel: function () {}
							}]
						}).showModal();
					}
				}, {
					value: '取消',
					cancel: function () {}
				}]
			},
			
			
			
			

			
			
			
			
		reportBack: {
			width: 400,
			height: 400,
			open: function(id, taskId) {
				// 打开对话框的时候读取内容
				loadDetail.call(this, id, taskId);
			},
			btns: [{
				value: '提交',
				callback: function() {

					var taskId = this.iframeNode.contentWindow.document.getElementById('popTaskId').getAttribute("value");
					var realityStartTime = $('#realityStartTime').val();
					var realityEndTime = $('#realityEndTime').val();
					
					if (realityStartTime == '') {
						alert('请选择实际开始时间！');
						return;
					}
					
					if (realityEndTime == '') {
						alert('请选择实际结束时间！');
						return;
					}
					
					
					complete(taskId, [{
						key: 'realityStartTime',
						value: realityStartTime,
						type: 'D'
					}, {
						key: 'realityEndTime',
						value: realityEndTime,
						type: 'D'
					}]);
				
			
				}
			},{
				value: '取消',
				cancel: function () {}
				
			}]
		}
	};
	
})