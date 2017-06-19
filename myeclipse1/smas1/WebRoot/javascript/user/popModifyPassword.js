$(document).ready(function(){
	
	$("#form_modify_password").validate({
		errorElement: "label",
		event: "blur",
		rules:{
			"newPassword":{required:true, rangelength:[6,16], isPassWord:true},
			"rePassword":{required:true, rangelength:[6,16], isPassWord:true, equalTo:"#newPassword"}
			},
		// set this class to error-labels to indicate valid fields
		success: function(label) {
			// set &nbsp; as text for IE
			label.html("&nbsp;").addClass("checked");
		},
		errorPlacement: function(error, element) { 
			error.appendTo( element.parent("td").next("td") ); 
		}
	});
	
	$(".confirm").click(function(){
		if($("#form_modify_password").valid()){
			$.ajax({
			url: "user/modifyPassword.action",
			type: "post",
			data: "newPassword=" + $("#newPassword").val(),
			dataType: "json",
			success: function(result){
				if (result == null || result.errorInfo == null || result.errorInfo == "") {
					thisPopLayer.callBack(result);
					thisPopLayer.destroy();
				}else{
					alert(result.errorInfo);
				}
			}
		})
		}
	});
});