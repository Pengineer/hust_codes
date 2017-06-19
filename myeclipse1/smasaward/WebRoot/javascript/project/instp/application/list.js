$(function(){
	var nameSpace = "project/instp";
	
	/*
	 * 配置列表
	 * */
	pageShow({
		"nameSpace":nameSpace,
		"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5","sortcolumn6","sortcolumn7"],
		"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5,"sortcolumn6":6,"sortcolumn7":7}
	});
	
	/*
	 * 进入项目详情
	 * */
	$(".linkView").live("click", function(){
		var url = basePath + nameSpace + "/toView.action?entityId=" + this.id;	//不再需要页码
		url += (this.type) ? "&listType=" + this.type : "";//(项目列表类型先如是判别)
		url += "&isReviewable=" + $("#isReviewable").val();
		url += "&businessType=" + $("#businessType").val();//项目业务类型
		window.location.href = url;
		return false;
	});
	
	/*
	 * 弹出层选择导出内容
	 * */
	$("#popExport").click(function(){
		popProject({
			title : "选择导出内容",
			src : "project/instp/popExport.action?isReviewable=" + $(":input[name='isReviewable']").val(),
			callBack : function(result){
			}
		});
	});
	
	/*
	 * 自动匹配
	 * */
	$("#matchExpert").live("click", function(){
		if ($("#expertTreeFrame").length == 0) {
			$("body", this.document).append(
				"<p style='margin-top:20px;margin-left:10px;'>将在现有匹配结果上补配专家，原有专家匹配结果将保留。<br />请勾选指定参与匹配的专家，不指定则所有参评专家均参与匹配。</p>" +
				"<iframe id='expertTreeFrame' src='project/instp/showExpertTree.action?' scrolling='auto' frameborder='no' border='0' width='100%'></iframe>" +
				"<div style='margin-bottom:20px;text-align:center'><input type='button' id='startMatch' value='开始匹配/补配' class='btn' /><span id='waiting' style='display:none'>匹配/补配中，请稍后……</span></div>" 
			);
			$("#startMatch").click(function() {
				var iframeObj = document.getElementById('expertTreeFrame').contentDocument;
				var selectedExpertIds = iframeObj.getElementById('selectedExpertIds').value;
				$(this).remove();
				$("#waiting").show();
				
				/*
				 * 为匹配添加遮罩层
				*/
				$("#mask",parent.document).css({
					display: "block", height: $(parent.document).height()
				});
				var $box = $(".mask_box",parent.document);
				$box.css({
					//设置弹出层距离左边的位置
					left: ($("body",parent.document).width() - $box.width()) / 2 - 20 + "px",
					//设置弹出层距离上面的位置
					top: ($(window).height() - $box.height()) / 2 + $(window).scrollTop() + "px",
					display: "block"
				});
				$.ajax({
					type : "POST",
					url :"project/instp/matchExpert.action",
					data : {selectedExpertIds: selectedExpertIds},
					success : function(){
						$("#mask,.mask_box",parent.document).css("display", "none");
						location.reload();
					},
					error : function(){
						$("#mask,.mask_box",parent.document).css("display", "none");
						alert("匹配出错!");
						location.reload();
					}
				});
			});
//			$("#startMatch").get(0).scrollIntoView();
		};
	});
	
	/*
	 * 手动匹配
	 * */
	$("#reMatchExpert").live("click", function(){
		var xx = document.getElementsByName('entityIds');
		var cnt = 0;
		var id = '';
		for(var i = 0; i < xx.length; i++) {
			if(xx[i].checked) {
				cnt++;
				id = id + xx[i].value + ';';
			}
		}
		if(cnt == 0) {
			alert('请选择至少一个项目！');
			return ;
		}
		if(id.length > 1) {
			id = id.substr(0, id.length - 1);
		}
		
		var url = 'project/instp/toSelectExpert.action?entityId=' +  id;
		document.forms[0].action = url;
		document.forms[0].submit();
	});
	
	/**
	 *清空匹配
	 */
	$("#deleteMatches").live("click", function(){
		popSetReview({
			title : "清空匹配",
			src : "project/instp/toDeleteMatches.action",
			callBack : function(result){
				window.location.href = "project/instp/toList.action?update=1&listType=1&isReviewable=1";
			}
		});
	});
});