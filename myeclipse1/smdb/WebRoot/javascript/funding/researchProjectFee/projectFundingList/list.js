define(function(require, exports, module) {
	require('pop-self');
	var list = require('javascript/list'),
		nameSpace = "funding/fundingList/project";
	$("#addFundList").live("click",function() {
		var getPara = function(obj){
			return  "?listName=" + obj["listName"] + "&rate=" + obj["rate"] + "&projectYear=" + 
						obj["projectYear"] + "&projectSubtype=" + obj["projectSubtype"] + "&note=" + obj["note"];
		};
		popAddFundList({
			title : "添加拨款清单",
			src : nameSpace + "/toAdd.action",
			callBack : function(result){
				window.location.href = basePath + nameSpace + "/add.action" + getPara(result);
				this.destroy();
			}
		});
	});
	exports.init = function(){
		list.pageShow({
			"nameSpace":nameSpace,
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5","sortcolumn6"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn":5,"sortcolumn":6},
			"dealWith": function(){}
		});
	}
})