// ========================================================================
// 文件名：jquery.datepick.self.js
//
// 文件说明：
//     本文件主要实现各模块中页面的日期选择器，包括全选、输入框的动态显示、
// 
// 历史记录：
// 2010-01-28  周中坚  创建文件，完成基本功能。
// 2010-02-25  周中坚  整合文件，补充内容。
// 2014-04-04  徐江伟  补充内容，并设置当日期为空时显示“不限”
// ========================================================================

// ==============================================================
// 函数描述：定义了日期选择器的对齐方式，日期显示格式，自动大小。
// 通过输入框id来选择js事件的侦听。
// 返回值：无
// ==============================================================
$(document).ready(function(){
	$('#datepicks').datepick({yearRange: '-90:+10', alignment: "left"});
	$('#datepicke').datepick({yearRange: '-90:+10', alignment: "left"});
	$('#datepickm').datepick({yearRange: '-90:+10', alignment: "left"});
	$('#datepickr').datepick({yearRange: '-90:+10', alignment: "left"});
	$('#datepick').datepick({yearRange: '-90:+10', defaultDate: new Date(2002, 1-1, 01), alignment: "left", autoSize: true});
	$('#birthday').datepick({yearRange: '-90:+10', defaultDate: new Date(1980, 1-1, 01), alignment: "left", dateFormat:'yy-mm-dd'});
	$('#form_user_user_birthday').datepick({yearRange: '-90:+10', alignment: "left"});
	$('#form_user_inputAlumni_alumni_graduateTime').datepick({yearRange: '-80:+20', alignment: "left"});
	$('#form_user_register3_edus_0__stime').datepick({yearRange: '-90:+10', alignment: "left"});
	$('#form_user_register3_edus_0__etime').datepick({yearRange: '-90:+10', alignment: "left"});
	$('#form_user_register3_edus_1__stime').datepick({yearRange: '-90:+10', alignment: "left"});
	$('#form_user_register3_edus_1__etime').datepick({yearRange: '-90:+10', alignment: "left"});
	$('#form_user_register3_edus_2__stime').datepick({yearRange: '-90:+10', alignment: "left"});
	$('#form_user_register3_edus_2__etime').datepick({yearRange: '-90:+10', alignment: "left"});
	$('#form_user_register3_edus_3__stime').datepick({yearRange: '-90:+10', alignment: "left"});
	$('#form_user_register3_edus_3__etime').datepick({yearRange: '-90:+10', alignment: "left"});
	$('#form_user_register3_edus_4__stime').datepick({yearRange: '-90:+10', alignment: "left"});
	$('#form_user_register3_edus_4__etime').datepick({yearRange: '-90:+10', alignment: "left"});
	$('#form_user_register3_edus_5__stime').datepick({yearRange: '-90:+10', alignment: "left"});
	$('#form_user_register3_edus_5__etime').datepick({yearRange: '-90:+10', alignment: "left"});
	$('#form_user_register3_edus_6__stime').datepick({yearRange: '-90:+10', alignment: "left"});
	$('#form_user_register3_edus_6__etime').datepick({yearRange: '-90:+10', alignment: "left"});
	$('#form_user_register3_edus_7__stime').datepick({yearRange: '-90:+10', alignment: "left"});
	$('#form_user_register3_edus_7__etime').datepick({yearRange: '-90:+10', alignment: "left"});
	$('input[name="expert.birthday"]').datepick({yearRange: '-90:+10', alignment: "left"});
	$('input[name="reviewAuditDate"]').datepick({yearRange: '-90:+10', alignment: "left"});//申报评审审核时间
	$('input[name="midDate"]').datepick({yearRange: '-90:+10', alignment: "left"});//中检审核时间
	$('input[name="varAuditDate"]').datepick({yearRange: '-90:+10', alignment: "left"});//变更审核时间
	$('input[name="ministryAuditDate"]').datepick({yearRange: '-90:+10', alignment: "left"});//结项部级审核时间
	$('input[name="finalAuditDate"]').datepick({yearRange: '-90:+10', alignment: "left"});//结项最终审核时间
	$('input[name="newDate1"]').datepick({yearRange: '-90:+10', alignment: "left"});//变更录入结果延期时间
	$('input[name="varDate"]').datepick({yearRange: '-90:+10', alignment: "left"});//变更录入结果时间
	$('input[name="generalReviewerImportedStartDate.value"],input[name="generalReviewerImportedEndDate.value"]').datepick({
		dateFormat:'yy-mm-dd',
		yearRange: '-90:+10', 
		alignment: "left"
	}).change(function(){
		if("" == $(this).val()){
			$(this).val("不限");
		}
	});
	$('input[name="instpReviewerImportedStartDate.value"],input[name="instpReviewerImportedEndDate.value"]').datepick({
		dateFormat:'yy-mm-dd',
		yearRange: '-90:+10', 
		alignment: "left"
	}).change(function(){
		if("" == $(this).val()){
			$(this).val("不限");
		}
	});
	$('input[name="generalReviewerBirthdayStartDate.value"],input[name="generalReviewerBirthdayEndDate.value"]').datepick({
		dateFormat:'yy-mm-dd',
		yearRange: '-90:+10', 
		alignment: "left"
	}).change(function(){
		if("" == $(this).val()){
			$(this).val("不限");
		}
	});
	$('input[name="instpReviewerBirthdayStartDate.value"],input[name="instpReviewerBirthdayEndDate.value"]').datepick({
		dateFormat:'yy-mm-dd',
		yearRange: '-90:+10', 
		alignment: "left"
	}).change(function(){
		if("" == $(this).val()){
			$(this).val("不限");
		}
	});
});