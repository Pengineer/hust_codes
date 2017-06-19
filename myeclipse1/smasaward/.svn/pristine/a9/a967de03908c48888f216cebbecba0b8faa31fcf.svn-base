$(function(){
	
	$(".expand").live('click', function(){
		//初始化日期字段的值：如果为空或者0或者未定义，则初始化为空；如果不为空，则初始化为传来的日期。
		if(""==$("#startTimeMM").val() || undefined == $("#startTimeMM").val() || "0" == $("#startTimeMM").val()){
			$("#startTime").datetimepicker({ dateFormat: 'yy-mm-dd' }).datetimepicker();
		}else{
			var starTime = iniTime($("#startTimeMM").val());
			$("#startTime").val(starTime);
			$("#startTime").datetimepicker({ dateFormat: 'yy-mm-dd' }).datetimepicker();
		}
		if(""==$("#endTimeMM").val() || undefined == $("#endTimeMM").val() || "0" == $("#endTimeMM").val()){
			$("#endTime").datetimepicker({ dateFormat: 'yy-mm-dd' }).datetimepicker();
		}else{
			var endTime = iniTime($("#endTimeMM").val());
			$("#endTime").val(endTime);
			$("#endTime").datetimepicker({ dateFormat: 'yy-mm-dd' }).datetimepicker();
		}
		
		$(".hide").each(function(){
			$(this).hide();
		});
		$(".base_info").each(function(){
			$(this).removeClass("close");
			$(this).addClass("expand");
		});
		$(this).next().show(500);
		$(this).removeClass("expand");
		$(this).addClass("close");
	});
	
	$(".close").live('click', function(){
		$(this).next().hide();
		$(this).removeClass("close");
		$(this).addClass("expand");
	});
	
	/**
	 * 参评项目列表弹出层导出项目一览表
	 * @param exportAll 其中1：表示所有项目；0：表示根据条件导出
	 */
	$("#exportReviewProject").click(function(){
		//包含评审信息
		document.location.href = "project/general/exportProjectReviewer.action?exportAll=0";
		return true;
	});
	
	/**
	 * 退评项目列表弹出层导出项目一览表
	 * @param exportAll 其中1：表示所有项目；0：表示根据条件导出
	 */
	$("#exportNotReviewProject").click(function(){
			document.location.href = "project/general/exportProject.action?exportAll=0";
			return true;
	});
	
	//导出专家评审结果
	$("#expertReviewer").click(function(){
		var startTime = $("#startTime").datetimepicker('getDate');
		var endTime = $("#endTime").datetimepicker('getDate');
		var newEndTime = endTime.getTime() + 60000;//由于要包含截止时间对应的分钟在内的毫秒数，例如截止时间2014-04-24 10:19，则应包括10:19在内的那分钟包含的毫秒数
		document.location.href = "project/general/exportMatchUpdate.action?startTime=" + startTime.getTime() + "&endTime=" +newEndTime;
	});
	
	//导出立项核算情况
	$("#check").click(function(){
		// todo
	});
	
	//导出拟立项统计表
	$("#grantingSchedule").click(function(){
		document.location.href = "project/general/exportGrantingSchedule.action";
	});
	
	//导出拟立项清单(计划表)
	$("#grantingList").click(function(){
		document.location.href = "project/general/exportGrantingList.action";
	});
	
});

/*
 * 将后台读取数据库的时间进行格式转换，初始化并填到表单对应字段里
*/
var iniTime = function(time){
	var date = new Date(parseInt(time));
	var year = date.getFullYear();
	var month = date.getMonth() + 1;//月份是从0开始的
	if(month < 10){
		month = "0" + month;
	}
	var day = date.getDate();//日期是从1到31
	if(day < 10){
		day = "0" + day;
	}
	var hours = date.getHours();
	if(hours < 10){
		hours = "0" + hours;
	}
	var minutes = date.getMinutes();
	if(minutes < 10){
		minutes = "0" + minutes;
	}
	return year + "-" +  month + "-" + day + " " + hours + ":" + minutes;
}