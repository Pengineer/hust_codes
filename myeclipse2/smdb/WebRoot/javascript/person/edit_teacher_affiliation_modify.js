define(function(require, exports, module) {
	require('javascript/engine');
	require('dwr/util');
	require('dwr/interface/personService');
	
	require('javascript/step_tools');
	require('validate');
	var validate = require('javascript/person/validate');
	require('tool/poplayer/js/pop');
	require('pop-self');
	

	function selectDI(){
		$here = $(this).parent().parent();
		var unitType = $("[name^=unitType]", $here).val();
		if (unitType == 0){
			popSelect({
				type : 4,
				inData : {"id" :  $("[name$='department.id']", $here).val(), "name" : $("[name^='DIName_subjectionName']", $here).val()},
				callBack : function(result){
					$("[name$='department.id']", $here).val(result.data.id);
					$("[name^='DIName_subjectionName']", $here).val(result.data.name);
					$("[name$='institute.id']", $here).val("");
					$(document.forms[0]).valid();
				}
			});
		} else if (unitType == 1){
			popSelect({
				type : 5,
				inData : {"id" : $("[name$='institute.id']", $here).val(), "name" : $("[name^='DIName_subjectionName']", $here).val()},
				callBack : function(result){
					$("[name$='institute.id']", $here).val(result.data.id);
					$("[name^='DIName_subjectionName']", $here).val(result.data.name);
					$("[name$='department.id']", $here).val("");
					$(document.forms[0]).valid();
				}
			});
		} else {
			alert("请先选择单位类型！");
		}
	}

	function call_back_checkIfIsFulltimeTeacher(_result){
		result = _result;
	}
	
	var setting = new Setting({

		id: "teacher_affiliation_modify",

		on_in_opt: function(){
			validate.valid_modify_teacher_affiliation();
		},

		on_submit_opt: function(){
			$("#table_affiliation tr").each(function(key, value){
				$("input, select", $(value)).each(function(key1, value1){
					value1.name = value1.name.replace(/\[.*\]/, "[" + (key-1) + "]");
				});
			});
		},

		out_check: function(){
			if($("#form_person").valid() == false){return false;}
			if($("#checkedIds").length>0){ //如果是合并则跳过专职检测
				return true;
			}
			
			var idcardNumber = $("[name='person.idcardNumber']").val();
			DWREngine.setAsync(false);
			personService.checkIfIsFulltimeTeacher(idcardNumber, call_back_checkIfIsFulltimeTeacher);
			DWREngine.setAsync(true);

			var fullTimeCnt = !!result ? 1 : 0;
			if (fullTimeCnt){
				$("[name$='.id']").each(function(){
					if ($(this).val() == result){
						fullTimeCnt = 0;
					}
				});
			}
			$("[name$='.type']").each(function(){
				if ($(this).val() == '专职人员'){
					fullTimeCnt++;
				}
			});
			if (fullTimeCnt > 1){
				alert("最多只能有一个专职教师职位!");
				return false;
			}
			return true;
		}

	});
	
	var init = function(){
		// 子页面初始化接口
		$("#add_affiliation").click(function(){
			addRow("table_affiliation", "tr_affiliation");
		});
		
		$(".delete_row").live("click", function(){
			$(this).parent().parent().remove();
		});

		$("[name^=DIName_subjectionName]").live('click',selectDI);
	};
	
	module.exports = {
		setting : setting,
		init : function(){init()}
	};
});
