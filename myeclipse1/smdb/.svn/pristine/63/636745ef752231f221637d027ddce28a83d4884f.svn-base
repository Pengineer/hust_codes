/**
 * @author 肖宁
 */

define(function(require, exports, module) {
	var fundListKeyEnd = require('javascript/fundList/list');
	var nameSpace = "fundList/key/end";
	var projectType = "key";
//	var modifyGenenralFee = require('javascript/projectFee/popModifyFee');
	
	exports.init = function() {
		fundListKeyEnd.init(nameSpace);//公共部分
		fundListKeyEnd.graExport(nameSpace);
//		listGeneral.modifyFee(nameSpace);
//		modifyGenenralFee.init(nameSpace);
	};
	
	$("#addFundList").live("click",function() {
			var getPara = function(obj){
				return  "?listName=" + obj["listName"] + "&rate=" + obj["rate"] + "&projectYear=" + 
							obj["projectYear"] + "&researchType=" + obj["researchType"] + "&note=" + obj["note"];
			};
			popAddFundList({
				title : "添加一般项目结项拨款清单",
				src : nameSpace + "/toAdd.action",
				callBack : function(result){
					window.location.href = basePath + nameSpace + "/add.action" + getPara(result);
					this.destroy();
				}
			});
		});
});