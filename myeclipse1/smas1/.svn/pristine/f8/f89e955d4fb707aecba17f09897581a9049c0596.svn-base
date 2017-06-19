/*
 * 基地项目结项审核弹出层
*/
$(document).ready(function(){
	
	//提交审核结果
	$(".confirmFee").bind("click",function(){
		thisPopLayer.outData = {
			"bookFee": $("input[name='bookFee']").val(),
			"bookNote": $("input[name='bookNote']").val(),
			"dataFee": $("input[name='dataFee']").val(),
			"dataNote": $("input[name='dataNote']").val(),
			"travelFee": $("input[name='travelFee']").val(),
			"travelNote": $("input[name='travelNote']").val(),
			"conferenceFee": $("input[name='conferenceFee']").val(),
			"conferenceNote": $("input[name='conferenceNote']").val(),
			"internationalFee": $("input[name='internationalFee']").val(),
			"internationalNote": $("input[name='internationalNote']").val(),
			"deviceFee": $("input[name='deviceFee']").val(),
			"deviceNote": $("input[name='deviceNote']").val(),
			"consultationFee": $("input[name='consultationFee']").val(),
			"consultationNote": $("input[name='consultationNote']").val(),
			"laborFee": $("input[name='laborFee']").val(),
			"laborNote": $("input[name='laborNote']").val(),
			"printFee": $("input[name='printFee']").val(),
			"printNote": $("input[name='printNote']").val(),
			"indirectFee": $("input[name='indirectFee']").val(),
			"indirectNote": $("input[name='indirectNote']").val(),
			"otherFee": $("input[name='otherFee']").val(),
			"otherNote": $("input[name='otherNote']").val(),
			"totalFee": $("input[name='totalFee']").val(),
			"feeNote": $("input[name='feeNote']").val()
		}
		thisPopLayer.callBack(thisPopLayer.outData);
		thisPopLayer.destroy();
	});
});