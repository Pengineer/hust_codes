define(function(require, exports, module) {
	require('validate');
	
	exports.valid = function() {
		$("#form_notice").validate({
			errorElement: "span",
			event: "blur",
			rules:{
				"notice.title":{required:true, maxlength:100},
				"notice.type.id":{},
				"notice.source":{maxlength:200},
				"notice.isOpen":{required:true},
				"notice.validDays":{required:true, digits:true, max:999},
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
