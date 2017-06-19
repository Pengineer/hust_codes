$(document).ready(function(){
	$('#datepicks').datepick({yearRange: '-90:+10', alignment: "left", autoSize: true});
	$('#datepicke').datepick({yearRange: '-90:+10', alignment: "left", autoSize: true});
	$('#edus[1].stime').datepick({yearRange: '-90:+10', alignment: "left", autoSize: true});
	$('#edus[1].etime').datepick({yearRange: '-90:+10', alignment: "left", autoSize: true});
	$('#eduetime1').datepick({yearRange: '-90:+10', alignment: "left", autoSize: true});
	$('#personBirthday').datepick({yearRange: '-90:+10', alignment: "left", autoSize: true});
	$('#applyTime').datepick({yearRange: '-90:+10', alignment: "left", autoSize: true});
	$('#startTime').datepick({yearRange: '-90:+10', alignment: "left", autoSize: true});
	$('#endTime').datepick({yearRange: '-90:+10', alignment: "left", autoSize: true});
	$('#contactBirthday').datepick({yearRange: '-90:+10', alignment: "left", autoSize: true});
	$('#customerConsultationTime').datepick({yearRange: '-90:+10', alignment: "left", autoSize: true});
	
	$('#form_account_account_birthday').datepick({yearRange: '-90:+10', alignment: "left"});
	$('#form_expert_birthday').datepick({yearRange: '-90:+10', alignment: "left"});
});