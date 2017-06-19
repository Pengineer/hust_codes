define(function(require, exports, module) {
	var view = require('javascript/view');
	var viewPro = require('javascript/product/view');
	require('tool/poplayer/js/pop');
	require('pop-self');
		
	var apply = function(url, nameSpace, showDetails){//添加报奖申请
		popProductOperation({
			title: "添加奖励申请",
			src: url,
			callBack: function(layer){
				viewPro.show(nameSpace, viewPro.showDetails);
				layer.destroy();
			}
		});
	};
	
	var modifyApply = function(url, nameSpace, showDetails){//修改报奖申请
		popProductOperation({
			title: "修改奖励申请",
			src: url,
			callBack: function(layer){
				viewPro.show(nameSpace, viewPro.showDetails);
				layer.destroy();
			}
		});
	};
	var submitApply = function(url, nameSpace, showDetails){//提交报奖申请
		if (confirm("提交后不能修改, 您确定要提交吗？")) {
			$.ajax({
				url: url,
				success: function(result){
					if(result.errorInfo == null || result.errorInfo == "") {
						viewPro.show(nameSpace, viewPro.showDetails);
					} else {
						alert(result.errorInfo);
					};
				 }
			});
		};
	};
	var deleteApply = function(url, nameSpace, showDetails){//删除报奖申请
		if (confirm("您确定要删除此项奖励申请吗？")) {
			$.ajax({
				url: url,
				success: function(result){
					if(result.errorInfo == null || result.errorInfo == "") {
						viewPro.show(nameSpace, viewPro.showDetails);
					} else {
						alert(result.errorInfo);
					};
				}
			});
		 };
	};
	
	var loadSession = function(id){//获得获奖届次下拉框
		for(var i=1;i<30;i++){
		DWRUtil.addOptions(id, [{name:'第'+Num2Chinese(parseInt(i))+'届 ',id:i}], 'id', 'name');
		}
	};
	
	module.exports = {
		apply: function(url, nameSpace, showDetails) {apply(url, nameSpace, showDetails)},
		modifyApply: function(url, nameSpace, showDetails) {modifyApply(url, nameSpace, showDetails)},
		submitApply: function(url, nameSpace, showDetails) {submitApply(url, nameSpace, showDetails)},
		deleteApply: function(url, nameSpace, showDetails) {deleteApply(url, nameSpace, showDetails)},
		loadSession: function(id){loadSession(id)}
	};
});