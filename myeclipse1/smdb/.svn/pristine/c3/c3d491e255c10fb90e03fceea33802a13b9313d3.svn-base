/**
 * @author 肖宁
 */

define(function(require, exports, module) {
	var fundListEntrustEnd = require('javascript/fundList/list');
	var nameSpace = "fundList/entrust/end";
	var projectType = "entrust";
//	var modifyGenenralFee = require('javascript/projectFee/popModifyFee');
	
	exports.init = function() {
		fundListEntrustEnd.init(nameSpace);//公共部分
		fundListEntrustEnd.graExport(nameSpace);
//		listGeneral.modifyFee(nameSpace);
//		modifyGenenralFee.init(nameSpace);
	};
	
	$("#addFundList").live("click",function() {
			var getPara = function(obj){
				return  "?listName=" + obj["listName"] + "&rate=" + obj["rate"] + "&projectYear=" + 
							obj["projectYear"] + "&projectSubtype=" + obj["projectSubtype"] + "&note=" + obj["note"];
			};
			popAddFundList({
				title : "添加委托应急课题结项拨款清单",
				src : nameSpace + "/toAdd.action",
				callBack : function(result){
					window.location.href = basePath + nameSpace + "/add.action" + getPara(result);
					this.destroy();
				}
			});
		});
});