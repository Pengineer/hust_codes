/*
 * 数据处理页面
 * author：fengzheqi
 * */
$(function(){
	/*var right = window.frames['right'];
	$("#tabs").tabs();
	$("#dataTabs" ).tabs().addClass( "ui-tabs-vertical ui-helper-clearfix" );
	$("#dataTabs li" ).removeClass( "ui-corner-top" ).addClass( "ui-corner-left" );
	
	$(":input[name='isSecret']").live("change", function(){
		var onDiv = $(this).parent().parent();
		var isSecret = $(":input[name='isSecret']:checked", onDiv).val();
		if(isSecret == 0) {
			$(".secret-type", onDiv).show(100);
		} else {
			$(".secret-type", onDiv).hide();
		}
	});
	
	$("#aa").live("click", function(){
		alert("a");
	})*/
	
	$("#form_synData").validate({
		errorElement: "label",
		event: "blur",
		rules: {
			"dataTypeTag": {required:true,isSelected:true},
			"projectTypeTag": {required:true,isSelected:true}
		},
		success: function(label) {
			// set &nbsp; as text for IE
			label.html("&nbsp;").addClass("checked");
		},
		errorPlacement: function(error, element) { 
			//alert(element.innerHTML);
			error.appendTo( element.parent("td")); 
		}
	})
})