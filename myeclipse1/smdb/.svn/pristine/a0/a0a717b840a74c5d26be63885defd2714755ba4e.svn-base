define(function(require, exports, module) {
	require('validate');
	
	exports.valid = function() {
		$("#form_news").validate({
			errorElement: "span",
			event: "blur",
			rules:{
				"news.title":{required:true, maxlength:100},
				"news.type.id":{},
				"news.source":{maxlength:200},
				"news.isOpen":{required:true},
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
