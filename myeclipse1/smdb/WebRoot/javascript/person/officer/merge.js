define(function(require, exports, module) {
	var merge = require('javascript/person/merge');
	exports.init = function(nameSpace) {
		var checkedIds = $('#checkedIds').val();
		$.getJSON(
			nameSpace+"/fetchMergeData.action",
			{"checkedIds":checkedIds},
			function(data){
				initMerge(data);
			}
		);
	};

	function initMerge(data){
		//任职信息
		var agencyNames = [];//对部级省级高校有效
		var officerTypes = [];
		var startDates = [];
		var endDates = [];
		var positions = [];
		var staffCardNumbers = [];

		for(var i=0;i<data.incomeOfficers.length;i++){
			officerTypes[i]=data.incomeOfficers[i].type;
			startDates[i]=data.incomeOfficers[i].startDate;
			endDates[i]=data.incomeOfficers[i].endDate;
			positions[i]=data.incomeOfficers[i].position;
			staffCardNumbers[i]=data.incomeOfficers[i].staffCardNumber;
		}
		for(var i=0;i<data.incomeAgencies.length;i++){
			agencyNames[i]=data.incomeAgencies[i].name;
		}

		bindBlockUnitData($("#unitName"),agencyNames,data.unitIds);
		bindBlockData($("#form_person_officer_type"),officerTypes);
		bindBlockData($("#form_person_officer_startDate"),startDates,true);
		bindBlockData($("#form_person_officer_endDate"),endDates,true);
		bindBlockData($("#form_person_officer_staffCardNumber"),staffCardNumbers,true);
		//对于院系绑定院系名称和所在高校
		bindBlockUnitData($("#departmentName"),data.incomeDepartments,data.unitIds);
		bindBlockUnitData($("#instituteName"),data.incomeInstitutes,data.unitIds);
		
		merge.initBasic(data);
		merge.initContact(data);
		
		if($("#account_select option").length>2){
			$("#account_select").addClass("select_warn");
		}
		$("#account_select").get(0).selectedIndex=1;//设置默认选中为选项1
		
	}
	
	
	
});