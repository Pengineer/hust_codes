$(document).ready(function(){
	var nameSpace = "project/general/publicity";
	pageShow({
		"nameSpace":nameSpace,
		"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5"],
		"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5}
	});

	$(".linkView").live("click", function(){
		var url = basePath + nameSpace + "/toView.action?entityId=" + this.id;	//不再需要页码
		url += (this.type) ? "&listType=" + this.type : "";//(项目列表类型先如是判别)
		url += "&isReviewable=" + $("#isReviewable").val();
		window.location.href = url;
		return false;
	});
	
	//列表类别，是否显示公示的项目
//	$("#listFlag1").attr('checked', true);
	$(":input[name='listFlag']").click(function(){
		var listFlag = $(this).val();
		window.location.href = basePath + nameSpace + "/toList.action?update=1&isReviewable=1&listType=2&listFlag="+listFlag;
		return false;
	});
		/**
	 * 确定立项
	 * @param {Object} entityIds 
	 * 如果为null表示全部立项<br>
	 * 否则将选定的项目立项
	 */
	var confirmGrant = function(entityIds){
		var listType = $("#listType").val();
		$.ajax({
			url: basePath + nameSpace + "/confirmGrant.action",
			type: "post",
			dataType: "json", 
			data: entityIds,
			success: function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
					window.location.href = basePath + nameSpace + "/toList.action?update=1&isReviewable=1&listType=" + listType;
				} else {
					alert(result.errorInfo);
				}
			}
		});
	};
	
	//全部立项
	$("#allGrant").live("click", function(){
		if(!confirm("提示：确定要将所有公示的项目立项吗？")){
			return;
		}
		confirmGrant(null);
	});
	
	//选择立项
	$("#selectedGrant").live("click", function(){
		var entityIds = "";
		$("input[type='checkbox'][name='entityIds']:checked").each(function(index){
			if(index == 0) {
				entityIds += "entityIds[0]=" + $(this).val();
			} else {
				entityIds += "&entityIds[" + index + "]=" + $(this).val();
			}
		});
		if(entityIds == ""){
			alert("请选择要立项的项目！");
			return false;
		}
		if(!confirm("提示：确定要将当前选中的项目立项吗？")){
			return ;			
		}
		confirmGrant(entityIds);
	});
	
	
	// 弹出层添加举报信息，初始化列表
	$("img[class='addInform']").live('click', function(){
		var entityId = $("#entityId", $(this).parent("td").parent("tr")).val();// 项目id
		popProject({
			title : "给当前项目添加举报信息",
			src : basePath + nameSpace + "popInform.action?entityId=" + entityId,
			inData : {"entityId" : entityId}//从父页面传入弹层的参数
		});
	});
	
	//导出公示项目清单
	$("#exportPubList").live('click', function(){
		window.location.href = basePath + nameSpace + "exportPublicityList.action";
	});
	
	// 项目公示列表点击改变公示状态
	$(".isPublic").live('click', function(){
		var $obj = $(this);
		var $objGranted = $("#isGranted",$(this).parent("td").parent("tr"));//是否已经确定立项
		var $objPublic = $("#publicStatus", $(this).parent("td").parent("tr"));//是否公示
		var entityId = $("#entityId", $(this).parent("td").parent("tr")).val();//项目id
		var publicFlag = $objPublic.val();// 公示状态
		var isGranted = $objGranted.val();//是否已经确定立项
		if(isGranted == 1){//已立项不能再修改公式了
			alert("该项目已确定立项，无法修改！");
			return false;
		}
		$.ajax({
			url: nameSpace + "updatePublic.action",
			type: "post",
			data: "publicFlag=" + publicFlag + "&entityId=" + entityId,
			success: function(){
				if(publicFlag == 0){
					$obj.html('是');
					$objPublic.attr('value', 1);
					window.location.href = basePath + nameSpace + "/toList.action?update=1&isReviewable=1";//刷新页面
				} else if(publicFlag == 1){
					$obj.html('否');
					$objPublic.attr('value', 0);
					window.location.href = basePath + nameSpace + "/toList.action?update=1&isReviewable=1";//刷新页面
				}else{
					$obj.html('是');
					$objPublic.attr('value', 2);
				}
				return false;
			}
		});
	});
	
//	// 项目列表点击改变拟立项状态
//	$(".isGranting").live('click', function(){
//		var $obj = $(this);
//		var $objPublic = $(".isPublic", $(this).parent("td").parent("tr"));
//		var $objGrant = $("#grantStatus", $(this).parent("td").parent("tr"));//项目立项状态
//		var entityId = $("#entityId", $(this).parent("td").parent("tr")).val();// 项目id
//		var flag = $objGrant.val();
//		$.ajax({
//			url: "project/general/granting.action",
//			type: "post",
//			data: "flag=" + flag + "&projectids=" + entityId,
//			success: function(){
//				if(flag == 0){
//					$obj.html('拟立项');
//					$objPublic.html('是');
//					$objGrant.attr('value', 1);
//				} else if(flag == 1){
//					$obj.html('确定立项');
//					$objPublic.html('是');
//					$objGrant.attr('value', 2);
//				} else {
//					$obj.html('不立项');
//					$objPublic.html('否');
//					$objGrant.attr('value', 0);
//				}
//			}
//		});
//	});
});


