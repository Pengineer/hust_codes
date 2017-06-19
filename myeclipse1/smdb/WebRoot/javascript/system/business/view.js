define(function(require, exports, module) {
	var view = require('javascript/view');
	
	var nameSpace = "business";
	
	var init_view = function(nameSpace) {
		$(function() {
			view.show(nameSpace, showDetails);
			view.add(nameSpace);
			view.mod(nameSpace);
			view.del(nameSpace, "您确定要删除此人员吗？");
			view.prev(nameSpace, showDetails);
			view.next(nameSpace, showDetails);
			view.back(nameSpace);// 所有查看页面公共部分
		});
	};
	
	var showDetails = function(result) {// 显示查看内容
		if (result.errorInfo == null || result.errorInfo == "") {
			$("#entityId").attr("value", result.business.id);
			$("#entityIds").attr("value", result.business.id);
			$("#view_container").hide();
			$("#view_container").html(TrimPath.processDOMTemplate("view_template", result));
			$("#view_container").show();
			reviewDeadlineValidate();
			businessYearValidate();
		}else {
			alert(result.errorInfo);
		}
		if (parent != null) {
			parent.loading_flag = false;
			parent.hideLoading();
		}
	};
	
	/**
	 * 根据业务子类类型判断专家评审截止时间是否需要
	 */
	var reviewDeadlineValidate = function(){
		var subType = $("#subType").text();
		if(subType.indexOf('申请')>0 || subType.indexOf('结项')>0 || subType.indexOf('招标')>0){
			$("#reviewDeadline").show();
		}else{
			$("#reviewDeadline").hide();
		}
	};
	
	/**
	 * 根据业务子类类型判断业务年份是否需要
	 */
	var businessYearValidate = function(){
		var subType = $("#subType").text();
		if(subType.indexOf('年检')>0){
			$("#businessYear").show();
		}else{
			$("#businessYear").hide();
		}
	};
	
	exports.init = function() {
		init_view(nameSpace);
	};
	
});

