$(document).ready(function(){
	
	var autoCompleteFlag = true;// false表示从自动补全中选择 true表示自己写的
	
	$("#valid").click(function(){
		if($("#form_person").valid()){
			if(checkPersonInfo()){
				valid_personInfo();
				hideAll();
				$("#personInfo").show();
			}
		}
	});
	$("#prev2").click(function(){
		remove_personInfo();
		hideAll();
		$("#identifier").show();
	});
	$("#next2").click(function(){
		if($("#form_person").valid()){
			valid_contactInfo();
			hideAll();
			$("#contactInfo").show();
		}
	});
	$("#prev3").click(function(){
		remove_contactInfo()
		hideAll();
		$("#personInfo").show();
	});
	$("#next3").click(function(){
		if($("#form_person").valid()){
			valid_accountInfo();
			hideAll();
			$("#accountInfo").show();
		}
	});
	$("#prev4").click(function(){
		remove_accountInfo();
		hideAll()
		$("#contactInfo").show();
	});
	$("#toIndex").click(function(){
		window.location.href = basePath + "toIndex.action";
		return false;
	});
	
	$("#university").click(function(){
		if (this.style.color == 'gray'){
			this.style.color = 'black';
			this.value = "";
		}
	});
	
	$("#university").blur(function(){
		if(autoCompleteFlag){
			this.style.color = 'gray';
			this.value = "输入首字母，选择匹配结果";
		} else {
			this.style.color = 'black';
		}
	});
	
	$("#university").change(function(){
		autoCompleteFlag = true;
	});
	
	$("#university").autocomplete({
		source: "account/teacher/universityJSON.action",
			minLength: 2,
			select: function( event, ui ) {
				saveSelected( ui.item );
				selectDepartment;
				this.style.color = 'black';
				autoCompleteFlag = false;
			}
	});
	
	$("#university").bgiframe();//修复ie6下控件被select标签遮盖的问题
})

var hideAll = function(){
	$("#identifier").hide();
	$("#personInfo").hide();
	$("#contactInfo").hide();
	$("#accountInfo").hide();
};

var saveSelected = function(item){
	$("#universityId").val(item.id);
};

/**
 * 根据已选择的学校获取对应院系的列表
 */
var selectDepartment = function(){
	var universityId = $("#universityId").val();
	if(universityId != null && universityId != ""){
		personService.getDepartmentByUniversity(universityId, callbackDepartment);
	} else{
		DWRUtil.removeAllOptions("departmentId");
		DWRUtil.addOptions('departmentId',[{name:'--请选择--',id:'-1'}],'id','name');
	}
};

var callbackDepartment = function(data) {
	DWRUtil.removeAllOptions("departmentId");
	DWRUtil.addOptions('departmentId',[{name:'--请选择--',id:'-1'}],'id','name');
	DWRUtil.addOptions('departmentId',data);
};