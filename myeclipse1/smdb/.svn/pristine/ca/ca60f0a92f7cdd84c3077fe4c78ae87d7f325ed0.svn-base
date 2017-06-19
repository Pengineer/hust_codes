define(function(require, exports, module){
	require('javascript/engine');
	require('dwr/util');
	require('dwr/interface/unitService');
	
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	/**
	 * 保存机构
	 */
	exports.doSave = function(objectName){
		if($("#form_"+objectName.toLowerCase()).valid() == false){
			return false;
		}
		if($("#viewFlag").val() ==1 ){ //个人空间页面
			if($("#form_"+objectName.toLowerCase()).attr("action").indexOf('modify')>0){
				$("#form_"+objectName.toLowerCase()).attr("action",$("#namespace").val()+"/modify.action");
			}
		}
		var name = document.getElementById("name").value;
		var id;
		try{
			id=document.getElementById("entityId").value;
		}catch(error){
			id="";
		}
		if(objectName === "Institute" || objectName === "Department"){
			var subjectionId = $("#subjectionId").val()||$("#universityId").val();
			unitService.checkInsNameUnique(objectName,id,name,subjectionId,getUnique); //判断研究基地重名情况
		}else{
			unitService.checkUnitNameUnique(objectName,id,name,getUnique); //判断机构重名情况			
		}
	};
	
	/**
	 * 判断后的处理
	 * @param {Object} aa
	 * @return {TypeName} 
	 */
	var getUnique = function(aa){
		if(!aa){ //存在同名
			alert("您输入的单位名已经存在，请重新输入");
			return false;
		}else{
			document.forms[0].submit();
		}
	};
	
	/**
	 * 弹出层选择管理人员（负责人、联系人等）供调用
	 * @param {Object} officerId
	 * @param {Object} officerName
	 */
	var initSelectOfficer = function(officerId, officerName){
		popSelect({
			type : 6,
			inData : {"id" : $("#" + officerId).val(), "name" :  $("#" + officerName).html()},
			callBack : function(result){
				$("#" + officerId).val(result.data.id);
				$("#" + officerName).html(result.data.name);
			}
		});
	};
	
	exports.initSelectOfficer = function(officerId, officerName) {
		initSelectOfficer(officerId, officerName);
	};
	
	/**
	 * 弹出层选择学科类型
	 */
	exports.selectDiscilineType = function(){
		popSelect({
			type : 11,
			inData : $("#discipline").val(),
			callBack : function(result){
				doWithXX(result, "discipline", "disptr");
			}
		});
	};
	
	/**
	 * 根据已选择的省获取对应市的列表
	 * @return
	 */
	var selectCity = function(){
		var province = $("#province").val();
		if(province && province != ""){
			unitService.getSystemOptionById(province, callbackCity);
		}else{
			DWRUtil.removeAllOptions("city");
			DWRUtil.addOptions('city',[{name:'请选择市',id:''}],'id','name');
		}
	};
	
	var callbackCity = function(data) {
		DWRUtil.removeAllOptions("city");
		DWRUtil.addOptions('city',[{name:'请选择市',id:''}],'id','name');
		DWRUtil.addOptions('city',data);
	};
	
	// 载入页面后的操作，包括学科和依赖学科的显示处理
	exports.prepare = function(){
		var discipline = document.getElementById("discipline").value;
//		var relyDiscipline = document.getElementById("relyDisciplineId").value;
		doWithXX(discipline,"discipline","disptr");
//		doWithXX(relyDiscipline,"relyDisciplineId","rdsp");
	};
	
	/**
	 * 显示城市列表
	 * @param {Object} data
	 */
	exports.displayCity = function(data){
		DWRUtil.removeAllOptions("city");
		DWRUtil.addOptions('city', [{name:'请选择市',id:''}],'id','name');
		DWRUtil.addOptions('city', data);
		DWRUtil.setValue('city', $("#cityId").val());
	};
	
	exports.init = function(){
		$("#province").live('change', function(){
			selectCity();
		});
		$("#city").live('focus', function(){
			selectCity();
		});
		//弹出层选择部门联系人
		$("#sLinkmanName").click(function(){
			initSelectOfficer("sLinkmanId","sLinkmanName");
		});
		//弹出层选择高校
		$("#select_university_btn").click(function(){
			popSelect({
				type : 3,
				title : "选择高校",
				inData : {"id" : $("#universityId").val(), "name" : $("#universityName").html()},
				callBack : function(result){
					$("#universityId").val(result.data.id);
					$("#universityName").html(result.data.name);
				}
			});
		});
		//弹出层选择上级管理部门
		$("#select_province_btn").click(function(){
			popSelect({
				type : 2,
				title : "选择上级管理部门",
				inData : {"id" : $("#subjectionId").val(), "name" : $("#subjectionName").html()},
				callBack : function(result){
					$("#subjectionId").val(result.data.id);
					$("#subjectionName").html(result.data.name);
				}
			});
		});
	};
});
