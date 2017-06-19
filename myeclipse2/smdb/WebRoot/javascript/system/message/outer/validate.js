define(function(require, exports, module) {
	require('validate');
	
	exports.valid = function() {
		$("#form_message").validate({
			errorElement: "span",
			event: "blur",
			rules:{
				"message.title":{required:true, maxlength:100},
				"message.authorName":{required:true, maxlength:100},
				"message.email":{required:true, email:true},
				"message.type.id":{selected:true},
				"message.phone":{phone:true}
			},
			errorPlacement: function(error, element) { 
				error.appendTo( element.parent("td").next("td") ); 
			}
		});
	};
});
