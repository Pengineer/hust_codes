define(function(require, exports, module) {
	require('validate');
	
	exports.valid = function() {
		$("#form_message").validate({
			errorElement: "span",
			event: "blur",
			rules:{
				"message.title":{required:true, maxlength:100},
				"message.email":{required:true, email:true},
				"message.phone":{phone:true},
				"message.type.id":{selected:true},
				"keyword1":{maxlength:40},
				"keyword2":{},
				"keyword3":{},
				"keyword4":{maxlength:40},
				startDate:{dateSMDB:true},
				endDate:{dateSMDB:true}
			},
			errorPlacement: function(error, element) { 
				error.appendTo( element.parent("td").next("td") ); 
			}
		});
	};
});
