$(document).ready(function(){
	$("#form_person").validate({
		errorElement: "span",
		event: "blur",
		rules:{
				"person.name":{required:true, maxlength:40},
				"person.idcardNumber":{required:true, cardNumber:true}
			},
		errorPlacement: function(error, element) {
			error.appendTo( element.parent().parent() ); 
		}
	});
});

var attrRules = {
	"personInfo":[
		["universityId",{required:true}],
		["departmentId",{selected:true}],
		["academic.specialityTitle",{selected:true}]
	],
	"contactInfo":[
		["person.mobilePhone",{phone:true}],
		["person.officePhone",{phone:true}],
		["person.email",{required:true, email:true}]
	],
	"accountInfo":[
		["passport.name",{required:true, rangelength:[3,40], userName:true, remote:{url: "account/teacher/checkUsername.action", type: "post", dataType: "json"}}],
		["newPassword",{required:true, passWord:true}],
		["rePassword",{required:true, passWord:true, equalTo:"#newPassword"}]
	]
};

var valid_personInfo = function(){
	for(i = 0; i < attrRules.personInfo.length; i++){
		$("':input[name=" + attrRules.personInfo[i][0] + "]'").rules("add", attrRules.personInfo[i][1] );
	}
};
var remove_personInfo = function(){
	for(i = 0; i < attrRules.personInfo.length; i++){
		$("':input[name=" + attrRules.personInfo[i][0] + "]'").rules("remove");
	}
};

var valid_contactInfo = function(){
	for(i = 0; i < attrRules.contactInfo.length; i++){
		$("':input[name=" + attrRules.contactInfo[i][0] + "]'").rules("add", attrRules.contactInfo[i][1] );
	}
};
var remove_contactInfo = function(){
	for(i = 0; i < attrRules.contactInfo.length; i++){
		$("':input[name=" + attrRules.contactInfo[i][0] + "]'").rules("remove");
	}
};

var valid_accountInfo = function(){
	for(i = 0; i < attrRules.accountInfo.length; i++){
		$("':input[name=" + attrRules.accountInfo[i][0] + "]'").rules("add", attrRules.accountInfo[i][1] );
	}
};
var remove_accountInfo = function(){
	for(i = 0; i < attrRules.accountInfo.length; i++){
		$("':input[name=" + attrRules.accountInfo[i][0] + "]'").rules("remove");
	}
};

//根据注册时填写的人员姓名及证件号判断是否存在此人
var checkPersonInfo = function(){
	var personName = $("input[name='person.name']").val().trim();
	var idcardNumber = $("input[name='person.idcardNumber']").val().trim();
	DWREngine.setAsync(false);
	personService.checkPersonInfo(idcardNumber, personName, call_back_checkPersonInfo);
	DWREngine.setAsync(false);
	if (result[0] == 0){
		alert("您已有账号，请点击取消返回首页直接登录！");
		return false;
	} else if (result[0] == 3){
		alert("您的姓名和证件号不匹配，请重新填写！");
		return false;
	} else if (result[0] == 1){
		$("#registerType").val(1);
		return true;
	} else if(result[0] == 2){
		$("#registerType").val(2);
		return true;
	}
}

function call_back_checkPersonInfo(_result){
	result = _result;
}