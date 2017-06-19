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
		var agencyNames = [];
		var divisionNames = [];
		var expertTypes = [];
		var expertPositions = [];		
		
		for(var i=0;i<data.incomeExperts.length;i++){
			agencyNames[i]=data.incomeExperts[i].agencyName;
			divisionNames[i]=data.incomeExperts[i].divisionName;
			expertTypes[i]=data.incomeExperts[i].type;
			expertPositions[i]=data.incomeExperts[i].position;
		}

		//任职信息
		bindBlockData($("#form_person_expert_agencyName"),agencyNames);
		bindBlockData($("#form_person_expert_divisionName"),divisionNames);
		bindBlockData($("#form_person_expert_type"),expertTypes);
		bindBlockData($("#form_person_expert_position"),expertPositions,true);
		
		merge.initBasic(data);
		merge.initContact(data);
		merge.initAcademic(data);
		merge.initBank(data);
		
		if($("#account_select option").length>2){
			$("#account_select").addClass("select_warn");
		}
		$("#account_select").get(0).selectedIndex=1;//设置默认选中为选项1
	}
	
});