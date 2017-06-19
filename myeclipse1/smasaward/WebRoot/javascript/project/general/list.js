/*
 * 退评与参评
 * */
function setReview(ob, // 复选框ID
			ff, // form表单名称或id
			flag) // 评审状态
{	
	var idsList1 = new Array();
	var $obj = $(":input[name='entityIds']");
	for(var i = 0; i < $obj.size(); i++){
		if($obj.eq(i).prop("checked")){
			idsList1.push($obj.eq(i).val());
		}
	}
	var entityIds = "";
	for(var i in idsList1){
		entityIds += ((i == 0) ? "" : "&") + "entityIds[" + i + "]=" + idsList1[i];
	}
	
	// 判断是否有专家被选中
	var cnt = count(ob);
	if (cnt == 0) {
		if (flag == 0) {
			alert("请选择要退出评审的项目！");
		} else if (flag == 1) {
			alert("请选择要参加评审的项目！");
		} else {
			alert("无效的项目参评状态！");
		}
		return false;
	} else {
		if (flag == 0){
			popSetReview({
				title : "填写退评原因",
				src : "project/general/toDisableReview.action?" + entityIds,
				callBack : function(result){
					window.location.href = "project/general/toList.action?update=1&listType=" + $("input[name='listType']").val() + "&isReviewable=1";
				}
			});
		} else if (flag == 1) {
			if (confirm("您确定要让选中的项目参加评审吗？")) {
				document.forms[ff].action = "project/general/enableReview.action";
				document.forms[ff].submit();
				return true;
			}
		} else {
			alert("无效的项目参评状态！");
			return false;
		}
	}
}

$(function(){
	var nameSpace = "project/general";
	
	pageShow({
		"nameSpace":nameSpace,
		"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5","sortcolumn6","sortcolumn7","sortcolumn8","sortcolumn9","sortcolumn10"],
		"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5,"sortcolumn6":6,"sortcolumn7":7,"sortcolumn8":8,"sortcolumn9":9,"sortcolumn10":10}
	});
	
	$(".linkView").live("click", function(){
		var url = basePath + nameSpace + "/toView.action?entityId=" + this.id;	//不再需要页码
		url += (this.type) ? "&listType=" + this.type : "";//(项目列表类型先如是判别)
		url += "&isReviewable=" + $("#isReviewable").val();
		url += "&businessType=" + $("#businessType").val();//项目业务类型
		window.location.href = url;
		return false;
	});
	
	//弹出层选择导出内容
	$("#popExport").click(function(){
		popProject({
			title : "选择导出内容",
			src : "project/general/popExport.action?isReviewable=" + $(":input[name='isReviewable']").val(),
			callBack : function(result){
			}
		});
	});
	
	//点击修改立项状态
	$(".isGranting").live('click', function(){
		var $obj = $(this);
		var flag = $obj.next().val();//立项状态：0,1,2
		var entityIds = $obj.next().next().attr('value');//项目ids
		$.ajax({
			url: "project/general/granting.action",
			type: "post",
			data: "flag=" + flag + "&entityIds=" + entityIds,
			success: function(){
				if(flag == 0){
					$obj.html('拟立项');
					$obj.next().attr('value', 1);
				} else if(flag == 1){
					$obj.html('确定立项');
					$obj.next().attr('value', 2);
				} else {
					$obj.html('不立项');
					$obj.next().attr('value', 0);
				}
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
				"<iframe id='expertTreeFrame' src='project/general/showExpertTree.action?' scrolling='auto' frameborder='no' border='0' width='100%'></iframe>" +
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
					url :"project/general/matchExpert.action",
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
			/*$("#startMatch").get(0).scrollIntoView();*/
		};
	});
	
	/*
	 * 手动匹配
	 * */
	$("#reMatchSelected").live("click", function(){
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
		
		var url = 'project/general/toSelectExpert.action?entityId=' +  id;
		document.forms[0].action = url;
		document.forms[0].submit();
	});
	
	/*
	 * 清空匹配
	 * */
	$("#deleteMatches").live("click", function(){
		popSetReview({
			title : "清空匹配",
			src : "project/general/toDeleteMatches.action",
			callBack : function(result){
				window.location.href = "project/general/toList.action?update=1&listType=1&isReviewable=1";
			}
		});
	});
	
	
	/*
	 * 立项核算
	 * */
	$("#grantedCheck").live("click", function(){
		window.location.href = "finance/toCheck.action";
	});
	
	/*
	 * 参评与退评
	 * */
	$("#review").live("click", function(){
		setReview('entityIds', 'list', 1); //参评
	});
	$("#unReview").live("click", function(){
		setReview('entityIds', 'list', 0); //退评
	});
	
});