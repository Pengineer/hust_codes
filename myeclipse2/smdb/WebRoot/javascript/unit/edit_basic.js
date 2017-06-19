define(function(require, exports, module) {
	require('javascript/step_tools');
	
	var setting = new Setting({
		
		id: "basicInfo",
		
		buttons: ['next', 'finish', 'cancel'],
		
		out_check: function(){
			var a= $("#form_agency").valid();
			return a;
		}
	});
	
	var init = function(){
		// 子页面初始化接口
	};
	
	module.exports = {
		setting : setting,
		init : function(){init()}
	};
});
