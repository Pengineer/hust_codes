$(function() {
	var errorInfo = [
	"",
	"该用户名已注册，请重新输入",
	"该邮箱已注册，请输入其他邮箱",
	"该身份证已注册，请核实后重新输入"
	];
	
	$("#register-form").validate({
		errorElement: "span",
		event: "blur",
		submitHandler:function() {
			$("#register-form").ajaxSubmit(function(data) {
				if(data.result=="success") {
					$("#tips").show(500);
					setTimeout("window.location.href = window.location.protocol + '//' + window.location.host + '/dmss/'", 2000);
					return false;
				} else {
					var label = data.error.registerError;
					$("#errorInfo").html(errorInfo[label]);
					return false;
				}
			});
		},
		rules: {
			"accountName": {required:true,userName:true},
			"password": {required:true,passWord:true},
			"repassword": {required:true,equalTo: "#Passwd"},
			"mobilePhone": {cellPhone:true},
			"email": {required:true,email:true},
			"qq": {QQ:true},
			"idCard": {idCard:true}
			},
		errorPlacement: function(error, element){
			error.appendTo(element.parent().next().addClass("warning"));
		}
	});

});