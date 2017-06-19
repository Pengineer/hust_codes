/**
 * @author fengzq
 */
define(function(require, exports, module) {
	require("validate");
	var datepick = require('datepick-init');
	require("uploadify");
	require("uploadify-ext");
	require("javascript/portal/recruit/share");
	/**/
	/*申请职位流程控制*/
	exports.init = function() {
		datepick.init();
		var isIdCard = true;
		var isEmail = true;
		$.validator.addMethod("idCard", function(value, element) {
			var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
			isIdCard = reg.test(value);
			return this.optional(element) || reg.test(value);
		});
		$.validator.addMethod("emailAddress", function(value, element) {
			var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
			isEmail = reg.test(value);
			return this.optional(element) || reg.test(value);
		});
		
		$("#recruit_form").validate({
			errorElement: "span",
			rules: {
				idCardNumber: {
					required: true,
					idCard: true
				},
				email: {
					required: true,
					emailAddress: true
				},
				name: "required",
				address: "required",
				education: "required",
				birthday: "required",
				gender: "required",
				mobilePhone: "required",
				major: "required"
			},
			messages: {
				idCardNumber: {
					required: "身份证号不能为空",
					idCard: "身份证输入不合法"
				},
				email: {
					required: "邮箱不能为空",
					emailAddress: "邮箱输入不合法"
				},
			},
			errorLabelContainer: $("#recruit_form div.error"),
			errorPlacement: function(error, element) {
				error.appendTo(element.parent().parent());
			}
		});

		
		$(".nextBtn").click(function() {
				$.ajax({
					url: "portal/recruit/getInfo.action",
					data: "jobId=" + $("#jobId").val() + "&idCardNumber=" + $("#idCardNumber").val() + "&email=" + $("#email").val(),
					type: "post",
					dataType: "json",
					success: function(result) {
						if (result.errorInfo != null) {
							$(".errorInfo > p").html(result.errorInfo);
						} else if (result.applicantInfo != 0&&isIdCard&&isEmail) {
							if (result.applicantType != 1) {
								var birthday = result.birthday.substring(5, 7) + "/" + result.birthday.substring(8, 10) + "/" + result.birthday.substring(0, 4);
								$("#name").val(result.name);
								$("#genderData").val(result.gender).html(result.gender);
								$("#ethnicData").val(result.ethnic).html(result.ethnic);
								$("#birthplace").val(result.birthPlace);
								$("#birthday").val(birthday);
								$("#membershipData").val(result.membership).html(result.membership);
								$("#mobilePhone").val(result.mobilePhone);
								$("#qq").val(result.qq);
								$("#major").val(result.major);
								$("#address").val(result.address);
								$("#educationData").val(result.education).html(result.education);
								$("div#recruit_apply_login").hide();
								$("div#recruit_apply_selfInfo").show();
							} else {
								$(".errorInfo>p").html("<p>您已申请此职位，请在我的职位信息中查看详情！</p>");
							}
						} else if (result.applicantInfo != 1&&isIdCard&&isEmail) {
							$("div#recruit_apply_login").hide();
							$("div#recruit_apply_selfInfo").show();
						}
					}
				});
		});
		$(".returnSec").click(function() {
			$("div#recruit_apply_selfInfo").hide();
			$("div#recruit_apply_login").show();
		});

	/*	$("#birthday").datepick({
			alignment: "left"
		});*/
		
		$("#photo_applicant_add").uploadifyExt({
			uploadLimitExt: 1, //文件上传个数的限制
			fileSizeLimit: '1MB', //文件上传大小的限制
			fileTypeDesc: '附件', //可以不用管
			fileTypeExts: '*.jpeg; *.jpg; *.png',
			buttonClass: "upload"
		});

		$("#file_applicant_add").uploadifyExt({
			uploadLimitExt: 1, //文件上传个数的限制
			fileSizeLimit: '20MB', //文件上传大小的限制
			fileTypeDesc: '附件', //可以不用管
			fileTypeExts: '*.zip;*.rar;*.doc;*.docx;*.pdf',
			buttonClass: "upload"
		});

	}


})