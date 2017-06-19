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
	var pos;
	function initMerge(data){
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