define(function(require, exports, module) {
	require('validate');
	
	exports.valid = function() {
		$("#form_stat").validate({
			errorElement: "span",
			event: "blur",
			rules:{
				"num":{digits:true},
			},
			errorPlacement: function(error, element) {
				error.appendTo( element.parent("td").next("td") ); 
			}
		});
	};
});
