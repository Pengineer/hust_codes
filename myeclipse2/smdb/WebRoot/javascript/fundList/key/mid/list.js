/**
 * @author 肖宁
 */

define(function(require, exports, module) {
	var fundListKeyMid = require('javascript/fundList/list');
	var nameSpace = "fundList/key/mid";
	var projectType = "key";
//	var modifyGenenralFee = require('javascript/projectFee/popModifyFee');
	
	exports.init = function() {
		fundListKeyMid.init(nameSpace);//公共部分
		fundListKeyMid.graExport(nameSpace);
//		listGeneral.modifyFee(nameSpace);
//		modifyGenenralFee.init(nameSpace);
	};
	
	$("#addFundList").live("click",function() {
			var getPara = function(obj){
				return  "?listName=" + obj["listName"] + "&rate=" + obj["rate"] + "&projectYear=" + 
							obj["projectYear"] + "&researchType=" + obj["researchType"] + "&note=" + obj["note"];
			};
			popAddFundList({
				title : "添加重大攻关项目中检拨款清单",
				src : nameSpace + "/toAdd.action",
				callBack : function(result){
					window.location.href = basePath + nameSpace + "/add.action" + getPara(result);
					this.destroy();
				}
			});
		});
});