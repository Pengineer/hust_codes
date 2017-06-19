$(document).ready(function(){
	
	$('body').on('focus',".datepickCls", function(){
	    $(this).datepick();
	});
	
	var flag1 = 1;
	var flag2 = 1;
	//添加教育信息
	$('.add_education').live('click',function(){
		addTable1(this, 'table_education');
	});
	
	
	$('.add_experience').live('click',function(){
		addTable2(this, 'table_experience');	
	});
	
	var addTable1 = function(obj ,tableId){
		if($('.education').length > 3){
			alert('太多了');
			return false;
		};
		var onthis = $(obj).parent().parent().parent().parent();
		var addTable1 = $("<table width='100%' border='0' cellspacing='0' cellpadding='0' class='education'></table>").insertAfter(onthis);
		addTable1.html($(obj).parent().parent().parent().parent().html());	
		$(":input", addTable1).each(function(key,value){
			value.name = value.name.replace(/\[.*\]/, "[" + flag1 + "]");
			value.id = value.name;//校验需要修改id，可能是jquery的内部实现
		});
		flag1++;
		sortNum1();
	};

	var addTable2 = function(obj ,tableId){
		if($('.experience').length > 3){
			return false;
		};
		var onthis = $(obj).parent().parent().parent().parent();
		var addTable2 = $("<table width='100%' border='0' cellspacing='0' cellpadding='0' class='experience'></table>").insertAfter(onthis);
		addTable2.html($("#" + tableId).html());
		$(":input", addTable2).each(function(key,value){
			value.name = value.name.replace(/\[.*\]/, "[" + flag2 + "]");
			value.id = value.name;//校验需要修改id，可能是jquery的内部实现
		});
		flag2++;
		sortNum2();
	};

	var sortNum1 = function(){//自动编号	
		var spans = $("span[title='memberSpan1']");
	 	for(var iLoop = 0; iLoop < spans.length; iLoop++) {
	 		spans[iLoop].innerHTML=iLoop+1;
	 	}
	};
	
	var sortNum2 = function(){//自动编号	
		var spans = $("span[title='memberSpan2']");
	 	for(var iLoop = 0; iLoop < spans.length; iLoop++) {
	 		spans[iLoop].innerHTML=iLoop+1;
	 	}
	};
	
	//删除教育信息
	$(".delete_education").live("click", function(){
		if($('.education').length < 2 ){
			return false;
		};
		$(this).parent().parent().parent().parent().remove();
		sortNum1();
	});
	
	//删除工作经历信息
	$(".delete_experience").live("click", function(){
		if($('.experience').length < 2 ){
			return false;
		};
		$(this).parent().parent().parent().parent().remove();
		sortNum2();
	});
	
	/*文件上传*/
/*	var listAttachment = $.parseJSON($("#existingAttachment").html());
	for (file in listAttachment){
		var progress = new FileProgress(listAttachment[file], swfu.customSettings.progressTarget);
		progress.setComplete();

		var processFileId = document.createElement("input");
		processFileId.type = "hidden";
		processFileId.name = "fileIds";
		processFileId.value = file;
		progress.fileProgressElement.appendChild(processFileId);

		progress.toggleCancel(true, this);
		progress.setStatus("Existing file.");
	};*/
  
/*	$(function() {
		$("#file_upload").uploadifyExt({
			uploadLimitExt : 1, //文件上传个数的限制
			fileSizeLimit : '300MB',//文件上传大小的限制
			fileTypeDesc : '附件',//可以不用管
			fileTypeExts : '*.doc; *.docx; *.pdf; *.jpg'
		});
	});*/
})



