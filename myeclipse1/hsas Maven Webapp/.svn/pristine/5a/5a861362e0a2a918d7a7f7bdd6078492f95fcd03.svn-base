/*
 * @author:Liujia
 * @description:账号常看
 * @date:2015/08/28
 */
define(function(require, exports, module){
	var view = require("view");
	var nameSpace = "account";
	var showDetail = function(results){
		if (results.errorInfo == null || results.errorInfo == "") {
			$("#entityId").val(results.account.id);
	        $("#view_container").hide();
	        $("#view_container").html(TrimPath.processDOMTemplate("view_template",results.account))
	        $("#view_container").show();
		} else {
			alert(results.errorInfo);
		}
		  $.isLoading("hide");//关闭加载信息
	}
	exports.init = function(){
		view.show(nameSpace,showDetail);
    	view.add(nameSpace);
    	view.mod(nameSpace);
    	view.del(nameSpace);
    	view.next(nameSpace,showDetail);
    	view.prev(nameSpace,showDetail);
    	view.back(nameSpace);
	}
});