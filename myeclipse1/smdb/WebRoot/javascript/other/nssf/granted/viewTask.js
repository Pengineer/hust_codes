define(function(require, exports, module) {
	require('javascript/template_tool');
	require('pop-init');
	
	var showData = function() {
		$.get("other/nssf/granted/viewTask.action", function(data){
			console.log(data);
			Template_tool.populate(data);
		});
	}
	
	exports.init = function() {
		Template_tool.init();

		$("#btn_update").live("click", function(){
			$.post("other/nssf/granted/update.action");
		});
		
		showData();
		setInterval(showData, 2000);
	};
});
