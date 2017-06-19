define(function(require, exports, module) {
	require('validate');
	
	exports.valid = function(){
		$("#form_dm").validate({
			errorElement: "span",
			event: "blur",
			rules:{
				"type":{selected:true},
				"topKnum":{required:true}
			},
			errorPlacement: function(error, element) { 
				error.appendTo( element.parent("td").next("td") ); 
			}
		});
	};
});
