define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('form');
	var valicate = require('valicate');
	var datepick = require("datepick-init");
	var thisPopLayer = top.PopLayer.instances[top.PopLayer.id];
	
	//当输入姓名时，提示人员库中已有的人员
	var showPersonDetail = function(obj){
		var memberType = $("#memberType").val();
		var $info = $(obj).next();
		var $item = $(obj).next().find(".item");
		var type = {'1' : "selectTeacher", '2' : "selectExpect", '3' : "selectStudent"};//教师|专家|学生
		$.ajax({
			url: type[memberType]+"/fetchAutoData.action?personName="+$(obj).val(),
			dataType: "json",
			method: "post",
			success : function(result){
				var json_data = result.autoData;
				var info = "<ul>";
				for (var i in json_data) {
					info += "<li><a class='choosePerson' href='javascript:void(0);' title='" + json_data[i][2] + "'><span>" + json_data[i][1] + "</span>(<span>" + json_data[i][4] + "</span>)"  + "<input type='hidden' value='"+json_data[i][0]+"'>"+"</a></li>";
				}
				info+="</ul>";
				$($item).html(info);
				if(json_data.length!=0) {
					$($info).show();
				}else {
					$($info).hide();
				}
			}
		});
	}
	
	
	exports.init = function(){
		$("#form_memberDetail").validate({
			errorElement: "span",
			event:"blur",
			rules: {
				"memberType": {required:true},
				"memberName": {required:true},
				"workMonthPerYear": {range:[0, 12],number:true},
				"isDirector": {required:true}
			},
			errorPlacement: function(error, element){
				var errorInfo = element.parent().parent().clone();
				errorInfo.find("td").eq(0).html("");
				errorInfo.find("td").eq(1).html(error);
				element.parent().parent().after(errorInfo);
			}
		});
		datepick.init();
		if(thisPopLayer.inData.typeId=="1"){
			$(".j_choseMember").hide();
			$(".j_newMember").show();
		}
		if(!thisPopLayer.inData.agencyName){
			$(".choose_agencyName").show();
			$("#selected_agency").hide();
		}
		if(!thisPopLayer.inData.divisionName.trim()){
			$(".choose_division").show();
			$("#selected_divison").hide();
		}
		$("#memberId").val(thisPopLayer.inData.merberId);
		$("#personId").val(thisPopLayer.inData.personId);
		$("#memberName").val(thisPopLayer.inData.memberName);
		$("#memberType").val(thisPopLayer.inData.memberType);
		$("#type").html(thisPopLayer.inData.type);
		$("#typeId").val(thisPopLayer.inData.typeId);
		$(".universityId").val(thisPopLayer.inData.universityId);
		$(".departmentId").val(thisPopLayer.inData.departmentId);
		$(".instituteId").val(thisPopLayer.inData.instituteId);
		$(".divisionType").val(thisPopLayer.inData.divisionType);
		$(".agencyName").html(thisPopLayer.inData.agencyName);
		$(".divisionName").html(thisPopLayer.inData.divisionName);
		$("#idcardType").val(thisPopLayer.inData.idcardType);
		$("#idcardNumber").val(thisPopLayer.inData.idcardNumber);
		$("#gender").val(thisPopLayer.inData.gender);
		$("#specialistTitle").val(thisPopLayer.inData.specialistTitle);
		$("#major").val(thisPopLayer.inData.major);
		$("#workMonthPerYear").val(thisPopLayer.inData.workMonthPerYear);
		$("#workDivision").val(thisPopLayer.inData.workDivision);
		$("#isDirector").val(thisPopLayer.inData.isDirector);
		$("#position").val(thisPopLayer.inData.position);
		$("#variation5").val(thisPopLayer.inData.birthday);
		$("#education").val(thisPopLayer.inData.education);
		
		$(".keyword").live("focus", function(){
			var memberType = $("#memberType").val();
			if (memberType == "-1") {
				alert("请选择成员类型!");
				$("#memberType").focus();
			}
			showPersonDetail(this);
		});
		
		$(".keyword").live("input propertychange", function(){
			showPersonDetail(this);
		});
		
		$(".choosePerson").live("click", function(){
			var uni = $(this).find("span");
			var divisionName = $(this).attr("title");
			var id= $(this).find("input").val();
			$(".j_newMember input[type=button]").hide();
			//填充姓名
			$("#memberName").attr("value", uni.eq(0).html());
			//填充人员id
			$("#memberId").val(id);
			//填充是否关联
			$("#type").html("关联");
			$("#typeId").val(0);
			//填充所属单位
			$(".agencyName").html(uni.eq(1).html());
			//填充所属部门
			$(".divisionName").html(divisionName);
			//隐藏提示框
			$(this).parent().parent().parent().parent().hide();
		});
		
		$("body").click(function(){
			$("div.info").hide();
		});
		
		$(".selectPerson").live("click", function(){
			var $self = $(this).parent().parent().parent().prev();
			var $selfTr = $(this).parent().parent().parent().parent();
			var memberType = $("#memberType").val();
			var type = {"1":8, "2":7, "3":15};//教师|专家|学生
			popSelect({
				type: type[memberType],
				label: 3,
				inData: {
					id: "",
					name: ""
				},
				callBack: function(result) {
					var agencyName = result.data.sname.split(/\s/)[0];
					var divisionName = result.data.sname.split(/\s/)[1];
					$(".j_newMember input[type=button]").hide();
					$("#type").html("关联");
					$("#memberId").val(result.data.id);
					$("#memberName").html(result.data.name);
					$(".agencyName").html(agencyName);
					$(".divisionName").html(divisionName);
				}
			})
		});
		
		$("#confirm").live("click", function() {
			thisPopLayer.outData = {
				"memberId":			$("#memberId").val(),
				"personId":			$("#personId").val(),
				"memberName": 		$("#memberName").val(),
				"memberType": 		$("#memberType").val(),
				"type":				$("#type").html(),
				"typeId":			$("#typeId").val(),
				"universityId":		$(".universityId").val(),
				"departmentId":		$(".departmentId").val(),
				"instituteId":		$(".instituteId").val(),
				"divisionType":		$(".divisionType").val(),
				"agencyName":		$(".agencyName").html(),
				"divisionName":		$(".divisionName").html(),
				"idcardType": 		$("#idcardType").val(),
				"idcardNumber":		$("#idcardNumber").val(),
				"gender":			$("#gender").val(),
				"specialistTitle":	$("#specialistTitle").val(),
				"major":			$("#major").val(),
				"workMonthPerYear":	$("#workMonthPerYear").val(),
				"workDivision":		$("#workDivision").val(),
				"isDirector":		$("#isDirector").val(),
				"position":			$("#position").val(),
				"birthday":			$("#variation5").val(),
				"education":		$("#education").val()		
			};
			thisPopLayer.callBack(thisPopLayer.outData);
			thisPopLayer.destroy();
		});
		$("#cancel").live("click", function(){
			thisPopLayer.destroy();
		});
		
		// 绑定弹出层选择院系
		$(".select_dep_btn_1").live("click",function() {
			var $onTable = $(this).parent().parent().parent().parent();
			var memberName = $("#memberName").val();
			var universityId = $(".universityId").val();
			var type = $("#memberType").val();
			var divisionName = $(".divisionName").html();
			var universityId = $(".universityId").val();
			var departmentId = $(".departmentId").val();
			var divisionType = $(".divisionType").val();
			popSelect({
				type: 4,
				label: 3,
				inData: {"id":departmentId, "name":divisionName, "memberName":memberName, "universityId":universityId, "type":type},
				callBack: function(result) {
					$(".choose_division").hide();
					$(".departmentId").val(result.data.id);
					$(".divisionName").html(result.data.name);
					$(".divisionName").parent().append("<a class='reselect' href='javascript:void(0);' title='重选'><img src='image/del.gif' /></a>")
					$(".divisionName").show();
					$(".divisionType").val(2);
				}
			});
		});

		// 绑定弹出层选择基地
		$(".select_ins_btn_1").live("click",function() {
			var $onthis = $(this).parent().parent().parent();
			var $memberName = $onthis.prev().prev().prev();
			var memberName = $("#memberName", $memberName).val();
			var $universityId = $onthis.prev();
			var universityId = $("#universityId", $universityId).val();
			var $type = $onthis.prev().prev().prev().prev();
			var type = $("#memberType", $type).val();
			var $divisionName = $onthis.find(".divisionName");
			var $instituteId = $onthis.find("#instituteId");
			var $divisionType = $onthis.find("#divisionType");
			popSelect({
				type: 5,
				label: 3,
				inData: {"id":$($instituteId).val(), "name":$($divisionName).html(), "memberName":memberName, "universityId":universityId, "type":type},
				callBack: function(result) {
					$($onthis).find(".choose_division").hide();
					$($instituteId).val(result.data.id);
					$($divisionName).html(result.data.name);
					$($divisionName).parent().append("<a class='reselect' href='javascript:void(0);' title='重选'><img src='image/del.gif' /></a>")
					$($divisionName).show();
					$($divisionType).val(1);
				}
			});
		});
		
		$(".reselect").live("click", function(){
			var $onthis = $(this).parent().parent().parent();
			$(this).parent().find("a").hide();
			$($onthis).find(".divisionName").hide();
			$($onthis).find(".choose_division").show();
		});
	};
	
	
})