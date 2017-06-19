define(function(require, exports, module) {
	require('javascript/engine');
	require('dwr/util');
	require('dwr/interface/personService');
	var datepick = require("datepick-init");
	require('validate');
	var validate = require('javascript/person/validate');
	
	require('javascript/step_tools');
	var edit_identifier1 = require('javascript/person/edit_identifier1');
	var edit_basic1 = require('javascript/person/edit_basic1');
	var edit_ministry_officer_affiliation = require('javascript/person/edit_ministry_officer_affiliation');
	var edit_contact = require('javascript/person/edit_contact');
	
	exports.init = function() {
		var identifier1_setting = edit_identifier1.setting;
		var basic1_setting = edit_basic1.setting;
		var ministry_officer_affiliation_setting = edit_ministry_officer_affiliation.setting;
		var contact_setting = edit_contact.setting;
		
		validate.valid();
		datepick.init();
		
		edit_identifier1.init();
		edit_basic1.init();
		edit_ministry_officer_affiliation.init();
		edit_contact.init();
		
		//身份信息
		identifier1_setting.buttons = ['confirm', 'cancel'];
		identifier1_setting.out_check_custom = function(){
			var idcardType_val = $("[name='person.idcardType']").val();
			var name = $("[name='person.name']").val();
			var idcardNumber = $("[name='person.idcardNumber']").val();

			$.ajax({ 
				type: "post", 
				url: "person/ministryOfficer/checkIfIsOfficer.action", 
				data:{
					"idcardNumber":idcardNumber,
					"name":name,
					"officerId":""
				},
			    cache:false, 
			    async:false, 
			    dataType: "json", 
			    success: function(_result){ 
			    	result = _result.result;
			    } 		
			});

			if (result[0] == 2){
				alert("该人已有专职管理人员，只能添加兼职人员!");
			} else if (result[0] == 3){
				alert("姓名和证件号不匹配!");
				return false;
			}
			return true;
		};
		identifier1_setting.on_in_opt_custom = function(){
			validate.valid_identifier1();
			$("#identifier2").hide();
			$("#procedure").hide();
		};
		identifier1_setting.on_out_opt_custom = function(){
			var idcardType_val = $("[name='person.idcardType']").val();
			var name = $("[name='person.name']").val();
			var idcardNumber = $("[name='person.idcardNumber']").val();

			$("#person_name").html(name);
			$("#person_idcardType").html(idcardType_val);
			$("#person_idcardNumber").html(idcardNumber);

			if (result[1]){
				for (index in result[1]){
					fill_input('person.' + index, result[1][index]);
				}
				edit_contact.initHomeAddr(result[1].homeAddress,result[1].homePostcode);
				edit_contact.initOfficeAddr(result[1].officeAddress,result[1].officePostcode);
				
				//初始化照片
				var personId = result[1].id;
				$('#photo_person_add').after('<input type="file" id="photo_person_add" />');
				$('#photo_person_add').remove();
				$("#photo_person_add").uploadifyExt({
					uploadLimitExt : 1,
					fileSizeLimit : '3MB',
					fileTypeExts : '*.gif; *.jpg; *.png',
					fileTypeDesc : '图片'
				});
			}
			if (result[2]){
				for (index in result[2]){
					fill_input('officer.' + index, result[2][index]);
				}
			}
			$("#identifier2").show();
			$("#procedure").show();
		};

		//基本信息
		basic1_setting.buttons = ['next', 'retry', 'cancel'];

		//任职信息
		ministry_officer_affiliation_setting.buttons = ['prev', 'next', 'retry', 'cancel'];

		//联系信息
		contact_setting.buttons = ['prev', 'finish', 'retry', 'cancel'];

		
		
		step_controller = new Step_controller();

		step_controller.after_move_opt = function(){
			var flag = false;
			for (step in step_controller.steps){
				var $curLi = $("li[name=" + step_controller.steps[step].id + "]");
				if (step_controller.steps[step].id == step_controller.state){
					flag = true;
					$curLi.attr("class", "proc step_d");
				} else if (!flag){
					$curLi.attr("class", "proc step_e");
				} else {
					$curLi.attr("class", "proc step_f");
				}
			}
		};

		step_controller.add_step(identifier1_setting);
		step_controller.add_step(basic1_setting);
		step_controller.add_step(ministry_officer_affiliation_setting);
		step_controller.add_step(contact_setting);
		step_controller.init();

		////////////////////分割线//////////////////////

		function call_back_checkIfIsOfficer(_result){
			result = _result;
		}

		var act = {
			'person.birthday' : function(key, value){
				$("[name="+key+"]").val(new Date(value).format("yyyy-MM-dd"));
			}
		};
		
		function fill_input(key, value){
			if (act[key]){
				act[key](key, value);
			} else {
				$("[name="+key+"]").val(value);
			}

		}

		////////////////////分割线//////////////////////

		$("#prev").click(function(){
			step_controller.prev();
		});

		$("#next").click(function(){
			step_controller.next();
		});

		$("#confirm").click(function(){
			step_controller.next();
		});

		$("#retry").click(function(){
			location.reload();
		});

		$("#cancel").click(function(){
			history.go(-1);
		});

		$("#finish").click(function(){
			step_controller.submit();
		});

		$("#info").show();
		
	};
});

