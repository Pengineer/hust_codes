/**
 * @author 肖宁
 */

define(function(require, exports, module) {
	var fundListGeneralGranted = require('javascript/fundList/list');
	var nameSpace = "fundList/general/granted";
	var projectType = "general";
//	var modifyGenenralFee = require('javascript/projectFee/popModifyFee');
	
	exports.init = function() {
		fundListGeneralGranted.init(nameSpace);//公共部分
		fundListGeneralGranted.graExport(nameSpace);
//		listGeneral.modifyFee(nameSpace);
//		modifyGenenralFee.init(nameSpace);
	};
	
	$("#addFundList").live("click",function() {
			var getPara = function(obj){
				return  "?listName=" + obj["listName"] + "&rate=" + obj["rate"] + "&projectYear=" + 
							obj["projectYear"] + "&projectSubtype=" + obj["projectSubtype"] + "&note=" + obj["note"];
			};
			popAddFundList({
				title : "添加一般项目立项拨款清单",
				src : nameSpace + "/toAdd.action",
				callBack : function(result){
					window.location.href = basePath + nameSpace + "/add.action" + getPara(result);
					this.destroy();
				}
			});
		});
});