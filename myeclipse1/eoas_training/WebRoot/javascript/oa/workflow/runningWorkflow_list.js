$(document).ready(function(){
	$('#runningWorkflowList').dataTable({  
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
	    "sAjaxSource": "workflow/runningList.action",

	    
	    "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
	    	$('td:eq(2)', nRow).html('<a href="project/view.action?projectId=' + aData[5] + '">' +
                    aData[2] + '</a>');
            $('td:eq(3)', nRow).html('<a class="trace"  pid=' + aData[6] + ' pdid=' + aData[7] + ' title="点击查看流程图">' + aData[3]  +'</a>');
	    	
	    	
/*            $('td:eq(2)', nRow).html('<a href="project/viewRunning.action?processDefinitionId=' + aData[2] + '&resourceType=image' +  '">' +
                    aData[2] + '</a>');*/

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
                      {"mData": "4"}
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
	
	
	//查看流程图
	$(document).on("click", "a.trace", function(){

	    // 获取图片资源
	    var imageUrl = "/eoas/workflow/view.action?processDefinitionId=" + $(this).attr('pdid') + "&resourceType=image";
	    $.getJSON( "/eoas/workflow/trace.action?processDefinitionId=" + $(this).attr('pid'), function(infos) {

	        var positionHtml = "";

	        // 生成图片
	        var varsArray = new Array();
	        $.each(infos.activityInfos, function(i, v) {
	            var $positionDiv = $('<div/>', {
	                'class': 'activity-attr'
	            }).css({
	                position: 'absolute',
	                left: (v.x - 1),
	                top: (v.y - 1),
	                width: (v.width - 2),
	                height: (v.height - 2),
	                backgroundColor: 'black',
	                opacity: 0,
	                zIndex: $.fn.qtip.zindex - 1
	            });

	            // 节点边框
	            var $border = $('<div/>', {
	                'class': 'activity-attr-border'
	            }).css({
	                position: 'absolute',
	                left: (v.x - 1),
	                top: (v.y - 1),
	                width: (v.width - 4),
	                height: (v.height - 3),
	                zIndex: $.fn.qtip.zindex - 2
	            });

	            if (v.currentActiviti) {
	                $border.addClass('ui-corner-all-12').css({
	                    border: '3px solid red'
	                });
	            }
	            positionHtml += $positionDiv.outerHTML() + $border.outerHTML();
	            varsArray[varsArray.length] = v.vars;
	        });

	        if ($('#workflowTraceDialog').length == 0) {
	            $('<div/>', {
	                id: 'workflowTraceDialog',
	                title: '查看流程（按ESC键可以关闭）<button id="changeImg">如果坐标错乱请点击这里</button>',
	                html: "<div><img src='" + imageUrl + "' style='position:absolute; left:0px; top:0px;' />" +
	                "<div id='processImageBorder'>" +
	                positionHtml +
	                "</div>" +
	                "</div>"
	            }).appendTo('body');
	        } else {
	            $('#workflowTraceDialog img').attr('src', imageUrl);
	            $('#workflowTraceDialog #processImageBorder').html(positionHtml);
	        }

	        // 设置每个节点的data
	        $('#workflowTraceDialog .activity-attr').each(function(i, v) {
	            $(this).data('vars', varsArray[i]);
	        });

	        

	        dialog({
			    title: '查看流程',
			    
	            content: function() {
	                
	                $('#workflowTraceDialog').css('padding', '0.2em');
	                $('#workflowTraceDialog .ui-accordion-content').css('padding', '0.2em').height($('#workflowTraceDialog').height() - 75);

	                // 此处用于显示每个节点的信息，如果不需要可以删除
	                $('.activity-attr').qtip({
	                    content: function() {
	                        var vars = $(this).data('vars');
	                        var tipContent = "<table class='need-border'>";
	                        $.each(vars, function(varKey, varValue) {
	                            if (varValue) {
	                                tipContent += "<tr><td class='label'>" + varKey + "</td><td>" + varValue + "<td/></tr>";
	                            }
	                        });
	                        tipContent += "</table>";
	                        return tipContent;
	                    },
	                    position: {
	                        at: 'bottom left',
	                        adjust: {
	                            x: 3
	                        }
	                    }
	                });
	                // end qtip
	            },
	            width: document.documentElement.clientWidth * 0.95,
	            height: document.documentElement.clientHeight * 0.95
			}).showModal();
			return false; 
	        
	        
	    });
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
/*		alert($(this).attr('pid'));
		dialog({
		    title: '查看流程',
		    url: "/eoas/workflow/trace.action?processDefinitionId=" + $(this).attr('pid'),
		    content:'流程图',
		    width:250
		}).showModal();
		return false;*/
	});
	
	
	
	
	
	
	
	


	
	
	
	
	
})
