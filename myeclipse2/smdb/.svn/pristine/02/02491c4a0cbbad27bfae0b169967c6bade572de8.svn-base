define(function(require, exports, module) {
	require('dwr/util');
	require('javascript/engine');
	require('dwr/interface/businessService');
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('form');
	var datepick = require("datepick-init");
	var validate = require("javascript/system/business/validate");

	var init = function(){
		$(function() {
			var selectType = $("#type").val();
			//页面初始化时下拉框显示业务子类
			if (selectType && selectType !='-1'){
				businessService.getChildrenMapByParentId(selectType, displaySubType);
			}
			
			//动态添加业务对象起止年份校验
			$("#subType").change(function(){
				startEndYearValidate();
			});
			
			//动态添加业务年份校验
			$("#subType").change(function(){
				businessYearValidate();
			});
			
			//动态添加业务专家评审截止时间校验
			$("#subType").change(function(){
				reviewDeadlineValidate();
			});
		});
	};
	
	/**
	 * 根据已选择业务子类类型判断业务对象起止年份是否为必填
	 */
	var startEndYearValidate = function(){
		var subType = $("#subType").find("option:selected").text();
		if(subType.indexOf('申报')>0 || subType.indexOf('招标')>0 || subType.indexOf('选题')>0){
			$("#startEndYear").attr("class", "table_title6");
			validate.valid_year(true);
		}else{
			$("#startEndYear").attr("class", "");
			validate.valid_year(false);
		}
	};

	/**
	 * 根据已选择业务子类类型判断专家评审截止时间是否需要
	 */
	var reviewDeadlineValidate = function(){
		var subType = $("#subType").find("option:selected").text();
		if(subType.indexOf('申报')>0 || subType.indexOf('结项')>0 || subType.indexOf('招标')>0){
			$("#reviewDeadline").show();
		}else{
			$("#reviewDeadline").hide();
		}
	};

	/**
	 * 根据已选择业务子类类型判断业务年份设置是否需要
	 */
	var businessYearValidate = function(){
		var subType = $("#subType").find("option:selected").text();
		if(subType.indexOf('年检')>0){
			$("#businessYear").show();
		}else{
			$("#businessYear").hide();
		}
	};

	/**
	 * 根据已选择的业务类型获取对应业务子类的列表
	 * @return
	 */
	var selectSubType = function(){
		var type = $("#type").val();
		if(type && type != "" && type != -1){
			businessService.getChildrenMapByParentId(type, callbackSubType);
		}else{
			DWRUtil.removeAllOptions("subType");
			DWRUtil.addOptions('subType',[{name:'请选择业务子类',id:'-1'}],'id','name');
		}
	};

	var callbackSubType = function(data) {
		DWRUtil.removeAllOptions("subType");
		DWRUtil.addOptions('subType',[{name:'请选择业务子类',id:'-1'}],'id','name');
		DWRUtil.addOptions('subType',data);
	};

	/**
	 * 显示业务子类列表
	 * @param {Object} data
	 */
	var displaySubType = function(data){
		DWRUtil.removeAllOptions("subType");
		DWRUtil.addOptions('subType', [{name:'请选择业务子类',id:''}],'id','name');
		DWRUtil.addOptions('subType', data);
		DWRUtil.setValue('subType', $("#subTypeId").val());
		
		startEndYearValidate();
		businessYearValidate();
		reviewDeadlineValidate();
	};

	var isBusinessTypeExist;

	var submitBusiness = function(){
		var valid1 = $("#form_business").valid();
		var valid2 = validate.valid_deadline();
		var valid3 = true;
		var valid4 = true;
		if(valid1){
			valid3 = validate.valid_selectBusinessType();
			valid4 = validate.valid_isBusinessYearEqual();
			valid5 = validate.valid_isBusinessYearCrossed();
		}
		if(valid1 && valid2 && valid3 && valid4 && valid5){
			$("#form_business").submit();
		}
	}
	
	exports.init = function() {
		datepick.init();
		init();
		$(".j_submitBusiness").live('click',function(){
			submitBusiness();
		});
		$(".j_selectSubType").live('click',function(){
			selectSubType();
		});
	};

});
