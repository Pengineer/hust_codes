define(function(require, exports, module) {
	require('validate');
	
	exports.valid = function() {
		$("#form_role").validate({
			errorElement: "span",
			event: "blur",
			rules:{
				"role.name":{required:true, maxlength:200},
				"role.description":{required:true, maxlength:800},
				"keyword1":{maxlength:200},
				"keyword2":{maxlength:800},
				"keyword3":{maxlength:40}
			},
			errorPlacement: function(error, element) { 
				error.appendTo( element.parent("td").next("td") ); 
			}
		});
	};
});
