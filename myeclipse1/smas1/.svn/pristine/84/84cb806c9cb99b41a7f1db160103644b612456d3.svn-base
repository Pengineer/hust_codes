/*
 * 奖励分组列表实现
 * author：fengzheqi
 * */
$(function(){
	var nameSpace = "award/moeSocial/awardGroup";
	
	//列表初始化
	pageShow({
		"nameSpace":nameSpace,
		"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5"],
		"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5}
	});
	
	//查看详情
	$(".linkView").live("click", function(){
		var url = basePath + nameSpace + "/toView.action?entityId=" + this.id;	//不再需要页码
		url += (this.type) ? "&listType=" + this.type : "";//(奖励列表类型先如是判别)
		url += "&isReviewable=" + $("#isReviewable").val();
		window.location.href = url;
		return false;
	});
	
	//TODO
	//弹出层选择导出内容
	$("#popExport").click(function(){
		popAward({
			title : "选择导出内容",
			src : "award/moeSocial/popExport.action?isReviewable=" + $(":input[name='isReviewable']").val(),
			callBack : function(result){
			}
		});
	});
	
	//TODO
	/*
	 * 自动分组
	 * */
	$("#matchGroup").live("click", function(){
		if(confirm("您确定要进行自动分组吗？")) {
			
		}
	});
	
	//TODO
	/*
	 * 手动分组
	 * */
	$("#reMatchGroup").live("click", function(){
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
		var url = 'award/moeSocial/toSelectExpert.action?entityId=' +  id;
		document.forms[0].action = url;
		document.forms[0].submit();
	});
	
	//TODO
	/*
	 * 清空匹配
	 * */
	$("#deleteMatches").live("click", function(){
		popSetReview({
			title : "清空匹配",
			src : "award/moeSocial/toDeleteMatches.action",
			callBack : function(result){
				window.location.href = "award/moeSocial/toList.action?update=1&listType=1&isReviewable=1";
			}
		});
	});
	
});