/*
 * 一般项目变更录入弹出层
*/
$(document).ready(function(){
	
	$("#projectid").val(thisPopLayer.inData.projectid);//填充页面隐藏域
	//配置文件上传
	$("#file_add,#file_add_1").uploadify({
		'buttonImage' : '',
		'buttonText' : '选择文件',
		'swf'	  : 'tool/uploadify/uploadify.swf',
		'uploader' : basePath + 'upload/upload;jsessionid=' + $.cookie("JSESSIONID"),
		'fileObjName' : 'file',
		'progressData' : 'speed',
		'width' : 80,
		'height' : 22,
		'removeCompleted' : false,
		'uploadLimitExt' : 999,
		
		'onInit' : function(instance) {
			var groupId = instance.settings.id;
			var queueId = instance.settings.queueID;
			$.get("upload/fetchGroupFilesSummary", {"groupId" : groupId}, function(data){
				var summary = data.groupFilesSummary;
				for (var i = 0; i < summary.length; i++) {
					var fakeFile = {
						id : summary[i][0],
						name : summary[i][1],
						size : summary[i][2]
					};
					try {
						instance.settings.file_queued_handler.call(instance, fakeFile);
						instance.settings.upload_progress_handler.call(instance, fakeFile, fakeFile.size, fakeFile.size);
						$("#" + fakeFile.id + " div.uploadify-progress").remove();
					} finally {
					}
				}
			});
			
			$("#" + queueId + " a.discard").live("click", function(){
				var fileId = $(this).parent().parent().attr("fileId");
				var id = $(this).parent().parent().attr("id");
				$.post("upload/discard", {fileId : fileId || id, groupId : groupId}, function(){
					$("#" + groupId).uploadify("cancel", id);
				});
			});
			
			$("#" + groupId + " div.uploadify-button").css("text-indent", "");
		},

		'onUploadStart' : function(file) {
			if ($("#" + this.settings.queueID + " div.uploadify-queue-item").length > this.settings.uploadLimitExt) {
				alert("最多只能上传" + this.settings.uploadLimitExt + "个文件!");
				$("#" + this.settings.id).uploadify("cancel", file.id);
			}
			$("#" + this.settings.id).uploadify("settings", "formData", {groupId : this.settings.id});
		},

		'onUploadSuccess' : function(file, data, response) {
			$("#" + file.id).attr("fileId", $.parseJSON(data).fileId);
			$("#" + file.id + " div.uploadify-progress").remove();
		},
		
		'itemTemplate' : 
			'<div id="${fileID}" class="uploadify-queue-item">\
				<div class="cancel1">\
					<a class="discard" href="javascript:void(0);">X</a>\
				</div>\
				<span class="fileName">${fileName} (${fileSize})</span><span class="data"></span>\
				<div class="uploadify-progress">\
					<div class="uploadify-progress-bar"><!--Progress Bar--></div>\
				</div>\
			</div>'
	});
	
	/*
	 * 显示选中变更条目
	*/
	var showVarResults = function(){
		var elems = $("input[type='checkbox'][name='selectIssue']:checked",$(varItemsTb)).parent().clone();
		elems.children("input").attr("name","varSelectIssue");
		var $containerTb = $("#varResultTb");
		$containerTb.empty();
		elems.each(function(index){
			if(index % 4 == 0){
				$containerTb.append($("<tr></tr>"));
			}
			$("tr:last",$containerTb).append(elems[index]);
		});
		
		/*elems.each(function(index){
			if((0 <= elems.length) && (4 >= elems.length)) {
				$("tr:last",$containerTb).append(elems[index]);
			}else if((5 <= elems.length) && (8 >= elems.length)){
				if(index > 4){
					$containerTb.append($("<tr><td></td></tr>"));
					$("tr:last",$containerTb).append(elems[index]);
				}
			}else if(8 <= elems.length){
				if(index > 4){
					$containerTb.append($("<tr><td></td></tr>"));
					$("tr:last",$containerTb).append(elems[index]);
				}else if(index > 8){
					$containerTb.append($("<tr><td></td></tr>"));
					$("tr:last",$containerTb).append(elems[index]);
				}
			}
		});*/
	};
	
	/*
	 * 添加成员
	*/
	$(".addMember").click(function(){
		var varMemTempl = $("#varMembersTempl").children("table").clone();
		$("#varMembers").append(varMemTempl);
		sortMemberNum();
	});
	
	/*
	 * 删除成员
	*/
	$(".deleteMember").live("click",function(){
		//如果界面上删除的只剩下一个成员的table，则隐藏，否则就删除
		if($(".varMemberTb",$("#varMembers")).length == 1){
			$("#varMembers").hide(200);
			$("#01",varItemsTb).attr("checked",false);
			showVarResults();
		}else{
			$(this).parent().parent().parent().parent("table").remove();
		}
		sortMemberNum();
		//sortNameNum('newMoeProjectTr','newMoeProjectInfos');
	});
	
	var sortMemberNum = function(){
		$(".memberNum",$("#varMembers")).each(function(index){
			$(this).html((index+1));
		});
		$(".varMemberTb",$("#varMembers")).each(function(key1,value){
			$(":input[name^=member]",value).each(function(key2, value){
				value.name = value.name.replace(/\[.*\]/, "[" + key1 + "]");
	
//				/*
//				 * 校验用。默认jquery validate会在指定位置（errorPlacement）增加label(errorElement),如果不手动更改它的name，则会出现错误消息重叠现象。
//				*/
//				if(0 != $(this).parent("td").next("td").find("label:first-child").length){
//					var thisLabel = $(this).parent("td").next("td").find("label:first-child");
//					thisLabel.attr("for",thisLabel.attr("for").replace(/\[.*\]/, "[" + key1 + "]"));
//				}
			});
		});
	};
	
	//初始化变更机构名称的隐藏输入域
	var agencyName = $("#oldAgencyName").val().split(";");
	$("#agenceUniversity").val(agencyName[0]);
	$("#agenceColleage").val(agencyName[1]);
	
	var $varItemsTb = $("#varItemsTb");
	//处理各变更机构的隐藏与显示
	$("#01",varItemsTb).change(function(){
		if( $("#01",$varItemsTb).attr("checked") == "checked"){
			$("#varMembers").show(200);
		}else{
			$("#varMembers").hide(200);
		}
		showVarResults();
	});
	
	//处理各变更机构的隐藏与显示
	$("#02",varItemsTb).change(function(){
		if( $("#02",$varItemsTb).attr("checked") == "checked"){
			$("#varAgency").show(200);
		}else{
			$("#varAgency").hide(200);
		}
		showVarResults();
	});
	
	//处理各变更成果形式的隐藏与显示
	$("#03",$varItemsTb).change(function(){
		if( $("#03",$varItemsTb).attr("checked") == "checked"){
			$("#productType1").show(200);
			$("#productType2").show(200);
		}else{
			$("#productType1").hide(200);
			$("#productType2").hide(200);
		}
		showVarResults();
	});
	
	//处理各变更项目名称的隐藏与显示
	$("#04",$varItemsTb).change(function(){
		if( $("#04",$varItemsTb).attr("checked") == "checked"){
			$("#projectName").show(200);
		}else{
			$("#projectName").hide(200);
		}
		showVarResults();
	});
	
	//处理各变更重大内容调整的隐藏与显示
	$("#05",$varItemsTb).change(function(){
		if( $("#05",$varItemsTb).attr("checked") == "checked"){
			$("#researchContent").show(200);
		}else{
			$("#researchContent").hide(200);
		}
		showVarResults();
	});
	
	//处理各变更延期的隐藏与显示
	$("#06",$varItemsTb).change(function(){
		if( $("#06",$varItemsTb).attr("checked") == "checked"){
			$("#projectDelay1").show(200);
			$("#projectDelay2").show(200);
		}else{
			$("#projectDelay1").hide(200);
			$("#projectDelay2").hide(200);
		}
		showVarResults();
	});
	
	//处理各变更自行中止项目的隐藏与显示
	$("#07",$varItemsTb).change(function(){
		if( $("#07",$varItemsTb).attr("checked") == "checked"){
			$("#suspension").show(200);
		}else{
			$("#suspension").hide(200);
		}
		showVarResults();
	});
	
	//处理各变更申请撤项的隐藏与显示
	$("#08",$varItemsTb).change(function(){
		if( $("#08",$varItemsTb).attr("checked") == "checked"){
			$("#cancelProject").show(200);
		}else{
			$("#cancelProject").hide(200);
		}
		showVarResults();
	});
	
	//处理各变更项目经费的隐藏与显示
	$("#09",$varItemsTb).change(function(){
		if( $("#09",$varItemsTb).attr("checked") == "checked"){
			$("#projectFee").show(200);
		}else{
			$("#projectFee").hide(200);
		}
		showVarResults();
	});
	
	//处理各变更申请撤项的隐藏与显示
	$("#10",$varItemsTb).change(function(){
		if( $("#10",$varItemsTb).attr("checked") == "checked"){
			$("#other").show(200);
		}else{
			$("#other").hide(200);
		}
		showVarResults();
	});
	
	
	//处理同意变更事项的隐藏与显示
	$("input[name=varResult]").click(function(){
		var self = this;
		if( $(self).val() == 2){
			$("#varResultListTr").show(200);
		}else{
			$("#varResultListTr").hide(200);
		}
	});
	
	/*
	 * 经费结算修改输入弹层
	*/
	$(".modifyVarFee").live("click",function(){
		var projectid = thisPopLayer.inData.projectid;
		popInstpAudit({
			title : "添加经费预算变更明细",
			src : "project/instp/variation/toAddFee.action?projectid=" + projectid + "&feeNote=" + $("#feeNote").val() + 
			"&bookFee=" + $("#bookFee").val() + "&bookNote=" + $("#bookNote").val() + 
			"&dataFee=" + $("#dataFee").val() + "&dataNote=" + $("#dataNote").val() +
			"&travelFee=" + $("#travelFee").val() + "&travelNote=" + $("#travelNote").val() +
			"&conferenceFee=" + $("#conferenceFee").val() + "&conferenceNote=" + $("#conferenceNote").val() +
			"&internationalFee=" + $("#internationalFee").val() + "&internationalNote=" + $("#internationalNote").val() +
			"&deviceFee=" + $("#deviceFee").val() + "&deviceNote=" + $("#deviceNote").val() +
			"&consultationFee=" + $("#consultationFee").val() + "&consultationNote=" + $("#consultationNote").val() +
			"&laborFee=" + $("#laborFee").val() + "&laborNote=" + $("#laborNote").val() +
			"&printFee=" + $("#printFee").val() + "&printNote=" + $("#printNote").val() +
			"&indirectFee=" + $("#indirectFee").val() + "&indirectNote=" + $("#indirectNote").val() +
			"&otherFeeD=" + $("#otherFee").val() + "&otherNote=" + $("#otherNote").val() +
			"&totalFee=" + $("#totalFee").val() + "&surplusFee=" + $("#surplusFee").val() ,
			callBack : function(result){
				$("#feeNote").val(result.feeNote);
				$("#bookFee").val(result.bookFee);
				$("#bookNote").val(result.bookNote);
				$("#dataFee").val(result.dataFee);
				$("#dataNote").val(result.dataNote);
				$("#travelFee").val(result.travelFee);
				$("#travelNote").val(result.travelNote);
				$("#conferenceFee").val(result.conferenceFee);
				$("#conferenceNote").val(result.conferenceNote);
				$("#internationalFee").val(result.internationalFee);
				$("#internationalNote").val(result.internationalNote);
				$("#deviceFee").val(result.deviceFee);
				$("#deviceNote").val(result.deviceNote);
				$("#consultationFee").val(result.consultationFee);
				$("#consultationNote").val(result.consultationNote);
				$("#laborFee").val(result.laborFee);
				$("#laborNote").val(result.laborNote);
				$("#printFee").val(result.printFee);
				$("#printNote").val(result.printNote);
				$("#indirectFee").val(result.indirectFee);
				$("#indirectNote").val(result.indirectNote);
				$("#otherFee").val(result.otherFee);
				$("#otherNote").val(result.otherNote);
				$("#totalFee").val(result.totalFee);
				$("#surplusFee").val(result.surplusFee);
			}
		});
	});
	
	//提交审核结果
	$(".confirm").bind("click",function(){
		
		/*var projectid = thisPopLayer.inData.projectid;
		var selectIssue = [];//勾选的变更事项
		$("input[name=selectIssue]:checked",$("#varItemsTb")).each(function (){
			selectIssue.push($(this).val());
		});
		var varSelectIssue = [];//同意勾选的变更事项
		$("input[name=selectIssue]:checked",$("#varResultTb")).each(function (){
			varSelectIssue.push($(this).val());
		});
		var oldAgencyName = $("#oldAgencyName").val();//机构的旧名字
		var agenceUniversity = $("#agenceUniversity").val();//新的机构的名字
		var agenceColleage = $("#agenceColleage").val();//新的部门的名字
		var selectProductType = [];//变更成果形式数据
		$("input[name=selectProductType]:checked").each(function (){
			selectProductType.push($(this).val());
		});
		var projectName = $("input[name=projectName]").val();//新的变更后的项目名称
		var otherInfo = $("#otherInfo").val();//其它变更事项
*/		
		$("#varAddForm").ajaxSubmit({
			success: function(result) {
				if(result.errorInfo != "" && result.errorInfo != null && result.errorInfo != undefined){
					alert(result.errorInfo);
				}else{
					thisPopLayer.callBack(result);
					thisPopLayer.destroy();
				}
			}
		});
	});

});